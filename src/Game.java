import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

public class Game extends Observable {
    private List<Food> foodList = new ArrayList<Food>();
    private List<Snake> snake = new ArrayList<Snake>();
    private Thread mainloop;
    private boolean alive;
    private long delay = 500;
    private int size = 30;
    private long delayFood = 30;
    private long tick;
    private Random random = new Random();

    public Game() {
        setStarterSnake();
        alive = true;
        tick = delayFood;
        mainloop = new Thread() {
            @Override
            public void run() {
                while (alive) {
                    tick();
                    if (tick == delayFood){
                        tick = 0;
                        generateFood();
                    }
                    setChanged();
                    notifyObservers();
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    tick++;

                }
            }
        };
        mainloop.start();
    }

    public void increaseSize() {
        Snake tail = snake.get(snake.size()-1);
        Snake newBody = new Snake(tail.getX(), tail.getY());
        System.out.println(tail.getX());
        System.out.println(tail.getY());
        snake.add(newBody);
    }

    public void checkCollision() {
        int size = snake.size();
        for (int i = 0; i < size; i++) {
            for (int j = i+1; j<size; j++){
                Snake body1 = snake.get(i);
                Snake body2 = snake.get(j);
                if (i == 0 && j == 1){
                    checkHitWall(body1);
                }
                if (body1.getX() == body2.getX() && body1.getY() == body2.getY()){
                    alive = false;
                }
            }
        }
    }

    public void hitFood() {
        Snake head = snake.get(0);
        int size = foodList.size();
        for (int i = 0; i<size; i++){
            Food food = foodList.get(i);
            if (head.getX() == food.getX() && head.getY() == food.getY()){
                foodList.remove(i);
                increaseSize();
                break;
            }
        }
    }

    public void generateFood() {
        int x = random.nextInt(size + 1);
        int y = random.nextInt(size + 1);
        Food food = new Food(x, y);
        foodList.add(food);
    }

    public void checkHitWall(Snake head) {
        if (head.getX() > size || head.getX() < 0 || head.getY() > size || head.getY() < 0) {
            alive = false;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void setStarterSnake() {
        int middle = size/2;
        Snake head = new Snake(middle, middle);
        Snake body = new Snake(middle, middle + 1);
        Snake body2 = new Snake(middle, middle + 2);
        Snake body3 = new Snake(middle, middle + 3);
        head.turnNorth();
        snake.add(head);
        snake.add(body);
        snake.add(body2);
        snake.add(body3);
    }

    public void tick() {
        move();
        checkCollision();
        hitFood();
    }

    public List<Snake> getSnake() {
        return snake;
    }

    public List<Food> getFood() {
        return foodList;
    }

    public int getSize() {
        return size;
    }

    public void move() {
        for(int counter = snake.size() - 1; counter > 0; counter--){
            WObject body = snake.get(counter);
            WObject frontBody = snake.get(counter - 1);
            body.setX(frontBody.getX());
            body.setY(frontBody.getY());
        }
        snake.get(0).move();
    }
}
