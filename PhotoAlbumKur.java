import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: A photo album application that cycles through local images.
 * Uses a folder named 'my_photos' to source image files and allows continuous navigation.
 */
public class PhotoAlbumKur implements ActionListener 
{
    JFrame frame;
    JPanel contentPane;
    JLabel label;
    JButton nextButton;
    JButton backButton;
    
    // Additional fields for image logic
    List<String> imagePaths = new ArrayList<>();
    int currentIndex = 0;

    public PhotoAlbumKur() 
    {
        // Create and set up the frame
        frame = new JFrame("PhotoAlbumKur");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a content pane with a BorderLayout and empty borders
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Load images from the local file folder
        loadImagesFromFolder("my_photos");

        // Create and add label that holds the image and is centered
        label = new JLabel("", SwingConstants.CENTER);
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        if (imagePaths.isEmpty()) {
            label.setText("No images found. Please add files to the 'my_photos' folder.");
        } else {
            updateImage();
        }
        contentPane.add(label, BorderLayout.CENTER);

        // Create a panel for the control buttons
        JPanel buttonPanel = new JPanel();
        
        // Create and add back button that is centered
        backButton = new JButton("Back");
        backButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        backButton.setActionCommand("Back");
        backButton.addActionListener(this);
        buttonPanel.add(backButton);

        // Create and add next button that is centered
        nextButton = new JButton("Next");
        nextButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        nextButton.setActionCommand("Next");
        nextButton.addActionListener(this);
        buttonPanel.add(nextButton);

        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Add content pane to frame
        frame.setContentPane(contentPane);

        // Size and then display the frame
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    /**
     * Pre-condition: folderPath must be a valid directory name string.
     * Post-condition: Fills the imagePaths list with absolute paths of found images.
     */
    private void loadImagesFromFolder(String folderPath) 
    {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                String name = file.getName().toLowerCase();
                if (name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg")) {
                    imagePaths.add(file.getAbsolutePath());
                }
            }
        }
    }

    /**
     * Pre-condition: imagePaths list must not be empty.
     * Post-condition: Updates the label with the image at the current index.
     */
    private void updateImage() 
    {
        ImageIcon icon = new ImageIcon(imagePaths.get(currentIndex));
        // Scaling the image to fit the label area
        Image img = icon.getImage().getScaledInstance(700, 450, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
    }

    /**
     * Description: Handle button click action event.
     * Pre-condition: Action event is Next or Back.
     * Post-condition: Clicked button changes the displayed image.
     */
    public void actionPerformed(ActionEvent event) 
    {
        String eventName = event.getActionCommand();

        if (imagePaths.isEmpty()) return;

        if (eventName.equals("Next")) 
        {
            currentIndex = (currentIndex + 1) % imagePaths.size();
        } 
        else if (eventName.equals("Back")) 
        {
            currentIndex = (currentIndex - 1 + imagePaths.size()) % imagePaths.size();
        }
        
        updateImage();
    }

    /**
     * Description: Create and show the GUI.
     * Post-condition: Look and Feel decoration is enabled before instantiation.
     */
    private static void runGUI() 
    {
        JFrame.setDefaultLookAndFeelDecorated(false);
        new PhotoAlbumKur();
    }

    public static void main(String[] args) 
    {
        // Methods that create and show a GUI should be run from an event-dispatching thread.
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                runGUI();
            }
        });
    }
}