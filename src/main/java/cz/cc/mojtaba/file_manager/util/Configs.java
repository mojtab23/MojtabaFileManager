package cz.cc.mojtaba.file_manager.util;

import org.controlsfx.dialog.ExceptionDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;


/**
 * Created with IntelliJ IDEA.
 * User: Mojtaba
 * Date: 26/01/2015
 * Time: 16:09
 */
public class Configs {

    private static volatile Configs instance;
    private final Properties configs;
    private Locale currentLocale;

    private Configs() {
        configs = new Properties();
        loadConfigs();
    }

    public static Configs getInstance() {
        if (instance == null) synchronized (Configs.class) {
            instance = new Configs();
        }
        return instance;
    }

    public Locale getCurrentLocale() {
        if (currentLocale == null) {
            String language = configs.getProperty("current_language", "en");
            String country = configs.getProperty("current_country", "US");
            currentLocale = new Locale(language, country);
        }
        return currentLocale;
    }

    private void loadConfigs() {

        FileInputStream in;
        try {
            URL url = Configs.class.getResource("/configs.properties");
            in = new FileInputStream(new File(url.toURI()));
            configs.load(in);
            in.close();
        } catch (IOException | URISyntaxException e) {
            //todo logging...
            e.printStackTrace();
            showExceptionDialog(e);
        }
    }

    private void showExceptionDialog(Exception e) {
        ExceptionDialog exceptionDialog = new ExceptionDialog(e);
        exceptionDialog.setOnCloseRequest(event -> System.exit(1));
        exceptionDialog.show();
    }


}
