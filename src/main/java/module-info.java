module BumpersSprint {

    exports de.tum.in.ase.eist;
    exports de.tum.in.ase.eist.gameview;
    exports de.tum.in.ase.eist.car;
    exports de.tum.in.ase.eist.audio;
    exports de.tum.in.ase.eist.collision;
    exports de.tum.in.ase.eist.usercontrol;

    requires javafx.controls;
    requires javafx.media;
    requires javafx.graphics;

    opens de.tum.in.ase.eist to javafx.media, javafx.graphics;
    opens de.tum.in.ase.eist.gameview to javafx.graphics, javafx.media;
    opens de.tum.in.ase.eist.car;
}