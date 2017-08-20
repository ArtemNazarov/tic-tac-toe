package visual.main;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import visual.mainmenu.MainMenu;

import java.io.File;

public class Main extends Application {

    private static Stage primaryStg;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tic-Tac-Toe");
        MainMenu menu = new MainMenu();
        primaryStage.setScene(menu.createScene());
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(new File("resources/icons/tic-tac-toe.png").toURI().toURL().toString()));
        primaryStg = primaryStage;
        primaryStage.show();

    }

    public static Stage getPrimaryStg() {
        return primaryStg;
    }

    public static void closeProgram() {
        primaryStg.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
