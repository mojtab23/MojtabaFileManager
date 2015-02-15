package cz.cc.mojtaba.file_manager.file_browser;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

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
    private final Border defaultBorder;
    private final ContextMenu contextMenu;
    private final VBox graphic;
    private final TextArea editableItemName;
    private final Label itemNameLabel;
    private final SimpleStringProperty itemNameProperty;
    private final SimpleBooleanProperty editMode;
    private Property<Path> path;

    public FileItem(Path path) {
        this.path = new SimpleObjectProperty<>();
        this.path.addListener(onPathChanged());
        fileBrowser = FileBrowser.getInstance();
        defaultBorder = new Border(new BorderStroke(Color.TRANSPARENT,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        hoverBorder = new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        setBorder(defaultBorder);
        Tooltip tooltip = new Tooltip();
        setTooltip(tooltip);
//        setText(path.getFileName().toString());
        setContentDisplay(ContentDisplay.TOP);
        setMaxWidth(100);
        setPrefWidth(100);
        setMaxHeight(200);
//        setPrefHeight(200);
        setWrapText(true);
        setTextAlignment(TextAlignment.CENTER);
        setPadding(Insets.EMPTY);
        setAlignment(Pos.TOP_CENTER);
        setBackground(Background.EMPTY);
        editMode = new SimpleBooleanProperty(false);
        // menu on the right click.
        contextMenu = buildContextMenu();
        graphic = new VBox(5);
        graphic.setAlignment(Pos.TOP_CENTER);
        itemNameProperty = new SimpleStringProperty();
        itemNameLabel = buildItemNameLabel();
        editableItemName = buildEditableItemName();

        setContextMenu(contextMenu);
        setOnAction(event -> fileBrowser.open(this.path.getValue()));
        hoverProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue && !oldValue) {
                    setBorder(hoverBorder);
                } else if (!newValue && oldValue) {
                    setBorder(defaultBorder);
                }
            }
        });

        this.path.setValue(path);
        detectFileType();
        graphic.getChildren().add(1, editableItemName);
        graphic.getChildren().add(2, itemNameLabel);
        setGraphic(graphic);
    }

    private ChangeListener<? super Path> onPathChanged() {
        return (observable, oldValue, newValue) -> change(newValue);
    }

    private void change(Path newValue) {
        String string = newValue.getFileName().toString();
        getTooltip().setText(string);
        itemNameProperty.setValue(string);
    }

    private Label buildItemNameLabel() {
        Label label = new Label();
        label.setWrapText(true);
        label.textProperty().bind(itemNameProperty);
        label.visibleProperty().bind(editMode.not());
        label.managedProperty().bind(editMode.not());
        return label;
    }

    private TextArea buildEditableItemName() {
        TextArea itemName = new TextArea();
        itemName.textProperty().bindBidirectional(itemNameProperty);
        itemName.setBackground(Background.EMPTY);
        itemName.setBorder(defaultBorder);
        itemName.setEditable(true);
        itemName.setWrapText(true);
        itemName.setMinHeight(50);
        itemName.setDisable(false);
        itemName.visibleProperty().bind(editMode);//todo use textproperty and lable
        itemName.managedProperty().bind(editMode);
        itemName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                path.setValue(fileBrowser.rename(getPath(), itemName.getText()));
                change(path.getValue());
                editMode.set(false);
                event.consume();
            }
        });
        itemName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                editMode.set(false);
                change(path.getValue());
            }
        });
        return itemName;
    }

    public Path getPath() {
        return path.getValue();
    }

    public Property<Path> pathProperty() {
        return path;
    }

    private ContextMenu buildContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem renameItem = new MenuItem("Rename");//todo should use resource bundle.
        renameItem.setOnAction(rename());
        contextMenu.getItems().add(renameItem);
        return contextMenu;
    }

    private EventHandler<ActionEvent> rename() {
        return event -> {
            if (!editMode.get()) {
                editMode.set(true);
                editableItemName.requestFocus();
            }
        };
    }

    private void detectFileType() {
        if (Files.isDirectory(path.getValue())) {
            graphic.getChildren().add(0, new ImageView(dirIcon));
        } else graphic.getChildren().add(0, new ImageView(fileIcon));
        //todo

    }
}
