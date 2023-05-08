package de.tum.in.ase.eist.car;

import de.tum.in.ase.eist.Dimension2D;

public class LeafCar extends Car {
    private static final String FAST_CAR_IMAGE_FILE = "leaf.png";

    private static final int MIN_SPEED_FAST_CAR = 5;
    private static final int MAX_SPEED_FAST_CAR = 7;
    /**
     * Constructor, taking the maximum coordinates of the game board. Each car gets
     * a random X and Y coordinate, a random direction and a random speed.
     * <p>
     * The position of the car cannot be larger than the dimensions of the game
     * board.
     *
     * @param gameBoardSize dimensions of the game board
     */
    public LeafCar(Dimension2D gameBoardSize) {
        super(gameBoardSize);
        setMinSpeed(MIN_SPEED_FAST_CAR);
        setMaxSpeed(MAX_SPEED_FAST_CAR);
        setRandomSpeed();
        setIconLocation(FAST_CAR_IMAGE_FILE);
        this.health = 250;
        this.damage = 60;
    }
}
