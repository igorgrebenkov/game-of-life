import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * The class <b>GridView</b> provides the current view of the whole grid.
 * It extends <b>JPanel</b> and creates a layout of a 2D array of <b>GridTile</b> instances.
 *
 * @author Igor Grebenkov
 */
public class GridView extends JPanel {

    private GridTile[][] grid;      // 2D array of GridTile instances
    private GameModel gameModel;    // Reference to the game model

    /**
     * Constructor to initialize GridView
     *
     * @param gameModel      // Reference to the game model
     * @param gameController // Reference to the game controller
     */
    public GridView( GameModel gameModel, GameController gameController ) {
        this.gameModel = gameModel;

        // JPanel/GridLayout properties
        setBackground( Color.darkGray );
        GridLayout gridLayout = new GridLayout( gameModel.getSize(), 1 );
        gridLayout.setHgap( 0 );
        gridLayout.setVgap( -5 );
        setLayout( gridLayout );
        setBorder( BorderFactory.createEmptyBorder( 0, 0, 0, 0 ) );

        // 2D Array of GridTiles that make up the whole grid
        grid = new GridTile[ gameModel.getSize() ][ gameModel.getSize() ];

        // Each row of the grid consists of a JPanel containing n GridTiles, n = number of cols.
        for ( int row = 0; row < gameModel.getSize(); row++ ) {
            JPanel panel = new JPanel();
            panel.setBackground( Color.darkGray );
            for ( int column = 0; column < gameModel.getSize(); column++ ) {
                grid[ column ][ row ] = new GridTile( row, column, gameModel.INACTIVE );
                grid[ column ][ row ].addActionListener( gameController );
                panel.add( grid[ column ][ row ] );
            }
            add( panel );
        }
    }

    /**
     * Updates the grid view.
     */
    public void update() {
        for ( int i = 0; i < gameModel.getSize(); i++ ) {
            for ( int j = 0; j < gameModel.getSize(); j++ ) {
                grid[ i ][ j ].setType( gameModel.getCurrentStatus( i, j ) );
            }
        }
        repaint();
    }
}