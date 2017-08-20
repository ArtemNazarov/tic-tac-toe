package visual.game;


import javafx.scene.shape.Rectangle;

public class Field extends Rectangle {

    private Integer row;
    private Integer column;


    public Field(Double width, Double height, Integer row, Integer column) {
        super(width, height);
        this.row = row;
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }
}
