import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Gui extends JFrame implements Observer {

    private Game game;
    private Renderer renderer;
    private int size = 500;

    public Gui() {
        game = new Game();
        game.addObserver(this);

        renderer = new Renderer();
        addKeyListener(new Controller());
        setLayout(new BorderLayout());
        add(renderer);

        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    public static void main(String[] args) {
        Gui gui = new Gui();
        gui.start();
    }

    public void start() {
        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    class Renderer extends JPanel {

        public Renderer() {
            setDoubleBuffered(true);
            setPreferredSize(new Dimension(size, size));
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            paintSnake(g);
            paintFood(g);
            if (!game.isAlive()){
                JOptionPane.showMessageDialog(this,"You Lose!", "Message",
                    JOptionPane.WARNING_MESSAGE);
            }
        }

        public void paintFood(Graphics g) {
            int perCell = size/game.getSize();
            List<Food> foods = game.getFood();
            g.setColor(Color.BLACK);
            for(Food food: foods){
                g.fillRect(food.getX() * perCell, food.getY() * perCell, perCell, perCell);
            }
        }

        public void paintSnake(Graphics g) {
            int perCell = size/game.getSize();
            List<Snake> snake = game.getSnake();
            Snake head = snake.get(0);
            g.setColor(Color.lightGray);
            g.fillRect(head.getX() * perCell, head.getY() * perCell, perCell, perCell);
            for (int i = 1; i < snake.size(); i++){
                g.setColor(Color.GREEN);
                Snake body = snake.get(i);
                g.fillRect(body.getX() * perCell, body.getY() * perCell, perCell, perCell);
            }
        }
    }

    class Controller extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            Snake head = game.getSnake().get(0);
            if(e.getKeyCode() == KeyEvent.VK_UP) {
                if(!(head.getDy() > 0)){
                    head.turnNorth();
                }

            } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                if(!(head.getDy() < 0)){
                    head.turnSouth();
                }

            } else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                if(!(head.getDx() > 0)){
                    head.turnWest();
                }

            } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if(!(head.getDx() < 0)){
                    head.turnEast();
                }
            }
        }
    }
}
