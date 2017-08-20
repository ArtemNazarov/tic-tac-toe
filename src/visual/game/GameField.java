package visual.game;

import game.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import lists.Rectangles;
import visual.main.Main;
import visual.dialogs.DialogManager;
import visual.figures.CrossPaint;
import visual.figures.Toe;
import visual.mainmenu.MainMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameField {

    private Computer computer;
    private Game game;
    GridPane gameGrid = new GridPane();
    private Integer BUTTON_Y = 485;
    private Integer GRID_Y_LAYOUT = 110;
    private Integer GAMERS_GROUP_X_LAYOUT = 50;
    ResourceBundle bundle = ResourceBundle.getBundle("bundles.ResBundle");
    private ObservableList<Node> figures = FXCollections.observableArrayList();
    private Boolean SHOW_WARNING_DIALOG = true;


    public GameField(Difficulty difficulty, Integer cell_count, Figure comp) {
        this.computer = new Computer(difficulty, comp);
        this.game = new Game(cell_count);
        Step[][] gameField = this.game.getGameField();
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                gameField[i][j] = new Step(i, j);
            }
        }
    }

    private void createField() {
        gameGrid.setPrefSize(342, 300);
        gameGrid.setGridLinesVisible(true);
        gameGrid.setLayoutX(286);
        gameGrid.setLayoutY(GRID_Y_LAYOUT);
        List<ColumnConstraints> columns = new ArrayList<>();
        for (int i = 0; i < this.game.getCell_count(); i++) {
            columns.add(new ColumnConstraints());
        }
        List<RowConstraints> rows = new ArrayList<>();
        for (int i = 0; i < this.game.getCell_count(); i++) {
            rows.add(new RowConstraints());
        }
        gameGrid.getColumnConstraints().addAll(columns);
        for (ColumnConstraints columnConstraints : gameGrid.getColumnConstraints()) {
            columnConstraints.setPrefWidth(gameGrid.getPrefWidth() / this.game.getCell_count());
        }
        gameGrid.getRowConstraints().addAll(rows);
        for (RowConstraints rowConstraints : gameGrid.getRowConstraints()) {
            rowConstraints.setPrefHeight(gameGrid.getPrefWidth() / this.game.getCell_count());
        }
    }

    private void createGrid() {
        boolean first = false;
        if (Rectangles.rectangles.size() == 0)
            first = true;
        for (int i = 0; i < this.game.getCell_count(); i++) {
            for (int j = 0; j < this.game.getCell_count(); j++) {
                double rect_width = gameGrid.getColumnConstraints().get(0).getPrefWidth();
                double rect_height = gameGrid.getRowConstraints().get(0).getPrefHeight();
                Field rect = new Field(rect_width, rect_height, j, i);
                rect.setOnMouseClicked(event -> {
                    step(rect);
                });
                rect.setOpacity(0);
                if (first)
                    Rectangles.rectangles.add(rect);
                addRect(rect, i, j);
            }
        }
    }

    private void step(Field rect) {
        if (addFigureWithCheck(rect) && !checkStep()) {
            addFigureComputer(this.computer.findOptimal(this.game));
            checkStep();
        }
    }

    private void addFigureComputer(Field rect) {
        if (this.computer.getFigure() == Figure.TOE)
            addCircle(rect);
        else
            addCross(rect);
        this.game.setCurrent_stroke(this.game.getCurrent_stroke() + 1);
    }

    private boolean addFigureWithCheck(Field rect) {
        if (this.game.getGameField()[rect.getRow()][rect.getColumn()].getFigure() == null) {
            if (this.computer.getFigure() == Figure.TOE)
                addCross(rect);
            else
                addCircle(rect);
            this.game.setCurrent_stroke(this.game.getCurrent_stroke() + 1);
            return true;
        }
        else
            DialogManager.showErrorDialog(bundle.getString("key.filled_cell"));
        return false;
    }

    private boolean checkStep() {
        if (this.game.getCurrent_stroke() == Math.pow(this.game.getCell_count(), 2))
            this.game.setEnd(true);
        Integer res = checkGame();
        String result = checkResult(res);
        if (this.game.getEnd()) {
            boolean repeat = DialogManager.showConfirmDialog(bundle.getString("key.end") + result + bundle.getString("key.repeat"));
            if (repeat) {
                new_game();
                return true;
            }
            else {
                MainMenu menu = new MainMenu();
                Main.getPrimaryStg().setScene(menu.createScene());
            }
        }
        return false;
    }

    private void addRect(Node figure, int col, int row) {
        ObservableList<Node> content = gameGrid.getChildren();
        content.add(figure);
        GridPane.setConstraints(figure, col, row);
        GridPane.setHalignment(figure, HPos.CENTER);
    }

    public Scene showField() {
        AnchorPane anchorP = new AnchorPane();
        anchorP.setPrefSize(873.0, 550.0);
        createField();
        Group gamers = createCountGroup();
        Button repeat_game = createRepeatGameButton();
        Button exitToMenu = createExitToMenuButton();
        anchorP.getChildren().addAll(gameGrid, repeat_game, exitToMenu, gamers);
        anchorP.getStylesheets().add("file:resources/themes/gameFrame.css");
        anchorP.getStyleClass().add("background");
        Scene field = new Scene(anchorP);
        createGrid();
        if (this.computer.getFigure() == Figure.CROSS)
            addFigureComputer(this.computer.findOptimal(this.game));
        return field;
    }

    private Group createCountGroup() {
        Integer labels_layoutX = 90;
        Group gamersImagesGroup = new Group();
        Image human = new Image("file:resources/icons/human.png");
        ImageView humanView = new ImageView(human);
        Image computer = new Image("file:resources/icons/laptop.png");
        ImageView computerView = new ImageView(computer);
        Image puzzle = new Image("file:resources/icons/puzzle.png");
        ImageView diffView = new ImageView(puzzle);
        Label comp_won = new Label();
        Label human_won = new Label();
        Label comp_difficulty = new Label();
        comp_won.textProperty().bind(this.game.getMatchesCounter().computer_wonStringProperty());
        human_won.textProperty().bind(this.game.getMatchesCounter().human_wonStringProperty());
        String diff = this.computer.getDifficulty() == Difficulty.EASY ? bundle.getString("key.easy") : bundle.getString("key.hard");
        diff = ":   " + diff;
        comp_difficulty.setText(diff);
        gamersImagesGroup.getChildren().addAll(computerView, humanView, diffView, comp_won, human_won, comp_difficulty);
        gamersImagesGroup.setLayoutX(GAMERS_GROUP_X_LAYOUT);
        gamersImagesGroup.setLayoutY(GRID_Y_LAYOUT + 90);
        computerView.setLayoutY(90);
        diffView.setLayoutY(180);
        human_won.setLayoutX(labels_layoutX);
        human_won.setLayoutY(15);
        comp_won.setLayoutX(labels_layoutX);
        comp_won.setLayoutY(110);
        comp_difficulty.setLayoutX(labels_layoutX);
        comp_difficulty.setLayoutY(205);
        return gamersImagesGroup;
    }

    private Button createRepeatGameButton() {
        Button rep = new Button(bundle.getString("key.repeat_game"));
        rep.setLayoutX(286);
        rep.setLayoutY(BUTTON_Y);
        rep.setOnAction(event -> {
            if (SHOW_WARNING_DIALOG) {
                Integer new_game = DialogManager.showWarningDialog(bundle.getString("key.start_again"));
                switch (new_game) {
                    case 0:
                        event.consume();
                        break;
                    case 1:
                        SHOW_WARNING_DIALOG = false;
                    case 2:
                        new_game();
                        break;
                }
            } else
                new_game();
        });
        return rep;
    }

    private Button createExitToMenuButton() {
        Button exitToMenu = new Button(bundle.getString("key.exit_to_menu"));
        exitToMenu.setLayoutX(456);
        exitToMenu.setLayoutY(BUTTON_Y);
        exitToMenu.setOnAction(event -> {
            MainMenu menu = new MainMenu();
            Main.getPrimaryStg().setScene(menu.createScene());
        });
        return exitToMenu;
    }

    private Integer checkGame() {
        int not_over = 0;
        int crosses_win = 1;
        int toes_win = 2;
        int draw = 3;
        for (int i = 0; i < this.game.getGameField().length; i++) {
            int crosses_row = 0, crosses_column = 0, toes_row = 0, toes_column = 0;
            for (int j = 0; j < this.game.getGameField().length; j++) {
                if (this.game.getGameField()[i][j].getFigure() == Figure.CROSS)
                    crosses_row++;
                if (this.game.getGameField()[i][j].getFigure() == Figure.TOE)
                    toes_row++;
                if (this.game.getGameField()[j][i].getFigure() == Figure.CROSS)
                    crosses_column++;
                if (this.game.getGameField()[j][i].getFigure() == Figure.TOE)
                    toes_column++;
            }
            if ((crosses_row == this.game.getGameField().length) || (crosses_column == this.game.getGameField().length))
                return crosses_win;
            if ((toes_row == this.game.getGameField().length) || (toes_column == this.game.getGameField().length))
                return toes_win;
        }
        int crosses_diagonal = 0, toes_diagonal = 0, crossesAnotherDiagonal = 0, toesAnotherDiagonal = 0;
        for (int i = 0, j = 0, k = this.game.getGameField().length - 1; i < this.game.getGameField().length; i++, j++, k--) {
            if (this.game.getGameField()[i][j].getFigure() == Figure.CROSS)
                crosses_diagonal++;
            if (this.game.getGameField()[i][j].getFigure() == Figure.TOE)
                toes_diagonal++;
            if (this.game.getGameField()[k][j].getFigure() == Figure.CROSS)
                crossesAnotherDiagonal++;
            if (this.game.getGameField()[k][j].getFigure() == Figure.TOE)
                toesAnotherDiagonal++;
        }
        if ((crosses_diagonal == this.game.getGameField().length) || (crossesAnotherDiagonal == this.game.getGameField().length))
            return crosses_win;
        if ((toes_diagonal == this.game.getGameField().length) || (toesAnotherDiagonal == this.game.getGameField().length))
            return toes_win;
        if (this.game.getEnd())
            return draw;
        return not_over;
    }

    private String checkResult(Integer res) {
        String result = "";
        String lose = bundle.getString("key.lose");
        String win = bundle.getString("key.win");
        this.game.setEnd(true);
        switch (res) {
            case 0:
                this.game.setEnd(false);
                break;
            case 1:
                if (this.computer.getFigure() == Figure.CROSS) {
                    result = lose;
                    incrementCompCount();
                } else {
                    result = win;
                    incrementHumanCount();
                }
                break;
            case 2:
                if (this.computer.getFigure() == Figure.TOE) {
                    result = lose;
                    incrementCompCount();
                } else {
                    result = win;
                    incrementHumanCount();
                }
                break;
            case 3:
                result = bundle.getString("key.draw");
                break;
        }
        return result;
    }

    private void incrementHumanCount() {
        this.game.getMatchesCounter().setHuman_won(this.game.getMatchesCounter().getHuman_won() + 1);
        this.game.getMatchesCounter().human_wonStringProperty().setValue(":   " + String.valueOf(this.game.getMatchesCounter().getHuman_won()));
    }

    private void incrementCompCount() {
        this.game.getMatchesCounter().setComputer_won(this.game.getMatchesCounter().getComputer_won() + 1);
        this.game.getMatchesCounter().computer_wonStringProperty().setValue(":   " + String.valueOf(this.game.getMatchesCounter().getComputer_won()));
    }

    private void new_game() {
        for (int i = 0; i < this.game.getGameField().length; i++) {
            for (int j = 0; j < this.game.getGameField().length; j++) {
                this.game.getGameField()[i][j].setFigure(null);
            }
        }
        ObservableList<Node> content = gameGrid.getChildren();
        for (Node figure : this.figures) {
            content.remove(figure);
        }
        this.figures.clear();
        this.game.setCurrent_stroke(0);
        this.game.setEnd(false);
        if (this.computer.getFigure() == Figure.CROSS)
            addFigureComputer(this.computer.findOptimal(this.game));

    }

    private void addCross(Field rect) {
        CrossPaint cross = new CrossPaint(rect.getWidth(), rect.getHeight());
        addFigure(cross, rect);
        this.game.getGameField()[rect.getRow()][rect.getColumn()].setFigure(Figure.CROSS);
    }

    private void addFigure(Node figure, Field rect) {
        ObservableList<Node> content = gameGrid.getChildren();
        content.add(figure);
        this.figures.add(figure);
        GridPane.setConstraints(figure, rect.getColumn(), rect.getRow());
        GridPane.setHalignment(figure, HPos.CENTER);
    }

    private void addCircle(Field rect) {
        Toe toe = new Toe(rect.getWidth() / 2, rect.getHeight() / 2, (rect.getWidth() - 20) / 2, Color.GREENYELLOW,
                Color.WHITE, Color.BLACK);
        addFigure(toe, rect);
        this.game.getGameField()[rect.getRow()][rect.getColumn()].setFigure(Figure.TOE);
    }
}
