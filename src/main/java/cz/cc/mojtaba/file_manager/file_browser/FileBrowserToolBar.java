package cz.cc.mojtaba.file_manager.file_browser;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

import java.nio.file.Path;

/**
 * Created with IntelliJ IDEA.
 * User: Mojtaba
 * Date: 23/01/2015
 * Time: 21:20
 */

public class FileBrowserToolBar extends VBox {

    private final FileBrowser fileBrowser;
    private final ToolBar toolBar;
    private final Property<Path> currentDir;
    private final TextField addressBar;

    public FileBrowserToolBar() {
        super();
//        setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setPadding(new Insets(5, 5, 0, 5));
        fileBrowser = FileBrowser.getInstance();
        currentDir = fileBrowser.CurrentDirectoryProperty();
        toolBar = new ToolBar();
//        toolBar.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
//        toolBar.prefWidth(600);
        toolBar.getItems().add(buildUpButton());
        addressBar = new TextField();
        addressBar.prefWidthProperty().bind(prefWidthProperty());
        fileBrowser.setCurrentDirectoryAddress(addressBar.textProperty());
//        addressBar.textProperty().addListener(changeText());
        addressBar.setOnAction(event ->
                fileBrowser.setAddress(addressBar.getText()));
        getChildren().addAll(addressBar, toolBar);
        prefWidth(Double.MAX_VALUE);
        prefHeight(Double.MAX_VALUE);
    }

    private ChangeListener<? super String> changeText() {
        return (observable, oldValue, newValue) -> {

            if (newValue != null) {
                fileBrowser.currentDirectoryAddressProperty().setValue(newValue);
            }
        };
    }


    private Button buildUpButton() {
        Button upButton = new Button("↑");
        upButton.setTooltip(new Tooltip("go to root directory."));
        upButton.setOnAction(event -> {
            currentDir.setValue(currentDir.getValue().getParent());
        });
        return upButton;
    }

}
