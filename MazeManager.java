import java.util.Random;

public class MazeManager {
    private MazeTile[][] mazeGrid;
    private int rows, cols;
    private Random rand;

    public MazeManager(int rows, int cols, Random rand) {
        this.rows = rows;
        this.cols = cols;
        this.rand = rand;
        this.mazeGrid = new MazeTile[rows][cols];
    }

    public void generateMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mazeGrid[i][j] = new MazeTile(i, j, generateRandomTileType(rand));
            }
        }
    }

    private char generateRandomTileType(Random rand) {
        int randomNum = rand.nextInt(100); // 0-99
        if (randomNum < 55) return 'W';    // 55% Wall
        else if (randomNum < 65) return 'T'; // 10% Trap
        else if (randomNum < 75) return 'P'; // 10% Power-Up
        else return 'E'; // 25% Empty
    }

    public MazeTile getTile(int x, int y) {
        return mazeGrid[x][y];
    }

    public void displayMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(mazeGrid[i][j] + " ");
            }
            System.out.println();
        }
    }
    public void placeAgent(Agent agent, int x, int y) {
        agent.setCurrentX(x);
        agent.setCurrentY(y);
        agent.recordMove(x, y);
        MazeTile tile = getTile(x, y);
        if (tile != null) {
            tile.setHasAgent(true);
        }
    }
    
    public void printMaze(char[][] maze) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                MazeTile tile = maze[i][j];
                if (tile.hasAgent()) {
                    System.out.print("A ");
                } else {
                    System.out.print(tile.getType() + " ");
                }
            }
            System.out.println();
        }
        System.out.println(); // Boşluk bırak
    }
    
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
