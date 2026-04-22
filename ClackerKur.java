import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Description: A game of Clacker using GridLayout for the number buttons.
 * This ensures all buttons are perfectly aligned and equal in size.
 */
public class ClackerKur implements ActionListener 
{
    JFrame frame;
    JPanel contentPane;
    JPanel buttonPanel; // New panel specifically for the grid
    
    JButton[] buttons = new JButton[12];
    JButton rollButton, newGameButton;
    JLabel die1Label, die2Label, rollsLabel;

    int rollCount = 0;
    int total = 0;
    int d1Val = 0;
    int d2Val = 0;

    public ClackerKur() 
    {
        frame = new JFrame("Clacker Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main container uses BoxLayout to stack the labels and the grid
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(Color.white);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        // Top section: Labels and score
        die1Label = new JLabel("Die 1: ?");
        die1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        die2Label = new JLabel("Die 2: ?");
        die2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        rollsLabel = new JLabel("Rolls: 0");
        rollsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPane.add(die1Label);
        contentPane.add(die2Label);
        contentPane.add(rollsLabel);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10))); // Gap

        // Middle section: The 12 buttons in a 4-row, 3-column grid
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 3, 5, 5)); // 4 rows, 3 cols, 5px gaps
        buttonPanel.setBackground(Color.white);
        addButtonsToGrid(); 
        contentPane.add(buttonPanel);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10))); // Gap

        // Bottom section: Control buttons
        rollButton = new JButton("Roll");
        rollButton.addActionListener(this);
        rollButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(rollButton);

        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(this);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(newGameButton);

        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * This method uses a loop to create 12 buttons and adds them 
     * directly into the GridLayout panel.
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

    public void actionPerformed(ActionEvent event) 
    {
        String cmd = event.getActionCommand();
        Random rand = new Random();

        if (cmd.equals("Roll")) 
        {
            d1Val = rand.nextInt(6) + 1;
            d2Val = rand.nextInt(6) + 1;
            total = d1Val + d2Val;
            
            // Updating labels and images
            die1Label.setText("Die 1: " + d1Val);
            die2Label.setText("Die 2: " + d2Val);
            die1Label.setIcon(new ImageIcon("die" + d1Val + ".gif"));
            die2Label.setIcon(new ImageIcon("die" + d2Val + ".gif"));
            
            rollCount++;
            rollsLabel.setText("Rolls: " + rollCount);
        } 
        else if (cmd.equals("New Game")) 
        {
            rollCount = 0;
            rollsLabel.setText("Rolls: 0");
            // Reset visibility loop
            for (int i = 0; i < 12; i++) 
            {
                buttons[i].setVisible(true);
            }
        }
        else 
        {
            // Logic for covering buttons based on dice roll
            int clickedNum = Integer.parseInt(cmd);
            if (clickedNum == total || clickedNum == d1Val || clickedNum == d2Val) 
            {
                JButton clickedButton = (JButton)event.getSource();
                clickedButton.setVisible(false);
            }
        }
    }

    private static void runGUI() 
    {
        JFrame.setDefaultLookAndFeelDecorated(false);
        new ClackerKur();
    }

    public static void main(String[] args) 
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() { runGUI(); }
        });
    }
}