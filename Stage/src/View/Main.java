package View;

import java.beans.XMLDecoder;
import javafx.application.Application;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ModuleLayer.Controller;

import Controllers.Controler;
import Model.User;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

	User user;

	@Override
	public void start(Stage primaryStage) { 
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("interface1.fxml"));
			user = new User();
			reloadUserData();

			Controler controller = new Controler(user);

			loader.setController(controller);

			Parent root = loader.load();
			Scene scene = new Scene(root);

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent e) {		
					System.exit(0);
				}
			});

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reloadUserData() { // Méthode permettant de reload les paramètres sauvegardés 

		File fichier = new File("user.xml");
		XMLDecoder decoder = null;

		try {

			FileInputStream fis = new FileInputStream(fichier);
			BufferedInputStream ois = new BufferedInputStream(fis);
			decoder = new XMLDecoder(ois);

			user = (User) decoder.readObject();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (decoder != null)
				decoder.close();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
