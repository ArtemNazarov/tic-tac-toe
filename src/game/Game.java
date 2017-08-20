package game;

public class Game {

    private MatchesCounter matchesCounter;
    private Integer cell_count;
    private Integer current_stroke;
    private Step[][] gameField;
    private Boolean end;

    public Game(Integer cell_count) {
        this.cell_count = cell_count;
        this.current_stroke = 0;
        this.gameField = new Step[cell_count][cell_count];
        this.matchesCounter = new MatchesCounter();
        this.end = false;
    }

    public Step[][] getGameField() {
        return gameField;
    }

    public Integer getCell_count() {
        return cell_count;
    }

    public MatchesCounter getMatchesCounter() {
        return matchesCounter;
    }

    public Integer getCurrent_stroke() {
        return current_stroke;
    }

    public Boolean getEnd() {
        return end;
    }

    public void setCurrent_stroke(Integer current_stroke) {
        this.current_stroke = current_stroke;
    }

    public void setMatchesCounter(MatchesCounter matchesCounter) {
        this.matchesCounter = matchesCounter;
    }

    public void setEnd(Boolean end) {
        this.end = end;
    }
}
