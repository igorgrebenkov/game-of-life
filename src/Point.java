/**
 * The class <b>Point</b> is a helper class to store point objects.
 *
 * @author Igor Grebenkov
 */
public class Point {
    int x;       // Point x co-ordinate
    int y;       // Point y co-ordinate

    /**
     * Constructor initializes a point.
     *
     * @param x // Point x co-ordinate
     * @param y // Point y co-ordinate
     */
    public Point( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for x co-ordinate.
     *
     * @return x co-ordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for y co-ordinate.
     *
     * @return y co-ordinate
     */
    public int getY() {
        return y;
    }
}
