import java.util.LinkedList;
import java.util.Queue;

public class TurnManager {
    private Queue<Agent> agentQueue;
    private int currentRound;

    public TurnManager() {
        this.agentQueue = new LinkedList<>();
        this.currentRound = 1;
    }
    public void addAgent(Agent agent) {
        agentQueue.add(agent);
    }
    
    public void enqueue(Agent agent) {
        agentQueue.offer(agent);
    }

    public Agent getCurrentAgent() {
        return agentQueue.peek();
    }

    public void advanceTurn() {
        Agent agent = agentQueue.poll();
        if (agent != null) {
            agentQueue.offer(agent);
        }
        currentRound++;
    }

    public boolean allAgentsFinished() {
        for (Agent a : agentQueue) {
            if (!a.hasReachedGoal()) {
                return false;
            }
        }
        return true;
    }

    public void logTurnSummary(Agent a) {
        System.out.println("Turn " + currentRound + ": Agent " + a.getId() + " at position (" +
            a.getCurrentX() + ", " + a.getCurrentY() + ")");
    }
}
