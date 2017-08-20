package game;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MatchesCounter {

    private Integer human_won;
    private Integer computer_won;
    private StringProperty human_wonString;
    private StringProperty computer_wonString;

    public Integer getHuman_won() {
        return human_won;
    }

    public Integer getComputer_won() {
        return computer_won;
    }

    public void setHuman_won(Integer human_won) {
        this.human_won = human_won;
    }

    public void setComputer_won(Integer computer_won) {
        this.computer_won = computer_won;
    }

    public StringProperty human_wonStringProperty() {
        return human_wonString;
    }

    public StringProperty computer_wonStringProperty() {
        return computer_wonString;
    }

    public MatchesCounter() {
        this.human_won = 0;
        this.computer_won = 0;
        this.human_wonString = new SimpleStringProperty(":   " + human_won.toString());
        this.computer_wonString = new SimpleStringProperty(":   " + computer_won.toString());
    }
}
