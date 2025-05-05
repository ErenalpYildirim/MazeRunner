/**
 * Represents a cell in the maze.
 * Possible types: 'E': Empty, 'W': Wall, 'T': Trap, 'P': Power-up, 'G': Goal
 */
public class MazeTile {
    private int x;
    private int y;
    private char type;
    private boolean hasAgent;
    
    // maze tile constructor
    public MazeTile(int x, int y, char type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.hasAgent = false;
    }
    
    // check if traversable
    public boolean isTraversable() {
        return type != 'W'; // Only walls are not traversable
    }
    
    //get x cordinate of tile
    public int getX() {
        return x;
    }
    
    //get y cordinate of tile
    public int getY() {
        return y;
    }
    
    //get type of tile
    public char getType() {
        return type;
    }
    
    // check if tile has agent
    public boolean hasAgent() {
        return hasAgent;
    }
    
    // set agent presence 
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