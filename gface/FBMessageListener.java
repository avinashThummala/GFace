package gface;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class FBMessageListener implements MessageListener{

    private FBChat ref;

    public FBMessageListener(FBChat ref) 
    {
        this.ref=ref;
    }

    @Override
    public void processMessage(Chat chat, Message message) 
    {       
        if(message==null || message.getBody()==null)
            return;     

        if(ref.chatWindows.containsKey(ref.getPosJabberId(chat.getParticipant())) )
        {
            CMessageFrame cMFrame=(CMessageFrame)ref.chatWindows.get(ref.getPosJabberId(chat.getParticipant()));                    
            cMFrame.addFriendMessage(message.getBody());                    
            cMFrame.setVisible(true);
        }
        else
        {
            CMessageFrame cMFrame=new CMessageFrame((ChatFriendEntry)ref.friends.get(ref.getPosJabberId(chat.getParticipant())), ref.user, ref);
            cMFrame.addFriendMessage(message.getBody());                                
            cMFrame.setVisible(true);

            ref.chatWindows.put(ref.getPosJabberId(chat.getParticipant()), cMFrame);
        }
    }   
}

