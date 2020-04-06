
import java.awt.Color;
import java.awt.Graphics;

public class Paddle
{

    public int paddleNumber;

    public int x, y, width = 35, height = 200;

    public int score;

    public Paddle(Pong pong, int paddleNumber)    //Creating a paddle
    {
        this.paddleNumber = paddleNumber;

        if (paddleNumber == 1)
        {
            this.x = 0;
        }

        if (paddleNumber == 2)
        {
            this.x = pong.width - width;
        }

        this.y = pong.height / 8 - this.height / 8;
    }

    public void renderp1(Graphics g)   //painting
    {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

    public void renderp2(Graphics g)   //painting
    {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }


    public void move(boolean up)    //moving in y-direction
    {
        int speed = 60;

        if (up)
        {
            if (y - speed > 0)
            {
                y -= speed;
            }
            else
            {
                y = 0;
            }
        }
        else
        {
            if (y + height + speed < Pong.pong.height)
            {
                y += speed;
            }
            else
            {
                y = Pong.pong.height - height;
            }
        }
    }

}
