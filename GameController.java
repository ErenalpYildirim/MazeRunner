import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Orchestrates the entire maze simulation
 */
public class GameController {
    private MazeManager maze;
    private TurnManager turns;
    private int maxTurns;
    private int turnCount;
    private Random random;
    private static final String[] DIRECTIONS = {"UP", "DOWN", "LEFT", "RIGHT"};
    
    /**
     * Constructor for GameController
     * @param maxTurns Maximum number of turns for the simulation
     */
    public GameController(int maxTurns) {
        this.maxTurns = maxTurns;
        this.turnCount = 0;
        this.random = new Random();
    }
    
    /**
     * Initializes the game with the specified parameters
     * @param mazeWidth Width of the maze
     * @param mazeHeight Height of the maze
     * @param numAgents Number of agents in the simulation
     * @param wallDensity Probability of a tile being a wall
     * @param trapDensity Probability of a tile being a trap
     * @param powerUpDensity Probability of a tile being a power-up
     * @param numRotatingRows Number of rows that can rotate
     * @param numRotatingCols Number of columns that can rotate
     */
    public void initializeGame(int mazeWidth, int mazeHeight, int numAgents, 
                               double wallDensity, double trapDensity, double powerUpDensity,
                               int numRotatingRows, int numRotatingCols) {
        // Initialize the maze
        maze = new MazeManager(mazeWidth, mazeHeight);
        maze.generateMaze(wallDensity, trapDensity, powerUpDensity, numRotatingRows, numRotatingCols);
        
        // Initialize the turn manager
        turns = new TurnManager();
        
        // Create and add agents
        for (int i = 0; i < numAgents; i++) {
            Agent agent = new Agent(i, 0, 0); // All agents start at the top-left corner
            maze.addAgent(agent);
            turns.addAgent(agent);
        }
        
        System.out.println("Game initialized with " + numAgents + " agents.");
        System.out.println("Maze size: " + mazeWidth + "x" + mazeHeight);
        System.out.println("Initial maze state:");
        System.out.println(maze.printMazeSnapshot());
    }
    
    /**
     * Runs the entire simulation until completion or max turns
     */
    public void runSimulation() {
        System.out.println("Starting simulation...");
        
        while (!turns.allAgentsFinished() && turnCount < maxTurns) {
            // Get the next agent
            Agent currentAgent = turns.advanceTurn();
            
            if (currentAgent == null || currentAgent.hasReachedGoal()) {
                continue;
            }
            
            turnCount++;
            
            // Process the agent's action
            String action = processAgentAction(currentAgent);
            
            // Log the turn
            turns.logTurnSummary(currentAgent, action, maze.printMazeSnapshot());
            
            // Every 5 turns, rotate a corridor
            if (turnCount % 5 == 0) {
                String rotationResult = maze.rotateRandomCorridor();
                System.out.println("Turn " + turnCount + ": " + rotationResult);
            }
        }
        
        System.out.println("Simulation completed after " + turnCount + " turns.");
        printFinalStatistics();
    }
    
    /**
     * Processes an agent's action for their turn
     * @param agent The agent whose turn it is
     * @return Description of the action taken
     */
    public String processAgentAction(Agent agent) {
        // If agent has a power-up, 30% chance to use it
        if (agent.hasPowerUp() && random.nextDouble() < 0.3) {
            agent.applyPowerUp();
            return "used a power-up";
        }
        
        // 20% chance to backtrack if possible
        if (random.nextDouble() < 0.2 && agent.getBacktracks() < agent.getTotalMoves() / 2) {
            if (agent.backtrack()) {
                maze.updateAgentLocation(agent, -1, -1); // Force update
                return "backtracked to (" + agent.getCurrentX() + "," + agent.getCurrentY() + ")";
            }
        }
        
        // Try to move in a random direction
        String[] shuffledDirections = shuffleDirections();
        boolean moved = false;
        
        for (String direction : shuffledDirections) {
            if (maze.isValidMove(agent.getCurrentX(), agent.getCurrentY(), direction)) {
                int oldX = agent.getCurrentX();
                int oldY = agent.getCurrentY();
                
                agent.move(direction);
                maze.updateAgentLocation(agent, oldX, oldY);
                
                // Check if the agent landed on a trap
                MazeTile currentTile = maze.getTile(agent.getCurrentX(), agent.getCurrentY());
                if (currentTile.getType() == 'T') {
                    // Trigger trap effect
                    String trapEffect = checkTileEffect(agent, currentTile);
                    return "moved " + direction + " to (" + agent.getCurrentX() + "," + 
                           agent.getCurrentY() + ") and " + trapEffect;
                }
                
                moved = true;
                return "moved " + direction + " to (" + agent.getCurrentX() + "," + agent.getCurrentY() + ")";
            }
        }
        
        if (!moved) {
            return "waited (no valid moves)";
        }
        
        return "took no action";
    }
    
    /**
     * Checks the effect of the tile the agent landed on
     * @param agent The agent
     * @param tile The tile
     * @return Description of the effect
     */
    public String checkTileEffect(Agent agent, MazeTile tile) {
        switch (tile.getType()) {
            case 'T': // Trap
                System.out.println("Agent " + agent.getId() + " triggered a trap!");
                agent.backtrackMultiple(2);
                maze.updateAgentLocation(agent, -1, -1); // Force update
                return "triggered a trap! Backtracked 2 steps to (" + 
                       agent.getCurrentX() + "," + agent.getCurrentY() + ")";
                
            case 'P': // Power-up
                System.out.println("Agent " + agent.getId() + " collected a power-up!");
                agent.setHasPowerUp(true);
                tile.setType('E'); // Remove the power-up
                return "collected a power-up";
                
            case 'G': // Goal
                System.out.println("Agent " + agent.getId() + " reached the goal!");
                agent.setHasReachedGoal(true);
                return "reached the goal!";
                
            default:
                return "moved to an empty space";
        }
    }
    
    /**
     * Prints the final statistics of the simulation
     */
    public void printFinalStatistics() {
        System.out.println("\n===== FINAL STATISTICS =====");
        System.out.println("Total turns executed: " + turnCount);
        
        // Print finished agents
        System.out.println("\nAgents who reached the goal:");
        for (int i = 0; i < turns.getFinishedAgents().size(); i++) {
            Agent agent = turns.getFinishedAgents().get(i);
            System.out.println((i+1) + ". Agent " + agent.getId() + 
                               " (in " + agent.getTotalMoves() + " moves)");
        }
        
        // Print agent statistics
        System.out.println("\nAgent Performance:");
        System.out.println("----------------------------------");
        System.out.println("| Agent | Moves | Backtracks | Traps | Reached Goal |");
        System.out.println("----------------------------------");
        
        double totalMoves = 0;
        double totalBacktracks = 0;
        double totalTraps = 0;
        
        for (Agent agent : maze.getAgents()) {
            totalMoves += agent.getTotalMoves();
            totalBacktracks += agent.getBacktracks();
            totalTraps += agent.getTrapsTriggered();
            
            System.out.printf("| %5d | %5d | %10d | %5d | %12s |\n",
                              agent.getId(),
                              agent.getTotalMoves(),
                              agent.getBacktracks(),
                              agent.getTrapsTriggered(),
                              agent.hasReachedGoal() ? "Yes" : "No");
        }
        System.out.println("----------------------------------");
        
        // Print averages
        int numAgents = maze.getAgents().size();
        System.out.printf("\nAverage moves per agent: %.2f\n", totalMoves / numAgents);
        System.out.printf("Average backtracks per agent: %.2f\n", totalBacktracks / numAgents);
        System.out.printf("Average traps triggered per agent: %.2f\n", totalTraps / numAgents);
        
        // Print maze final state
        System.out.println("\nFinal maze state:");
        System.out.println(maze.printMazeSnapshot());
    }
    
    /**
     * Logs the game summary to a file
     * @param filename Name of the log file
     */
    public void logGameSummaryToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("===== MAZE ESCAPE SIMULATION LOG =====");
            writer.println("Total turns: " + turnCount);
            
            // Write turn logs
            writer.println("\n===== TURN LOGS =====");
            for (String log : turns.getTurnLogs()) {
                writer.println(log);
            }
            
            // Write finished agents
            writer.println("\n===== AGENTS WHO REACHED THE GOAL =====");
            for (int i = 0; i < turns.getFinishedAgents().size(); i++) {
                Agent agent = turns.getFinishedAgents().get(i);
                writer.println((i+1) + ". Agent " + agent.getId() + 
                              " (in " + agent.getTotalMoves() + " moves)");
            }
            
            // Write agent statistics
            writer.println("\n===== AGENT PERFORMANCE =====");
            writer.println("----------------------------------");
            writer.println("| Agent | Moves | Backtracks | Traps | Reached Goal |");
            writer.println("----------------------------------");
            
            for (Agent agent : maze.getAgents()) {
                writer.printf("| %5d | %5d | %10d | %5d | %12s |\n",
                            agent.getId(),
                            agent.getTotalMoves(),
                            agent.getBacktracks(),
                            agent.getTrapsTriggered(),
                            agent.hasReachedGoal() ? "Yes" : "No");
            }
            writer.println("----------------------------------");
            
            // Write final maze state
            writer.println("\n===== FINAL MAZE STATE =====");
            writer.println(maze.printMazeSnapshot());
            
            System.out.println("Game summary logged to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
    
    /**
     * Shuffles the directions array for random movement selection
     * @return Shuffled array of directions
     */
    private String[] shuffleDirections() {
        String[] shuffled = DIRECTIONS.clone();
        
        // Fisher-Yates shuffle
        for (int i = shuffled.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            String temp = shuffled[i];
            shuffled[i] = shuffled[j];
            shuffled[j] = temp;
        }
        
        return shuffled;
    }
}