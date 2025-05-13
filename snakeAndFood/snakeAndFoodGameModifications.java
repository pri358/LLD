
import java.util.*;

/*
 Requirements: 
after an hour
 */

public class snakeAndFoodGameModifications
{

    public static void main(String[] args)
    {
        GameController snakesAndFood = GameController.getInstance(); 
        snakesAndFood.playGame();
    }
}

// singleton
class GameController
{
    private static GameController gameController;
    Game game; 

    private GameController()
    {
        game = new Game(10,10);
    }

    public static GameController getInstance()
    {
        if(gameController == null) gameController = new GameController();
        return gameController;
    }

    public void playGame()
    {
        boolean playGame = true; 
        System.out.println("Welcome to Snakes and Food !!");

        while(playGame)
        {
            playGame = game.nextMove();
        }
    }
}

class Game 
{
    GameBoard gameboard;
    // todo: have a separate service for fooditems 
    List<FoodItem> foodItems;
    int curFoodIndex; 
    Snake snake;
    int score; 
    // todo: have a staretgy manager to return correct strategy based on some factors 
    ISnakeMoveStrategy snakeMoveStrategy;

    public Game(int width, int height)
    {
        gameboard = new GameBoard(width, height);
        snake = new Snake(new Position(width/2, height/2)); 
        foodItems = new ArrayList<>(); 
        curFoodIndex = 0; 
        score = 0; 
        addFoodItems();
        snakeMoveStrategy = new HumanSnakeMoveStrategy();
    }

    // TODO: factory (different types) and builder (creation) for food 
    private void addFoodItems()
    {
        Random random = new Random();
        foodItems.add(new FoodItem(new Position(2,3),1));
        for(int i=0;i<=3;i++)
        {
            int x = random.nextInt(gameboard.width-2) + 1;
            int y = random.nextInt(gameboard.height-2) + 1;
            foodItems.add(new FoodItem(new Position(x,y),1));
        }
    }

    public boolean nextMove()
    {
        // print game before every move 
        System.out.println(this);
        Position nextPosition = snakeMoveStrategy.getNextSnakeMove(snake.getSnakeHead());
        if(snake.bitesItself(nextPosition) || gameboard.isBoundary(nextPosition))
        {
            System.out.println("GAME OVER!! ------------"); return false;
        }
        // check if position is food 
        if(checkIfFoodItem(nextPosition))
        {
            System.out.println("Caught food");
            snake.increaseLength(1);
            score += 1;
        }
        if(snake.bitesItself(nextPosition))
        {
            System.out.println("GAME OVER!! ------------"); return false;
        }
        snake.move(nextPosition);
        return true;
    }

    private boolean checkIfFoodItem(Position position)
    {
        if(curFoodIndex >= foodItems.size())
        {
            System.out.println("No more food items!! ");
            return false;
        }
        if(position.equals(foodItems.get(curFoodIndex).position))
        {
            curFoodIndex++;
            return true;
        }
        return false;
    }

    @Override
    public String toString()
    {
        //proper rendering to be done
        return "Score: " + score  + " Snake:  " + snake.toString();
    }

}

interface ISnakeMoveStrategy 
{
    public Position getNextSnakeMove(Position curHead);
}

class HumanSnakeMoveStrategy implements ISnakeMoveStrategy
{
    public Position getNextSnakeMove(Position curHead)
    {
        System.out.println("Enter the next move for Snake: Up(U), Down (D), Right (R), Left (L): ");
        Scanner scanner = new Scanner(System.in); 
        String direction = scanner.next(); 
        // TODO:  validate the direction entered 
        switch (direction) 
        {
            case "D": 
                return new Position(curHead.x+1, curHead.y); 
            case "U": 
                return new Position(curHead.x-1, curHead.y); 
            case "L": 
                return new Position(curHead.x, curHead.y -1); 
            case "R":
                return new Position(curHead.x, curHead.y+1);
        }
        return curHead;
    }
}

// based on some logic 
class AISnakeMoveStrategy implements ISnakeMoveStrategy
{
    public Position getNextSnakeMove(Position curHead)
    {
        return curHead; 
    }
}

class GameBoard
{
    int width;
    int height; 

    public GameBoard(int height, int width)
    {
        this.width = width;
        this.height = height;
    }

    public boolean isBoundary(Position position)
    {
        if(position.x == 0 || position.x == width-1 || position.y == 0 || position.y == height-1)
        {
            System.out.println("Snake has hit a wall!! -------");
            return true;
        }
        return false;
    }
}

class Snake
{
    // first -> tail, last -> head
    Deque<Position> snakePositions; 
    Set<Position> curPositions; 
    int length; 

    public Snake(Position intialPos)
    {
        snakePositions = new LinkedList<>();
        curPositions = new HashSet<>();
        length = 1;
        snakePositions.addLast(intialPos);
        curPositions.add(intialPos);
    }

    public boolean bitesItself(Position nextHead)
    {
        if(curPositions.contains(nextHead) && nextHead.equals(snakePositions.getFirst()))
        {
            System.out.println("Snake has bitten itself!! -------");
            return true;
        }
        return false;
    }

    public void move(Position nextPos)
    {
        Position tail = snakePositions.removeFirst(); 
        snakePositions.addLast(nextPos);
        curPositions.remove(tail); curPositions.add(nextPos);
    }

    public void increaseLength(int points)
    {
        length += points;
        // logic for increasing length
    }

    public Position getSnakeHead()
    {
        return snakePositions.getLast();
    }

    public Position getSnakeTail()
    {
        return snakePositions.getFirst();
    }

    @Override 
    public String toString()
    {
        StringBuilder snake = new StringBuilder();
        snake.append("Position: " + getSnakeHead()); snake.append('\n');
        snake.append("Length " + length);
        return snake.toString();
    }
}

class FoodItem
{
    Position position;
    int score; 

    public FoodItem(Position position, int score)
    {
        this.position = position; 
        this.score = score; 
    }

}

class Position
{
    int x; int y; 
    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj) return true; 
        if(getClass() != obj.getClass()) return false;

        Position position = (Position) obj; 
        return this.x == position.x && this.y == position.y;
    }

    @Override
    public String toString()
    {
        return "x: " + x + " y: " + y;
    }
}
