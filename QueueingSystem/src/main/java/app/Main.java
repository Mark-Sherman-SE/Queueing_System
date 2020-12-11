package app;

import app.controllers.AutoModeFormController;
import app.controllers.MenuFormController;
import app.controllers.SettingsFormController;
import app.controllers.StepModeFormController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        loadMenu();
        stage.show();
    }

    public void loadMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MenuForm.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Главное меню");
            ((MenuFormController) loader.getController()).setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSettings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SettingsForm.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Настройки");
            ((SettingsFormController) loader.getController()).setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAutoMode() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AutoModeForm.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Автоматический режим");
            ((AutoModeFormController) loader.getController()).setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadStepMode() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/StepModeForm.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Пошаговый режим");
            ((StepModeFormController) loader.getController()).setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
