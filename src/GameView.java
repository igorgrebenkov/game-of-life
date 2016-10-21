import java.awt.*;
import java.util.Hashtable;
import javax.swing.*;

/**
 * The class <b>GameView</b> provides the present view of the game.
 * It extends <b>JFrame</b> and lays out an instance of GridView (the view of the game's grid).
 * It also contains a few instances of JButton for various functions:
 * - Start the simulation
 * - End the simulation
 * - Reset the simulation
 *
 * @author Igor Grebenkov
 */
public class GameView extends JFrame {

    private GridView grid;                      // Reference to the grid's view
    private GameModel gameModel;                // Reference to the game's model
    private JTextField numOfGenerationsField;   // TextField used to display the # of generations
    private JButton buttonStart;                // Button used to start the game
    private JButton buttonStop;                 // Button used to stop the game
    private JComboBox< String > presetsBox;     // ComboBox used to hold a list of presets
    private static final int SPEED_MIN = 25;    // Min generation speed (25ms per generation)
    private static final int SPEED_MAX = 500;   // Max generation speed (500ms per generation)
    private static final int SPEED_INIT = 200;  // Initial generation speed (200ms per generation)

    /**
     * Constructor used to initialize the JFrame.
     *
     * @param model          the game's model
     * @param gameController the game's controller
     */
    public GameView( GameModel model, GameController gameController ) {
        super( "Conway's Game of Life" );

        gameModel = model;

        // JFrame properties
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setResizable( false );
        setVisible( true );

        // Create layout
        grid = new GridView( model, gameController );
        add( grid, BorderLayout.CENTER );

        // Preset ComboBox
        String[] presets = { "", "Glider", "Small Exploder", "Exploder",
                "10 Cell Row", "Lightweight Spaceship", "Tumbler", "Gosper Glider Gun" };
        presetsBox = new JComboBox< String >( presets );
        presetsBox.addActionListener( gameController );

        // Control buttons
        buttonStart = new JButton( "Start" );
        buttonStart.setFocusPainted( false );
        buttonStart.addActionListener( gameController );

        buttonStop = new JButton( "Stop" );
        buttonStop.setFocusPainted( false );
        buttonStop.addActionListener( gameController );
        buttonStop.setEnabled( false );

        JButton buttonNext = new JButton( "Next" );
        buttonNext.setFocusPainted( false );
        buttonNext.addActionListener( gameController );
        buttonNext.setEnabled( true );

        JButton buttonRandom = new JButton( "Random" );
        buttonRandom.setFocusPainted( false );
        buttonRandom.addActionListener( gameController );

        JButton buttonReset = new JButton( "Reset" );
        buttonReset.setFocusPainted( false );
        buttonReset.addActionListener( gameController );

        JButton buttonQuit = new JButton( "Quit" );
        buttonQuit.setFocusPainted( false );
        buttonQuit.addActionListener( gameController );

        // Slider to change simulation speed
        JSlider simSpeed = new JSlider( JSlider.HORIZONTAL, SPEED_MIN, SPEED_MAX, SPEED_INIT );
        simSpeed.addChangeListener( gameController );
        simSpeed.setPaintTicks( false );
        simSpeed.setOpaque( false );
        simSpeed.setPaintLabels( true );
        simSpeed.putClientProperty( "JSlider.isFilled", Boolean.FALSE );
        simSpeed.setInverted( true );

        // Labels for the sim speed slider
        JLabel speedLabel = new JLabel( "Speed" );
        speedLabel.setForeground( Color.WHITE );

        JLabel speedMinLabel = new JLabel( "+" );
        speedMinLabel.setForeground( Color.WHITE );

        JLabel speedMaxLabel = new JLabel( "-" );
        speedMaxLabel.setForeground( Color.WHITE );

        // Add labels to Hashtable and set simSpeed slider label
        Hashtable labelTable = new Hashtable();
        labelTable.put( new Integer( SPEED_MIN ), speedMinLabel );
        labelTable.put( new Integer( SPEED_MAX / 2 ), speedLabel );
        labelTable.put( new Integer( SPEED_MAX ), speedMaxLabel );
        simSpeed.setLabelTable( labelTable );

        // Text field to display # of generations
        numOfGenerationsField = new JTextField( 5 );
        numOfGenerationsField.setEditable( false );
        numOfGenerationsField.setHorizontalAlignment( JTextField.CENTER );
        numOfGenerationsField.setFont( new Font( "Courier", Font.BOLD, 16 ) );

        // JLabel for number of generations text field
        JLabel numOfGenerationsLabel = new JLabel( "Generations: " );
        numOfGenerationsLabel.setForeground( Color.WHITE );
        //numOfGenerationsLabel.setFont( new Font( "Helvetica", Font.PLAIN, 16 ) );

        // Add the control buttons to a JPanel
        JPanel control = new JPanel();
        control.setBackground( Color.darkGray );
        control.add( presetsBox );
        control.add( buttonStart );
        control.add( buttonStop );
        control.add( buttonNext );
        control.add( buttonRandom );
        control.add( buttonReset );
        control.add( buttonQuit );
        control.add( simSpeed );
        control.add( numOfGenerationsLabel );
        control.add( numOfGenerationsField );

        // Add JPanel with controls to JFrame
        add( control, BorderLayout.SOUTH );

        pack();
    }

    /**
     * Updates the game view.
     */
    public void update() {
        grid.update();
    }

    /**
     * Method to update the number of generations in a JTextField
     *
     * @param text the text to update with
     */
    public void setJTextFieldString( String text ) {
        numOfGenerationsField.setText( text );
    }

    /**
     * Retrieves the currently selected string from the presets ComboBox
     *
     * @return the currently selected string
     */
    public String getComboBoxString() {
        return ( String ) presetsBox.getSelectedItem();
    }

    /**
     * Disables the start button
     */
    public void disableStartButton() {
        buttonStart.setEnabled( false );
    }

    /**
     * Enables the start button
     */
    public void enableStartButton() {
        buttonStart.setEnabled( true );
    }

    /**
     * Disables the stop button
     */
    public void disableStopButton() {
        buttonStop.setEnabled( false );
    }

    /**
     * Enables the stop button
     */
    public void enableStopButton() {
        buttonStop.setEnabled( true );
    }
}