package visual.figures;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polyline;

public class CrossPaint extends Polyline {

    public CrossPaint(double width, double height, Color color1_gradient, Color color2_gradient, Color color3_shadow) {
        super(width - 10, height - 10);
        double[] points = new double[]{
                10, 10,
                width / 2, height / 2,
                10, height - 10,
                width / 2, height / 2,
                width - 10, height - 10,
                width / 2, height / 2,
                width - 10, 10
        };
        for (double point : points) {
            this.getPoints().add(point);
        }
        setFill(Color.TRANSPARENT);
        LinearGradient gradient = new LinearGradient(0, 0, 1.5, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
                new Stop(0, color1_gradient),
                new Stop(1, color2_gradient),
        });
        setStroke(gradient);
        setStrokeWidth(5);
        final DropShadow dropShadow = new DropShadow(10, 4, 3.85, color3_shadow);
        setEffect(dropShadow);
    }

    public CrossPaint(double height, double width) {
        super(width - 10, height - 10);
        double[] points = new double[]{
                10, 10,
                width / 2, height / 2,
                10, height - 10,
                width / 2, height / 2,
                width - 10, height - 10,
                width / 2, height / 2,
                width - 10, 10
        };
        for (double point : points) {
            this.getPoints().add(point);
        }
        setFill(Color.TRANSPARENT);
        LinearGradient gradient = new LinearGradient(0, 0, 1.5, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
                new Stop(0, Color.DODGERBLUE),
                new Stop(1, Color.WHITE),
        });
        setStroke(gradient);
        setStrokeWidth(5);
        final DropShadow dropShadow = new DropShadow(10, 4, 3.85, Color.BLACK);
        setEffect(dropShadow);
    }

    public CrossPaint(double height, double width, Color color) {
        super(width - 10, height - 10);
        double[] points = new double[]{
                10, 10,
                width / 2, height / 2,
                10, height - 10,
                width / 2, height / 2,
                width - 10, height - 10,
                width / 2, height / 2,
                width - 10, 10
        };
        for (double point : points) {
            this.getPoints().add(point);
        }
        setFill(color);
    }


}
