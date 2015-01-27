package cz.cc.mojtaba.file_manager.file_browser;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by mojtab23 on 1/23/15.
 */
public class FileItem extends Button {

    private final Image dirIcon = new Image("/icon-95-folder.png");
    private final Image fileIcon = new Image("icon-54-document.png");
    private final FileBrowser fileBrowser;
    private final Border hoverBorder;
    private Property<Path> path;


    public FileItem(Path path) {
        this.path = new SimpleObjectProperty<>();
        this.path.setValue(path);
        fileBrowser = FileBrowser.getInstance();
        hoverBorder = new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        detectFileType();
        setTooltip(new Tooltip(path.getFileName().toString()));
        setText(path.getFileName().toString());
        setContentDisplay(ContentDisplay.TOP);
        setMaxWidth(100);
        setMaxHeight(200);
        setWrapText(true);
        setTextAlignment(TextAlignment.CENTER);
        setPadding(Insets.EMPTY);
        setAlignment(Pos.TOP_CENTER);
        setBackground(Background.EMPTY);
        setOnAction(event -> {
            if (Files.isDirectory(path)) {
                fileBrowser.CurrentDirectoryProperty().setValue(path);
            } else {
                if (Files.isRegularFile(path) && Desktop.isDesktopSupported()) {
                    try {
                        Desktop desktop = Desktop.getDesktop();
                        if (desktop.isSupported(Desktop.Action.OPEN)) {
                            desktop.open(path.toFile());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        hoverProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue && !oldValue) {
                    setBorder(hoverBorder);
                } else if (!newValue && oldValue) {
                    setBorder(Border.EMPTY);
                }
            }
        });

    }

    private void detectFileType() {
        if (Files.isDirectory(path.getValue())) {
            setGraphic(new ImageView(dirIcon));
        } else setGraphic(new ImageView(fileIcon));
        //todo

    }
}
