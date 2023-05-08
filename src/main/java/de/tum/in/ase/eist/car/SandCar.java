package de.tum.in.ase.eist.car;

import de.tum.in.ase.eist.Dimension2D;

public class SandCar extends Car {

    private static final String SLOW_CAR_IMAGE_FILE = "sand.png";

    private static final int MIN_SPEED_SLOW_CAR = 1;
    private static final int MAX_SPEED_SLOW_CAR = 3;

    public SandCar(Dimension2D gameBoardSize) {
        super(gameBoardSize);
        setMinSpeed(MIN_SPEED_SLOW_CAR);
        setMaxSpeed(MAX_SPEED_SLOW_CAR);
        setRandomSpeed();
        setIconLocation(SLOW_CAR_IMAGE_FILE);
        this.health = 200;
        this.damage = 50;
    }
}
