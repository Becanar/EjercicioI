package org.example.ejercicioi.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Clase principal que extiende de Application para ejecutar la aplicación de la tabla de personas.
 */
public class Tabla extends Application {
    /**
     * Método de inicio que se ejecuta al lanzar la aplicación JavaFX.
     * Carga la interfaz gráfica desde un archivo FXML y configura el icono y título de la ventana.
     * @param stage El escenario principal de la aplicación.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        /*Properties connConfig = ConectorDB.loadProperties() ;
        String lang = connConfig.getProperty("language");
        Locale locale = new Locale.Builder().setLanguage(lang).build();
        ResourceBundle bundle = ResourceBundle.getBundle("/org/example/ejercicioi/languages/lang", locale);
        */FXMLLoader fxmlLoader = new FXMLLoader(Tabla.class.getResource("/org/example/ejercicioi/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/org/example/ejercicioi/style/style.css").toExternalForm());
        stage.setResizable(false);
        stage.setWidth(650);
        stage.setHeight(582);
        try {
            Image img = new Image(getClass().getResource("/org/example/ejercicioi/agenda.png").toString());
            stage.getIcons().add(img);
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen: " + e.getMessage());
        }

        stage.setTitle("PERSONAS");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Método main que lanza la aplicación.
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch();
    }
}
