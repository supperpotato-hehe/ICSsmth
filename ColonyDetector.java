import java.util.ArrayList;

// Handles the grid logic and recursive searching
public class ColonyDetector {
    private int[][] grid;
    private int rows;
    private int cols;
    
    // Accumulator list to save the colonies we find
    private ArrayList<Colony> colonyStorage;

    // Constructor to copy the slide data and setup our list
    public ColonyDetector(int[][] inputGrid) {
        this.rows = inputGrid.length;
        this.cols = inputGrid[0].length;
        this.grid = new int[rows][cols];
        
        // Copy original grid data
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.grid[i][j] = inputGrid[i][j];
            }
        }
        this.colonyStorage = new ArrayList<Colony>();
    }

    // Scans the grid row by row to look for colonies
    public void analyzeSlide() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != 0) {
                    int targetColour = grid[r][c];
                    
                    // Capture the current r,c as the initial discovery location
                    int startR = r;
                    int startC = c;
                    
                    // Call 4-way recursion to get the exact size of the cluster
                    int totalSize = calculateColonySize(r, c, targetColour);
                    
                    // Save the found colony into our accumulator list
                    Colony newlyFoundColony = new Colony(startR, startC, targetColour, totalSize);
                    colonyStorage.add(newlyFoundColony);
                }
            }
        }
    }

    // Recursive method to count matching cells (Strict 4-way horizontal/vertical checking)
    private int calculateColonySize(int r, int c, int targetColour) {
        // Base case: Check if out of bounds
        if (r < 0 || r >= rows || c < 0 || c >= cols) {
            return 0;
        }
        
        // Base case: Check if cell color doesn't match or is already cleared
        if (grid[r][c] != targetColour) {
            return 0;
        }
        
        // Mark cell as visited by setting it to 0
        grid[r][c] = 0;
        
        // Count 1 for this cell and check only 4 orthogonal directions recursively
        return 1 + calculateColonySize(r - 1, c, targetColour)      // Up
                 + calculateColonySize(r + 1, c, targetColour)      // Down
                 + calculateColonySize(r, c - 1, targetColour)      // Left
                 + calculateColonySize(r, c + 1, targetColour);     // Right
    }

    // Prints out the final table summary matching the layout format exactly
    public void printSummaryReport() {
        // Print headings aligned exactly like the assignment sheet prompt
        System.out.printf("%-10s %-10s %-15s%n", "Colour", "Size", "Location");
        
        if (colonyStorage.isEmpty()) {
            System.out.println("No colonies detected.");
        } else {
            for (Colony current : colonyStorage) {
                System.out.println(current.getDetails());
            }
        }
    }
}