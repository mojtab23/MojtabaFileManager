package cz.cc.mojtaba.file.manager;

import cz.cc.mojtaba.file.manager.main.gui.MainGUI;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * User: Mojtaba
 * Date: 28/01/2015
 * Time: 20:47
 */
@SpringBootApplication
@Configuration
public class ApplicationMain {
    private static Stage primaryStage;
    private Stage splashScreen;
    @Autowired
    private MainGUI mainGUI;

    public static void start(Stage primaryStage) {
        ApplicationMain.primaryStage = primaryStage;
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationMain.class);
        context.addApplicationListener(applicationEvent -> {


        });
    }

    @PostConstruct
    public void actualStart() {
        initialApp();
        showAppGUI(primaryStage);
    }

    /**
     * showing Main App GUI when the program ready to work.
     */
    private void showAppGUI(Stage primaryStage) {
        Scene scene = new Scene(mainGUI, 800, 600);
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
//        mainGUI = MainGUI.getInstance();
        // initialize mainGUI with the properties manager...
        mainGUI.initialize();
        mainGUI.build();
    }


}
