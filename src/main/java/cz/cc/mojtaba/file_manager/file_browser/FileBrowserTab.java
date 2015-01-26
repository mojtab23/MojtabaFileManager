package cz.cc.mojtaba.file_manager.file_browser;

import cz.cc.mojtaba.file_manager.GUIComponent;
import cz.cc.mojtaba.file_manager.main_gui.MainGUI;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

/**
 * Created by mojtab23 on 1/14/15.
 */
public class FileBrowserTab extends Tab implements GUIComponent {

    private static FileBrowserTab instance;
    private final String DEFAULT_TAB_NAME = "File browser";
    private FileBrowser fileBrowser;
    private String tabName;
    private ScrollPane scrollPane;
    private BorderPane mainPane;
    private MainGUI mainGUI;

    public TilePane getTilePane() {
        return tilePane;
    }

    public void setTilePane(TilePane tilePane) {
        this.tilePane = tilePane;
    }

    private TilePane tilePane;

    private FileBrowserTab() {
    }

    public static FileBrowserTab getInstance() {
        if (instance == null) synchronized (FileBrowserTab.class) {
            instance = new FileBrowserTab();
        }
        return instance;
    }


    @Override
    public void initialize() {
        fileBrowser = FileBrowser.getInstance();
        mainGUI = MainGUI.getInstance();
        fileBrowser.initialize();
        tabName = DEFAULT_TAB_NAME;


    }


    @Override
    public void build() {
        setText(tabName);
        buildContent();
        fileBrowser.build();
        setContent(mainPane);
        setOnCloseRequest(event -> mainGUI.setFileBrowserTabBuilt(false));

    }

    private void buildContent() {
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        tilePane = new TilePane(Orientation.HORIZONTAL);
        tilePane.setPadding(new Insets(5));
        tilePane.setHgap(5);
        tilePane.setVgap(5);
        scrollPane.setContent(tilePane);
        mainPane = new BorderPane();
        mainPane.setTop(new FileBrowserToolBar());
        mainPane.setCenter(scrollPane);

    }

}
