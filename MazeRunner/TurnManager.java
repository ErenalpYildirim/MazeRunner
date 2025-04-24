import java.util.ArrayList;
import java.util.List;

// control turn order
public class TurnManager {
    private Queue<Agent> agentQueue;
    private int currentRound;
    private List<Agent> finishedAgents;
    private List<String> turnLogs;
    
    //constructor
    public TurnManager() {
        this.agentQueue = new Queue<>();
        this.currentRound = 0;
        this.finishedAgents = new ArrayList<>();
        this.turnLogs = new ArrayList<>();
    }
    
    // add agent to the queue
    public void addAgent(Agent agent) {
        agentQueue.enqueue(agent);
    }
    
    // get current round
    public int getCurrentRound() {
        return currentRound;
    }
    
    // advance turn
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
    
    // get current agent
    public Agent getCurrentAgent() {
        if (agentQueue.isEmpty()) {
            return null;
        }
        return agentQueue.peek();
    }
    
    // check if all agents finished
    public boolean allAgentsFinished() {
        return agentQueue.isEmpty();
    }
    
    // log turn summary each turn
    public void logTurnSummary(Agent agent, String action, String mazeState, int turnCount) {
        StringBuilder log = new StringBuilder();
        log.append("======= Turn ").append(turnCount).append(" =======\n");
        log.append("Agent ").append(agent.getId()).append(" ");
        log.append(action).append("\n");
        log.append("Position: (").append(agent.getCurrentX()).append(",").append(agent.getCurrentY()).append(")\n");
        log.append("Move History: ").append(agent.getMoveHistoryAsString()).append("\n");
        log.append("Maze State:\n").append(mazeState).append("\n");
        log.append("=====================\n");
        
        turnLogs.add(log.toString());
    }
    
    // get the list of finished agents
    public List<Agent> getFinishedAgents() {
        return finishedAgents;
    }
    
    // get the list of turn logs
    public List<String> getTurnLogs() {
        return turnLogs;
    }
    
    // get turn order as string
    public String getTurnOrderAsString() {
        return "Turn Order: " + agentQueue.toString();
    }
}