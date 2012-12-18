package gface;

//A JPanel which displays a single message in a conversation
//It has the sender'd profile image along with the sent text

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class CMessage extends javax.swing.JPanel {

    public CMessage(String message, BufferedImage img, String name, boolean val) {
        
        initComponents(message, img, name, val);
        setVisible(true);
    }

    private void initComponents(String text, BufferedImage img, String name, boolean val) {
        
        pAvatar = new javax.swing.JPanel();
        avatar = new javax.swing.JLabel();
        mScroll = new javax.swing.JScrollPane();
        message = new javax.swing.JTextArea();        
        
        if(img!=null)
            avatar = new javax.swing.JLabel(new ImageIcon(img));
        else
        {
            if(val) //Friend
                avatar=new javax.swing.JLabel("Frnd");
            else
                avatar=new javax.swing.JLabel("You");  
            
            avatar.setToolTipText(name);
        }
        
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(225, 43));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pAvatar.setPreferredSize(new java.awt.Dimension(40, 40));
        pAvatar.setLayout(new javax.swing.BoxLayout(pAvatar, javax.swing.BoxLayout.X_AXIS));

        pAvatar.add(avatar);

        add(pAvatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 41, 41));

        mScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        message.setColumns(20);
        message.setEditable(false);
        message.setLineWrap(true);
        message.setRows(1);
        message.setText(text);
        mScroll.setViewportView(message);

        add(mScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 0, 183, 43));        
        
    }
    
    private javax.swing.JLabel avatar;
    private javax.swing.JScrollPane mScroll;
    private javax.swing.JTextArea message;
    private javax.swing.JPanel pAvatar;
    
}
