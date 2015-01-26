package cz.cc.mojtaba.file_manager.main_gui;

import cz.cc.mojtaba.file_manager.GUIComponent;
import cz.cc.mojtaba.file_manager.file_browser.FileBrowserTab;
import javafx.scene.control.TabPane;

/**
 * Created by mojtab23 on 1/14/15.
 */
public class MainGUI extends TabPane implements GUIComponent {

    private volatile static MainGUI instance;
    //    private TabPane tabPane;
    private HomeTab homeTab;

    public FileBrowserTab getFileBrowserTab() {
        return fileBrowserTab;
    }

    private FileBrowserTab fileBrowserTab;
    private boolean fileBrowserTabBuilt = false;


    private MainGUI() {
        super();
    }

    public static MainGUI getInstance() {
        if (instance == null) {
            synchronized (MainGUI.class) {
                instance = new MainGUI();
            }
        }
        return instance;
    }

    public boolean isFileBrowserTabBuilt() {
        return fileBrowserTabBuilt;
    }

    public void setFileBrowserTabBuilt(boolean fileBrowserTabBuilt) {
        this.fileBrowserTabBuilt = fileBrowserTabBuilt;
    }

    public void initialize() {
        //todo

        homeTab = HomeTab.getInstance();
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
        getTabs().add(1,fileBrowserTab);

    }

}
