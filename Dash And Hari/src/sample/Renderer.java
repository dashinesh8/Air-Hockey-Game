

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Renderer extends JPanel
{

    private static final long serialVersionUID = 1L;   // The serialVersionUID is a universal version identifier for a Serializable class.
                                                         // SerialVersionUID is used for version control of object.

    @Override
    protected void paintComponent(Graphics g)   //The Graphics g only can be seen by subclasses or package member.
    {
        super.paintComponent(g);   // paints the panel's background

        Pong.pong.render((Graphics2D) g);  // transforming react components that can be displayed on the screen.
    }



}

