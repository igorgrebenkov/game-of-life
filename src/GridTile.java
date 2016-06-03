import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * <p>
 * <p>
 * A <b>GridTile</b> is a type of <b>JButton</b> that represents one tile in the grid.
 * A black tile is inactive and a green tile is active.
 * <p>
 * Images are stored in subdirectory ''data'':
 * - data/tile-0.jpg -> black (inactive) tile
 * - data/tile-1.jpg -> green (active) tile
 *
 * @author Igor Grebenkov
 */
public class GridTile extends JButton {


    private static final int NUM_COLOURS = 2;    // Number of colours
    private int type;                            // Tile type -> active or inactive
    private int row, column;                     // Stores tile co-ordinates

    // Array used to cache tile images
    private static final ImageIcon[] icons = new ImageIcon[ NUM_COLOURS ];

    /**
     * Constructor to initialize a tile of a specified type.
     *
     * @param row    the row of this GridTile
     * @param column the column of this GridTile
     * @param type   the type of this GridTile
     */
    public GridTile( int row, int column, int type ) {
        this.row = row;
        this.column = column;
        this.type = type;

        setBackground( Color.WHITE );

        // Fetch image icon
        setIcon( getImageIcon() );

        this.setMargin(new Insets(0,0,0,0));
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setBorder(new EmptyBorder(0,0,0,0));

        // Create empty border for each tile
        //Border emptyBorder = BorderFactory.createEmptyBorder( 0, 0, 0, 0 );
        //setBorder( emptyBorder );
        //setBorderPainted( false );
    }

    /**
     * Determines which image to used based on tile type and returns an icon of that type.
     *
     * @return the image to display on the tile
     */
    private ImageIcon getImageIcon() {
        if ( icons[ type ] == null ) {
            // String strId = (Integer) Integer.toString( type );

            if ( type == 0 ) {
                icons[type] = new ImageIcon( getClass().getResource("data/tile-0.jpg"));
            } else {
                icons[type] = new ImageIcon( getClass().getResource("data/tile-1.jpg"));
            }
            // icons[ type ] = new ImageIcon( "src/data/tile-" + strId + ".jpg" );
        }
        return icons[ type ];
    }

    /**
     * Changes the tile type and updates its image.
     *
     * @param type the type we are changing the tile to
     */
    public void setType( int type ) {
        this.type = type;
        setIcon( getImageIcon() );
    }

    /**
     * Getter method for row attribute.
     *
     * @return the value of row
     */
    public int getRow() {
        return row;
    }

    /**
     * Getter method for column attribute
     *
     * @return the value of column
     */
    public int getColumn() {
        return column;
    }


}
