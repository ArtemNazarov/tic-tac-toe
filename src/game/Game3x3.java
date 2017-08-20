package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game3x3 {

    private Game game;
    private Random random = new Random();
    private Difficulty difficulty;
    private Step[][] field;

    public Game3x3(Game game, Difficulty difficulty) {
        this.game = game;
        this.difficulty = difficulty;
        this.field = this.game.getGameField();
    }

    public void crosses_game(Step current_step) {
        Integer current_crosses_stroke = this.game.getCurrent_stroke() / 2;
        switch (current_crosses_stroke) {
            case 0:
                firstStepCrosses(current_step);
                break;
            case 1:
                secondStepCrosses(current_step);
                break;
            case 2:
                thirdStepCrosses(current_step);
                break;
            default:
                lastStepCrosses(current_step);
        }
    }

    public void firstStepCrosses(Step step) {
        int calculatedColumn, calculatedRow;
        Integer cellsInRow = this.game.getCell_count();
        if (this.difficulty == Difficulty.EASY) {
            calculatedColumn = random.nextInt(cellsInRow);
            calculatedRow = random.nextInt(cellsInRow);
        } else {
            calculatedColumn = random.nextInt(cellsInRow);
            calculatedRow = calculatedColumn == 1 ? calculatedColumn : random.nextInt(2) * 2;
        }
        step.setColumn(calculatedColumn);
        step.setRow(calculatedRow);
    }

    public void secondStepCrosses(Step current_step) {
        if (this.difficulty == Difficulty.EASY) {
            putAtFree(current_step);
        } else {
            if (!putAtCenter(current_step))
                putAtRandomCorner(current_step);
        }

    }

    public void thirdStepCrosses(Step current_step) {
        List<Step> crosses = findCrosses();
        List<Step> toes = findToes();
        if (!checkTwo(crosses, toes, current_step))
            if (!checkTwo(toes, crosses, current_step)) {
                if (this.difficulty == Difficulty.HARD) {
                    if (!putAtCenter(current_step)) {
                        putAtRandomCorner(current_step);
                    }
                } else {
                    if (!putNearFoundToe(current_step, crosses, toes))
                        putAtFree(current_step);
                }
            }
    }

    public void lastStepCrosses(Step current_step) {
        List<Step> crosses = findCrosses();
        List<Step> toes = findToes();
        if (!checkTwo(crosses, toes, current_step))
            if (!checkTwo(toes, crosses, current_step))
                if (!putNearFoundToe(current_step, crosses, toes))
                    putAtFree(current_step);
    }

    public void toes_game(Step current_step) {
        Integer current_toes_stroke = Math.round(this.game.getCurrent_stroke() / 2);
        switch (current_toes_stroke) {
            case 0:
                firstStepToes(current_step);
                break;
            case 1:
                secondStepToes(current_step);
                break;
            case 2:
            case 3:
                lastStepToes(current_step);
        }
    }

    public List<Step> findCrosses() {
        List<Step> crosses = new ArrayList<>();
        for (Step[] steps : field) {
            for (int i = 0; i < steps.length; i++) {
                if (steps[i].getFigure() == Figure.CROSS)
                    crosses.add(steps[i]);
            }
        }
        return crosses;
    }

    public List<Step> findToes() {
        List<Step> toes = new ArrayList<>();
        for (Step[] steps : field) {
            for (int i = 0; i < steps.length; i++) {
                if (steps[i].getFigure() == Figure.TOE)
                    toes.add(steps[i]);
            }
        }
        return toes;
    }

    public void firstStepToes(Step current_step) {
        List<Step> crosses = findCrosses();
        if (this.difficulty == Difficulty.EASY) {
            putAtFree(current_step);
        } else {
            if (isAtMid(crosses.get(0))) {
                putAtRandomCorner(current_step);
            } else {
                putAtCenter(current_step);
            }
        }
    }

    public boolean putAtCenter(Step step) {
        if (field[1][1].getFigure() == null) {
            step.setColumn(1);
            step.setRow(1);
            return true;
        }
        return false;
    }

    public void putAtRandomCorner(Step step) {
        boolean empty = false;
        while (!empty) {
            int calculatedColumn = random.nextInt(2) * 2;
            int calculatedRow = random.nextInt(2) * 2;
            if (field[calculatedRow][calculatedColumn].getFigure() == null) {
                empty = true;
                step.setColumn(calculatedColumn);
                step.setRow(calculatedRow);
            }
        }
    }

    public void putAtFree(Step current_step) {
        List<Step> freeSteps = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j].getFigure() == null) {
                    freeSteps.add(field[i][j]);
                }
            }
        }
        Step randomStep = freeSteps.get(random.nextInt(freeSteps.size()));
        current_step.setColumn(randomStep.getColumn());
        current_step.setRow(randomStep.getRow());
    }

    public void putAtRandomEdge(Step step) {
        step.setColumn(random.nextInt(field.length));
        int putAtRow = step.getColumn() % 2 == 0 ? 1 : random.nextInt(2) * 2;
        step.setRow(putAtRow);
    }

    public boolean isAtCorner(Step step) {
        if (step.getColumn() == step.getRow()) {
            return !(step.getColumn() == 1);
        } else {
            if ((step.getColumn() == (step.getRow() + 2)) || (step.getRow() == (step.getColumn() + 2)))
                return true;
        }
        return false;
    }

    public boolean isAtMid(Step step) {
        if (step.getColumn() == step.getRow())
            return (step.getColumn() == 1);
        return false;
    }

    public boolean isAtColumn(List<Step> figures, int column) {
        for (Step figure : figures) {
            if (figure.getColumn() == column)
                return true;
        }
        return false;
    }

    public boolean isAtRow(List<Step> figures, int row) {
        for (Step figure : figures) {
            if (figure.getRow() == row)
                return true;
        }
        return false;
    }

    public boolean checkTwo(List<Step> checkableFigures, List<Step> secondFigures, Step current_step) {
        for (int i = 0; i < checkableFigures.size(); i++) {
            for (int j = 0; j < checkableFigures.size(); j++) {
                if (i != j) {
                    if ((checkableFigures.get(i).getColumn() == checkableFigures.get(j).getColumn())
                            && (!isAtColumn(secondFigures, checkableFigures.get(j).getColumn()))) {
                        current_step.setColumn(checkableFigures.get(i).getColumn());
                        int putAtRow = findThird(checkableFigures.get(i).getRow(), checkableFigures.get(j).getRow());
                        current_step.setRow(putAtRow);
                        return true;
                    }
                    if ((checkableFigures.get(i).getRow() == checkableFigures.get(j).getRow())
                            && (!isAtRow(secondFigures, checkableFigures.get(j).getRow()))) {
                        current_step.setRow(checkableFigures.get(i).getRow());
                        int putAtCol = findThird(checkableFigures.get(i).getColumn(), checkableFigures.get(j).getColumn());
                        current_step.setColumn(putAtCol);
                        return true;
                    }
                    if ((isOnMainDiagonal(checkableFigures.get(i)) && isOnMainDiagonal(checkableFigures.get(j))
                            && !checkMainDiagonal(secondFigures))
                            || (isOnReverseDiagonal(checkableFigures.get(i)) && isOnReverseDiagonal(checkableFigures.get(j))
                            && !checkReverseDiagonal(secondFigures))) {
                        int putAtRow = findThird(checkableFigures.get(i).getRow(), checkableFigures.get(j).getRow());
                        int putAtCol = findThird(checkableFigures.get(i).getColumn(), checkableFigures.get(j).getColumn());
                        current_step.setRow(putAtRow);
                        current_step.setColumn(putAtCol);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int findThird(int first, int second) {
        int putAt = 0;
        switch (first) {
            case 0:
                putAt = second == 2 ? 1 : 2;
                break;
            case 1:
                putAt = second == 2 ? 0 : 2;
                break;
            case 2:
                putAt = second == 0 ? 1 : 0;
                break;
        }
        return putAt;
    }


    public boolean isOnMainDiagonal(Step step) {
        return (step.getColumn() == step.getRow());
    }

    public boolean isOnReverseDiagonal(Step step) {
        return (((step.getColumn() % 2 == 0) && (step.getRow() % 2 == 0) && (step.getColumn() != step.getRow()))
                || ((step.getColumn() == step.getRow()) && (step.getColumn() == 1)));
    }

    public boolean checkMainDiagonal(List<Step> figuresForCheck) {
        for (Step step : figuresForCheck) {
            if (isOnMainDiagonal(step))
                return true;
        }
        return false;
    }

    public boolean checkReverseDiagonal(List<Step> figuresForCheck) {
        for (Step step : figuresForCheck) {
            if (isOnReverseDiagonal(step))
                return true;
        }
        return false;
    }

    public boolean putNearFoundToe(Step step, List<Step> computerFigures, List<Step> playersFigures) {
        boolean column_free = false, row_free = false, mainDiagonalFree = true, reverseDiagonalFree = true,
                haveOnMainDiagonal = false, haveOnReverse = false;
        List<Integer> columns = new ArrayList<>();
        List<Integer> rows = new ArrayList<>();
        for (Step playersFigure : playersFigures) {
            columns.add(playersFigure.getColumn());
            rows.add(playersFigure.getRow());
        }
        int columnNotOccupied = 0, rowNotOccupied = 0, chosen = 0, chosenMainDiagonal = 0, chosenReverse = 0;
        for (int i = 0; i < computerFigures.size(); i++) {
            for (int j = 0; j < columns.size(); j++) {
                if (computerFigures.get(i).getColumn() != columns.get(j))
                    columnNotOccupied++;
                if (computerFigures.get(i).getRow() != rows.get(j))
                    rowNotOccupied++;
                if (isOnMainDiagonal(playersFigures.get(j)))
                    mainDiagonalFree = false;
                if (isOnReverseDiagonal(playersFigures.get(j)))
                    reverseDiagonalFree = false;
            }
            if (isOnMainDiagonal(computerFigures.get(i)))
                haveOnMainDiagonal = true;
            if (isOnReverseDiagonal(computerFigures.get(i)))
                haveOnReverse = true;
            if (columnNotOccupied == playersFigures.size()) {
                column_free = true;
                chosen = i;
            }
            if (rowNotOccupied == playersFigures.size()) {
                row_free = true;
                chosen = i;
            }
            if ((isOnMainDiagonal(computerFigures.get(i))) && mainDiagonalFree && haveOnMainDiagonal)
                chosenMainDiagonal = i;
            if ((isOnReverseDiagonal(computerFigures.get(i))) && reverseDiagonalFree && haveOnReverse)
                chosenReverse = i;
        }
        if (reverseDiagonalFree) {
            step.setColumn(Math.abs(1 - computerFigures.get(chosenReverse).getColumn()));
            int putRow = step.getColumn() == 1 ? 1 : 2 - step.getColumn();
            step.setRow(putRow);
        }
        if (mainDiagonalFree) {
            step.setColumn(Math.abs(1 - computerFigures.get(chosenMainDiagonal).getColumn()));
            step.setRow(step.getColumn());
        }
        if (column_free) {
            step.setColumn(computerFigures.get(chosen).getColumn());
            step.setRow(Math.abs(1 - computerFigures.get(chosen).getRow()));
        }
        if (row_free) {
            step.setRow(computerFigures.get(chosen).getRow());
            step.setColumn(Math.abs(1 - computerFigures.get(chosen).getColumn()));
        }
        return (reverseDiagonalFree || mainDiagonalFree || column_free || row_free);
    }


    public void secondStepToes(Step current_step) {
        List<Step> crosses = findCrosses();
        List<Step> toes = findToes();
        if (!checkTwo(crosses, toes, current_step)) {
            if (this.difficulty == Difficulty.EASY) {
                putNearFoundToe(current_step, toes, crosses);
            } else {
                int cross_at_corner = 0;
                for (int i = 0; i < crosses.size(); i++) {
                    if (isAtCorner(crosses.get(i)))
                        cross_at_corner++;
                }
                if (isAtMid(toes.get(0))) {
                    if (cross_at_corner == 2)
                        putAtRandomEdge(current_step);
                    else
                        putAtRandomCorner(current_step);
                } else {
                    if ((cross_at_corner == 1) && (field[1][1].getFigure() == Figure.CROSS))
                        putAtRandomCorner(current_step);
                    else
                        putNearFoundToe(current_step, toes, crosses);
                }
            }
        }
    }

    public void lastStepToes(Step current_step) {
        List<Step> crosses = findCrosses();
        List<Step> toes = findToes();
        if (!checkTwo(toes, crosses, current_step))
            if (!checkTwo(crosses, toes, current_step))
                if (!putNearFoundToe(current_step, toes, crosses))
                    putAtFree(current_step);
    }

}
