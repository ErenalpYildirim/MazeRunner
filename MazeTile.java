/**
 * Represents a cell in the maze.
 * Possible types: 'E': Empty, 'W': Wall, 'T': Trap, 'P': Power-up, 'G': Goal
 */
public class MazeTile {
    private int x;
    private int y;
    private char type;
    private boolean hasAgent;
    
    /**
     * Constructor for MazeTile
     * @param x X-coordinate
     * @param y Y-coordinate
     * @param type Tile type ('E', 'W', 'T', 'P', 'G')
     */
    public MazeTile(int x, int y, char type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.hasAgent = false;
    }
    
    /**
     * Checks if this tile can be traversed by an agent
     * @return true if the tile is traversable, false otherwise
     */
    public boolean isTraversable() {
        return type != 'W'; // Only walls are not traversable
    }
    
    /**
     * @return X-coordinate of the tile
     */
    public int getX() {
        return x;
    }
    
    /**
     * @return Y-coordinate of the tile
     */
    public int getY() {
        return y;
    }
    
    /**
     * @return Type of the tile
     */
    public char getType() {
        return type;
    }
    
    /**
     * @return true if an agent is on this tile, false otherwise
     */
    public boolean hasAgent() {
        return hasAgent;
    }
    
    /**
     * Sets the agent presence on this tile
     * @param hasAgent true if an agent is on this tile, false otherwise
     */
    public void setHasAgent(boolean hasAgent) {
        this.hasAgent = hasAgent;
    }
    
    /**
     * Sets the type of this tile
     * @param type New tile type
     */
    public void setType(char type) {
        this.type = type;
    }
    
    /**
     * Sets the X-coordinate of this tile
     * @param x New X-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * Sets the Y-coordinate of this tile
     * @param y New Y-coordinate
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * String representation of the tile
     * @return String showing tile type and agent presence
     */
    @Override
    public String toString() {
        if (hasAgent) {
            return "A";
        } else {
            return String.valueOf(type);
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MazeTile other = (MazeTile) obj;
        return x == other.x && y == other.y && type == other.type;
    }
}