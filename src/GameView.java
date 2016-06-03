import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private GridView grid;        // Reference to the grid's view
    private GameModel gameModel;  // Reference to the game's model

    /**
     * Constructor used to intitialize the JFrame.
     * @param model           the game's model
     * @param gameController  the game's controller
     */
    public GameView(GameModel model, GameController gameController) {
        super("Conway's Game of Life");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);

        gameModel = model;
        grid = new GridView(model, gameController);
        add(grid, BorderLayout.CENTER);

        JButton buttonStart = new JButton("Start");
        buttonStart.setFocusPainted(false);
        buttonStart.addActionListener(gameController);

        JButton buttonStop = new JButton("Stop");
        buttonStop.setFocusPainted(false);
        buttonStop.addActionListener(gameController);

        JButton buttonRandom = new JButton("Random");
        buttonRandom.setFocusPainted(false);
        buttonRandom.addActionListener(gameController);

        JButton buttonSpeedUp = new JButton("Speed +");
        buttonSpeedUp.setFocusPainted(false);
        buttonSpeedUp.addActionListener(gameController);

        JButton buttonReset = new JButton("Reset");
        buttonReset.setFocusPainted(false);
        buttonReset.addActionListener(gameController);

        JButton buttonQuit = new JButton("Quit");
        buttonQuit.setFocusPainted(false);
        buttonQuit.addActionListener(gameController);

        JPanel control = new JPanel();
        control.setBackground(Color.darkGray);
        control.add(buttonStart);
        control.add(buttonStop);
        control.add(buttonRandom);
        control.add(buttonSpeedUp);
        control.add(buttonReset);
        control.add(buttonQuit);
        add(control, BorderLayout.SOUTH);

        pack();
        setResizable(false);
        setVisible(true);
    }

    public void update() {
        grid.update();
    }
}
