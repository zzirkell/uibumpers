package de.tum.in.ase.eist.car;

import de.tum.in.ase.eist.Dimension2D;

public class PlayersCar extends Car {

    private static final String FAST_CAR_IMAGE_FILE = "blue.png";

    private static final int MIN_SPEED_FAST_CAR = 3;
    private static final int MAX_SPEED_FAST_CAR = 8;

    public PlayersCar(Dimension2D gameBoardSize) {
        super(gameBoardSize);
        setMinSpeed(MIN_SPEED_FAST_CAR);
        setMaxSpeed(MAX_SPEED_FAST_CAR);
        setRandomSpeed();
        setIconLocation(FAST_CAR_IMAGE_FILE);
        this.health = 300;
        this.damage = 100;
    }
}
