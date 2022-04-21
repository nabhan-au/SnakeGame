public class Snake extends WObject {
    private int dx;
    private int dy;

    public Snake(int x, int y) {
        super(x, y);
    }

    public void turnNorth() {
        dx = 0;
        dy = -1;
    }

    public void turnSouth() {
        dx = 0;
        dy = 1;
    }

    public void turnWest() {
        dx = -1;
        dy = 0;
    }

    public void turnEast() {
        dx = 1;
        dy = 0;
    }

    public void move() {
        this.setX(this.getX() + dx);
        this.setY(this.getY() + dy);
    }

    public int getDx() { return dx; }

    public int getDy() { return dy; }
}
