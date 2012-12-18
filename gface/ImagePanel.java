package gface;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

class ImagePanel extends JPanel{
    
    BufferedImage img;
    
    ImagePanel(BufferedImage img)
    {
        this.img=img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);        
    }
    
}
