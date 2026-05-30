import java.io.InputStream;
import java.util.Scanner;

// Main application class that handles reading from an embedded resource file
public class DetectColonies2Kur {

    public static void main(String[] args) {
        // Define grid dimensions (6 rows, 8 columns)
        int totalRows = 6;
        int totalCols = 8;
        int[][] dynamicGrid = new int[totalRows][totalCols];

        // Wrap file operations in a try-catch block to handle errors safely
        try {
            // BULLETPROOF: Looks for slide.txt in the exact same location as the compiled class files
            InputStream fileStream = DetectColonies2Kur.class.getResourceAsStream("slide.txt");
            
            // Safety check in case the file wasn't bundled correctly
            if (fileStream == null) {
                throw new Exception("slide.txt could not be found next to your compiled Java classes.");
            }
            
            Scanner fileReader = new Scanner(fileStream);
            int currentRow = 0;

            // Read the file line-by-line until it reaches the end
            while (fileReader.hasNextLine() && currentRow < totalRows) {
                String line = fileReader.nextLine();
                
                // Parse each character digit of the string line into our 2D array
                for (int currentCol = 0; currentCol < totalCols; currentCol++) {
                    char digitChar = line.charAt(currentCol);
                    dynamicGrid[currentRow][currentCol] = Character.getNumericValue(digitChar);
                }
                currentRow++;
            }
            
            // Close the stream resource
            fileReader.close();

            // Pass our dynamically loaded file grid data into our detector system
            ColonyDetector detector = new ColonyDetector(dynamicGrid);
            detector.analyzeSlide();
            detector.printSummaryReport();

        } catch (Exception e) {
            // Graceful error catch if the file cannot be accessed
            System.out.println("Error reading the slide file: " + e.getMessage());
        }
    }
}