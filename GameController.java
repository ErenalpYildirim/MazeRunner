import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

//for controlling the game
public class GameController {
    private MazeManager maze;
    private TurnManager turns;
    private int maxTurns;
    private int turnCount;
    private Random random;
    private static final String[] DIRECTIONS = {"UP", "DOWN", "LEFT", "RIGHT"};
    
    //for initializing game
    public GameController(int maxTurns) {
        this.maxTurns = maxTurns;
        this.turnCount = 0;
        this.random = new Random();
    }
    
    
    //parameters
    public void initializeGame(int mazeWidth, int mazeHeight, int numAgents, 
                               double wallDensity, double trapDensity, double powerUpDensity
                               ) {
        // initialize the maze
        maze = new MazeManager(mazeWidth, mazeHeight);
        maze.generateMaze(wallDensity, trapDensity, powerUpDensity);
        
        // initialize the turn manager
        turns = new TurnManager();
        
        // initalizing agents
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
    
    // running simulation
    public void runSimulation() {
        System.out.println("Starting simulation...");
        
        while (!turns.allAgentsFinished() && turnCount < maxTurns) {
            // next agents turn
            Agent currentAgent = turns.advanceTurn();
            
            if (currentAgent == null || currentAgent.hasReachedGoal()) {
                continue;
            }
            
            turnCount++;
            
            // process agent action
            String action = processAgentAction(currentAgent);
            
            // Log the action
            turns.logTurnSummary(currentAgent, action, maze.printMazeSnapshot(), turnCount);
            
            // Every turn, rotate a corridor
            String rotationResult = maze.rotateRandomCorridor();
            System.out.println("Turn " + turnCount + ": " + rotationResult);
            System.out.println("Look for '*' to see which corridor was rotated:");
            System.out.println(maze.printMazeSnapshot());
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
                tile.setType('E'); // Remove the trap
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
        System.out.println("--------------------------------------------------------------------");
        System.out.println("| Agent | Moves | Backtracks | Traps | Power-ups Used | Reached Goal |");
        System.out.println("--------------------------------------------------------------------");
        
        double totalMoves = 0;
        double totalBacktracks = 0;
        double totalTraps = 0;
        int totalPowerUpsUsed = 0;
        
        for (Agent agent : maze.getAgents()) {
            totalMoves += agent.getTotalMoves();
            totalBacktracks += agent.getBacktracks();
            totalTraps += agent.getTrapsTriggered();
            totalPowerUpsUsed += agent.getPowerUpsUsed();
            
            System.out.printf("| %5d | %5d | %10d | %5d | %13d | %12s |\n",
                              agent.getId(),
                              agent.getTotalMoves(),
                              agent.getBacktracks(),
                              agent.getTrapsTriggered(),
                              agent.getPowerUpsUsed(),
                              agent.hasReachedGoal() ? "Yes" : "No");
        }
        System.out.println("--------------------------------------------------------------------");
        
        // Print averages
        int numAgents = maze.getAgents().size();
        System.out.printf("\nAverage moves per agent: %.2f\n", totalMoves / numAgents);
        System.out.printf("Average backtracks per agent: %.2f\n", totalBacktracks / numAgents);
        System.out.printf("Average traps triggered per agent: %.2f\n", totalTraps / numAgents);
        
        // Print power-up statistics
        System.out.println("\n===== POWER-UP STATISTICS =====");
        System.out.printf("Total power-ups used: %d\n", totalPowerUpsUsed);
        System.out.printf("Average power-ups per agent: %.2f\n", (double)totalPowerUpsUsed / numAgents);
        System.out.printf("Power-up usage rate: %.2f%%\n", 
                         (double)totalPowerUpsUsed / (double)turnCount * 100);
        
        // Print maze final state
        System.out.println("\nFinal maze state (rotating corridors marked with '*'):");
        System.out.println(maze.printMazeSnapshot());
    }
    
    //logs the game summary to a file
    public void logGameSummaryToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("===== MAZE ESCAPE SIMULATION LOG =====");
            writer.println("Total turns: " + turnCount);
            
            // Write turn logs
            writer.println("\n===== TURN LOGS =====");
            for (String log : turns.getTurnLogs()) {
                writer.println(log);
                // Add a note about rotating corridors
                if (log.contains("Rotated")) {
                    writer.println("(Rotating corridors are marked with '*' on the borders)");
                }
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
            writer.println("| Agent | Moves | Backtracks | Traps | Power-ups Used | Reached Goal |");
            writer.println("----------------------------------");
            
            int totalPowerUpsUsed = 0;
            for (Agent agent : maze.getAgents()) {
                int powerUpsUsed = agent.getPowerUpsUsed();
                totalPowerUpsUsed += powerUpsUsed;
                
                writer.printf("| %5d | %5d | %10d | %5d | %13d | %12s |\n",
                              agent.getId(),
                              agent.getTotalMoves(),
                              agent.getBacktracks(),
                              agent.getTrapsTriggered(),
                              powerUpsUsed,
                              agent.hasReachedGoal() ? "Yes" : "No");
            }
            writer.println("----------------------------------");
            
            // Write power-up statistics
            writer.println("\n===== POWER-UP STATISTICS =====");
            writer.printf("Total power-ups used: %d\n", totalPowerUpsUsed);
            writer.printf("Average power-ups per agent: %.2f\n", (double)totalPowerUpsUsed / maze.getAgents().size());
            writer.printf("Power-up usage rate: %.2f%%\n", 
                         (double)totalPowerUpsUsed / (double)turnCount * 100);
            
            // Write final maze state
            writer.println("\n===== FINAL MAZE STATE =====");
            writer.println(maze.printMazeSnapshot());
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
    
    //shuffles the directions array for random movement selection
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

    public int getTurnCount() {
        return turnCount;
    }
}