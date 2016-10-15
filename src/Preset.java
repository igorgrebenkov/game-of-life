/**
 * The class <b>Preset</b> is used to draw various preset patterns.
 */
public class Preset {
    private int x;
    private int y;
    private GameModel gameModel;
    private GameView gameView;

    /**
     * Constructor that initializes the x and y co-ordinates
     * of the tile around which the preset will be drawn.
     *
     * @param x the x co-ordinate of the target tile
     * @param y the y co-ordinate of the target tile
     */
    public Preset( int x, int y, GameModel gameModel, GameView gameView) {
        this.x = x;
        this.y = y;
        this.gameModel = gameModel;
        this.gameView = gameView;
    }

    /**
     * Draws a glider.
     */
    public void drawGlider() {
        if ( ( x - 1 >= 0 ) &&
                ( y - 1 >= 0 ) &&
                ( x + 1 < gameModel.getSize() ) &&
                ( y + 1 < gameModel.getSize() ) ) {
            gameModel.selectTile( x, y - 1 );
            gameModel.selectTile( x + 1, y );
            gameModel.selectTile( x, y + 1 );
            gameModel.selectTile( x - 1, y + 1 );
            gameModel.selectTile( x + 1, y + 1 );
            gameView.update();
        }
    }

    /**
     * Draws a small exploder.
     */
    public void drawSmallExploder() {
        if ( ( x - 1 >= 0 ) &&
                ( y - 1 >= 0 ) &&
                ( x + 1 < gameModel.getSize() ) &&
                ( y + 2 < gameModel.getSize() ) ) {
            gameModel.selectTile( x, y );
            gameModel.selectTile( x, y - 1 );
            gameModel.selectTile( x - 1, y );
            gameModel.selectTile( x + 1, y );
            gameModel.selectTile( x - 1, y + 1 );
            gameModel.selectTile( x + 1, y + 1 );
            gameModel.selectTile( x, y + 2 );
            gameView.update();
        }
    }

    /**
     * Draws an exploder.
     */
    public void drawExploder() {
        if ( ( x - 2 >= 0 ) &&
                ( y - 2 >= 0 ) &&
                ( x + 2 < gameModel.getSize() ) &&
                ( y + 2 < gameModel.getSize() ) ) {
            gameModel.selectTile( x - 2, y - 2 );
            gameModel.selectTile( x, y - 2 );
            gameModel.selectTile( x + 2, y - 2 );
            gameModel.selectTile( x - 2, y - 1 );
            gameModel.selectTile( x + 2, y - 1 );
            gameModel.selectTile( x - 2, y );
            gameModel.selectTile( x + 2, y );
            gameModel.selectTile( x - 2, y + 1 );
            gameModel.selectTile( x + 2, y + 1 );
            gameModel.selectTile( x - 2, y + 2 );
            gameModel.selectTile( x, y + 2 );
            gameModel.selectTile( x + 2, y + 2 );
            gameView.update();
        }
    }

    /**
     * Draws a 10 Cell Row.
     */
    public void drawTenCellRow() {
        if ( ( x - 4 >= 0 ) &&
                ( x + 5 < gameModel.getSize() ) ) {
            gameModel.selectTile( x - 4, y );
            gameModel.selectTile( x - 3, y );
            gameModel.selectTile( x - 2, y );
            gameModel.selectTile( x - 1, y );
            gameModel.selectTile( x, y );
            gameModel.selectTile( x + 1, y );
            gameModel.selectTile( x + 2, y );
            gameModel.selectTile( x + 3, y );
            gameModel.selectTile( x + 4, y );
            gameModel.selectTile( x + 5, y );
            gameView.update();
        }
    }

    /**
     * Draws a lightweight spaceship.
     */
    public void drawLightweightSpaceship() {
        if ( ( x - 2 >= 0 ) &&
                ( y - 1 >= 0 ) &&
                ( x + 2 < gameModel.getSize() ) &&
                ( y + 2 < gameModel.getSize() ) ) {
            gameModel.selectTile( x - 1, y - 1 );
            gameModel.selectTile( x, y - 1 );
            gameModel.selectTile( x + 1, y - 1 );
            gameModel.selectTile( x + 2, y - 1 );
            gameModel.selectTile( x - 2, y );
            gameModel.selectTile( x + 2, y );
            gameModel.selectTile( x + 2, y + 1 );
            gameModel.selectTile( x - 2, y + 2 );
            gameModel.selectTile( x + 1, y + 2 );
            gameView.update();
        }
    }

    /**
     * Draws a tumbler.
     */
    public void drawTumbler() {
        if ( ( x - 3 >= 0 ) &&
                ( y - 2 >= 0 ) &&
                ( x + 3 < gameModel.getSize() ) &&
                ( y + 3 < gameModel.getSize() ) ) {
            gameModel.selectTile( x - 2, y - 2 );
            gameModel.selectTile( x - 1, y - 2 );
            gameModel.selectTile( x + 1, y - 2 );
            gameModel.selectTile( x + 2, y - 2 );
            gameModel.selectTile( x - 2, y - 1 );
            gameModel.selectTile( x - 1, y - 1 );
            gameModel.selectTile( x + 1, y - 1 );
            gameModel.selectTile( x + 2, y - 1 );
            gameModel.selectTile( x - 1, y );
            gameModel.selectTile( x + 1, y );
            gameModel.selectTile( x - 3, y + 1 );
            gameModel.selectTile( x - 1, y + 1 );
            gameModel.selectTile( x + 1, y + 1 );
            gameModel.selectTile( x + 3, y + 1 );
            gameModel.selectTile( x - 3, y + 2 );
            gameModel.selectTile( x - 1, y + 2 );
            gameModel.selectTile( x + 1, y + 2 );
            gameModel.selectTile( x + 3, y + 2 );
            gameModel.selectTile( x - 3, y + 3 );
            gameModel.selectTile( x - 2, y + 3 );
            gameModel.selectTile( x + 2, y + 3 );
            gameModel.selectTile( x + 3, y + 3 );
            gameView.update();
        }
    }

    public void drawGosperGliderGun() {
        if ( ( x - 18 >= 0 ) &&
                ( y - 4 >= 0 ) &&
                ( x + 19 < gameModel.getSize() ) &&
                ( y + 10 < gameModel.getSize() ) ) {
            gameModel.selectTile( x + 5, y - 4 );
            gameModel.selectTile( x + 6, y - 4 );
            gameModel.selectTile( x + 16, y - 4 );
            gameModel.selectTile( x + 17, y - 4 );
            gameModel.selectTile( x + 4, y - 3 );
            gameModel.selectTile( x + 6, y - 3 );
            gameModel.selectTile( x + 16, y - 3 );
            gameModel.selectTile( x + 17, y - 3 );
            gameModel.selectTile( x - 8, y - 2 );
            gameModel.selectTile( x - 9, y - 2 );
            gameModel.selectTile( x - 17, y - 2 );
            gameModel.selectTile( x - 18, y - 2 );
            gameModel.selectTile( x + 4, y - 2 );
            gameModel.selectTile( x + 5, y - 2 );
            gameModel.selectTile( x - 8, y - 1 );
            gameModel.selectTile( x - 10, y - 1 );
            gameModel.selectTile( x - 17, y - 1 );
            gameModel.selectTile( x - 18, y - 1 );
            gameModel.selectTile( x - 1, y );
            gameModel.selectTile( x - 2, y );
            gameModel.selectTile( x - 9, y );
            gameModel.selectTile( x - 10, y );
            gameModel.selectTile( x, y + 1 );
            gameModel.selectTile( x - 2, y + 1 );
            gameModel.selectTile( x - 2, y + 2 );
            gameModel.selectTile( x + 17, y + 3 );
            gameModel.selectTile( x + 18, y + 3 );
            gameModel.selectTile( x + 17, y + 4 );
            gameModel.selectTile( x + 19, y + 4 );
            gameModel.selectTile( x + 17, y + 5 );
            gameModel.selectTile( x + 6, y + 8 );
            gameModel.selectTile( x + 7, y + 8 );
            gameModel.selectTile( x + 8, y + 8 );
            gameModel.selectTile( x + 6, y + 9 );
            gameModel.selectTile( x + 7, y + 10 );
            gameView.update();
        }

    }
}