package gface;

import java.awt.image.BufferedImage;

//A JPanel which contains CMessages (which in turn are JPanels)

public class CMessagePanel extends javax.swing.JPanel {

    public CMessagePanel() {
        
        initComponents();
    }

    
    public void addMessage(String mesg, BufferedImage img, String name, boolean val)
    {        
        if(this.getComponentCount()+1 > 5)        
            ((java.awt.GridLayout)this.getLayout()).setRows(this.getComponentCount()+1);
        
        add(new CMessage(mesg, img, name, val));
        repaint();        
    }
    
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridLayout(5, 1));

        setVisible(true);
    }   
    
}
