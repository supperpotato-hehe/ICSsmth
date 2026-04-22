// author: Noel Kurian
// date created: 2026-4-19
// date modified: 2026-04-22
// description: User "rolls" the dice to either cover each number or the total sum of the dice
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ClackerKur implements ActionListener 
{
    JFrame frame;
    JPanel contentPane;
    JPanel buttonPanel; // This panel will hold our 4x3 grid of buttons
    
    // An array is used here to manage all 12 buttons efficiently
    JButton[] buttons = new JButton[12];
    JButton rollButton, newGameButton;
    JLabel die1Label, die2Label, rollsLabel;

    // Variables to track the state of the game
    int rollCount = 0;
    int total = 0;
    int d1Val = 0;
    int d2Val = 0;

    public ClackerKur() 
    {
        frame = new JFrame("Clacker Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // This main panel stacks our game sections vertically
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(Color.white);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        // Display labels for the current dice values and total rolls
        die1Label = new JLabel("Die 1: ?");
        die1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        die2Label = new JLabel("Die 2: ?");
        die2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        rollsLabel = new JLabel("Rolls: 0");
        rollsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPane.add(die1Label);
        contentPane.add(die2Label);
        contentPane.add(rollsLabel);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10))); // Vertical spacer

        // Creating the grid area for our number buttons
        buttonPanel = new JPanel();
        // 4 rows and 3 columns makes exactly 12 slots
        buttonPanel.setLayout(new GridLayout(4, 3, 5, 5)); 
        buttonPanel.setBackground(Color.white);
        addButtonsToGrid(); 
        contentPane.add(buttonPanel);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10))); // Vertical spacer

        // Setup the main 'Roll' button
        rollButton = new JButton("Roll");
        rollButton.addActionListener(this);
        rollButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(rollButton);

        // Setup the 'New Game' button to reset the board
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(this);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(newGameButton);

        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * This method runs a loop 12 times to build our number buttons.
     * Each button is labeled 1-12 and added to the grid panel.
     */
    private void addButtonsToGrid() 
    {
        for (int i = 0; i < 12; i++) 
        {
            buttons[i] = new JButton(String.valueOf(i + 1));
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }
    }

    /**
     * This is the heart of the game logic. It handles dice rolls,
     * resetting the game, and checking if a number can be "covered".
     */
    public void actionPerformed(ActionEvent event) 
    {
        String cmd = event.getActionCommand();
        Random rand = new Random();

        if (cmd.equals("Roll")) 
        {
            // Pick two random numbers between 1 and 6
            d1Val = rand.nextInt(6) + 1;
            d2Val = rand.nextInt(6) + 1;
            total = d1Val + d2Val;
            
            // Update the UI labels and show the corresponding die images
            die1Label.setText("Die 1: " + d1Val);
            die2Label.setText("Die 2: " + d2Val);
            die1Label.setIcon(new ImageIcon("die" + d1Val + ".gif"));
            die2Label.setIcon(new ImageIcon("die" + d2Val + ".gif"));
            
            rollCount++;
            rollsLabel.setText("Rolls: " + rollCount);
        } 
        else if (cmd.equals("New Game")) 
        {
            // Reset the score and make all number buttons visible again
            rollCount = 0;
            rollsLabel.setText("Rolls: 0");
            for (int i = 0; i < 12; i++) 
            {
                buttons[i].setVisible(true);
            }
        }
        else 
        {
            // This part runs when a number button is clicked.
            // We check if the number matches the total sum OR either individual die.
            int clickedNum = Integer.parseInt(cmd);
            if (clickedNum == total || clickedNum == d1Val || clickedNum == d2Val) 
            {
                // Hide the button to represent it being "covered"
                JButton clickedButton = (JButton)event.getSource();
                clickedButton.setVisible(false);
            }
        }
    }

    private static void runGUI() 
    {
        // Turn off default decorations if you want the standard OS window look
        JFrame.setDefaultLookAndFeelDecorated(false);
        new ClackerKur();
    }

    public static void main(String[] args) 
    {
        // Standard Swing practice to run the GUI on the event-dispatching thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() { runGUI(); }
        });
    }
}