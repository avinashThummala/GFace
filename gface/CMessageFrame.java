package gface;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class CMessageFrame extends javax.swing.JFrame {

    CMessageFrame(ChatFriendEntry friend, ChatUserEntry user, FBChat ref) {
        
        this.friend=friend;
        this.user=user;
        this.ref=ref;
        
        initComponents();
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (Exception ex) {
            java.util.logging.Logger.getLogger(CMessageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }         
    }

    private void initComponents() {

        fScroll = new javax.swing.JScrollPane();
        mesgPanel = new gface.CMessagePanel();
        mScroll = new javax.swing.JScrollPane();
        cMesgArea = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();

        setTitle(friend.rEntry.getName());
        setBackground(new java.awt.Color(255, 255, 255));        
        setAlwaysOnTop(true);
        setName("mFrame");
        setResizable(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE); 
        getContentPane().setLayout(new java.awt.FlowLayout());        

        fScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);        
        fScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        fScroll.setViewportView(mesgPanel);
        
        getContentPane().add(fScroll);        

        cMesgArea.setColumns(20);
        cMesgArea.setLineWrap(true);
        cMesgArea.setRows(5);
        mScroll.setViewportView(cMesgArea);

        sendButton.setText("Send");
                
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });
        
        cMesgArea.getActionMap().put(cMesgArea.getInputMap().get(KeyStroke.getKeyStroke("ENTER")), new EnterAction());               
        
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(fScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 217));
        getContentPane().add(mScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 227, 166, 96));
        getContentPane().add(sendButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 263, 57, 23));                
        
        pack();          

    }
    
    public void addFriendMessage(String message)
    {
        mesgPanel.addMessage(message, friend.avatar, friend.rEntry.getName(), true);
        repaint();        
    }

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt)
    {                
        try
        {
            ref.sendMessage(friend.rEntry, cMesgArea.getText());                
        }
        catch(Exception e)
        {
            ref.eFrame.setLocationRelativeTo(this);
            ref.eFrame.setError("Unable to send message");
            return;
        }
        
        mesgPanel.addMessage(cMesgArea.getText(), user.avatar, user.name, false);        
        
        cMesgArea.setText(null);
        this.setVisible(false);
        repaint();
        this.setVisible(true);
    }
    
    public class EnterAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            sendButtonActionPerformed(e);
        }
    }


    private javax.swing.JTextArea cMesgArea;
    private javax.swing.JScrollPane fScroll;
    private javax.swing.JScrollPane mScroll;
    private gface.CMessagePanel mesgPanel;
    private javax.swing.JButton sendButton;
    private ChatFriendEntry friend;
    private ChatUserEntry user; 
    private FBChat ref;    
    
}
