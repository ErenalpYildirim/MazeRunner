import java.util.ArrayList;
import java.util.List;

/**
 * Controls the order in which agents take their turns
 */
public class TurnManager {
    private Queue<Agent> agentQueue;
    private int currentRound;
    private List<Agent> finishedAgents;
    private List<String> turnLogs;
    
    /**
     * Constructor for TurnManager
     */
    public TurnManager() {
        this.agentQueue = new Queue<>();
        this.currentRound = 0;
        this.finishedAgents = new ArrayList<>();
        this.turnLogs = new ArrayList<>();
    }
    
    /**
     * Adds an agent to the turn queue
     * @param agent Agent to add
     */
    public void addAgent(Agent agent) {
        agentQueue.enqueue(agent);
    }
    
    /**
     * Gets the current round number
     * @return Current round
     */
    public int getCurrentRound() {
        return currentRound;
    }
    
    /**
     * Advances to the next turn by rotating the queue
     * @return The agent whose turn it is now
     */
    public Agent advanceTurn() {
        if (agentQueue.isEmpty()) {
            return null;
        }
        
        // Dequeue the next agent
        Agent currentAgent = agentQueue.dequeue();
        
        // If the agent hasn't reached the goal, re-enqueue it
        if (!currentAgent.hasReachedGoal()) {
            agentQueue.enqueue(currentAgent);
        } else if (!finishedAgents.contains(currentAgent)) {
            // Add to finished agents list if not already there
            finishedAgents.add(currentAgent);
        }
        
        // If we've gone through all agents, increment the round counter
        if (agentQueue.size() + finishedAgents.size() == 1) {
            currentRound++;
        }
        
        return currentAgent;
    }
    
    /**
     * Gets the current agent without advancing the turn
     * @return Current agent
     */
    public Agent getCurrentAgent() {
        if (agentQueue.isEmpty()) {
            return null;
        }
        return agentQueue.peek();
    }
    
    /**
     * Checks if all agents have reached the goal
     * @return true if all agents have finished, false otherwise
     */
    public boolean allAgentsFinished() {
        return agentQueue.isEmpty();
    }
    
    /**
     * Logs a summary of the current turn
     * @param agent Agent that just completed their turn
     * @param action Description of the action taken
     * @param mazeState Current state of the maze
     */
    public void logTurnSummary(Agent agent, String action, String mazeState) {
        StringBuilder log = new StringBuilder();
        log.append("======= Turn ").append(currentRound).append(" =======\n");
        log.append("Agent ").append(agent.getId()).append(" ");
        log.append(action).append("\n");
        log.append("Position: (").append(agent.getCurrentX()).append(",").append(agent.getCurrentY()).append(")\n");
        log.append("Move History: ").append(agent.getMoveHistoryAsString()).append("\n");
        log.append("Maze State:\n").append(mazeState).append("\n");
        log.append("=====================\n");
        
        turnLogs.add(log.toString());
    }
    
    /**
     * Gets the list of agents who have reached the goal
     * @return List of finished agents
     */
    public List<Agent> getFinishedAgents() {
        return finishedAgents;
    }
    
    /**
     * Gets the logs of all turns
     * @return List of turn logs
     */
    public List<String> getTurnLogs() {
        return turnLogs;
    }
    
    /**
     * Gets the current turn order as a string
     * @return String representation of the turn queue
     */
    public String getTurnOrderAsString() {
        return "Turn Order: " + agentQueue.toString();
    }
}