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
    private int powerUpsUsed;
    
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
        this.powerUpsUsed = 0;
        
        recordMove(startX, startY);
    }
    
    
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
        
        currentX = newX;
        currentY = newY;
        recordMove(newX, newY);
        totalMoves++;
    }
    
    
    public boolean backtrack() {
        if (moveHistory.size() <= 1) {
            return false;
        }
        
        moveHistory.pop();
        
        String previousPos = moveHistory.peek();
        String[] coords = previousPos.split(",");
        
        currentX = Integer.parseInt(coords[0]);
        currentY = Integer.parseInt(coords[1]);
        
        backtracks++;
        return true;
    }
    
 
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
    
    public boolean applyPowerUp() {
        if (hasPowerUp) {
            hasPowerUp = false;
            powerUpsUsed++;
            return true;
        }
        return false;
    }
  
    public void recordMove(int x, int y) {
        moveHistory.push(x + "," + y);
    }
    
    public String getMoveHistoryAsString() {
        return moveHistory.toString(); // Show last 5 moves
    }
    
   
    public int getId() {
        return id;
    }
    

    public int getCurrentX() {
        return currentX;
    }
    
    
    public int getCurrentY() {
        return currentY;
    }
    
    



    public boolean hasReachedGoal() {
        return hasReachedGoal;
    }
    
   
    public void setHasReachedGoal(boolean hasReachedGoal) {
        this.hasReachedGoal = hasReachedGoal;
    }
    
    
    public int getTotalMoves() {
        return totalMoves;
    }
    
   
    public int getBacktracks() {
        return backtracks;
    }



    public boolean hasPowerUp() {
        return hasPowerUp;
    }
    
    
    public void setHasPowerUp(boolean hasPowerUp) {
        this.hasPowerUp = hasPowerUp;
    }
    
    
    public int getTrapsTriggered() {
        return trapsTriggered;
    }
    
    
    public int getMaxStackDepth() {
        return moveHistory.size();
    }
    
    
    public int getPowerUpsUsed() {
        return powerUpsUsed;
    }
    
    
    @Override
    public String toString() {
        return "Agent " + id + " at (" + currentX + "," + currentY + ")";
    }
}