// Handles storing the data for each colony we find
public class Colony {
    private int startRow;
    private int startCol;
    private int colour;
    private int size;

    // Constructor to set up the colony data using 0-based index values
    public Colony(int startRow, int startCol, int colour, int size) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.colour = colour;
        this.size = size;
    }

    // Returns a string formatted to match the 1-indexed layout from the handout
    public String getDetails() {
        // Add 1 to convert raw Java array indexes to human-readable grid coordinates
        String locationStr = (startRow + 1) + ", " + (startCol + 1);
        return String.format("%-10d %-10d %-15s", colour, size, locationStr);
    }
}