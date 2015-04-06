package cz.cc.mojtaba.file.manager.file.browser;

import cz.cc.mojtaba.file.manager.GUIComponent;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.awt.*;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

/**
 * Created by mojtab23 on 1/17/15.
 */
public class FileBrowser implements GUIComponent {

    private final static char[] INVALID_FILE_NAME_CHARS = {'\u0000', '\\', '/', '*', '?', ':', '|', '"', '<', '>'};
    private volatile static FileBrowser instance;
    private final String DEFAULT_DIRECTORY = System.getProperty("user.home");
    // is the path of the directory of browser tab and string in address bar.
    private Property<Path> currentDirectory;
    private StringProperty currentDirectoryAddress;
    private FileBrowserTab browserTab;
    private WatchDir watchDir;

    private FileBrowser() {
    }

    public static FileBrowser getInstance() {
        if (instance == null) synchronized (FileBrowser.class) {
            instance = new FileBrowser();
        }
        return instance;
    }

    public void setCurrentDirectoryAddress(StringProperty currentDirectoryAddress) {
        this.currentDirectoryAddress = currentDirectoryAddress;
    }

    public Path getCurrentDirectory() {
        return currentDirectory.getValue();
    }

    public void setCurrentDirectory(Path currentDirectory) {
        this.currentDirectory.setValue(currentDirectory);
    }

    @Override
    public void initialize() {
        currentDirectoryAddress = new SimpleStringProperty("");
        currentDirectoryAddress.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) currentDirectoryAddress.setValue(newValue);
        });
        currentDirectory = new SimpleObjectProperty<>();
        currentDirectory.addListener(new ChangeListener<Path>() {
            @Override
            public void changed(ObservableValue<? extends Path> observable, Path oldValue, Path newValue) {
                if (newValue == null) {
                    currentDirectory.setValue(oldValue);
                } else if (Files.isDirectory(newValue)) {
                    try {
                        if (watchDir != null) {
                            watchDir.cancel();
                        }
                        watchDir = new WatchDir(newValue, false, instance);
                        refreshDir(newValue);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


//            todo  if the newValue is a file path should show the parent dir of this file
                } else {
                    System.err.println("this is not a directory.");
                    currentDirectoryAddress.set(oldValue.toString());
                }
            }
        });
        browserTab = FileBrowserTab.getInstance();
    }

    private void refreshDir(Path newValue) throws IOException {
        browserTab.getTilePane().getChildren().clear();
        Files.newDirectoryStream(newValue).forEach(new Consumer<Path>() {
            @Override
            public void accept(Path path) {
                browserTab.getTilePane().getChildren().add(buildFileItem(path));
            }
        });
        currentDirectoryAddress.set(newValue.toString());
    }

    @Override
    public void build() {


        currentDirectory.setValue(FileSystems.getDefault().getPath(DEFAULT_DIRECTORY));
    }

    public void setAddress(String address) {
        setCurrentDirectory(Paths.get(address));
    }

    private FileItem buildFileItem(Path path) {
        return new FileItem(path);
    }


    public void open(Path path) {
        if (Files.isDirectory(path)) {
            currentDirectory.setValue(path);
        } else {
            if (Files.isRegularFile(path)
                    && Desktop.isDesktopSupported()) {
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
    }

    public void upDir() {
        setCurrentDirectory(getCurrentDirectory().getParent());
    }

    public Path rename(Path path, String newName) {
        boolean invalidName = false;
        for (char invalidFileNameChar : INVALID_FILE_NAME_CHARS)
            if (newName.indexOf(invalidFileNameChar) > -1) {
                invalidName = true;
                break;
            }
        if (!invalidName) {
            try {
                return Files.move(path, path.resolveSibling(newName));
            } catch (IOException e) {
                e.printStackTrace();
                return path;
            }
        }
        System.err.println("The name can't contain \\ / * ? : | \" < > characters.");//todo an exception dialog
        return path;
    }

    public void addNewFile(Path child) {
        browserTab.getTilePane().getChildren().add(buildFileItem(child));
    }

    public void deleteOldFile(Path child) {
        browserTab.getTilePane().getChildren().remove(child);
    }
}
