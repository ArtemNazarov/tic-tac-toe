package game;

public class Step {

    private Figure figure;
    private Integer row;
    private Integer column;

    public Step(Figure figure) {
        this.figure = figure;
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

    public Step(Figure figure, Integer cur_row, Integer cur_column) {
        this.figure = figure;
        this.row = cur_row;
        this.column = cur_column;
    }

    public Step(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    public Step() {
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

}
