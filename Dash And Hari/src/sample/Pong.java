import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

    public class Pong implements ActionListener, KeyListener
    {

        public static Pong pong;  //Method Pong is visible from classes in other packages & not attached to a specific instance.

        public int width = 1000, height = 1000; //original size of the program

        public Renderer renderer;  //Process of generating a photorealistic or non-photorealistic image from a 2D or 3D model. -displaying the game.

        public Paddle player1;  //creating player 1

        public Paddle player2;  //creating player 2

        public Ball ball;  //creating and making the ball to move randomly

        public boolean bot = false, selectingDifficulty;  //option to play w bot

        public boolean w, s, up, down;  //movement of the player

        public int gameStatus = 0, scoreLimit = 7, playerWon; //0 = Menu, 1 = Paused, 2 = Playing, 3 = Over

        public int botDifficulty, botMoves, botCooldown = 0;

        public Random random;  //generating random number that can be used to all classes

        public JFrame jframe;  //interface of the program

        public Pong()
        {
            Timer timer = new Timer(20, this);  //displaying 20 secs late
            random = new Random();  //construct new obj

            jframe = new JFrame("EXTREMEPong");

            renderer = new Renderer();

            jframe.setSize(width + 20, height + 50);  //set frame size
            jframe.setVisible(true);
            jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jframe.add(renderer);
            jframe.addKeyListener(this);  // receiving keyboard events

            timer.start();
        }

        public void start()  //starting the game
        {
            gameStatus = 2;  //playing mode. 0 = Menu, 1 = Paused, 2 = Playing, 3 = Over
            player1 = new Paddle(this, 1);
            player2 = new Paddle(this, 2);
            ball = new Ball(this);  //creating new object of players and ball
        }

        public void update()  //tally marks
        {
            if (player1.score >= scoreLimit)  //scorelimit = 7
            {
                playerWon = 1;
                gameStatus = 3;
            }

            if (player2.score >= scoreLimit)
            {
                playerWon = 2;
                gameStatus = 3;

            }

            if (w)                               //key bindings for P1
            {
                player1.move(true);
            }
            if (s)
            {
                player1.move(false);
            }

            if (!bot)                                //key bindings for P2
            {
                if (up)
                {
                    player2.move(true);
                }
                if (down)
                {
                    player2.move(false);
                }
            }
            else
            {
                if (botCooldown > 0)            //botCooldown = 0
                {
                    botCooldown--;

                    if (botCooldown == 0)     //after bot making a move then it will stop
                    {
                        botMoves = 0;
                    }
                }

                if (botMoves < 10)
                {
                    if (player2.y + player2.height / 2 < ball.y)       //moving the bot paddle to the ball position
                    {
                        player2.move(false);      //moving down
                        botMoves++;                 //x,y = pong ball        //playerx.height = paddle height
                    }

                    if (player2.y + player2.height / 2 > ball.y)
                    {
                        player2.move(true);      //moving up
                        botMoves++;
                    }

                    if (botDifficulty == 0)
                    {
                        botCooldown = 10;
                    }
                    if (botDifficulty == 1)
                    {
                        botCooldown = 8;
                    }
                    if (botDifficulty == 2)
                    {
                        botCooldown = 5;
                    }
                }
            }

            ball.update(player1, player2);  //tally marks
        }

        public void render(Graphics2D g)  //mostly setting drawing attributes
        {

            g.setColor(Color.LIGHT_GRAY );
            g.fillRect(0, 0, width, height);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  //avoid jagged graphics

            if (gameStatus == 0)  //0 = Menu, 1 = Paused, 2 = Playing, 3 = Over
            {
                g.setColor(Color.WHITE);
                g.setFont(new Font("ARIAL", 1, 50));
                g.drawString("PONG", width / 2 - 75, 50);  //setting font and size font
                g.setFont(new Font("GARAMOND", 1, 150));
                g.drawString("EXTREME!!!", width / 6 - 75, 150);  //setting font and size font

                if (!selectingDifficulty)  //playing w humans by pressing space
                {
                    g.setFont(new Font("Arial", 1, 30));

                    g.drawString("Space to play with Player 2", width /2 - 150, height / 2 - 25);
                    g.drawString("      Shift to play with Bot", width / 2 - 200, height / 2 + 25);
                    g.drawString("<< Rules? First to  " + 7 + " >>", width / 4 - 150, height / 4+ 75);
                    g.drawString("<< Just dont play with bot. You will regret. ^_^  >>", width / 4 - 180, height / 4+ 105);
                    g.drawString("<< Oh btw, watch out your paddle.>>", width / 4 - 210, height / 4+ 135);
                }
            }

            if (selectingDifficulty)  //playing w bots by pressing shift
            {
                String string = botDifficulty == 0 ? "Hard" : (botDifficulty == 1 ? "Crushing" : "Brutal");

                g.setFont(new Font("Arial", 1, 30));

                g.drawString("<< Bot Difficulty: " + string + " >>", width / 2 - 180, height / 2 - 25);
                g.drawString("Space to Play", width / 2 - 150, height / 2 + 25);
            }

            if (gameStatus == 1)  //pause section. 0 = Menu, 1 = Paused, 2 = Playing, 3 = Over
            {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", 1, 50));
                g.drawString("PAUSED", width / 2 - 103, height / 2 - 25);
            }

            if (gameStatus == 1 || gameStatus == 2)
            {
                g.setColor(Color.WHITE);

                g.setStroke(new BasicStroke(5f));     //Stroke interface allows a Graphics2D object to obtain a Shape
                                                            // that is the decorated outline, or stylistic representation
                                                            //of the outline, of the specified Shape.

                g.drawLine(width / 2, 0, width / 2, height);  //the divider between p1 and p2

                g.setStroke(new BasicStroke(2f));

                g.drawOval(width / 2 - 150, height / 2 - 150, 300, 300);  //centre circle

                g.setFont(new Font("Arial", 1, 50));

                g.drawString(String.valueOf(player1.score), width / 2 - 180, 50);  //drawString = draw a big string
                                                                                        //use drawstring instead of System.Out.Println

                g.drawString(String.valueOf(player2.score), width / 2 + 150, 50);  //String.valueOf returns "null" String
                                                                                        // when Object is null.

                player1.renderp1(g);
                player2.renderp2(g);
                ball.render(g);    //painting
            }

            if (gameStatus == 3) //Game over. 0 = Menu, 1 = Paused, 2 = Playing, 3 = Over
            {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", 1, 50));

                g.drawString("PONG", width / 2 - 75, 50);

                if (bot && playerWon == 2)
                {
                    g.drawString("There's no way to defeat our bot!", width / 2 - 400, 400);
                }
                else
                {
                    g.drawString("Player " + playerWon + " Wins!", width / 2 - 165, 200);
                }

                g.setFont(new Font("Arial", 1, 30));

                g.drawString("Press Space to Play Again", width / 2 - 185, height / 2 - 25);
                g.drawString("Press ESC for Menu", width / 2 - 140, height / 2 + 25);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e)  //What system do after an action is performed
        {
            if (gameStatus == 2)  //Playing mode. 0 = Menu, 1 = Paused, 2 = Playing, 3 = Over
            {
                update();
            }

            renderer.repaint();  //repaint
        }

        public static void main(String[] args)
        {
            pong = new Pong();
        }  //construct new obj pong

        @Override
        public void keyPressed(KeyEvent e)  //received keyboard input,when the key goes down
        {
            int id = e.getKeyCode();  //Returns the integer keyCode associated with the key in this event

            if (id == KeyEvent.VK_W)    //VK.W is a constant for "W" key
            {
                w = true;
            }
            else if (id == KeyEvent.VK_S)   //VK.S is a constant for "S" key
            {
                s = true;
            }
            else if (id == KeyEvent.VK_UP)
            {
                up = true;
            }
            else if (id == KeyEvent.VK_DOWN)    //VK.DOWN is a constant for "DOWN" key
            {
                down = true;
            }
            else if (id == KeyEvent.VK_RIGHT)    //VK.RIGHT is a constant for "RIGHT" key
            {
                if (selectingDifficulty)
                {
                    if (botDifficulty < 2)
                    {
                        botDifficulty++;
                    }
                    else
                    {
                        botDifficulty = 0;
                    }
                }
                else if (gameStatus == 0)   //Menu. 0 = Menu, 1 = Paused, 2 = Playing, 3 = Over
                {
                    scoreLimit++;
                }
            }
            else if (id == KeyEvent.VK_LEFT)  //VK.LEFT is a constant for "LEFT" key
            {
                if (selectingDifficulty)
                {
                    if (botDifficulty > 0)
                    {
                        botDifficulty--;
                    }
                    else
                    {
                        botDifficulty = 2;
                    }
                }
                else if (gameStatus == 0 && scoreLimit > 1)
                {
                    scoreLimit--;
                }
            }
            else if (id == KeyEvent.VK_ESCAPE && (gameStatus == 2 || gameStatus == 3)) //VK.ESCAPE is a constant for "ESCAPE" key
            {
                gameStatus = 0;  //Menu.  0 = Menu, 1 = Paused, 2 = Playing, 3 = Over
            }
            else if (id == KeyEvent.VK_SHIFT && gameStatus == 0)
            {
                bot = true;
                selectingDifficulty = true;
            }


            else if (id == KeyEvent.VK_SPACE)  ////VK.SPACE is a constant for "SPACE" key
            {
                if (gameStatus == 0 || gameStatus == 3)
                {
                    if (!selectingDifficulty)
                    {
                        bot = false;
                    }
                    else
                    {
                        selectingDifficulty = false;
                    }

                    start();
                }
                else if (gameStatus == 1)  //Paused. 0 = Menu, 1 = Paused, 2 = Playing, 3 = Over
                {
                    gameStatus = 2; //Playing
                }
                else if (gameStatus == 2)  //Playing. 0 = Menu, 1 = Paused, 2 = Playing, 3 = Over
                {
                    gameStatus = 1;  //Paused
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e)   //received keyboard input, when the key comes up
        {
            int id = e.getKeyCode();  ////Returns the integer keyCode associated with the key in this event

            if (id == KeyEvent.VK_W)    //VK.W is a constant for "W" key
            {
                w = false;
            }
            else if (id == KeyEvent.VK_S)     //VK.S is a constant for "S" key
            {
                s = false;
            }
            else if (id == KeyEvent.VK_UP)    //VK.UP is a constant for "UP" key
            {
                up = false;
            }
            else if (id == KeyEvent.VK_DOWN)    //VK.DOWN is a constant for "DOWN" key
            {
                down = false;
            }
        }

        @Override
        public void keyTyped(KeyEvent e)  //received keyboard input,   when the unicode character
                                        // represented by this key is sent by the keyboard to system input.
        {

        }
    }
