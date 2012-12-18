package gface;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

public class FriendsPanel extends javax.swing.JPanel {

    BidiMap components = new DualHashBidiMap();
    
    public FriendsPanel() {
        
        initComponents();
    }

    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
        setVisible(false);
    }

    public void addComp(long jid, FriendViewPanel fPanel)
    {
        add(fPanel); 
        components.put(jid, fPanel);
    }    
    
    public void removeComp(long jid)
    {
        this.remove((FriendViewPanel)components.get(jid));
        components.remove(jid);
    }
    
}
