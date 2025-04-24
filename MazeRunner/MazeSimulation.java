    import java.util.Scanner;
    //main class for the game
    public class MazeSimulation {
        
        //main method to run the game
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            
            // welcome message
            System.out.println("===== ESCAPE FROM THE MAZE: SIMULATION =====");
            System.out.println("A Turn-Based Simulation Game Using Classical Data Structures");
            System.out.println("=============================================");
            
            // get parameters
            System.out.println("\nEnter simulation parameters:");
            
            // get width and height
            System.out.print("Maze width (6-20): ");
            int mazeWidth = getIntInput(scanner, 6, 20);
            
            System.out.print("Maze height (6-20): ");
            int mazeHeight = getIntInput(scanner, 6, 20);
            
            // get number of agents
            System.out.print("Number of agents (1-10): ");
            int numAgents = getIntInput(scanner, 1, 10);
            
            // get map entity densities
            System.out.print("Wall density (0.0-0.5): ");
            double wallDensity = getDoubleInput(scanner, 0.0, 0.5);
            
            System.out.print("Trap density (0.0-0.2): ");
            double trapDensity = getDoubleInput(scanner, 0.0, 0.2);
            
            System.out.print("Power-up density (0.0-0.1): ");
            double powerUpDensity = getDoubleInput(scanner, 0.0, 0.1);
            
            
            // get max turns
            System.out.print("Maximum number of turns (50-500): ");
            int maxTurns = getIntInput(scanner, 50, 500);
            
            // get output txt log name
            System.out.print("Output log filename: ");
            String logFilename = scanner.nextLine();
            if (logFilename.isEmpty()) {
                logFilename = "maze_simulation_log.txt";
            }
            
            // initalize game controller
            GameController controller = new GameController(maxTurns);
            controller.initializeGame(mazeWidth, mazeHeight, numAgents, 
                                    wallDensity, trapDensity, powerUpDensity
                                    );
            
            // run the simulation
            controller.runSimulation();
            
            // results to log file
            controller.logGameSummaryToFile(logFilename);
            
            scanner.close();
        }
        
        // get integer input within a range
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
        
        //get double input within a range
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