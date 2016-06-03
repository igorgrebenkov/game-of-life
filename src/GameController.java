import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Iterator;
import javax.swing.*;
import java.io.*;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * The class <b>GameController</b> implements the interface ActionListener to be called
 * when a player selects a tile. It computes the next state for each successive generation,
 * updating the game's model and view.
 *
 * @author Igor Grebenkov
 */
public class GameController implements ActionListener {
    private static final int DEFAULT_TIMER_DELAY = 300; // Default timer delay

    private GameView gameView;        // Reference to the game's view
    private GameModel gameModel;      // Reference to the game's model
    private Timer timer;              // Timer to control speed of each generation/board update
    private int numberOfGenerations;  // Counts the number of generations

    /**
     * Constructor to initialize the controller. Creates the game's view and model instances.
     *
     * @param size the size of grid for the game
     */
    public GameController( int size ) {
        gameModel = new GameModel( size );
        gameView = new GameView( gameModel, this );
        this.timer = new Timer( DEFAULT_TIMER_DELAY, this );
        numberOfGenerations = 0;
        gameView.update();
    }

    /**
     * Resets the game.
     */
    public void reset() {
        gameModel.reset();
        gameView.update();
        numberOfGenerations = 0;
    }

    /**
     * Callback for when the user clicks a button or a tile.
     *
     * @param e the ActionEvent
     */
    public void actionPerformed( ActionEvent e ) {
        // If a tile on the grid is clicked, toggle it's status (active to inactive or vice-versa)
        if ( e.getSource() instanceof GridTile ) {

            GridTile clicked = ( GridTile ) ( e.getSource() );

            if ( gameModel.getCurrentStatus( clicked.getColumn(), clicked.getRow() ) ==
                    GameModel.INACTIVE ) {
                gameModel.selectTile( clicked.getColumn(), clicked.getRow() );
                gameView.update();
            } else {
                gameModel.unselectTile( clicked.getColumn(), clicked.getRow() );
                gameView.update();
            }
        }

        if ( e.getSource() instanceof JButton ) {
            JButton clicked = ( JButton ) ( e.getSource() );

            if ( clicked.getText().equals( "Start" ) ) {
                runSimulation();
            } else if ( clicked.getText().equals( "Stop" ) ) {
                timer.stop();
            } else if ( clicked.getText().equals( "Random" ) ) {
                randomizeTiles();
            } else if ( clicked.getText().equals( "Speed +" ) ) {
            } else if ( clicked.getText().equals( "Reset" ) ) {
                reset();
                timer.stop();
            } else if ( clicked.getText().equals( "Quit" ) ) {
                System.exit( 0 );
            }
        }

        if ( e.getSource() instanceof Timer ) {
            oneGeneration();
        }
    }

    /**
     * Begins the simulation
     */
    private void runSimulation() {
        timer.start();
    }

    /**
     * Computes one iteration of the game.
     */
    private void oneGeneration() {
        // Get the status of each tile in the next generation of the game
        int[][] statusCache = cacheNextGenStatuses();

        // Select/unselect tiles according to cached positions
        for ( int i = 0; i < gameModel.getSize(); i++ ) {
            for ( int j = 0; j < gameModel.getSize(); j++ ) {
                if ( statusCache[ i ][ j ] == 0 ) {
                    gameModel.unselectTile( i, j );
                } else {
                    gameModel.selectTile( i, j );
                }
            }
        }

        numberOfGenerations++;
        gameView.update();
    }

    /**
     * Caches the status of all tiles in the next generation according to the rules of the game
     *
     * @return the status of all tiles in the next generation
     */
    private int[][] cacheNextGenStatuses() {
        int statusCache[][] = new int[ gameModel.getSize() ][ gameModel.getSize() ];

        // Determines the status of each tile in the next generation of the game
        for ( int i = 0; i < gameModel.getSize(); i++ ) {
            for ( int j = 0; j < gameModel.getSize(); j++ ) {
                int numValidNeighbours = 0;

                // Get the list of valid neighbouring tiles to the tile at (i,j)
                LinkedList< Point > neighbours = getNeighbours( i, j );

                Iterator< Point > itr = neighbours.iterator();

                // Counts the number of active neighbors to the tile at (i,j)
                while ( itr.hasNext() ) {
                    Point p = itr.next();

                    int x = p.getX();
                    int y = p.getY();

                    if ( gameModel.getCurrentStatus( x, y ) == 1 ) {
                        numValidNeighbours++;
                    }
                }

                // Mark tiles for selection or deselection according to the game's rules
                if ( gameModel.getCurrentStatus( i, j ) == 1 ) {
                    if ( numValidNeighbours <= 1 ||
                            numValidNeighbours >= 4 ) {
                        statusCache[ i ][ j ] = 0;
                    } else {
                        statusCache[ i ][ j ] = 1;
                    }
                } else {
                    if ( numValidNeighbours == 3 ) {
                        statusCache[ i ][ j ] = 1;
                    } else {
                        statusCache[ i ][ j ] = 0;
                    }
                }
            }
        }
        return statusCache;
    }

    /**
     * Returns a list of valid neighbors to the point at (x,y).
     *
     * @param x the x co-ordinate of the point
     * @param y the y co-ordinate of the point
     * @return the list of neighbouring tiles (represented as points)
     */
    private LinkedList< Point > getNeighbours( int x, int y ) {
        LinkedList< Point > neighbours = new LinkedList< Point >();

        // Add possible neighbors to list
        neighbours.add( new Point( x - 1, y + 1 ) );
        neighbours.add( new Point( x - 1, y ) );
        neighbours.add( new Point( x - 1, y - 1 ) );
        neighbours.add( new Point( x, y + 1 ) );
        neighbours.add( new Point( x, y - 1 ) );
        neighbours.add( new Point( x + 1, y + 1 ) );
        neighbours.add( new Point( x + 1, y ) );
        neighbours.add( new Point( x + 1, y - 1 ) );

        Iterator< Point > i = neighbours.iterator();

        // Remove any points that are outside the range of the board from the list of neighbours
        while ( i.hasNext() ) {
            Point p = i.next();

            if ( p.getX() < 0 ) {
                i.remove();
            } else if ( p.getX() >= gameModel.getSize() ) {
                i.remove();
            } else if ( p.getY() < 0 ) {
                i.remove();
            } else if ( p.getY() >= gameModel.getSize() ) {
                i.remove();
            }
        }

        return neighbours;
    }

    /**
     * Randomly activates a certain number in tiles with a 1/16 probability
     */
    private void randomizeTiles() {
        Random n = new Random();
        for ( int i = 0; i < gameModel.getSize(); i++ ) {
            for ( int j = 0; j < gameModel.getSize(); j++ ) {
                int newStatus = n.nextInt( 15 );

                if ( newStatus == 0 ) {
                    gameModel.selectTile( i, j );
                }
            }
        }
        gameView.update();
    }

}

