import java.util.Stack;

public class Agent {
    private int id;
    private int currentX, currentY;
    private Stack<String> moveHistory;
    private boolean hasReachedGoal;
    private int totalMoves;
    private int backtracks;
    private boolean hasPowerUp;

    public Agent(int id, int startX, int startY) {
        this.id = id;
        this.currentX = startX;
        this.currentY = startY;
        this.moveHistory = new Stack<>();
        this.hasReachedGoal = false;
        this.totalMoves = 0;
        this.backtracks = 0;
        this.hasPowerUp = false;
    }

    // Ajanın kimliğini döndüren getter metodu
    public int getId() {
        return this.id;
    }

    // Ajanın hedefe ulaşıp ulaşmadığını ayarlamak için setter metodu
    public void setHasReachedGoal(boolean hasReachedGoal) {
        this.hasReachedGoal = hasReachedGoal;
    }

    // Ajanın hedefe ulaşıp ulaşmadığını kontrol etmek için getter metodu
    public boolean hasReachedGoal() {
        return this.hasReachedGoal;
    }

    // Hareket fonksiyonu (yönü "UP", "DOWN", "LEFT", "RIGHT" olarak alır)
    public void move(String direction, MazeManager mazeManager) {
        int newX = currentX;
        int newY = currentY;
    
        switch (direction) {
            case "UP":
                newX--;
                break;
            case "DOWN":
                newX++;
                break;
            case "LEFT":
                newY--;
                break;
            case "RIGHT":
                newY++;
                break;
            default:
                System.out.println("Invalid direction!");
                return;
        }
    
        // Check bounds (valid grid indices)
        if (newX >= 0 && newX < mazeManager.getRows() && newY >= 0 && newY < mazeManager.getCols()) {
            currentX = newX;
            currentY = newY;
            recordMove(currentX, currentY);
            System.out.println("Agent " + id + " moved to (" + currentX + ", " + currentY + ")");
        } else {
            System.out.println("Agent " + id + " cannot move outside the maze boundaries.");
        }
    }
    public void setCurrentX(int x) {
        this.currentX = x;
    }
    
    public void setCurrentY(int y) {
        this.currentY = y;
    }
    

    // Geri adım atma fonksiyonu
    public void backtrack() {
        if (!moveHistory.isEmpty()) {
            String lastMove = moveHistory.pop();
            String[] coords = lastMove.split(",");
            this.currentX = Integer.parseInt(coords[0]);
            this.currentY = Integer.parseInt(coords[1]);
            this.backtracks++;
        }
    }

    // PowerUp kullanımı (boş bir fonksiyon örneği)
    public void applyPowerUp() {
        this.hasPowerUp = true;
    }

    // Ajanın hareketini kaydeder
    public void recordMove(int x, int y) {
        moveHistory.push(x + "," + y);
    }

    // Ajanın hareket geçmişini string olarak döndürür
    public String getMoveHistoryAsString() {
        return String.join(" -> ", moveHistory);
    }

    // Getters
    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
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
}
