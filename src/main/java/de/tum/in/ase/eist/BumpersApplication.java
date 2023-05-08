package de.tum.in.ase.eist;

import de.tum.in.ase.eist.car.Car;
import de.tum.in.ase.eist.gameview.GameBoardUI;
import de.tum.in.ase.eist.gameview.GameToolBar;
import de.tum.in.ase.eist.gameview.MenuScene;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Starts the Bumpers Application, loads the GameToolBar and GameBoardUI. This
 * class is the root of the JavaFX Application.
 *
 * @see Application
 */
public class BumpersApplication extends Application {

    private static final int GRID_LAYOUT_PADDING = 5;
    private static final int GRID_LAYOUT_PREF_HEIGHT = 700;
    private static final int GRID_LAYOUT_PREF_WIDTH = 1200;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the Bumpers Window by setting up a new toolbar, a new user interface
     * and adding them to the stage.
     *
     * @param primaryStage the primary stage for this application, onto which the
     *                     application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        // the toolbar object with start and stop buttons
        MenuScene menuScene = new MenuScene();
        GameToolBar toolBar = new GameToolBar();
        GameBoardUI gameBoardUI = new GameBoardUI(toolBar);
        gameBoardUI.getGameBoard().setMenuScene(menuScene);
        toolBar.initializeActions(gameBoardUI);

        Pane gridLayout = createLayout(gameBoardUI, toolBar);
        GridPane menu = new GridPane();
        menu.setPrefSize(GRID_LAYOUT_PREF_WIDTH, GRID_LAYOUT_PREF_HEIGHT);
        menu.add(menuScene, 0, 0);


        // scene and stages
        Scene scene1 = new Scene(menu);
        Image icon = new Image("blue.png");
        primaryStage.getIcons().add(icon);

        Scene scene = new Scene(gridLayout);
        primaryStage.setTitle("EveryOne Hates Circles");
        primaryStage.setScene(scene1);
        menuScene.getDefaultMode().setOnAction(event -> {
                gameBoardUI.getGameBoard().setChoise("default");
                primaryStage.setScene(scene);}
        );
        menuScene.getHealthDamage().setOnAction(event -> {
                gameBoardUI.getGameBoard().setChoise("health");
                primaryStage.setScene(scene);});
        toolBar.getRestart().setOnAction(event -> {
                for (Car car : gameBoardUI.getGameBoard().getCars()) {
                    car.setHealth(200);
        }
                gameBoardUI.getGameBoard().getPlayerCar().setHealth(300);
            primaryStage.setScene(scene1);});
        primaryStage.setOnCloseRequest(closeEvent -> gameBoardUI.stopGame());
        primaryStage.show();
    }

    /**
     * Creates a new {@link Pane} that arranges the game's UI elements.
     */
    private static Pane createLayout(GameBoardUI gameBoardUI, GameToolBar toolBar) {
        // GridPanes are divided into columns and rows, like a table
        GridPane gridLayout = new GridPane();
        gridLayout.setPrefSize(GRID_LAYOUT_PREF_WIDTH, GRID_LAYOUT_PREF_HEIGHT);
        gridLayout.setVgap(GRID_LAYOUT_PADDING);
        gridLayout.setPadding(new Insets(GRID_LAYOUT_PADDING));

        // add all components to the gridLayout
        // second parameter is column index, second parameter is row index of grid
        gridLayout.add(gameBoardUI, 0, 1);
        gridLayout.add(toolBar, 0, 0);
        return gridLayout;
    }
}
