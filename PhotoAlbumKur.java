// author: Noel Kurian
// date created: 2026-04-17
// date modified: 2026-04-19
// description: Allows users to scroll through photos in a photo album
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PhotoAlbumKur implements ActionListener 
{
    JFrame frame;
    JPanel contentPane;
    JLabel label;
    JButton nextButton, backButton;
    
    // This variable keeps track of which photo is currently on the screen
    int currentPhoto = 1;

    public PhotoAlbumKur() 
    {
        // Standard window setup
        frame = new JFrame("PhotoAlbumKur");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setting up the content pane with a white background and vertical layout
        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.setBackground(Color.white);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create the label where the photos will be displayed
        label = new JLabel("");
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        
        // Start the app by loading the very first image
        updateImage(1); 
        contentPane.add(label);

        // Setup the Back button to move through the album in reverse
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        contentPane.add(backButton);

        // Setup the Next button to move forward through the album
        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        nextButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        contentPane.add(nextButton);

        // Finalize the frame display
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * This method handles picking the right file name and resizing it
     * so it fits nicely in the window.
     */
    private void updateImage(int photoNum) 
    {
        String fileName = "";
        
        // Use a switch to map the current number to the actual file on the computer
        switch (photoNum) 
        {
            case 1: fileName = "Image1.jpg"; break;
            case 2: fileName = "Image2.jpg"; break;
            case 3: fileName = "Image3.jpg"; break;
            case 4: fileName = "Image4.jpg"; break;
            case 5: fileName = "image5.jpg"; break;
            case 6: fileName = "image6.jpg"; break;
        }

        // Create an icon from the file name
        ImageIcon icon = new ImageIcon(fileName);
        
        // Check if the image actually exists; if not, it prints a message to the console
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.out.println("Error: Could not find " + fileName);
        }

        // This part shrinks or stretches the image to exactly 500x400 pixels
        Image img = icon.getImage().getScaledInstance(500, 400, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
    }

    /**
     * This method runs whenever a button is clicked.
     * It handles the logic for moving between photos 1 and 6.
     */
    public void actionPerformed(ActionEvent event) 
    {
        String cmd = event.getActionCommand();

        if (cmd.equals("Next")) 
        {
            // If we aren't at the end, go up. If we are at 6, loop back to 1.
            if (currentPhoto < 6) { 
                currentPhoto++; 
            } else { 
                currentPhoto = 1; 
            }
        } 
        else if (cmd.equals("Back")) 
        {
            // If we aren't at the start, go down. If we are at 1, loop back to 6.
            if (currentPhoto > 1) { 
                currentPhoto--; 
            } else { 
                currentPhoto = 6; 
            }
        }
        
        // After updating the number, refresh the label with the new image
        updateImage(currentPhoto);
    }

    // Boilerplate code to get the GUI running
    private static void runGUI() 
    {
        JFrame.setDefaultLookAndFeelDecorated(true);
        new PhotoAlbumKur();
    }

    public static void main(String[] args) 
    {
        // Thread safety for Java Swing apps
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() { runGUI(); }
        });
    }
}