//represents cells in the maze
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
    
    //sets the type of tile
    public void setType(char type) {
        this.type = type;
    }
    
    // set x cordinate of tile
    public void setX(int x) {
        this.x = x;
    }
    
    // set y cordinate of tile
    public void setY(int y) {
        this.y = y;
    }
    
    // tile to string
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