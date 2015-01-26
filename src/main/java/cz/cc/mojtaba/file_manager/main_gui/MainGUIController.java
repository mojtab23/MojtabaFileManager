package cz.cc.mojtaba.file_manager.main_gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Created by mojtab23 on 1/17/15.
 */
public class MainGUIController {

    private static MainGUIController instance;
    private final MainGUI mainGUI = MainGUI.getInstance();

    private MainGUIController() {
    }

    public static MainGUIController getInstance() {
        if (instance == null) synchronized (MainGUIController.class) {
            instance = new MainGUIController();
        }
        return instance;
    }

    public EventHandler<ActionEvent> fileBrowserButtonAction() {
        return event -> {
            if (!mainGUI.isFileBrowserTabBuilt()) {
                mainGUI.buildFileBrowserTab();
                mainGUI.setFileBrowserTabBuilt(true);
            } else {
                mainGUI.getSelectionModel().select(mainGUI.getFileBrowserTab());
            }

        };
    }
}
