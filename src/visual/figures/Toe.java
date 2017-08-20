package visual.figures;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

public class Toe extends Circle {

    public Toe(double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);

    }

    public Toe(double centerX, double centerY, double radius, Color color) {
        super(centerX, centerY, radius);
        setFill(null);
        setStroke(color);
    }

    public Toe(double centerX, double centerY, double radius, Color color1_gradient, Color color2_gradient, Color color3shadow) {
        super(centerX, centerY, radius);
        setFill(null);
        LinearGradient gradient = new LinearGradient(0, 0, 1.5, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
                new Stop(0, color1_gradient),
                new Stop(1, color2_gradient),
        });
        setStroke(gradient);
        setStrokeWidth(5);
        final DropShadow dropShadow = new DropShadow(10, 4, 3.85, color3shadow);
        setEffect(dropShadow);
    }

    public Toe(double radius, Color color1_gradient, Color color2_gradient, Color color3shadow) {
        super(radius);
        setFill(null);
        LinearGradient gradient = new LinearGradient(0, 0, 1.5, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
                new Stop(0, color1_gradient),
                new Stop(1, color2_gradient),
        });
        setStroke(gradient);
        setStrokeWidth(5);
        final DropShadow dropShadow = new DropShadow(10, 4, 3.85, color3shadow);
        setEffect(dropShadow);
    }
}
