package cz.cc.mojtaba.file.manager.file.browser;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

/**
 * Created with IntelliJ IDEA.
 * User: Mojtaba
 * Date: 23/01/2015
 * Time: 21:20
 */

public class FileBrowserToolBar extends VBox {

    private final FileBrowser fileBrowser;
    private final ToolBar toolBar;
    private final TextField addressBar;

    public FileBrowserToolBar() {
        super();
//        setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setPadding(new Insets(5, 5, 0, 5));
        fileBrowser = FileBrowser.getInstance();
        toolBar = new ToolBar();
//        toolBar.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
//        toolBar.prefWidth(600);
        toolBar.getItems().add(buildUpButton());
        addressBar = new TextField();
        addressBar.prefWidthProperty().bind(prefWidthProperty());
        fileBrowser.setCurrentDirectoryAddress(addressBar.textProperty());
        addressBar.setOnAction(event ->
                fileBrowser.setAddress(addressBar.getText()));
        getChildren().addAll(addressBar, toolBar);
        prefWidth(Double.MAX_VALUE);
        prefHeight(Double.MAX_VALUE);
    }


    private Button buildUpButton() {
        Button upButton = new Button("↑");
        upButton.setTooltip(new Tooltip("go to root directory."));
        upButton.setOnAction(event -> fileBrowser.upDir());
        return upButton;
    }

}
