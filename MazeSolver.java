import java.util.ArrayList;

public class MazeSolver {
    // class responsible for solving a maze using recursive backtracking

    private char[][] maze;
    private int rows;
    private int cols;
    private boolean[][] visited;
    
    // Tracks the collection of valid ordered structural coordinates defining the path
    private ArrayList<String> pathCoordinates;

    /**
     * Pre-condition: Takes a valid 2D char grid along with row and column bounds.
     * Post-condition: Initializes structural tracking arrays and dimensions for the maze canvas.
     */
    public MazeSolver(char[][] inputMaze, int rows, int cols) {
        // constructor initializes maze structure and tracking system
        this.rows = rows;
        this.cols = cols;
        this.maze = inputMaze;
        this.visited = new boolean[rows][cols];
        this.pathCoordinates = new ArrayList<>();
    }

    /**
     * Pre-condition: Maze grid setup is complete.
     * Post-condition: Public wrapper method to safely initialize recursion from (0,0).
     */
    public boolean solve() {
        // starts the recursive search from the starting position
        return findPath(0, 0);
    }

    /**
     * Pre-condition: Takes a current row and column index to evaluate.
     * Post-condition: Core Recursive Backtracking algorithm exploring up, down, left, right vectors.
     */
    private boolean findPath(int r, int c) {
        // recursive function that explores the maze and builds a valid path

        // Base Case 1: Out-of-bounds safety check
        if (r < 0 || r >= rows || c < 0 || c >= cols) {
            return false;
        }

        // Base Case 2: Obstacle collision ('X') or cyclical node re-entry detection
        if (maze[r][c] == 'X' || visited[r][c]) {
            return false;
        }

        // Base Case 3: Target destination discovered ('$')
        if (maze[r][c] == '$') {
            pathCoordinates.add("(" + (r + 1) + "," + (c + 1) + ")");
            return true;
        }

        // State Selection: Register coordinate node as processed
        visited[r][c] = true;
        pathCoordinates.add("(" + (r + 1) + "," + (c + 1) + ")");

        // Directional Tree Exploration: Evaluates orthogonal vectors in prescribed order
        if (findPath(r, c + 1)) return true; // Right
        if (findPath(r + 1, c)) return true; // Down
        if (findPath(r, c - 1)) return true; // Left
        if (findPath(r - 1, c)) return true; // Up

        // Backtrack Step: Remove current cell from path sequence if dead-end encountered
        pathCoordinates.remove(pathCoordinates.size() - 1);
        return false;
    }

    /**
     * Pre-condition: Recursive traversal has completed execution.
     * Post-condition: Computes final status calculations and outputs coordinates in a 6-column formatted table.
     */
    public void printPathReport() {
        // outputs the final solved path in formatted layout

        if (pathCoordinates.isEmpty()) {
            System.out.println("No path is available.");
        } else {
            System.out.print("Path:\t");

            for (int i = 0; i < pathCoordinates.size(); i++) {
                System.out.print(pathCoordinates.get(i) + "   ");

                // formats output into rows of 6 entries
                if ((i + 1) % 6 == 0 && i != pathCoordinates.size() - 1) {
                    System.out.println();
                    System.out.print("\t");
                }
            }
            System.out.println();
        }
    }
}