package de.tum.in.ase.eist.gameview;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

import java.util.Optional;

/**
 * This class visualizes the toolbar with start and stop buttons above the game board.
 */
public class GameToolBar extends ToolBar {
    private final Button start;
    private final Button stop;
    private final Button restart;
    private final Label inff;


    public GameToolBar() {
        this.start = new Button("Start");
        this.stop = new Button("Stop");
        this.restart = new Button("Restart");
        this.inff = new Label("PLEASE RESTART ONLY AFTER GAME OUTCOME");
        this.inff.setTextFill(Color.RED);
        // the game is stopped initially
        updateToolBarStatus(false);
        getItems().addAll(this.start, new Separator(), this.stop, new Separator(), this.restart, new Separator(), this.inff);
    }

    /**
     * Initializes the actions of the toolbar buttons.
     */
    public void initializeActions(GameBoardUI gameBoardUI) {
        this.start.setOnAction(event -> gameBoardUI.startGame());

        this.stop.setOnAction(event -> {
            // stop the game while the alert is shown
            gameBoardUI.stopGame();

            Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to stop the game?", ButtonType.YES,
                                    ButtonType.NO);
            alert.setTitle("Stop Game Confirmation");
            // By default the header additionally shows the Alert Type (Confirmation)
            // but we want to disable this to only show the question
            alert.setHeaderText("");

            Optional<ButtonType> result = alert.showAndWait();
            // reference equality check is OK here because the result will return the same
            // instance of the ButtonType
            if (result.isPresent() && result.get() == ButtonType.YES) {
                // reset the game board to prepare the new game
                gameBoardUI.setup();
            } else {
                // continue running
                gameBoardUI.startGame();
            }
        });
    }

    /**
     * Updates the status of the toolbar. This will for example enable or disable
     * buttons.
     *
     * @param running true if game is running, false otherwise
     */
    public void updateToolBarStatus(boolean running) {
        this.start.setDisable(running);
        this.stop.setDisable(!running);
    }

    public Button getRestart() {
        return restart;
    }


}
