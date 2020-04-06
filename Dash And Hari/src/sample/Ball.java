
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball
{

    public int x, y, width = 25, height = 25;  //Drawing attributes

    public int motionX, motionY;

    public Random random;

    private Pong pong;  //pong are hidden from other classes within the package.

    public int amountOfHits;

    public Ball(Pong pong)
    {
        this.pong = pong;  //this refers to the current object.

        this.random = new Random();   //generating random number

        spawn();  //spawn a ball
    }

    public void update(Paddle paddle1, Paddle paddle2)
    {
        int speed = 10;   //speed of both paddle

        this.x += motionX * speed;  //movement up and down of paddle
        this.y += motionY * speed;

        if (this.y + height - motionY > pong.height || this.y + motionY < 0)
        {
            if (this.motionY < 0)
            {
                this.y = 0;
                this.motionY = random.nextInt(4);  //creating random movement of ball
                                                            // after hitting the paddle

                if (motionY == 0)   ////motionY == 0, the ball is not moving in y-direction
                {
                    motionY = 1;   //making the ball move upwards
                }
            }
            else
            {
                this.motionY = -random.nextInt(4);   //creating random movement of ball
                                                             // after hitting the paddle
                this.y = pong.height - height;

                if (motionY == 0)  //motionY == 0, the ball is not moving in y-direction
                {
                    motionY = -1;   //making the ball move downwards
                }
            }
        }

        if (checkCollision(paddle1) == 1)   // to detect when a sprite collides with another
                                             //checkCollision = 1, Bounce    checkCollision = 2, Score
        {
            this.motionX = 1 + (amountOfHits / 5);
            this.motionY = -2 + random.nextInt(4);

            if (motionY == 0)   //motionY == 0, the ball is not moving in y-direction
            {
                motionY = 1;  //making the ball move upwards
            }

            amountOfHits++;
        }
        else if (checkCollision(paddle2) == 1)   // checkCollision is to detect when a sprite collides with another
                                                //checkCollision = 1, Bounce    checkCollision = 2, Score
        {
            this.motionX = -1 - (amountOfHits / 5);
            this.motionY = -2 + random.nextInt(4);

            if (motionY == 0)    //motionY == 0, the ball is not moving in y-direction
            {
                motionY = 1;    //making the ball move upwards
            }

            amountOfHits++;
        }

        if (checkCollision(paddle1) == 2)   // to detect when a sprite collides with another
                                            //check if ball collides with left or right frame program
                                            //checkCollision = 1, Bounce    checkCollision = 2, Score
        {
            paddle2.score++;
            spawn();
            paddle2.height = paddle2.height - 20;
        }
        else if (checkCollision(paddle2) == 2)  // to detect when a sprite collides with another
                                              //check if ball collides with left or right frame program
                                                //checkCollision = 1, Bounce    checkCollision = 2, Score
        {
            paddle1.score++;
            spawn();
            paddle1.height = paddle1.height - 20;
        }
    }

    public void spawn()      //create spawn class that can be used by all classes
    {
        this.amountOfHits = 0;
        this.x = pong.width / 2 - this.width / 2;      //pong ball attributes
        this.y = pong.height / 2 - this.height / 2;

        this.motionY = -2 + random.nextInt(4);  //random movement of ball after the game started

        if (motionY == 0)  //motionY == 0, the ball is not moving in y-direction
        {
            motionY = 1;  //making the ball move upwards
        }

        if (random.nextBoolean())    //random movement of ball either to  right or left
        {
            motionX = 1;   //making the ball move to the right
        }
        else
        {
            motionX = -1;   //making the ball move to the left
        }
    }

    public int checkCollision(Paddle paddle)
    {
        if (this.x < paddle.x + paddle.width && this.x + width > paddle.x && this.y < paddle.y + paddle.height && this.y + height > paddle.y)
        {
            return 1; //bounce
        }
        else if ((paddle.x > x && paddle.paddleNumber == 1) || (paddle.x < x - width && paddle.paddleNumber == 2))
        {
            return 2; //score
        }

        return 0; //nothing
    }

    public void render(Graphics g)     //painting
    {
        g.setColor(Color.BLACK);
        g.fillOval(x, y, width, height);
    }

}
