import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Main GUI class that displays and runs the FruitCollect! game.
 * 
 * @author yuyingf
 *
 */
public class Game {

    private JFrame frame;
    private JPanel contentPane;
    private JPanel gamePanel;
    private JLabel lblCollected = new JLabel();
    private JLabel lblGenNum = new JLabel();
    private JLabel lblTimer = new JLabel();
    
    private FruitCollectGame game;
    private Map<String, Color> fruits;
    private int secondsToNextGen;
    private static final int GRID_WIDTH_DEFAULT = 30;
    private static final int GRID_HEIGHT_DEFAULT = 30;

    /**
     * Launch the game GUI.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Game window = new Game();
                    window.frame.setVisible(true);
                    window.gamePanel.requestFocusInWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the game GUI.
     */
    public Game() {
        // creates the game
        fruits = new HashMap<String, Color>();
        fruits.put("Apple", Color.RED);
        fruits.put("Banana", Color.YELLOW);
        fruits.put("Peach", Color.PINK);
        game = new FruitCollectGame(fruits);
        game.nextGen();
        
        // initializes the frame
        frame = new JFrame("FruitCollect!");
        frame.setBounds(50, 50, FruitCollectGame.DEFAULT_ORCHARD_WIDTH * GRID_WIDTH_DEFAULT + 30,
                        FruitCollectGame.DEFAULT_ORCHARD_HEIGHT * GRID_HEIGHT_DEFAULT + 130);
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
        JPanel topPanel = new JPanel();
        contentPane.add(topPanel, BorderLayout.NORTH);
        
        secondsToNextGen = FruitCollectGame.DEFAULT_SECONDS_PER_GEN;
        lblTimer.setText(secondsToNextGen + " seconds remaining until next generation");
        
        // a timer that updates the timer label every second
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!game.hasEnded()) {
                    if (secondsToNextGen > 0) {
                        secondsToNextGen--;
                    } else {
                        secondsToNextGen = FruitCollectGame.DEFAULT_SECONDS_PER_GEN;
                    }
                    if (secondsToNextGen == 0) {
                        game.nextGen();
                        lblTimer.setForeground(Color.RED);
                        lblTimer.setText("NEXT GENERATION");
                        lblGenNum.setText("Generation: " + game.getGen() + "/"
                                + FruitCollectGame.DEFAULT_MAX_GEN);
                    } else {
                        lblTimer.setForeground(Color.BLACK);
                        lblTimer.setText(secondsToNextGen
                                        + " seconds remaining until next generation");
                    }
                    frame.repaint();
                    if (game.getGen() > FruitCollectGame.DEFAULT_MAX_GEN) {
                        game.endGame();
                        int optionStatus = JOptionPane.showConfirmDialog(frame, "YOU LOST :(",
                                        "Game Over", JOptionPane.PLAIN_MESSAGE);
                        if (optionStatus == JOptionPane.OK_OPTION) {
                            System.exit(0);
                        }
                    }
                }
            }
        });
        
        JButton btnInstructions = new JButton("Instructions");
        btnInstructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                timer.stop();
                int optionStatus = JOptionPane.showConfirmDialog(frame, 
                                "WELCOME, farmer, to the Magical Orchard! You are sent here "
                                + "to collect \nas many Magical Fruits as possible. You can see "
                                + "yourself as the green \ncircle in the center of the screen, "
                                + "and you can use the arrow keys to \nmove around. "
                                + "An empty circle designates an immature tree with no \nfruit, "
                                + "a filled circle (apple = red, peach = pink, banana = yellow) "
                                + "is a \nmature tree with fruit. Every " 
                                + FruitCollectGame.DEFAULT_SECONDS_PER_GEN
                                + " seconds, the Fairy of the Magical \nOrchard randomly selects "
                                + FruitCollectGame.MAX_MATURE_PER_GEN
                                + " positions in the orchard and give them \nher magic. "
                                + "This will make any trees in these "
                                + FruitCollectGame.MAX_MATURE_PER_GEN
                                + " positions mature. \nHowever, Magical Fruits are perishable! "
                                + "If you don't collect them in \nthese "
                                + FruitCollectGame.DEFAULT_SECONDS_PER_GEN
                                + " seconds (which is a generation), they turn into black bad "
                                + "fruits \nthat are deadly. Sadly, a tree bearing bad fruit will "
                                + "no longer be able to \nrecover even if the Fairy gives them "
                                + "her magic. This is why we need \nyou here to collect the fruits "
                                + "while they are fresh! Your mission is \ncomplete when you "
                                + "successfully collect " + FruitCollectGame.MIN_GOAL_PER_TYPE
                                + " of each type of Magical \nFruits. The Fairy gets tired after "
                                + FruitCollectGame.DEFAULT_MAX_GEN
                                + " generations, so finish your goal \nbefore then! "
                                + "Also, beware of the deep, deep Magical Ponds scattered \nin "
                                + "the orchard. If you fall into one, you will drown no matter "
                                + "how \nexcellent a swimmer you are. Now, with the Fairy's "
                                + "blessing, GOOD \nLUCK!",
                                "Instructions", JOptionPane.PLAIN_MESSAGE);
                if (optionStatus == JOptionPane.OK_OPTION) {
                    gamePanel.requestFocusInWindow();
                    timer.start();
                }
            }
        });
        
        topPanel.add(btnInstructions);
        topPanel.add(lblTimer);
        
        gamePanel = new OrchardPanel(game, GRID_WIDTH_DEFAULT, GRID_HEIGHT_DEFAULT);
        // resizes the orchard grids when the window is resized
        gamePanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent arg0) {
                ((OrchardPanel) gamePanel).setGridWidth(gamePanel.getWidth() / game.getWidth());
                ((OrchardPanel) gamePanel).setGridHeight(gamePanel.getHeight() / game.getHeight());
                frame.repaint();
            }
        });
        // keyboard control with arrow keys
        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!game.hasEnded()) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        game.move(game.getPlayerX() - 1, game.getPlayerY());
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        game.move(game.getPlayerX() + 1, game.getPlayerY());
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        game.move(game.getPlayerX(), game.getPlayerY() + 1);
                    } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                        game.move(game.getPlayerX(), game.getPlayerY() - 1);
                    }
                    String collectionStatus = "";
                    for (String fruit : fruits.keySet()) {
                        collectionStatus += fruit + ": " + game.numCollected(fruit) + "/"
                                        + FruitCollectGame.MIN_GOAL_PER_TYPE + "  ";
                    }
                    lblCollected.setText(collectionStatus);
                    frame.repaint();
                    if (game.hasEnded()) {
                        timer.stop();
                        int optionStatus;
                        if (game.goalAccomplished()) {
                            optionStatus = JOptionPane.showConfirmDialog(frame, "YOU WON :D",
                                            "Game Over", JOptionPane.PLAIN_MESSAGE);
                        } else {
                            optionStatus = JOptionPane.showConfirmDialog(frame, "YOU LOST :(",
                                            "Game Over", JOptionPane.PLAIN_MESSAGE);
                        }
                        if (optionStatus == JOptionPane.OK_OPTION) {
                            System.exit(0);
                        }
                    }
                }
            }
        });
        contentPane.add(gamePanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel();
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        
        String collectionStatus = "";
        for (String fruit : fruits.keySet()) {
            collectionStatus += fruit + ": " + game.numCollected(fruit) + "/"
                            + FruitCollectGame.MIN_GOAL_PER_TYPE + "  ";
        }
        lblCollected.setText(collectionStatus);
        bottomPanel.add(lblCollected);
        
        lblGenNum.setText("Generation: " + game.getGen() + "/" + FruitCollectGame.DEFAULT_MAX_GEN);
        bottomPanel.add(lblGenNum);
        
        timer.start();
    }

}
