import java.util.Scanner;

/**
 * Main class for running the maze simulation
 */
public class MazeSimulation {
    
    /**
     * Main method to run the maze simulation
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Welcome message
        System.out.println("===== ESCAPE FROM THE MAZE: SIMULATION =====");
        System.out.println("A Turn-Based Simulation Game Using Classical Data Structures");
        System.out.println("=============================================");
        
        // Get simulation parameters
        System.out.println("\nEnter simulation parameters:");
        
        // Maze dimensions
        System.out.print("Maze width (6-20): ");
        int mazeWidth = getIntInput(scanner, 6, 20);
        
        System.out.print("Maze height (6-20): ");
        int mazeHeight = getIntInput(scanner, 6, 20);
        
        // Number of agents
        System.out.print("Number of agents (1-10): ");
        int numAgents = getIntInput(scanner, 1, 10);
        
        // Probabilities
        System.out.print("Wall density (0.0-0.5): ");
        double wallDensity = getDoubleInput(scanner, 0.0, 0.5);
        
        System.out.print("Trap density (0.0-0.2): ");
        double trapDensity = getDoubleInput(scanner, 0.0, 0.2);
        
        System.out.print("Power-up density (0.0-0.1): ");
        double powerUpDensity = getDoubleInput(scanner, 0.0, 0.1);
        
        // Rotating corridors
        System.out.print("Number of rotating rows (0-" + (mazeHeight/2) + "): ");
        int numRotatingRows = getIntInput(scanner, 0, mazeHeight/2);
        
        System.out.print("Number of rotating columns (0-" + (mazeWidth/2) + "): ");
        int numRotatingCols = getIntInput(scanner, 0, mazeWidth/2);
        
        // Max turns
        System.out.print("Maximum number of turns (50-500): ");
        int maxTurns = getIntInput(scanner, 50, 500);
        
        // Output file
        System.out.print("Output log filename: ");
        String logFilename = scanner.nextLine();
        if (logFilename.isEmpty()) {
            logFilename = "maze_simulation_log.txt";
        }
        
        // Initialize and run the simulation
        GameController controller = new GameController(maxTurns);
        controller.initializeGame(mazeWidth, mazeHeight, numAgents, 
                                  wallDensity, trapDensity, powerUpDensity,
                                  numRotatingRows, numRotatingCols);
        
        // Run the simulation
        controller.runSimulation();
        
        // Log results to file
        controller.logGameSummaryToFile(logFilename);
        
        scanner.close();
    }
    
    /**
     * Gets an integer input within the specified range
     * @param scanner Scanner for input
     * @param min Minimum allowed value
     * @param max Maximum allowed value
     * @return Input integer
     */
    private static int getIntInput(Scanner scanner, int min, int max) {
        int value;
        while (true) {
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.print("Please enter a value between " + min + " and " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid integer: ");
            }
        }
    }
    
    /**
     * Gets a double input within the specified range
     * @param scanner Scanner for input
     * @param min Minimum allowed value
     * @param max Maximum allowed value
     * @return Input double
     */
    private static double getDoubleInput(Scanner scanner, double min, double max) {
        double value;
        while (true) {
            try {
                value = Double.parseDouble(scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.print("Please enter a value between " + min + " and " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}