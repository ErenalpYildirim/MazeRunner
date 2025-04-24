import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// manage maze structure
public class MazeManager {
    private MazeTile[][] grid;
    private int width;
    private int height;
    private List<Agent> agents;
    private List<CircularLinkedList<MazeTile>> rotatingRows;
    private List<CircularLinkedList<MazeTile>> rotatingColumns;
    private Random random;
    private int rotatingRowIndex;  //  which row is rotating
    private int rotatingColumnIndex;  // which column is rotating
    
    //constructor with given parameters
    public MazeManager(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new MazeTile[height][width];
        this.agents = new ArrayList<>();
        this.rotatingRows = new ArrayList<>();
        this.rotatingColumns = new ArrayList<>();
        this.random = new Random();
        this.rotatingRowIndex = -1;
        this.rotatingColumnIndex = -1;
        
        // Initialize the grid with empty tiles
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new MazeTile(x, y, 'E');
            }
        }
    }
    
    //generate random maze 
    public void generateMaze(double wallDensity, double trapDensity, double powerUpDensity) {
        // First, create a grid of walls
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new MazeTile(x, y, 'W');
            }
        }
        
        // Create a path from start to goal using Prim's algorithm
        boolean[][] visited = new boolean[height][width];
        Stack<int[]> walls = new Stack<>();
        
        // Start from the top-left corner
        int startX = 0;
        int startY = 0;
        visited[startY][startX] = true;
        grid[startY][startX].setType('E');
        
        // Add walls of the starting cell
        addWalls(startX, startY, walls, visited);
        
        while (!walls.isEmpty()) {
            // Pick a random wall
            int randomIndex = random.nextInt(walls.size());
            int[] wall = (int[])walls.peek();
            walls.pop();
            int x = wall[0];
            int y = wall[1];
            
            // Count visited neighbors
            int visitedNeighbors = 0;
            if (x > 0 && visited[y][x-1]) visitedNeighbors++;
            if (x < width-1 && visited[y][x+1]) visitedNeighbors++;
            if (y > 0 && visited[y-1][x]) visitedNeighbors++;
            if (y < height-1 && visited[y+1][x]) visitedNeighbors++;
            
            // If this wall separates an unvisited cell from a visited cell
            if (visitedNeighbors == 1) {
                grid[y][x].setType('E');
                visited[y][x] = true;
                
                // Add walls of the new cell
                addWalls(x, y, walls, visited);
            }
        }
        
        // Place the goal at the bottom-right corner
        int goalX = width - 1;
        int goalY = height - 1;
        grid[goalY][goalX].setType('G');
        
        // Create a path to the goal
        createPathToGoal();
        
        // Add random walls to create dead ends
        addRandomWalls(wallDensity);
        
        // Add traps and power-ups
        addTrapsAndPowerUps(trapDensity, powerUpDensity);
        
        // Set up rotating rows and columns
        setupRotatingRows();
        setupRotatingColumns();
        
        // Verify goal is still accessible
        if (!isGoalAccessible()) {
            clearPathToGoal();
        }
    }
    

    // for unrelated bug fixes such as border emptying
    private void ensureBordersFilled() {
        // Fill top border
        for (int x = 0; x < width; x++) {
            if (grid[0][x].getType() == 'E') {
                // 70% chance to place a wall
                if (random.nextDouble() < 0.7) {
                    grid[0][x].setType('W');
                }
            }
        }
        
        // Fill right border
        for (int y = 0; y < height; y++) {
            if (grid[y][width-1].getType() == 'E') {
                // 70% chance to place a wall
                if (random.nextDouble() < 0.7) {
                    grid[y][width-1].setType('W');
                }
            }
        }
        
        // make sure start and goal are still accessible
        if (!isGoalAccessible()) {
            clearPathToGoal();
        }
    }
    
    private void addWalls(int x, int y, Stack<int[]> walls, boolean[][] visited) {
        // Add unvisited neighboring walls
        if (x > 0 && !visited[y][x-1]) {
            walls.push(new int[]{x-1, y});
        }
        if (x < width-1 && !visited[y][x+1]) {
            walls.push(new int[]{x+1, y});
        }
        if (y > 0 && !visited[y-1][x]) {
            walls.push(new int[]{x, y-1});
        }
        if (y < height-1 && !visited[y+1][x]) {
            walls.push(new int[]{x, y+1});
        }
    }
    
    private void createPathToGoal() {
        // Create a winding path to the goal
        int x = 0, y = 0;
        while (x < width - 1 || y < height - 1) {
            // Randomly choose direction (right or down)
            if (x < width - 1 && y < height - 1) {
                if (random.nextBoolean()) {
                    x++;
                } else {
                    y++;
                }
            } else if (x < width - 1) {
                x++;
            } else {
                y++;
            }
            
            // Clear the path
            grid[y][x].setType('E');
        }
        
        // make sure goal is set
        grid[height-1][width-1].setType('G');
    }
    
    private void addRandomWalls(double wallDensity) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Skip start and goal positions
                if ((x == 0 && y == 0) || (x == width-1 && y == height-1)) {
                    continue;
                }
                
                // Only add walls to empty spaces
                if (grid[y][x].getType() == 'E') {
                    if (random.nextDouble() < wallDensity) {
                        grid[y][x].setType('W');
                    }
                }
            }
        }
    }
    
    private void addTrapsAndPowerUps(double trapDensity, double powerUpDensity) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Skip start and goal positions
                if ((x == 0 && y == 0) || (x == width-1 && y == height-1)) {
                    continue;
                }
                
                // Only add traps and power-ups to empty spaces
                if (grid[y][x].getType() == 'E') {
                    double roll = random.nextDouble();
                    if (roll < trapDensity) {
                        grid[y][x].setType('T');
                    } else if (roll < trapDensity + powerUpDensity) {
                        grid[y][x].setType('P');
                    }
                }
            }
        }
    }
    
    private class Node implements Comparable<Node> {
        int x, y;
        double g, h, f;
        Node parent;
        
        Node(int x, int y, double g, double h, Node parent) {
            this.x = x;
            this.y = y;
            this.g = g;
            this.h = h;
            this.f = g + h;
            this.parent = parent;
        }
        
        @Override
        public int compareTo(Node other) {
            return Double.compare(this.f, other.f);
        }
    }
    
    //for rotating corridors as rows
    private void setupRotatingRows() {
        rotatingRows.clear();
        
        // Select random row to rotate (not the first or last row)
        rotatingRowIndex = 1 + random.nextInt(height - 2);
        CircularLinkedList<MazeTile> rotatingRow = new CircularLinkedList<>();
        
        // Add all tiles from the selected row to the circular list
        for (int x = 0; x < width; x++) {
            rotatingRow.add(grid[rotatingRowIndex][x]);
        }
        rotatingRows.add(rotatingRow);
    }
    
    //for rotating corridors as columns
    private void setupRotatingColumns() {
        rotatingColumns.clear();
        
        // Select random column to rotate (not the first or last column)
        rotatingColumnIndex = 1 + random.nextInt(width - 2);
        CircularLinkedList<MazeTile> rotatingCol = new CircularLinkedList<>();
        
        // Add all tiles from the selected column to the circular list
        for (int y = 0; y < height; y++) {
            rotatingCol.add(grid[y][rotatingColumnIndex]);
        }
        rotatingColumns.add(rotatingCol);
    }
    
    //rotate a specific row clockwise
    public void rotateRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= rotatingRows.size()) {
            throw new IndexOutOfBoundsException("Invalid row index: " + rowIndex);
        }
        
        CircularLinkedList<MazeTile> row = rotatingRows.get(rowIndex);
        row.rotateClockwise();  // Rotate the circular linked list
        
        // Update the grid with the rotated row
        List<MazeTile> rotatedTiles = row.getAllElements();
        for (int x = 0; x < width; x++) {
            MazeTile tile = rotatedTiles.get(x);
            tile.setX(x);
            tile.setY(rotatingRowIndex);
            grid[rotatingRowIndex][x] = tile;
        }
    }
    
    //rotate a specific column clockwise
    public void rotateColumn(int colIndex) {
        if (colIndex < 0 || colIndex >= rotatingColumns.size()) {
            throw new IndexOutOfBoundsException("Invalid column index: " + colIndex);
        }
        
        CircularLinkedList<MazeTile> col = rotatingColumns.get(colIndex);
        col.rotateClockwise();  // Rotate the circular linked list
        
        // Update the grid with the rotated column
        List<MazeTile> rotatedTiles = col.getAllElements();
        for (int y = 0; y < height; y++) {
            MazeTile tile = rotatedTiles.get(y);
            tile.setX(rotatingColumnIndex);
            tile.setY(y);
            grid[y][rotatingColumnIndex] = tile;
        }
    }
    
    //rotate a random corridor ( row or column)
    public String rotateRandomCorridor() {
        boolean rotateRowOrColumn = random.nextBoolean(); //true for row, false for column
        String result;
        
        if (rotateRowOrColumn) {
            int rowIndex = 0; // We only have one rotating row
            rotateRow(rowIndex);
            result = "Rotated row " + rowIndex;
        } else {
            int colIndex = 0; // We only have one rotating column
            rotateColumn(colIndex);
            result = "Rotated column " + colIndex;
        }
        
        return result;
    }
    
    //check if move is valid
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
    
    //get the tile at given coordinates
    public MazeTile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException("Invalid coordinates: (" + x + "," + y + ")");
        }
        return grid[y][x];
    }
    
    //update agent location on the grid
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
    
    //adding agent to the maze
    public void addAgent(Agent agent) {
        agents.add(agent);
        grid[agent.getCurrentY()][agent.getCurrentX()].setHasAgent(true);
    }
    
    //get agents to maze
    public List<Agent> getAgents() {
        return agents;
    }
    
    //get maze dimensions of array
    public int[] getDimensions() {
        return new int[] { width, height };
    }
    
    //prints the maze snapshot
    public String printMazeSnapshot() {
        StringBuilder sb = new StringBuilder();
        
        // Add top border with column markers for rotating column
        sb.append("+");
        for (int x = 0; x < width; x++) {
            if (x == rotatingColumnIndex) {
                sb.append("**");
            } else {
                sb.append("--");
            }
        }
        sb.append("+\n");
        
        // Add maze content
        for (int y = 0; y < height; y++) {
            // Add row marker for rotating row
            if (y == rotatingRowIndex) {
                sb.append("*");
            } else {
                sb.append("|");
            }
            
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
            
            // Add right border with row marker for rotating row
            if (y == rotatingRowIndex) {
                sb.append("*\n");
            } else {
                sb.append("|\n");
            }
        }
        
        // Add bottom border with column markers for rotating column
        sb.append("+");
        for (int x = 0; x < width; x++) {
            if (x == rotatingColumnIndex) {
                sb.append("**");
            } else {
                sb.append("--");
            }
        }
        sb.append("+");
        
        return sb.toString();
    }
    
    private boolean isGoalAccessible() {
        // Use BFS to check if goal is accessible from start
        boolean[][] visited = new boolean[height][width];
        Queue<int[]> queue = new Queue<>();
        queue.enqueue(new int[]{0, 0});
        visited[0][0] = true;
        
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        
        while (!queue.isEmpty()) {
            int[] current = (int[])queue.dequeue();
            int x = current[0];
            int y = current[1];
            
            if (x == width - 1 && y == height - 1) {
                return true;
            }
            
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];
                
                if (newX >= 0 && newX < width && newY >= 0 && newY < height 
                    && !visited[newY][newX] 
                    && grid[newY][newX].isTraversable()) {
                    visited[newY][newX] = true;
                    queue.enqueue(new int[]{newX, newY});
                }
            }
        }
        
        return false;
    }
    
    private void clearPathToGoal() {
        // Clear a direct path from start to goal
        int x = 0, y = 0;
        while (x < width - 1 || y < height - 1) {
            if (x < width - 1) {
                x++;
                grid[y][x].setType('E');
            }
            if (y < height - 1) {
                y++;
                grid[y][x].setType('E');
            }
        }
        // Ensure goal is set
        grid[height-1][width-1].setType('G');
    }
}