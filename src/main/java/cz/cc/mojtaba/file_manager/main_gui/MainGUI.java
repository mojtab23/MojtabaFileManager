package cz.cc.mojtaba.file_manager.main_gui;

import cz.cc.mojtaba.file_manager.GUIComponent;
import cz.cc.mojtaba.file_manager.file_browser.FileBrowserTab;
import cz.cc.mojtaba.file_manager.util.Configs;
import javafx.scene.control.TabPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by mojtab23 on 1/14/15.
 */
@Component
public class MainGUI extends TabPane implements GUIComponent {

    private volatile static MainGUI instance;
    //    private TabPane tabPane;
    @Autowired
    private HomeTab homeTab;
    private Configs configs;
    private Locale currentLocale;
    private FileBrowserTab fileBrowserTab;
    private boolean fileBrowserTabBuilt = false;
//    private MainGUI() {
//        super();
//    }

    public static MainGUI getInstance() {
        if (instance == null) {
            synchronized (MainGUI.class) {
                instance = new MainGUI();
            }
        }
        return instance;
    }

    public FileBrowserTab getFileBrowserTab() {
        return fileBrowserTab;
    }

    public boolean isFileBrowserTabBuilt() {
        return fileBrowserTabBuilt;
    }

    public void setFileBrowserTabBuilt(boolean fileBrowserTabBuilt) {
        this.fileBrowserTabBuilt = fileBrowserTabBuilt;
    }

    public void initialize() {
        //todo
        configs = Configs.getInstance();
        currentLocale = configs.getCurrentLocale();
//        homeTab = HomeTab.getInstance();
        homeTab.initialize();
        fileBrowserTab = FileBrowserTab.getInstance();
        fileBrowserTab.initialize();


    }

    public void build() {
        this.setMinSize(300, 200);
        //todo build root node and add configurations such as CSS style for all children in hierarchy
        homeTab.build();
        getTabs().add(homeTab);
    }

    public void buildFileBrowserTab() {
        fileBrowserTab.build();
        getTabs().add(1, fileBrowserTab);

    }

}
