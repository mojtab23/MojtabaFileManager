package cz.cc.mojtaba.file_manager.main_gui;

import cz.cc.mojtaba.file_manager.GUIComponent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Created by mojtab23 on 1/14/15.
 */
public class HomeTab extends Tab implements GUIComponent {

    private volatile static HomeTab instance;
    private final String DEFAULT_HOME_TAB_NAME = "Home";

    private MainGUIController controller;

    private String homeTabName = DEFAULT_HOME_TAB_NAME;

    //    private Tab tabNode;
    private HBox tabContent;

    private HomeTab() {
        super();
    }

    public static HomeTab getInstance() {
        if (instance == null) synchronized (HomeTab.class) {
            instance = new HomeTab();
        }
        return instance;
    }

    @Override
    public void initialize() {
        controller = MainGUIController.getInstance();
    }

    @Override
    public void build() {
        setText(homeTabName);
        setClosable(false);

        //todo add Buttons for every function of App
        Button fileBrowserButton = buildFileBrowserButton();


        tabContent = new HBox(fileBrowserButton);
        tabContent.setSpacing(15);
        tabContent.setPadding(new Insets(15, 15, 15, 15));
        tabContent.setAlignment(Pos.CENTER);
        contentProperty().set(tabContent);

    }

    private Button buildFileBrowserButton() {

        Button button = new Button("File Browser", new ImageView("/plain2.jpg"));
        button.setOnAction(controller.fileBrowserButtonAction());
        return button;
    }

}
