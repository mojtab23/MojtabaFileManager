package cz.cc.mojtaba.file_manager.file_browser;

import cz.cc.mojtaba.file_manager.GUIComponent;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by mojtab23 on 1/17/15.
 */
public class FileBrowser implements GUIComponent {

    private volatile static FileBrowser instance;
    private final String DEFAULT_DIRECTORY = System.getProperty("user.home");
    private final Image image = new Image("/Generic-icon.png");
    private final Image image1 = new Image("/FileIcon.png");
    private Property<Path> currentDirectory;
    private StringProperty currentDirectoryAddress;
    private FileBrowserTab browserTab;
    private List<Path> dirContent;

    private FileBrowser() {
    }

    public static FileBrowser getInstance() {
        if (instance == null) synchronized (FileBrowser.class) {
            instance = new FileBrowser();
        }
        return instance;
    }

    public String currentDirectoryAddress() {
        return currentDirectoryAddress.get();
    }

    public StringProperty currentDirectoryAddressProperty() {
        return currentDirectoryAddress;
    }

    private ImageView buildFileImage() {
        return new ImageView(image1);
    }

    private ImageView buildDirectoryImage() {
        return new ImageView(image);
    }

    public List<Path> getDirContent() {
        return dirContent;
    }

    public Path getCurrentDirectory() {
        return currentDirectory.getValue();
    }

    public Property<Path> CurrentDirectoryProperty() {
        return currentDirectory;
    }

    public String getDEFAULT_DIRECTORY() {
        return DEFAULT_DIRECTORY;
    }

    @Override
    public void initialize() {
        currentDirectoryAddress = new SimpleStringProperty("");
        currentDirectory = new SimpleObjectProperty<>();
        currentDirectory.addListener(new ChangeListener<Path>() {
            @Override
            public void changed(ObservableValue<? extends Path> observable, Path oldValue, Path newValue) {
                if (newValue == null) {
                    currentDirectory.setValue(oldValue);
                } else if (Files.isDirectory(newValue)) {
                    try {
                        browserTab.getTilePane().getChildren().clear();
                        Files.newDirectoryStream(newValue).forEach(new Consumer<Path>() {
                            @Override
                            public void accept(Path path) {
//                            dirContent.add(path);
                                browserTab.getTilePane().getChildren().add(buildFileItem(path));
                            }
                        });
                        currentDirectoryAddress.setValue(newValue.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else System.err.println("this is not a directory.");

            }
        });
        browserTab = FileBrowserTab.getInstance();
    }

    @Override
    public void build() {


        currentDirectory.setValue(FileSystems.getDefault().getPath(DEFAULT_DIRECTORY));
    }


    private FileItem buildFileItem(Path path) {
        return new FileItem(path);
    }


}
