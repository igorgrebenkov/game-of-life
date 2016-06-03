/**
 * Created by Igor on 6/3/2016.
 */
public class Point {
    int x;
    int y;
    int status;

    public Point( int x, int y ) {
        this. x = x;
        this.y = y;
    }

    public Point( int x, int y, int status) {
        this.x = x;
        this.y = y;
        this.status = status;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
