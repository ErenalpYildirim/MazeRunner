/**
 * Represents an autonomous player in the maze
 */
public class Agent {
    private int id;
    private int currentX;
    private int currentY;
    private Stack<String> moveHistory;
    private boolean hasReachedGoal;
    private int totalMoves;
    private int backtracks;
    private boolean hasPowerUp;
    private int trapsTriggered;
    
    /**
     * Constructor for Agent
     * @param id Unique identifier
     * @param startX Starting X-coordinate
     * @param startY Starting Y-coordinate
     */
    public Agent(int id, int startX, int startY) {
        this.id = id;
        this.currentX = startX;
        this.currentY = startY;
        this.moveHistory = new Stack<>();
        this.hasReachedGoal = false;
        this.totalMoves = 0;
        this.backtracks = 0;
        this.hasPowerUp = false;
        this.trapsTriggered = 0;
        
        // Record starting position
        recordMove(startX, startY);
    }
    
    /**
     * Moves the agent in the specified direction
     * @param direction Direction to move ("UP", "DOWN", "LEFT", "RIGHT")
     */
    public void move(String direction) {
        int newX = currentX;
        int newY = currentY;
        
        switch (direction) {
            case "UP":
                newY--;
                break;
            case "DOWN":
                newY++;
                break;
            case "LEFT":
                newX--;
                break;
            case "RIGHT":
                newX++;
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
        
        // Update position and record move
        currentX = newX;
        currentY = newY;
        recordMove(newX, newY);
        totalMoves++;
    }
    
    /**
     * Backtracks the agent by undoing the last move
     * @return true if backtracking was successful, false if history is empty
     */
    public boolean backtrack() {
        if (moveHistory.size() <= 1) {
            // Cannot backtrack beyond the starting position
            return false;
        }
        
        // Remove the current position
        moveHistory.pop();
        
        // Get the previous position
        String previousPos = moveHistory.peek();
        String[] coords = previousPos.split(",");
        
        // Update the current position
        currentX = Integer.parseInt(coords[0]);
        currentY = Integer.parseInt(coords[1]);
        
        backtracks++;
        return true;
    }
    
    /**
     * Forces the agent to backtrack multiple steps (e.g., when triggered by a trap)
     * @param steps Number of steps to backtrack
     * @return true if all steps were backtracked, false otherwise
     */
    public boolean backtrackMultiple(int steps) {
        boolean success = true;
        for (int i = 0; i < steps; i++) {
            if (!backtrack()) {
                success = false;
                break;
            }
        }
        trapsTriggered++;
        return success;
    }
    
    /**
     * Applies a power-up effect (if available)
     * @return true if power-up was used, false if none available
     */
    public boolean applyPowerUp() {
        if (hasPowerUp) {
            
            hasPowerUp = false;
            return true;
        }
        return false;
    }
    
    /**
     * Records a position to the move history
     * @param x X-coordinate
     * @param y Y-coordinate
     */
    public void recordMove(int x, int y) {
        moveHistory.push(x + "," + y);
    }
    
    /**
     * Gets a string representation of the move history
     * @return Move history as a string
     */
    public String getMoveHistoryAsString() {
        return moveHistory.toString(5); // Show last 5 moves
    }
    
    /**
     * Gets the agent's ID
     * @return Agent ID
     */
    public int getId() {
        return id;
    }
    
    /**
     * Gets the agent's current X-coordinate
     * @return Current X-coordinate
     */
    public int getCurrentX() {
        return currentX;
    }
    
    /**
     * Gets the agent's current Y-coordinate
     * @return Current Y-coordinate
     */
    public int getCurrentY() {
        return currentY;
    }
    
    /**
     * Checks if the agent has reached the goal
     * @return true if the agent has reached the goal, false otherwise
     */
    public boolean hasReachedGoal() {
        return hasReachedGoal;
    }
    
    /**
     * Sets whether the agent has reached the goal
     * @param hasReachedGoal true if the agent has reached the goal, false otherwise
     */
    public void setHasReachedGoal(boolean hasReachedGoal) {
        this.hasReachedGoal = hasReachedGoal;
    }
    
    /**
     * Gets the total number of moves made by the agent
     * @return Total number of moves
     */
    public int getTotalMoves() {
        return totalMoves;
    }
    
    /**
     * Gets the number of times the agent has backtracked
     * @return Number of backtracks
     */
    public int getBacktracks() {
        return backtracks;
    }
    
    /**
     * Checks if the agent has a power-up
     * @return true if the agent has a power-up, false otherwise
     */
    public boolean hasPowerUp() {
        return hasPowerUp;
    }
    
    /**
     * Sets whether the agent has a power-up
     * @param hasPowerUp true if the agent has a power-up, false otherwise
     */
    public void setHasPowerUp(boolean hasPowerUp) {
        this.hasPowerUp = hasPowerUp;
    }
    
    /**
     * Gets the number of traps triggered by the agent
     * @return Number of traps triggered
     */
    public int getTrapsTriggered() {
        return trapsTriggered;
    }
    
    /**
     * Gets the maximum stack depth (history length)
     * @return Stack depth
     */
    public int getMaxStackDepth() {
        return moveHistory.size();
    }
    
    /**
     * String representation of the agent
     * @return String showing agent ID and current position
     */
    @Override
    public String toString() {
        return "Agent " + id + " at (" + currentX + "," + currentY + ")";
    }
}