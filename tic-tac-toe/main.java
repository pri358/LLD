import controllers.GameController;

public class main {

    public static void main(String[] args)
    {
        GameController gameController = GameController.getInstance();
        gameController.startGame();
    }
    
}
