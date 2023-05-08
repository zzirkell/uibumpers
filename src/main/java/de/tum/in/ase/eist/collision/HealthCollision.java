package de.tum.in.ase.eist.collision;

import de.tum.in.ase.eist.Dimension2D;
import de.tum.in.ase.eist.Point2D;
import de.tum.in.ase.eist.car.Car;

public class HealthCollision extends Collision {

    public HealthCollision(Car car1, Car car2) {
        super(car1, car2);
    }

    @Override
    public boolean detectCollision() {
        Point2D p1 = getCar1().getPosition();
        Dimension2D d1 = getCar1().getSize();

        Point2D p2 = getCar2().getPosition();
        Dimension2D d2 = getCar2().getSize();

        boolean above = p1.getY() + d1.getHeight() < p2.getY();
        boolean below = p1.getY() > p2.getY() + d2.getHeight();
        boolean right = p1.getX() + d1.getWidth() < p2.getX();
        boolean left = p1.getX() > p2.getX() + d2.getWidth();

        return !above && !below && !right && !left;
    }

    @Override
    public Car evaluate() {

        Car winnerCar = null;
        if (getCar2().getMinSpeed() > getCar1().getMinSpeed()) {
            if (getCar1().getHealth() - getCar2().getDamage() < 0) {
                winnerCar = getCar2();
                getCar1().setHealth(0);
            } else {
                getCar1().setHealth(getCar1().getHealth()+10);
                getCar2().setHealth(getCar2().getHealth()- getCar1().getDamage());
            }
        } else {
            if (getCar2().getHealth() - getCar1().getDamage() < 0) {
                winnerCar = getCar1();
                getCar2().setHealth(0);
            } else {
                getCar2().setHealth(getCar2().getHealth()+10);
                getCar1().setHealth(getCar1().getHealth()- getCar2().getDamage());
            }
        }
        return winnerCar;
    }
}
