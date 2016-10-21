import javax.swing.*;

/**
 * The class <b>GameOfLife</b> launches the game.
 *
 * @author Igor Grebenkov
 */
public class GameOfLife {

    /**
     * The <b>main</b> method of the app.
     * Creates an instance of the game's controller and starts the game.
     *
     * @param args
     */
    public static void main( String[] args ) {
        int size = 49;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException: " + e.getMessage());
        } catch (InstantiationException e) {
            System.err.println("InstantiationException: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.err.println("IllegalAccessException: " + e.getMessage());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("UnsupportedLookAndFeelException: " + e.getMessage());
        }

        GameController game = new GameController( size );
    }
}