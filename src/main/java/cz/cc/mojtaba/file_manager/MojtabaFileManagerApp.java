package cz.cc.mojtaba.file_manager;

/**
 * Created by mojtab23 on 1/11/15.
 */

import cz.cc.mojtaba.file_manager.main_gui.MainGUI;
import cz.cc.mojtaba.file_manager.util.Configs;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MojtabaFileManagerApp extends Application {

    private Stage splashScreen;
    private MainGUI mainGUI;
    private Scene scene;
    private Configs configs;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        initialApp();
        showAppGUI(primaryStage);

    }

    /**
     * showing Main App GUI when the program ready to work.
     */
    private void showAppGUI(Stage primaryStage) {
        scene = new Scene(mainGUI, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(300);

        primaryStage.show();
        //todo show mainGUI and other gui's.
    }

    /**
     * show splash screen and initial the program and update the splash status.
     */
    private void initialApp() {
        startSplashScreen();

        //todo initial jobs and update status bar...
        configs = Configs.getInstance();
        buildMainGUI();
        splashScreen.close();
    }

    private void startSplashScreen() {
        splashScreen = new Stage();
        splashScreen.initStyle(StageStyle.TRANSPARENT);
        VBox box = new VBox();
        final Scene scene = new Scene(box, 256, 256);
        scene.setFill(new ImagePattern(new Image("/plain2.jpg")));
        splashScreen.setScene(scene);
        splashScreen.show();
    }

    /**
     * build mainGUI with initialize variables.
     */
    private void buildMainGUI() {
        mainGUI = MainGUI.getInstance();
        // initialize mainGUI with the properties manager...
        mainGUI.initialize();
        mainGUI.build();
    }


}
