public class MazeTile {
    private int x, y;
    private char type; // 'E' = Empty, 'W' = Wall, 'T' = Trap, 'P' = Power-up, 'G' = Goal
    private boolean hasAgent;

    // Constructor
    public MazeTile(int x, int y, char type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.hasAgent = false; // default olarak ajan yok
    }

    // Getter methods
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getType() {
        return type;
    }

    public boolean hasAgent() {
        return hasAgent;
    }

    // Setter methods
    public void setType(char type) {
        this.type = type;
    }

    public void setHasAgent(boolean hasAgent) {
        this.hasAgent = hasAgent;
    }

    // Methods
    public boolean isTraversable() {
        return type != 'W'; // Sadece duvar (W) geçilemez, diğer her şey geçilebilir
    }

    @Override
    public String toString() {
        if (hasAgent) {
            return "A"; // Eğer ajan varsa, ajanı gösterelim
        }
        return String.valueOf(type);
    }
    
}

