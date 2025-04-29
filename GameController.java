import java.util.Random;

public class GameController {
    private MazeManager maze;
    private TurnManager turns;
    private int maxTurns;
    private int turnCount;

    public GameController(MazeManager maze, TurnManager turns, int maxTurns) {
        this.maze = maze;
        this.turns = turns;
        this.maxTurns = maxTurns;
        this.turnCount = 0;
    }

    // Method to determine the next action of the agent
    private String determineNextAction(Agent a) {
        // Example random action for demonstration
        String[] actions = {"UP", "DOWN", "LEFT", "RIGHT"};
        Random rand = new Random();
        return actions[rand.nextInt(actions.length)];  // Randomly pick one action
    }

    public void processAgentAction(Agent a) {
        // Determine next action for agent
        String action = determineNextAction(a);
        a.move(action, maze);  // Move the agent with boundary check

        // Process tile effects like trap or power-ups
        checkTileEffect(a, maze.getTile(a.getCurrentX(), a.getCurrentY()));
    }

    // Handle tile effects like traps or power-ups
    public void checkTileEffect(Agent a, MazeTile tile) {
        switch (tile.getType()) {
            case 'T': // Trap
                a.backtrack();  // Move back on a trap
                System.out.println("Agent " + a.getId() + " triggered a trap!");
                break;
            case 'P': // Power-up
                a.applyPowerUp();
                System.out.println("Agent " + a.getId() + " collected a power-up!");
                break;
            case 'G': // Goal
                a.setHasReachedGoal(true);
                System.out.println("Agent " + a.getId() + " reached the goal!");
                break;
            default:
                break;
        }
    }
    

    public void runSimulation() {
        while (turnCount < maxTurns && !turns.allAgentsFinished()) {
            Agent currentAgent = turns.getCurrentAgent();
            processAgentAction(currentAgent);
            displayMaze();
            turns.advanceTurn();  // Move to the next agent
            turnCount++;

        }

        printFinalStatistics();
    }

    public void initializeGame(int numAgents) {
        for (int i = 0; i < numAgents; i++) {
            Agent a = new Agent(i + 1, 0, 1);  // Example starting position (0,1)
            turns.enqueue(a);
            System.out.println("Agent " + a.getId() + " initialized.");
        }
    }

    public void printFinalStatistics() {
        System.out.println("Game Over!");
        System.out.println("Turns taken: " + turnCount);
        // You can add additional statistics like agent progress or final positions
    }

    public void logGameSummaryToFile(String filename) {
        // Logic to log game summary to a file
    }
}
