import javafx.scene.control.ComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;

/**
 * The class <b>GameController</b> implements the interface ActionListener to be called
 * when a player selects a tile. It also implements the interface ChangeListener, called
 * when the player adjusts the game speed via a JSlider. It computes the next state for each successive
 * generation, updating the game's model and view.
 *
 * @author Igor Grebenkov
 */
public class GameController implements ActionListener, ChangeListener {
    private static final int DEFAULT_TIMER_DELAY = 300; // Default timer delay

    private GameView gameView;            // Reference to the game's view
    private GameModel gameModel;          // Reference to the game's model
    private Timer timer;                  // Timer to control speed of each generation/board update
    private int moveDelay;                // Timer delay between every generation (ms)
    public Integer numberOfGenerations;   // Counts the number of generations

    /**
     * Constructor to initialize the controller. Creates the game's view and model instances.
     *
     * @param size the size of grid for the game
     */
    public GameController( int size ) {
        gameModel = new GameModel( size );
        gameView = new GameView( gameModel, this );
        moveDelay = 200;
        gameView.setJTextFieldString( Integer.toString( 0 ) );
        numberOfGenerations = 0;
        gameView.update();
    }

    /**
     * Resets the game.
     */
    private void reset() {
        gameModel.reset();
        numberOfGenerations = 0;
        gameView.setJTextFieldString( Integer.toString( numberOfGenerations ) );
        gameView.update();
    }

    /**
     * Callback for when the user clicks a button or a tile.
     *
     * @param e the ActionEvent
     */
    public void actionPerformed( ActionEvent e ) {
        // - If a tile on the grid is clicked and a preset is active,
        // the preset will be drawn around the next tile clicked by the user.
        // - If no preset is active, clicking a tile toggles it's status
        if ( e.getSource() instanceof GridTile ) {
            GridTile source = ( GridTile ) ( e.getSource() );
            // If the preset is selected in the ComboBox, draw that preset
                if ( !gameView.getComboBoxString().equals( "" ) ) {
                Preset p = new Preset( source.getColumn(), source.getRow(), gameModel, gameView );
                switch ( gameView.getComboBoxString() ) {
                    case "Glider":
                        p.drawGlider();
                        break;
                    case "Small Exploder":
                        p.drawSmallExploder();
                        break;
                    case "Exploder":
                        p.drawExploder();
                        break;
                    case "10 Cell Row":
                        p.drawTenCellRow();
                        break;
                    case "Lightweight Spaceship":
                        p.drawLightweightSpaceship();
                        break;
                    case "Tumbler":
                        p.drawTumbler();
                        break;
                    case "Gosper Glider Gun":
                        p.drawGosperGliderGun();
                        break;
                }
            } else if ( gameModel.getCurrentStatus( source.getColumn(), source.getRow() ) ==
                    GameModel.INACTIVE ) {
                gameModel.selectTile( source.getColumn(), source.getRow() );
                gameView.update();
            } else {
                gameModel.unselectTile( source.getColumn(), source.getRow() );
                gameView.update();
            }
        }

        // Event handling for control buttons
        if ( e.getSource() instanceof JButton ) {
            JButton source = ( JButton ) ( e.getSource() );

            if ( source.getText().equals( "Start" ) ) {
                runSimulation();
                gameView.disableStartButton();
                gameView.enableStopButton();
            } else if ( source.getText().equals( "Stop" ) ) {
                timer.cancel();
                timer.purge();
                gameView.enableStartButton();
                gameView.disableStopButton();
            } else if ( source.getText().equals( "Next" ) ) {
                oneGeneration();
            } else if ( source.getText().equals( "Random" ) ) {
                randomizeTiles();
            } else if ( source.getText().equals( "Reset" ) ) {
                if ( numberOfGenerations != 0 ) {
                    timer.cancel();
                    timer.purge();
                }
                gameView.enableStartButton();
                gameView.disableStopButton();
                reset();
            } else if ( source.getText().equals( "Quit" ) ) {
                System.exit( 0 );
            }
        }
    }

    /**
     * Change listener for JSliders
     *
     * @param e the ChangeEvent
     */
    public void stateChanged( ChangeEvent e ) {
        JSlider source = ( JSlider ) e.getSource();

        if ( !source.getValueIsAdjusting() ) {
            moveDelay = source.getValue();
        }
    }

    /**
     * Begins the simulation
     */
    private void runSimulation() {
        timer = new Timer();
        timer.schedule( new Task(), moveDelay );
    }

    /**
     * Computes one iteration of the game.
     */
    private void oneGeneration() {
        // Get the status of each tile in the next generation of the game
        int[][] statusCache = cacheNextGenStatuses();

        // Select/unselect tiles according to previously cached positions
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
        gameView.setJTextFieldString( Integer.toString( numberOfGenerations ) );

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

                // Mark tiles for life or death according to the game's rules
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
     * Randomly activates some tiles with a 1/31 probability
     */
    private void randomizeTiles() {
        Random n = new Random();
        for ( int i = 0; i < gameModel.getSize(); i++ ) {
            for ( int j = 0; j < gameModel.getSize(); j++ ) {
                int newStatus = n.nextInt( 30 );

                if ( newStatus == 0 ) {
                    gameModel.selectTile( i, j );
                }
            }
        }
        gameView.update();
    }

    /**
     * The nested class <b>Task</b> is used to schedule tasks
     * (in this program, running each generation of the simulation).
     */
    private class Task extends TimerTask {
        /**
         * Runs a task -> repeatedly executes one generation of the game
         */
        public void run() {
            oneGeneration();
            timer.schedule( new Task(), moveDelay );
        }
    }


}