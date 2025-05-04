import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages the maze structure and game board
 */
public class MazeManager {
    private MazeTile[][] grid;
    private int width;
    private int height;
    private List<Agent> agents;
    private List<CircularLinkedList<MazeTile>> rotatingRows;
    private List<CircularLinkedList<MazeTile>> rotatingColumns;
    private Random random;
    
    /**
     * Constructor for MazeManager
     * @param width Width of the maze
     * @param height Height of the maze
     */
    public MazeManager(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new MazeTile[height][width];
        this.agents = new ArrayList<>();
        this.rotatingRows = new ArrayList<>();
        this.rotatingColumns = new ArrayList<>();
        this.random = new Random();
        
        // Initialize the grid with empty tiles
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new MazeTile(x, y, 'E');
            }
        }
    }
    
    /**
     * Generates a random maze with walls, traps, power-ups, and a goal
     * @param wallDensity Probability of a tile being a wall (0.0 to 1.0)
     * @param trapDensity Probability of a tile being a trap (0.0 to 1.0)
     * @param powerUpDensity Probability of a tile being a power-up (0.0 to 1.0)
     * 
     */
    public void generateMaze(double wallDensity, double trapDensity, double powerUpDensity
                             ) {
        // Place walls, traps, and power-ups randomly
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double roll = random.nextDouble();
                
                // Skip the corners (starting and goal positions)
                if ((x == 0 && y == 0) || (x == width-1 && y == height-1)) {
                    continue;
                }
                
                if (roll < wallDensity) {
                    grid[y][x].setType('W');
                } else if (roll < wallDensity + trapDensity) {
                    grid[y][x].setType('T');
                } else if (roll < wallDensity + trapDensity + powerUpDensity) {
                    grid[y][x].setType('P');
                }
            }
        }
        
        // Place the goal at the bottom-right corner
        grid[height-1][width-1].setType('G');
        
        // Set up rotating rows
        setupRotatingRows();
        
        // Set up rotating columns
        setupRotatingColumns();
        
        // Ensure the maze is solvable
        ensureSolvable();
    }
    
    /**
     * Sets up rotating rows for the maze
     * @param numRows Number of rows that can rotate
     */
    private void setupRotatingRows() {
        int numRows = 1;
        
        // Clear existing rotating rows
        rotatingRows.clear();
        
        // Select random row to rotate
        int row = random.nextInt(height);
        CircularLinkedList<MazeTile> rotatingRow = new CircularLinkedList<>();
        for (int x = 0; x < width; x++) {
            rotatingRow.add(grid[row][x]);
        }
        rotatingRows.add(rotatingRow);
    }
    
    /**
     * Sets up rotating columns for the maze
     * @param numCols Number of columns that can rotate
     */
    private void setupRotatingColumns() {
        int numCols = 1;
        
        // Clear existing rotating columns
        rotatingColumns.clear();
        
        // Select random column to rotate
        int col = random.nextInt(width);
        CircularLinkedList<MazeTile> rotatingCol = new CircularLinkedList<>();
        for (int y = 0; y < height; y++) {
            rotatingCol.add(grid[y][col]);
        }
        rotatingColumns.add(rotatingCol);
    }
    
    /**
     * Ensures the maze is solvable by creating a path from start to goal
     */
    private void ensureSolvable() {
        // Start from the top-left corner
        int x = 0, y = 0;
        
        // Create a simple path: right, down, right, down, etc.
        while (x < width - 1 || y < height - 1) {
            // Clear current position
            grid[y][x].setType('E');
            
            // Alternate between moving right and down
            if (x < width - 1 && (y == 0 || y % 2 == 0)) {
                x++; // Move right
            } else if (y < height - 1) {
                y++; // Move down
            }
        }
        
        // Ensure the goal is clear
        grid[height-1][width-1].setType('G');
    }
    
    /**
     * Rotates a specific row circularly
     * @param rowIndex Index of the row to rotate
     */
    public void rotateRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= rotatingRows.size()) {
            throw new IndexOutOfBoundsException("Invalid row index: " + rowIndex);
        }
        
        CircularLinkedList<MazeTile> row = rotatingRows.get(rowIndex);
        row.rotateClockwise();  // Rotate the circular linked list
        
        // Find the actual row index in the grid
        int actualRowIndex = -1;
        for (int y = 0; y < height; y++) {
            if (grid[y][0].equals(row.get(0))) {
                actualRowIndex = y;
                break;
            }
        }
        
        if (actualRowIndex != -1) {
            for (int x = 0; x < width; x++) {
                grid[actualRowIndex][x] = row.get(x);
            }
        }
    }
    
    /**
     * Rotates a specific column circularly
     * @param colIndex Index of the column to rotate
     */
    public void rotateColumn(int colIndex) {
        if (colIndex < 0 || colIndex >= rotatingColumns.size()) {
            throw new IndexOutOfBoundsException("Invalid column index: " + colIndex);
        }
        
        CircularLinkedList<MazeTile> col = rotatingColumns.get(colIndex);
        col.rotateClockwise();  // Rotate the circular linked list
        
        // Find the actual column index in the grid
        int actualColIndex = -1;
        for (int x = 0; x < width; x++) {
            if (grid[0][x].equals(col.get(0))) {
                actualColIndex = x;
                break;
            }
        }
        
        if (actualColIndex != -1) {
            for (int y = 0; y < height; y++) {
                grid[y][actualColIndex] = col.get(y);
            }
        }
    }
    
    /**
     * Rotates a random corridor (row or column)
     * @return Description of the rotation
     */
    public String rotateRandomCorridor() {
        boolean rotateRowOrColumn = random.nextBoolean(); //true for row, false for column
        int index;
        String result;
        
        if (rotateRowOrColumn ) {//&& !rotatingRows.isEmpty()
            index = random.nextInt(rotatingRows.size());
            rotateRow(index);
            result = "Rotated row " + index;
        } else  {//!rotatingColumns.isEmpty()
            index = random.nextInt(rotatingColumns.size());
            rotateColumn(index);
            result = "Rotated column " + index;}
        // } else {
        //     result = "No corridors to rotate";
        // }
        
        return result;
    }
    
    /**
     * Checks if a move is valid from the given position
     * @param fromX Starting X-coordinate
     * @param fromY Starting Y-coordinate
     * @param direction Direction to move
     * @return true if the move is valid, false otherwise
     */
    public boolean isValidMove(int fromX, int fromY, String direction) {
        int toX = fromX;
        int toY = fromY;
        
        switch (direction) {
            case "UP":
                toY--;
                break;
            case "DOWN":
                toY++;
                break;
            case "LEFT":
                toX--;
                break;
            case "RIGHT":
                toX++;
                break;
            default:
                return false;
        }
        
        // Check if the new position is within bounds
        if (toX < 0 || toX >= width || toY < 0 || toY >= height) {
            return false;
        }
        
        // Check if the new position is traversable
        return grid[toY][toX].isTraversable();
    }
    
    /**
     * Gets the tile at the specified coordinates
     * @param x X-coordinate
     * @param y Y-coordinate
     * @return MazeTile at the given coordinates
     */
    public MazeTile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException("Invalid coordinates: (" + x + "," + y + ")");
        }
        return grid[y][x];
    }
    
    /**
     * Updates the agent's location in the maze
     * @param agent Agent to update
     * @param oldX Previous X-coordinate
     * @param oldY Previous Y-coordinate
     */
    public void updateAgentLocation(Agent agent, int oldX, int oldY) {
        // Remove agent from old position
        if (oldX >= 0 && oldX < width && oldY >= 0 && oldY < height) {
            grid[oldY][oldX].setHasAgent(false);
        }
        
        // Add agent to new position
        int newX = agent.getCurrentX();
        int newY = agent.getCurrentY();
        
        if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
            grid[newY][newX].setHasAgent(true);
            
            // Check if the agent reached the goal
            if (grid[newY][newX].getType() == 'G') {
                agent.setHasReachedGoal(true);
            }
            
            // Check if the agent got a power-up
            if (grid[newY][newX].getType() == 'P') {
                agent.setHasPowerUp(true);
                grid[newY][newX].setType('E'); // Remove the power-up from the maze
            }
        }
    }
    
    /**
     * Adds an agent to the maze
     * @param agent Agent to add
     */
    public void addAgent(Agent agent) {
        agents.add(agent);
        grid[agent.getCurrentY()][agent.getCurrentX()].setHasAgent(true);
    }
    
    /**
     * Gets all agents in the maze
     * @return List of agents
     */
    public List<Agent> getAgents() {
        return agents;
    }
    
    /**
     * Gets the maze dimensions
     * @return Array with [width, height]
     */
    public int[] getDimensions() {
        return new int[] { width, height };
    }
    
    /**
     * Prints a snapshot of the maze
     * @return String representation of the maze
     */
    public String printMazeSnapshot() {
        StringBuilder sb = new StringBuilder();
        
        // Add top border
        sb.append("+");
        for (int x = 0; x < width; x++) {
            sb.append("--");
        }
        sb.append("+\n");
        
        // Add maze content
        for (int y = 0; y < height; y++) {
            sb.append("|");
            for (int x = 0; x < width; x++) {
                MazeTile tile = grid[y][x];
                
                // Check if an agent is on this tile
                boolean hasAgent = false;
                int agentId = -1;
                for (Agent agent : agents) {
                    if (agent.getCurrentX() == x && agent.getCurrentY() == y) {
                        hasAgent = true;
                        agentId = agent.getId();
                        break;
                    }
                }
                
                if (hasAgent) {
                    sb.append(agentId).append(" ");
                } else {
                    switch (tile.getType()) {
                        case 'W':
                            sb.append("# ");
                            break;
                        case 'T':
                            sb.append("T ");
                            break;
                        case 'P':
                            sb.append("P ");
                            break;
                        case 'G':
                            sb.append("G ");
                            break;
                        default:
                            sb.append(". ");
                    }
                }
            }
            sb.append("|\n");
        }
        
        // Add bottom border
        sb.append("+");
        for (int x = 0; x < width; x++) {
            sb.append("--");
        }
        sb.append("+");
        
        return sb.toString();
    }
}