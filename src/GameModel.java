/**
 * The class <b>GameModel</b> holds the state of the game.
 * It stores the following:
 * - The state of all tiles on the grid
 * - The size of the grid
 * - The number of generations
 *
 * @author Igor Grebenkov
 */
public class GameModel {

    public static final int INACTIVE = 0;  // Captures the state of an inactive dot
    public static final int ACTIVE = 1;    // Captures the state of an active dot
    private int sizeOfGrid;                // The size of the grid
    private int[][] tileStates;            // The state of each tile
    private int numberOfGenerations;       // The number of generations since starting the simulation

    /**
     * Constructor to initialize the model to a given size of grid.
     *
     * @param size the size of the grid
     */
    public GameModel( int size ) {
        numberOfGenerations = 0;
        sizeOfGrid = size;

        reset();
    }

    /**
     * Resets the model to start/re-start the game. Previous game (if any) is cleared.
     */
    public void reset() {
        tileStates = new int[ sizeOfGrid ][ sizeOfGrid ];

        for ( int i = 0; i < sizeOfGrid; i++ ) {
            for ( int j = 0; j < sizeOfGrid; j++ ) {
                tileStates[ i ][ j ] = INACTIVE;
            }
        }

        numberOfGenerations = 0;
    }

    /**
     * Getter method for the size of the grid.
     * @return the size of the grid
     */
    public int getSize() {
        return sizeOfGrid;
    }

    /**
     * Getter method to return the number of generations since starting the simulation.
     * @return number of generations since starting the simulation
     */
    public int getNumberOfGenerations() {
        return numberOfGenerations;
    }

    /**
     * Getter method to return the game's model.
     * @return the game model
     */
    public int[][] getTileStates() {
        return tileStates;
    }

    /**
     * Getter method to return the status of a title
     * @param i the tile's x co-ordinate
     * @param j the tile's y co-ordinate
     * @return the status (active or inactive) of the tile at (i,j)
     */
    public int getCurrentStatus(int i, int j) {
        return tileStates[i][j];
    }

    /**
     * Method that selects a tile and makes it active.
     * @param i the tile's x co-ordinate
     * @param j the tile's y co-ordinate
     */
    public void selectTile(int i, int j) {
        tileStates[i][j] = ACTIVE;
    }

    public void unselectTile( int i, int j ) {
        tileStates[i][j] = INACTIVE;
    }
}
