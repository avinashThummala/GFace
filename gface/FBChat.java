package gface;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.packet.VCard;
import org.jivesoftware.smackx.provider.VCardProvider;

//The main file

public class FBChat {
    
    public static final String FB_XMPP_HOST = "chat.facebook.com";
    public static final int FB_XMPP_PORT = 5222;

    private ConnectionConfiguration config;
    private XMPPConnection connection;
    FBMessageListener fbml;        
    private VCard vCard; //Used for obtaining Profile images of friends
    
    ErrorFrame eFrame;
    BidiMap friends = new DualHashBidiMap();
    BidiMap chatWindows = new DualHashBidiMap();    
    FriendsPanel fPanel; //Used for displaying all friends in ChatFrame
    ChatFrame cFrame;
    
    ChatUserEntry user;
    
    public byte[] getAvatar(String id)
    {
        try
        {        
            vCard.load(connection, id);                           
            
            return vCard.getAvatar();
        }
        catch(Exception e)
        {
            return null;
        }
    }
       
    public void sendMessage(final RosterEntry friend, String text) throws XMPPException 
    {
        if (connection != null && connection.isConnected()) 
        {
            ChatManager chatManager = connection.getChatManager();
            Chat chat = chatManager.createChat(friend.getUser(), fbml);
            chat.sendMessage(text);            
        }      
    }    
    
    public long getPosJabberId(String str)
    {
        Long val=Long.valueOf(str.substring(0, str.indexOf('@')))*-1;       
        
        return val.longValue();
    }
        
    public void getFriends() 
    {
        if (connection != null && connection.isConnected()) 
        {                       
            Roster roster = connection.getRoster();
            
            roster.addRosterListener(new FBRosterListener(this, roster));            
            
            fPanel=new FriendsPanel();
            
            for (RosterEntry entry : roster.getEntries()) 
            {
                Presence presence = roster.getPresence(entry.getUser());               
                
                if (presence != null && presence.getType() == Presence.Type.available ) 
                {
                    friends.put( getPosJabberId(entry.getUser()), new ChatFriendEntry(entry, presence.getStatus(), getAvatar(entry.getUser())));
                    fPanel.addComp(getPosJabberId(entry.getUser()), new FriendViewPanel((ChatFriendEntry)friends.get(getPosJabberId(entry.getUser())), this, user));
                }
                
            }
                                 
            cFrame=new ChatFrame(fPanel);
            cFrame.setLocationRelativeTo(null);
                    
            fPanel.setVisible(true);
            cFrame.setVisible(true);
            cFrame.repaint();                        
        }
        
    }       
    
    boolean login(String userName, String password) throws XMPPException
    {
        if( connection != null && connection.isConnected()) 
        {
            connection.login(userName, password);
            
            vCard = new VCard();              
            SmackConfiguration.setPacketReplyTimeout(300000);
            ProviderManager.getInstance().addIQProvider("vCard", "vcard-temp", new VCardProvider());                     
            
            vCard.load(connection);
            user=new ChatUserEntry(vCard.getFirstName(), null, vCard.getAvatar());
            
            return true;
        }       
        
        return false;        
    }    

    public String connect() throws XMPPException {

        config = new ConnectionConfiguration(FB_XMPP_HOST, FB_XMPP_PORT);

        SASLAuthentication.registerSASLMechanism("DIGEST-MD5", CustomSASLDigestMD5Mechanism.class);

        config.setSASLAuthenticationEnabled(true);
        config.setDebuggerEnabled(true);

        connection = new XMPPConnection(config);
        connection.connect();
        
        fbml = new FBMessageListener(this);               
        
        connection.getChatManager().addChatListener(
                new ChatManagerListener() 
                {
                    @Override
                    public void chatCreated(Chat chat, boolean createdLocally) 
                    {
                        if (!createdLocally) {
                            chat.addMessageListener(fbml);
                    }
                }
            }
        );        

        return connection.getConnectionID();
    }    
    
   public void disconnect() 
   {
        if (connection != null && connection.isConnected())
        {
            Presence presence = new Presence(Presence.Type.unavailable);
            presence.setStatus("Offline");
            
            connection.disconnect(presence);           
        }
        
   }    
    
    FBChat()
    {
        eFrame=new ErrorFrame(null);
        eFrame.setVisible(false); 
                              
        FLogin lFrame=new FLogin(this);
        lFrame.setVisible(true);                
        lFrame.setLocationRelativeTo(null);
        eFrame.setLocationRelativeTo(lFrame);        
    }
    
    public static void main(String[] args) {
        
        new FBChat();
    }
    
}
