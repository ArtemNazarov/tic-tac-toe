package game;

import lists.Rectangles;
import visual.game.Field;

public class Computer {

    private Figure figure;
    private Difficulty difficulty;

    public Computer(Difficulty difficulty, Figure figure) {
        this.figure = figure;
        this.difficulty = difficulty;
    }

    public Figure getFigure() {
        return figure;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Field findOptimal(Game game) {
        Step step = game.getCell_count() == 3 ? find3x3(game) : find5x5(game);
        for (Field rectangle : Rectangles.rectangles) {
            if ((rectangle.getColumn() == step.getColumn()) && (rectangle.getRow() == step.getRow()))
                return rectangle;
        }
        return null;
    }

    public Step find3x3(Game game) {
        Step step = new Step(this.figure);
        Game3x3 game3x3 = new Game3x3(game, this.difficulty);
        if (this.figure == Figure.CROSS)
            game3x3.crosses_game(step);
        else
            game3x3.toes_game(step);
        return step;
    }



    public Step find5x5(Game game) {
        Step step = new Step(this.figure);
        Game5x5 game5x5 = new Game5x5(game, this.difficulty);
        if (this.figure == Figure.CROSS)
            game5x5.crosses_game(step);
        else
            game5x5.toes_game(step);
        return step;
    }

}
