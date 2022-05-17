package Controllers;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Model.TraitementFichier;
import Model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class SettingsController { // Cette classe est le controller pour l'interface des réglages

	@FXML
	private Button selectSourceDirectory, selectImageDirectory;

	@FXML
	private Button sauvegarderButton;

	private String sourceFolderPath;

	private User user;

	private TraitementFichier traitement;
	private HashMap<String,ArrayList<String>> hm = new HashMap<>();

	public SettingsController(User user) {
		this.user = user;

	}
	

	@FXML
	public void initialize() {
		
		selectSourceDirectory.setText(user.getSourceFolder());
		String[] folderName = user.getSourceFolder().split("/");
		selectSourceDirectory.setText("/" + folderName[folderName.length - 1]);
		
	

		


		selectSourceDirectory.setOnAction(new EventHandler<ActionEvent>() { // Evenement qui permet d'appeler la fonction selectSourecRepertory()
			@Override													    // qui se trouve à la ligne 99 du code.
			public void handle(ActionEvent e) {
				System.out.println(selectSourceDirectory.getOnAction());
				selectSourceRepertory(e);
				/*try {
					traitement = new TraitementFichier(hm);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
						
						
				}*/
					
					
			}
				
		});
			
		selectImageDirectory.setOnAction(new EventHandler<ActionEvent>() { // Evenement qui permet d'appeler la fonction selectImageRepertory()
			@Override													   // qui se trouve à la ligne 117 du code. 				
			public void handle(ActionEvent e) {
				selectImageRepertory(e);
				/*try {
					traitement = new TraitementFichier(hm);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
						
				}*/
					
			}
		});
		sauvegarderButton.setOnAction(new EventHandler<ActionEvent>() { // Evenement qui permet d'appeler la fonction enregistreXML()
			@Override													// qui se trouve à la ligne 135 du code.								
			public void handle(ActionEvent e) {
				enregistrerXML();
				Stage stage = (Stage) sauvegarderButton.getScene().getWindow();
				// do what you have to do
				stage.close();

			}
		});

	}

	public void selectSourceRepertory(ActionEvent event) { // Méthode qui permet d'ouvrir l'explorateur de fichier pour
														   // sélectionner le dossier qui contient tout les fichiers à traités.

		DirectoryChooser directoryChooser = new DirectoryChooser();

		// get the file selected
		File file = directoryChooser.showDialog(null);

		if (file != null) {

			sourceFolderPath = file.getAbsolutePath();
			String[] folderName = this.sourceFolderPath.split("/");
			selectSourceDirectory.setText("/" + folderName[folderName.length - 1]);
			user.setSourceFolder(sourceFolderPath);
			System.out.println("votre répertoire de base est mtn : /" + folderName[folderName.length - 1]);
		}
	}
	
	public void selectImageRepertory(ActionEvent event) { // Méthode qui permet d'ouvrir l'explorateur de fichier pour
		  												  // sélectionner le dossier qui devra contenir les images générées.

		DirectoryChooser directoryChooser = new DirectoryChooser();

		// get the file selected
		File file = directoryChooser.showDialog(null);

		if (file != null) {

			sourceFolderPath = file.getAbsolutePath();
			String[] folderName = this.sourceFolderPath.split("/");
			selectImageDirectory.setText("/" + folderName[folderName.length - 1]);
			user.setSourceFolder(sourceFolderPath);
			System.out.println("votre répertoire de base est mtn : /" + folderName[folderName.length - 1]);
		}
	}

	public void enregistrerXML() { // Méthode qui permet de sauvegarder, la sélection du repertoire image
								   // et du repertoire qui contient les fichiers à traités. (sérialisation)

		File fichier = new File("user.xml");

		XMLEncoder encoder = null;

		try {
			FileOutputStream fos = new FileOutputStream(fichier);
			BufferedOutputStream oos = new BufferedOutputStream(fos);
			encoder = new XMLEncoder(oos);
			encoder.writeObject(user);
			encoder.flush();

		} catch (final java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (encoder != null)
				encoder.close();
		}

	}

}
