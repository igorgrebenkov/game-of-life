/**
 * The class <b>GameOfLife</b> launches the game.
 *
 * @author Igor Grebenkov
 */
public class GameOfLife {

    /**
     * The <b>main</b> method of the app.
     * Creates an instance of the game's controller and starts the game.
     * @param args
     */
    public static void main( String[] args ) {
        int size = 44;

        GameController game = new GameController( size );
    }
}
