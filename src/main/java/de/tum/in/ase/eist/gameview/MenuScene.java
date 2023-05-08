package de.tum.in.ase.eist.gameview;

import de.tum.in.ase.eist.collision.Collision;
import de.tum.in.ase.eist.collision.HealthCollision;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Optional;

public class MenuScene extends VBox {
    private String choise;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 100;
    private static final int LABEL_SIZE = 24;
    private static final int MENU_HEIGHT = 800;
    private static final int MENU_WIDTH = 1200;

    private final Button healthDamage;
    private final Button defaultMode;
    private Label name;

    public MenuScene() {
        this.healthDamage = new Button("Health Mode");
        this.defaultMode = new Button("Default Mode");
        this.name = new Label("Bumpers");
        // the game is stopped initially
        getChildren().addAll(this.name, new Separator(), this.healthDamage, new Separator(), this.defaultMode);
        healthDamage.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        defaultMode.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        name.setFont(new Font("Segoe UI", 50));
        this.setPrefSize(MENU_WIDTH, MENU_HEIGHT);
        this.defaultMode.setFont(new Font("Segoe UI", 24));
        this.healthDamage.setFont(new Font("Segoe UI", 24));
        this.setAlignment(Pos.CENTER);
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("space.jpg", MENU_WIDTH, MENU_HEIGHT, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        this.setBackground(background);
        this.setSpacing(30);
        this.name.setTextFill(Color.web("#FFFF"));
    }

    /**
     * Initializes the actions of the toolbar buttons.
     */

    public String getChoise() {
        return choise;
    }

    public Button getDefaultMode() {
        return defaultMode;
    }

    public Button getHealthDamage() {
        return healthDamage;
    }

    public void setChoise(String choise) {
        this.choise = choise;
    }
}
