import java.util.Random;

public class MazeMain {
    public static void main(String[] args) {
        Random rand = new Random();

        // MazeManager başlat
        MazeManager mazeManager = new MazeManager(8, 8, rand); // 8x8'lik bir labirent
        mazeManager.generateMaze(); // Labirenti oluştur

        // TurnManager başlat
        TurnManager turnManager = new TurnManager();

        // GameController başlat
        GameController gameController = new GameController(mazeManager, turnManager, 100);  // Maksimum 100 tur

        // Oyunu başlat
        gameController.initializeGame(3);  // 3 ajan ile oyuna başla
        gameController.runSimulation();  // Simülasyonu çalıştır

        // Sonuçları yazdır
        gameController.logGameSummaryToFile("game_summary.txt");
    }
}
