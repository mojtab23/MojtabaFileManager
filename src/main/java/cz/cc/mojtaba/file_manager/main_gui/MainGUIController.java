package cz.cc.mojtaba.file_manager.main_gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by mojtab23 on 1/17/15.
 */
@Component
public class MainGUIController {

    private static MainGUIController instance;
    @Autowired
    private MainGUI mainGUI;

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
