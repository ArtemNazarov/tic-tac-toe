package visual.mainmenu;

import game.Difficulty;
import game.Figure;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import visual.figures.CrossPaint;
import visual.main.Main;
import visual.figures.Toe;
import visual.game.GameField;
import java.util.ResourceBundle;

public class MainMenu {

    private static final double FONT_SIZE_BUTTONS = 24.0;
    private static final double FONT_SIZE_LABELS = 21.0;
    private static final Color LABELS_COLOUR = Color.web("f25b03");
    private static final double CHOICE_WIDTH = 114.0;
    private static final double CHOICE_HEIGHT = 31.0;
    private static final double CHOICE_X_LAYOUT = 203;
    private ChoiceBox difficultyChoice;
    private ChoiceBox fieldSizeChoice;
    private ChoiceBox figureChoice;
    private ResourceBundle bundle = ResourceBundle.getBundle("bundles.ResBundle");

    public Scene createScene() {
        AnchorPane anchorP = new AnchorPane();
        anchorP.setPrefHeight(584.0);
        anchorP.setPrefWidth(682.0);
        Label progTitleLabel = createTitleLabel();
        Group menuGroup = createMenuGroup();
        Group logoGroup = createLogoGroup();
        anchorP.getChildren().addAll(menuGroup, logoGroup, progTitleLabel);
        anchorP.getStylesheets().add("file:resources/themes/mainMenu.css");
        anchorP.getStyleClass().add("background");
        Scene menuScene = new Scene(anchorP);
        return menuScene;
    }

    private Group createMenuGroup() {
        Button startGameButton = createStartButton();
        Button exitButton = createExitButton();
        Label fieldSizeLabel = createFieldSizeLabel();
        Label difficultyLabel = createDifficultyLabel();
        Label figureLabel = createFigureLabel();
        createDifficultyChoice();
        createFieldSizeChoice();
        createFigureChoice();
        Group menuGroup = new Group(startGameButton, exitButton, difficultyLabel, fieldSizeLabel, figureLabel, difficultyChoice, fieldSizeChoice, figureChoice);
        menuGroup.setLayoutX(170);
        menuGroup.setLayoutY(264);
        return menuGroup;
    }

    private Group createLogoGroup() {
        Group group = new Group();
        Label versus = createLabelVs();
        CrossPaint cross = new CrossPaint(110, 110, Color.DODGERBLUE, Color.web("1F20AF"), Color.WHITE);
        cross.setLayoutX(0);
        cross.setLayoutY(0);
        Toe toe = new Toe(270, 50, 50, Color.RED, Color.web("7C1F5B"), Color.WHITE);
        group.getChildren().addAll(versus, cross, toe);
        group.setLayoutX(190);
        group.setLayoutY(125);
        return group;
    }

    private Label createTitleLabel() {
        Label progTitleLabel = new Label("Tic-tac-toe");
        progTitleLabel.setLayoutX(196);
        progTitleLabel.setLayoutY(37);
        progTitleLabel.setPrefHeight(73);
        progTitleLabel.setPrefWidth(339);
        progTitleLabel.setTextFill(Color.web("ff9f22"));
        progTitleLabel.setFont(Font.font("Arial Black", 50));
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.web("77ff86"));
        dropShadow.setOffsetX(10d);
        dropShadow.setOffsetY(4d);
        InnerShadow shadow = new InnerShadow();
        shadow.setRadius(6d);
        shadow.setColor(Color.web("ff114c"));
        dropShadow.setInput(shadow);
        progTitleLabel.setEffect(dropShadow);
        return progTitleLabel;
    }

    private Button createExitButton() {
        Button exitButton = new Button(bundle.getString("key.exit"));
        exitButton.setPrefSize(246.0, 25.0);
        exitButton.setLayoutX(62.0);
        exitButton.setLayoutY(260.0);
        exitButton.setFont(Font.font(FONT_SIZE_BUTTONS));
        exitButton.setMnemonicParsing(false);
        exitButton.setOnAction(event -> {
            Main.closeProgram();
        });
        return exitButton;
    }

    private Button createStartButton() {
        Button startGameButton = new Button(bundle.getString("key.start_game"));
        startGameButton.setLayoutX(68);
        startGameButton.setPrefSize(246.0, 25.0);
        startGameButton.setFont(Font.font(FONT_SIZE_BUTTONS));
        startGameButton.setMnemonicParsing(false);
        startGameButton.setOnAction(event -> {
            startGame();
        });
        return startGameButton;
    }

    private void createDifficultyChoice() {
        ObservableList<String> values = FXCollections.observableArrayList();
        values.addAll(bundle.getString("key.easy"), bundle.getString("key.hard"));
        difficultyChoice = new ChoiceBox(values);
        difficultyChoice.getSelectionModel().selectFirst();
        difficultyChoice.setPrefSize(CHOICE_WIDTH, CHOICE_HEIGHT);
        difficultyChoice.setLayoutX(CHOICE_X_LAYOUT);
        difficultyChoice.setLayoutY(67);
    }

    private void createFieldSizeChoice() {
        ObservableList<String> values = FXCollections.observableArrayList();
        values.addAll("3x3", "5x5");
        fieldSizeChoice = new ChoiceBox(values);
        fieldSizeChoice.getSelectionModel().selectFirst();
        fieldSizeChoice.setPrefSize(CHOICE_WIDTH, CHOICE_HEIGHT);
        fieldSizeChoice.setLayoutX(CHOICE_X_LAYOUT);
        fieldSizeChoice.setLayoutY(136);
    }

    private void createFigureChoice() {
        ObservableList<String> values = FXCollections.observableArrayList();
        values.addAll("X", "O");
        figureChoice = new ChoiceBox(values);
        figureChoice.getSelectionModel().selectFirst();
        figureChoice.setPrefSize(CHOICE_WIDTH, CHOICE_HEIGHT);
        figureChoice.setLayoutX(CHOICE_X_LAYOUT);
        figureChoice.setLayoutY(205);
    }

    private Label createFieldSizeLabel() {
        Label fieldSizeLabel = new Label(bundle.getString("key.field_size"));
        fieldSizeLabel.setFont(Font.font(FONT_SIZE_LABELS));
        fieldSizeLabel.setLayoutY(141.0);
        fieldSizeLabel.setTextFill(LABELS_COLOUR);
        return fieldSizeLabel;
    }

    private Label createDifficultyLabel() {
        Label difficultyLabel = new Label(bundle.getString("key.difficulty"));
        difficultyLabel.setFont(Font.font(FONT_SIZE_LABELS));
        difficultyLabel.setLayoutY(71);
        difficultyLabel.setTextFill(LABELS_COLOUR);
        return difficultyLabel;
    }

    private Label createFigureLabel() {
        Label figureLabel = new Label(bundle.getString("key.player"));
        figureLabel.setFont(Font.font(FONT_SIZE_LABELS));
        figureLabel.setLayoutY(211);
        figureLabel.setTextFill(LABELS_COLOUR);
        return figureLabel;
    }

    private Label createLabelVs() {
        Label versus = new Label("VS");
        versus.setFont(Font.font(40));
        versus.setTextFill(LABELS_COLOUR);
        versus.setLayoutX(135);
        versus.setLayoutY(23);
        return versus;
    }

    public void startGame() {
        Integer size = Integer.parseInt(fieldSizeChoice.getSelectionModel().getSelectedItem().toString().substring(0, 1));
        Integer index = difficultyChoice.getSelectionModel().getSelectedIndex();
        Difficulty difficulty;
        Figure compPlays;
        difficulty = index == 0? Difficulty.EASY : Difficulty.HARD;
        index = figureChoice.getSelectionModel().getSelectedIndex();
        compPlays = index == 0? Figure.TOE : Figure.CROSS;
        GameField game = new GameField(difficulty, size, compPlays);
        Main.getPrimaryStg().setScene(game.showField());
    }

}
