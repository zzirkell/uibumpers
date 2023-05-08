package de.tum.in.ase.eist.gameview;

import java.net.URL;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import de.tum.in.ase.eist.Dimension2D;
import de.tum.in.ase.eist.GameBoard;
import de.tum.in.ase.eist.GameOutcome;
import de.tum.in.ase.eist.Point2D;
import de.tum.in.ase.eist.audio.AudioPlayer;
import de.tum.in.ase.eist.car.Car;
import de.tum.in.ase.eist.usercontrol.MouseSteering;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * This class implements the user interface for steering the player car. The
 * user interface is implemented as a Thread that is started by clicking the
 * start button on the toolbar and stops by the stop button.
 */
public class GameBoardUI extends Canvas {

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private TextField health;

    /**
     * The update period of the game in ms, this gives us 25 fps.
     */
    private static final int UPDATE_PERIOD = 1000 / 15;
    private static final int DEFAULT_WIDTH = 1200;
    private static final int DEFAULT_HEIGHT = 700;
    private static final Dimension2D DEFAULT_SIZE = new Dimension2D(DEFAULT_WIDTH, DEFAULT_HEIGHT);

    public static Dimension2D getPreferredSize() {
        return DEFAULT_SIZE;
    }

    /**
     * Timer responsible for updating the game every frame that runs in a separate
     * thread.
     */
    private Timer gameTimer;

    private GameBoard gameBoard;

    private final GameToolBar gameToolBar;

    private MouseSteering mouseSteering;
    private final static String BACK_GROUND_IMAGE = "space.jpg";

    private HashMap<String, Image> imageCache;

    public GameBoardUI(GameToolBar gameToolBar) {
        this.gameToolBar = gameToolBar;
        setup();
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public MouseSteering getMouseSteering() {
        return mouseSteering;
    }

    /**
     * Removes all existing cars from the game board and re-adds them. Player car is
     * reset to default starting position. Renders graphics.
     */
    public void setup() {
        setupGameBoard();

        setupImageCache();
        this.gameToolBar.updateToolBarStatus(false);
        paint();
    }

    private void setupGameBoard() {
        Dimension2D size = getPreferredSize();
        this.gameBoard = new GameBoard(size);
        this.gameBoard.setAudioPlayer(new AudioPlayer());
        this.health = new TextField(gameBoard.getPlayerCar().getHealth() + "");
        health.setStyle("-fx-text-fill: #ffffff;");
        health.setAlignment(Pos.CENTER);
        widthProperty().set(size.getWidth());
        heightProperty().set(size.getHeight());
        this.mouseSteering = new MouseSteering(this.gameBoard.getPlayerCar());
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent clickEvent) -> {
            this.mouseSteering.mousePressed(clickEvent);
        });

    }

    private void setupImageCache() {
        this.imageCache = new HashMap<>();
        for (Car car : this.gameBoard.getCars()) {
            // Loads the image into the cache
            getImage(car.getIconLocation());
        }
        String playerImageLocation = this.gameBoard.getPlayerCar().getIconLocation();
        getImage(playerImageLocation);
    }

    /**
     * Returns the car's image. If no image is present in the cache, a new image is created.
     *
     * @param carImageFilePath an image file path that needs to be available in the
     *                         resources folder of the project
     */
    private Image getImage(String carImageFilePath) {
        return this.imageCache.computeIfAbsent(carImageFilePath, this::createImage);
    }

    /**
     * Loads the car's image.
     *
     * @param carImageFilePath an image file path that needs to be available in the
     *                         resources folder of the project
     */
    private Image createImage(String carImageFilePath) {
        URL carImageUrl = getClass().getClassLoader().getResource(carImageFilePath);
        if (carImageUrl == null) {
            throw new IllegalArgumentException(
                    "Please ensure that your resources folder contains the appropriate files for this exercise.");
        }
        return new Image(carImageUrl.toExternalForm());
    }

    /**
     * Starts the GameBoardUI Thread, if it wasn't running. Starts the game board,
     * which causes the cars to change their positions (i.e. move). Renders graphics
     * and updates tool bar status.
     */
    public void startGame() {
        if (!this.gameBoard.isRunning()) {
            this.gameBoard.startGame();
            this.gameToolBar.updateToolBarStatus(true);
            startTimer();
            paint();
        }
    }

    private void startTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                updateGame();
            }
        };
        if (this.gameTimer != null) {
            this.gameTimer.cancel();
        }
        this.gameTimer = new Timer();
        this.gameTimer.scheduleAtFixedRate(timerTask, UPDATE_PERIOD, UPDATE_PERIOD);
    }

    private void updateGame() {
        if (gameBoard.isRunning()) {
            // updates car positions and re-renders graphics
            this.gameBoard.update();
            // when this.gameBoard.getOutcome() is OPEN, do nothing
            if (this.gameBoard.getGameOutcome() == GameOutcome.LOST) {
                showAsyncAlert("Oh.. you lost.");
                this.stopGame();
            } else if (this.gameBoard.getGameOutcome() == GameOutcome.WON) {
                showAsyncAlert("Congratulations! You won!!");
                this.stopGame();
            }
            paint();
        }
    }

    /**
     * Stops the game board and set the tool bar to default values.
     */
    public void stopGame() {
        if (this.gameBoard.isRunning()) {
            this.gameBoard.stopGame();
            this.gameToolBar.updateToolBarStatus(false);
            this.gameTimer.cancel();
        }
    }

    /**
     * Render the graphics of the whole game by iterating through the cars of the
     * game board at render each of them individually.
     */
    private void paint() {
        //getGraphicsContext2D().setFill(BACKGROUND_COLOR);
        Image background = new Image(BACK_GROUND_IMAGE);
        getGraphicsContext2D().drawImage(background, 0, 0, gameBoard.getSize().getWidth(), gameBoard.getSize().getHeight());
        //getGraphicsContext2D().fillRect(0, 0, getWidth(), getHeight());

        for (Car car : this.gameBoard.getCars()) {
            paintCar(car);
        }
        // render player car
        paintCar(this.gameBoard.getPlayerCar());
    }

    /**
     * Show image of a car at the current position of the car.
     *
     * @param car to be drawn
     */
    private void paintCar(Car car) {
        Point2D carPosition = car.getPosition();
        getGraphicsContext2D().drawImage(getImage(car.getIconLocation()), carPosition.getX(),
                carPosition.getY(), car.getSize().getWidth(), car.getSize().getHeight());
    }

    /**
     * Method used to display alerts in moveCars().
     *
     * @param message you want to display as a String
     */
    private void showAsyncAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(message);
            alert.showAndWait();
            this.setup();
        });
    }
}
