public class Test {
    public static void main(String[] args) {
        // Create a small maze (5x5) for testing
        MazeManager maze = new MazeManager(5, 5);
        
        // Generate maze with low densities to make it easier to see
        maze.generateMaze(0.2, 0.1, 0.1);
        
        System.out.println("Initial Maze:");
        System.out.println("(Rotating corridors are marked with '*' on the borders)");
        System.out.println(maze.printMazeSnapshot());
        
        // Test multiple rotations
        for (int i = 0; i < 4; i++) {
            System.out.println("\nRotation " + (i + 1) + ":");
            String result = maze.rotateRandomCorridor();
            System.out.println(result);
            System.out.println("Look for '*' to see which corridor was rotated:");
            System.out.println(maze.printMazeSnapshot());
            
            try {
                Thread.sleep(1000); // Wait a second between rotations
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
} 