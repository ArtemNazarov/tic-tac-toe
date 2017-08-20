package game;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game5x5 {

    private Game game;
    private Random random = new Random();
    private Difficulty difficulty;
    private Step[][] field;


    public Game5x5(Game game, Difficulty difficulty) {
        super();
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
            default:
                lastStepCrosses(current_step);
        }
    }

    public void firstStepCrosses(Step step) {
        int calculatedColumn, calculatedRow;
        Integer cellsInRow = field.length;
        if (this.difficulty == Difficulty.EASY) {
            calculatedColumn = random.nextInt(cellsInRow);
            calculatedRow = random.nextInt(cellsInRow);
        } else {
            calculatedColumn = random.nextInt(cellsInRow);
            calculatedRow = calculatedColumn == 2 ? calculatedColumn : random.nextInt(2) * 2;
        }
        step.setColumn(calculatedColumn);
        step.setRow(calculatedRow);
    }

    public void lastStepCrosses(Step current_step) {
        List<Step> crosses = findCrosses();
        List<Step> toes = findToes();
        if (!checkFour(current_step))
            if (!putNearFoundToe(current_step, toes, crosses))
                putAtFree(current_step);
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

    public void toes_game(Step current_step) {
        Integer current_crosses_stroke = this.game.getCurrent_stroke() / 2;
        if (current_crosses_stroke == 0)
            firstStepToes(current_step);
        else
            lastStepToes(current_step);
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
        if (field[2][2].getFigure() == null) {
            step.setColumn(2);
            step.setRow(2);
            return true;
        }
        return false;
    }

    public void putAtRandomCorner(Step step) {
        boolean empty = false;
        while (!empty) {
            int calculatedColumn = random.nextInt(2) * 4;
            int calculatedRow = random.nextInt(2) * 4;
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
        int putAtColumn = random.nextInt(3) * 2;
        step.setColumn(putAtColumn);
        int putAtRow = putAtColumn == 2 ? random.nextInt(3) * 2 : 2;
        step.setRow(putAtRow);
    }

    public boolean isAtCorner(Step step) {
        return ((step.getColumn() % 4 == 0) && (step.getRow() % 4 == 0));
    }

    public boolean isAtMid(Step step) {
        if (step.getColumn() == step.getRow())
            return (step.getColumn() == 2);
        return false;
    }


    public boolean checkFour(Step current_step) {
        int[] toeInRow = new int[field.length];
        int[] toeInCol = new int[field.length];
        int[] crossInRow = new int[field.length];
        int[] crossInCol = new int[field.length];
        int crossMainDiagonal = 0, toesMainDiagonal = 0, crossReverseDiag = 0, toesReverseDiag = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j].getFigure() == Figure.CROSS)
                    crossInRow[i]++;
                if (field[i][j].getFigure() == Figure.TOE)
                    toeInRow[i]++;
                if (field[j][i].getFigure() == Figure.CROSS)
                    crossInCol[i]++;
                if (field[j][i].getFigure() == Figure.TOE)
                    toeInCol[i]++;
                if (isOnMainDiagonal(field[i][j])) {
                    if (field[i][j].getFigure() == Figure.CROSS)
                        crossMainDiagonal++;
                    if (field[i][j].getFigure() == Figure.TOE)
                        toesMainDiagonal++;
                }
                if (isOnReverseDiagonal(field[i][j])) {
                    if (field[i][j].getFigure() == Figure.CROSS)
                        crossReverseDiag++;
                    if (field[i][j].getFigure() == Figure.TOE)
                        toesReverseDiag++;
                }
            }
        }
        for (int i = 0; i < toeInCol.length; i++) {
            if (toeInCol[i] == field.length - 1) {
                for (int j = 0; j < field.length; j++) {
                    if (field[j][i].getFigure() == null) {
                        current_step.setColumn(i);
                        current_step.setRow(j);
                        return true;
                    }
                }
            }
            if (toeInRow[i] == field.length - 1) {
                for (int j = 0; j < field.length; j++) {
                    if (field[i][j].getFigure() == null) {
                        current_step.setColumn(j);
                        current_step.setRow(i);
                        return true;
                    }
                }
            }

            if (toesMainDiagonal == field.length - 1) {
                for (int j = 0; j < field.length; j++) {
                    if (field[j][j].getFigure() == null) {
                        current_step.setColumn(j);
                        current_step.setRow(j);
                        return true;
                    }
                }
            }
            if (toesReverseDiag == field.length - 1) {
                for (int j = 0, k = field.length - 1; j < field.length; j++, k--) {
                    if (field[k][j].getFigure() == null) {
                        current_step.setColumn(j);
                        current_step.setRow(k);
                        return true;
                    }
                }
            }
            if (crossInCol[i] == field.length - 1) {
                for (int j = 0; j < field.length; j++) {
                    if (field[j][i].getFigure() == null) {
                        current_step.setColumn(i);
                        current_step.setRow(j);
                        return true;
                    }
                }
            }
            if (crossInRow[i] == field.length - 1) {
                for (int j = 0; j < field.length; j++) {
                    if (field[i][j].getFigure() == null) {
                        current_step.setColumn(j);
                        current_step.setRow(i);
                        return true;
                    }
                }
            }

            if (crossMainDiagonal == field.length - 1) {
                for (int j = 0; j < field.length; j++) {
                    if (field[j][j].getFigure() == null) {
                        current_step.setColumn(j);
                        current_step.setRow(j);
                        return true;
                    }
                }
            }
            if (crossReverseDiag == field.length - 1) {
                for (int j = 0, k = field.length - 1; j < field.length; j++, k--) {
                    if (field[k][j].getFigure() == null) {
                        current_step.setColumn(j);
                        current_step.setRow(k);
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public boolean isOnMainDiagonal(Step step) {
        return (step.getColumn() == step.getRow());
    }

    public boolean isOnReverseDiagonal(Step step) {
        return ((step.getColumn() + step.getRow()) == 4);
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
            step.setColumn(Math.abs(3 - computerFigures.get(chosenReverse).getColumn()));
            int putRow = step.getColumn() == 2 ? 2 : 4 - step.getColumn();
            step.setRow(putRow);
        }
        if (mainDiagonalFree) {
            step.setColumn(Math.abs(3 - computerFigures.get(chosenMainDiagonal).getColumn()));
            step.setRow(step.getColumn());
        }
        if (column_free) {
            step.setColumn(computerFigures.get(chosen).getColumn());
            for (int i = 0; i < field.length; i++) {
                if (field[i][step.getColumn()].getFigure() == null)
                    step.setRow(i);
            }
        }
        if (row_free) {
            step.setRow(computerFigures.get(chosen).getRow());
            for (int i = 0; i < field.length; i++) {
                if (field[step.getRow()][i].getFigure() == null)
                    step.setColumn(i);
            }
        }
        return (reverseDiagonalFree || mainDiagonalFree || column_free || row_free);
    }


    public void lastStepToes(Step current_step) {
        List<Step> crosses = findCrosses();
        List<Step> toes = findToes();
        if (!checkFour(current_step))
            if (!putNearFoundToe(current_step, toes, crosses))
                putAtFree(current_step);
    }


}
