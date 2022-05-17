package Controllers;

import java.awt.AWTException;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.imageio.ImageIO;
import Model.TraitementFichier;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class Controler { // Cette classe est le controller pour l'interface génerale


	@FXML
	private AnchorPane anch_graphe, anch_agg;

	@FXML
	private Button tracer, rt,selectFileButton, settingsButton, genererImg, rechercher;

	@FXML
	private TextField tx1, tx2, ty1, ty2, pas, txt_graphe_selec;

	@FXML
	private Label selectedFileLabel;

	private NumberAxis xAxis, yAxis_up, yAxis_down;

	private LineChart lc_up, lc_down, lc_recherche, lineC_up,lineC_down;

	private String file_name;

	private User user;

	private HashMap<String,ArrayList<String>> hm=new HashMap<>();

	public Controler(User user) {
		this.user = user;

	}
	public void agrandir(ActionEvent event) { // Méthode permettant de mettre le graphique en plein ecran
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Grand_Ecran.fxml"));
		lineC_up=this.lc_up;
		lineC_down=this.lc_down;
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				stage.close();

			}
		});
		//Parent root;
		try {
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

			this.anch_agg = loader.load();
			if(Integer.valueOf(txt_graphe_selec.getText())==1) {
				System.out.println("hello");
				anch_agg.getChildren().add(lineC_up);
				anch_agg.setBottomAnchor(lineC_up, 50.0);
				anch_agg.setTopAnchor(lineC_up, 0.0);
				anch_agg.setRightAnchor(lineC_up, 0.0);
				anch_agg.setLeftAnchor(lineC_up, 0.0);
			}
			if(Integer.valueOf(txt_graphe_selec.getText())==2) {
				anch_agg.getChildren().add(lineC_down);
				anch_agg.setBottomAnchor(lineC_down, 50.0);
				anch_agg.setTopAnchor(lineC_down, 0.0);
				anch_agg.setRightAnchor(lineC_down, 0.0);
				anch_agg.setLeftAnchor(lineC_down, 0.0);
				
			}
			Button bt_retour = new Button("Retour");
			anch_agg.getChildren().add(bt_retour);
			anch_agg.setBottomAnchor(bt_retour, 14.0);
			anch_agg.setRightAnchor(bt_retour, 25.0);
			bt_retour.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					stage.close();
					anch_graphe.getChildren().clear();
					tracer(arg0);
					
				}
				
			});
			Scene scene = new Scene(this.anch_agg);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void tracer(ActionEvent event) { // Méthode permettant de tracer les courbes 
		Random rnd = new Random();
		try {

			TraitementFichier traitement = new TraitementFichier(hm,file_name); // Grâce à la classe TraitementFichier qui permet d'extraire les données 
																				// ici je récupere le dictionnaire avec les éléments à traiter triés
			ArrayList<String> E = traitement.récupérer_valeur(hm,"E");
			ArrayList<String> f_up= traitement.récupérer_valeur(hm,"f_up");	    // récupérer_valeur, méthode créé dans la classe TraitementFichier qui permet
			ArrayList<String> g_up= traitement.récupérer_valeur(hm,"g_up");		// de récupérer toutes les valeurs avec le nom de ce qu'on veut récupérer du dossier
			ArrayList<String> f_down= traitement.récupérer_valeur(hm,"f_down");
			ArrayList<String> g_down= traitement.récupérer_valeur(hm,"g_down");

			xAxis = new NumberAxis(Double.valueOf(E.get(0)), Double.valueOf(E.get(E.size()-1)), 0.5); // Ici je définis la ligne d'abscisse
			
			if(Double.valueOf(f_up.get(0))<Double.valueOf(g_up.get(0))) { // Ici je fait une condition pour vérifier quel est le plus petit
			// parite up												  // pour ensuite avoir la plus petite valeur de l'ordonnée
				
				if(Double.valueOf(f_up.get(f_up.size()-1))>Double.valueOf(g_up.get(g_up.size()-1))) {// Ici je fait une condition pour vérifier quel est le plus grand
					  																				// pour ensuite avoir la plus grande valeur de l'ordonnée

					yAxis_up = new NumberAxis(Double.valueOf(f_up.get(0)),Double.valueOf(f_up.get(f_up.size()-1)), 0.0025); // Ici je définis la ligne d'ordonnée en fonction des conditions

				}
				else {

					yAxis_up = new NumberAxis(Double.valueOf(f_up.get(0)),Double.valueOf(g_up.get(g_up.size()-1)), 0.0025); // Ici je définis la ligne d'ordonnée en fonction des conditions

				}


			}
			else {

				yAxis_up = new NumberAxis(Double.valueOf(g_up.get(0)),Double.valueOf(g_up.get(g_up.size()-1)), 0.00025); // Ici je définis la ligne d'ordonnée en fonction des conditions

			}
			if(Double.valueOf(f_down.get(0))<Double.valueOf(g_down.get(0))) { // ici je fait la meme chose qu'au dessus mais avec la partie down


				if(Double.valueOf(f_down.get(f_down.size()-1))<Double.valueOf(g_down.get(g_down.size()-1))) {

					yAxis_down = new NumberAxis(Double.valueOf(f_down.get(0)),Double.valueOf(f_down.get(f_down.size()-1)), 0.00025); 

				}
				else {

					yAxis_down = new NumberAxis(Double.valueOf(f_down.get(0)),Double.valueOf(g_down.get(g_down.size()-1)), 0.00025); 

				}


			}

			else {

				if(Double.valueOf(f_down.get(f_down.size()-1))<Double.valueOf(g_down.get(g_down.size()-1))) {

					yAxis_down = new NumberAxis(Double.valueOf(g_down.get(0)),Double.valueOf(f_down.get(f_down.size()-1)), 0.00025); 

				}
				else {

					yAxis_down = new NumberAxis(Double.valueOf(g_down.get(0)),Double.valueOf(g_down.get(g_down.size()-1)), 0.00025); 

				}


			}
			
			lc_up = new LineChart(xAxis,yAxis_up);
													// je définis les deux graphiques avec ordonnée et abscisse calculer juste avant
			lc_down = new LineChart(xAxis,yAxis_down);

			XYChart.Series<Number,Number> series_f_up = new XYChart.Series<Number,Number>();
			XYChart.Series<Number,Number> series_g_up = new XYChart.Series<Number,Number>();
																		// je définis des séries dans lesquels je vais ajouter les coordonnées des points à placer
			XYChart.Series<Number,Number> series_f_down = new XYChart.Series<Number,Number>();
			XYChart.Series<Number,Number> series_g_down = new XYChart.Series<Number,Number>();

			for (int i = 0;i<E.size() ; i++) {

				series_f_up.getData().add(new XYChart.Data<Number,Number>(Double.valueOf(E.get(i)),Double.valueOf(f_up.get(i))));
				series_g_up.getData().add(new XYChart.Data<Number,Number>(Double.valueOf(E.get(i)),Double.valueOf(g_up.get(i))));
																		// j'ajoute les coordonnées des points dans les différentes series
				series_f_down.getData().add(new XYChart.Data<Number,Number>(Double.valueOf(E.get(i)),Double.valueOf(f_down.get(i))));
				series_g_down.getData().add(new XYChart.Data<Number,Number>(Double.valueOf(E.get(i)),Double.valueOf(g_down.get(i))));

			}

			lc_up.setTitle("graphe pour la partie up");
													// je set un titre pour les deux graphes
			lc_down.setTitle("graphe pour la partie down");

			series_f_up.setName("f_up en fonction de E");
			series_g_up.setName("g_up en fonction de E");
													// je set la légende des courbes 
			series_f_down.setName("f_down en fonction de E");
			series_g_down.setName("g_down en fonction de E");


			lc_up.getData().addAll(series_f_up,series_g_up);
													// j'ajoute les différentes séries au graphique 
			lc_down.getData().addAll(series_f_down,series_g_down);

			anch_graphe.getChildren().addAll(lc_up,lc_down);
													// j'ajoute les graphiques au pane que j'ai set dans le window builder
			lc_up.setPrefWidth(765.0);
			lc_up.setPrefHeight(225.0);
													// je set la taille des graphes
			lc_down.setPrefWidth(765.0);
			lc_down.setPrefHeight(225.0);
			lc_down.setTranslateY(20);

			anch_graphe.setRightAnchor(lc_up,0.0);
			anch_graphe.setLeftAnchor(lc_up,0.0);
			anch_graphe.setTopAnchor(lc_up,20.0);

			anch_graphe.setRightAnchor(lc_down,0.0);
			anch_graphe.setLeftAnchor(lc_down,0.0);
			anch_graphe.setTopAnchor(lc_down,240.0);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}






	}

	@FXML
	private void initialize() { 
		rt.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				anch_graphe.getChildren().clear();
				tracer(arg0);
				
			} 
			
		});

		selectFileButton.setOnAction(new EventHandler<ActionEvent>() { // Evenement permettant d'appeler la méthode searchDirectory() 
			@Override                                                  // qui se trouve à la ligne 175 du controller.
			public void handle(ActionEvent e) {
				searchDirectory(e);
			}
		});
		rechercher.setOnAction(new EventHandler<ActionEvent>() {
			int min= 0;
			int ind_min= 0;
			int max=0;
			int ind_max=0;
			@Override
			public void handle(ActionEvent arg0) {
				if (txt_graphe_selec.getText()!=null && tx1.getText()!=null && tx2.getText()!=null && ty1.getText()!=null && ty2.getText()!=null && pas.getText()!=null) {
					
					anch_graphe.getChildren().clear();
					
					try {
						TraitementFichier traitement = new TraitementFichier(hm,file_name);
						
						ArrayList<String> E = traitement.récupérer_valeur(hm,"E");
						ArrayList<String> f_up= traitement.récupérer_valeur(hm,"f_up");	
						ArrayList<String> g_up= traitement.récupérer_valeur(hm,"g_up");
						ArrayList<String> f_down= traitement.récupérer_valeur(hm,"f_down");
						ArrayList<String> g_down= traitement.récupérer_valeur(hm,"g_down");
						
						NumberAxis xAxis = new NumberAxis(Double.valueOf(tx1.getText()), Double.valueOf(tx2.getText()), Double.valueOf(pas.getText())); 
						NumberAxis yAxis = new NumberAxis(Double.valueOf(ty1.getText()), Double.valueOf(ty2.getText()), Double.valueOf(pas.getText()));
						
						XYChart.Series<Number,Number> series_f= new XYChart.Series<Number,Number>();
						XYChart.Series<Number,Number> series_g = new XYChart.Series<Number,Number>();
						
						lc_recherche = new LineChart(xAxis,yAxis);
						
						for (int i = 0;i<E.size();i++) {
							if (ind_min==0 && Double.valueOf(tx1.getText())<=Double.valueOf(E.get(i))) {
								min=i-1;
								ind_min=1;
								
							}
							if (ind_max==0 && Double.valueOf(tx2.getText())<=Double.valueOf(E.get(i))) {
								max=i-1;
								ind_max=1;
							}
						}
						
						if(Integer.valueOf(txt_graphe_selec.getText())==1) {

							for(int i = min;i<max;i++) {
								
								series_f.getData().add(new XYChart.Data<Number,Number>(Double.valueOf(E.get(i)),Double.valueOf(f_up.get(i))));
								series_g.getData().add(new XYChart.Data<Number,Number>(Double.valueOf(E.get(i)),Double.valueOf(g_up.get(i))));

								
							}
							
							
							
							series_f.setName("f_up en fonction de E");
							series_g.setName("g_up en fonction de E");
							
							lc_recherche.setTitle("graphe pour la partie up");

							
							
						}
						if(Integer.valueOf(txt_graphe_selec.getText())==2) {
							

							
							for(int i = min;i<max;i++) {
								
								series_f.getData().add(new XYChart.Data<Number,Number>(Double.valueOf(E.get(i)),Double.valueOf(f_down.get(i))));
								series_g.getData().add(new XYChart.Data<Number,Number>(Double.valueOf(E.get(i)),Double.valueOf(g_down.get(i))));
								
							}

							
							series_f.setName("f_down en fonction de E");
							series_g.setName("g_down en fonction de E");
							
							lc_recherche.setTitle("graphe pour la partie down");
						}
						lc_recherche.getData().addAll(series_f,series_g);
						anch_graphe.getChildren().add(lc_recherche);

						lc_recherche.setPrefWidth(765.0);
						lc_recherche.setPrefHeight(250.0);


						anch_graphe.setRightAnchor(lc_recherche,0.0);
						anch_graphe.setLeftAnchor(lc_recherche,0.0);
						anch_graphe.setTopAnchor(lc_recherche,20.0);

					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					

				}

			}

		});


		settingsButton.setOnAction(new EventHandler<ActionEvent>() {  // Evenement permettant d'ouvrir la page de réglages au clique 
			@Override                                                 // du boutton réglages.
			public void handle(ActionEvent e) {

				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/settings.fxml"));

				SettingsController controller = new SettingsController(user);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent e) {
						stage.close();
					}
				});

				loader.setController(controller);

				Parent root;
				try {
					root = loader.load();
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		genererImg.setOnAction(new EventHandler<ActionEvent>() { // Evenement permettant d'appeler la méthode takeAScreen() 
			@Override											 // qui se trouve à la ligne 194 du code du controller.
			public void handle(ActionEvent e) {
				takeAScreen(e);
			}
		});

	}

	public void searchDirectory(ActionEvent event) { // Méthode permettant d'aller chercher dans l'explorateur de fichiers 
		// le fichier à sélectionner et ensuite de modifier le text sur le boutton et à coté.
		FileChooser fC = new FileChooser();

		fC.getExtensionFilters().add(new FileChooser.ExtensionFilter("File", "DOSCAR"));

		File userDirectory = new File(user.getSourceFolder());
		fC.setInitialDirectory(userDirectory);
		// get the file selected
		File file = fC.showOpenDialog(null);
		file_name=file.getAbsolutePath();
		if (file != null) {

			selectedFileLabel.setText(file.getAbsolutePath());
			selectFileButton.setText("Fichier selectionnée : ");
		}
	}

	public void takeAScreen(ActionEvent event) {  // Méthode permettant de prendre une capture d'écran et de mettre dans le dossier d'images
		try {                                     // préalablement enregistré dans les réglages.
			Robot robot_up = new Robot();         
			Robot robot_down = new Robot();
			// Dimension de l'écran
			Window scene_up = this.lc_up.getScene().getWindow();
			Window scene_down = this.lc_down.getScene().getWindow();

			Dimension dimension_up = new Dimension();
			Dimension dimension_down = new Dimension();
			dimension_up.setSize(scene_up.getWidth(), scene_up.getHeight());
			dimension_down.setSize(scene_up.getWidth(), scene_up.getHeight());
			Rectangle rec_up = new Rectangle(dimension_up);
			Rectangle rec_down = new Rectangle(dimension_down);
			rec_up.x = (int) this.lc_up.boundsInLocalProperty().get().getCenterX() +250;
			rec_up.y = (int) this.lc_up.boundsInLocalProperty().get().getCenterY() +250;
			rec_down.x = (int) this.lc_down.boundsInLocalProperty().get().getCenterX()+250;
			rec_down.y = (int) this.lc_down.boundsInLocalProperty().get().getCenterY()+250;
			BufferedImage bi_up = robot_up.createScreenCapture(rec_up);
			BufferedImage bi_down = robot_down.createScreenCapture(rec_down);
			// enregistrer l'image
			ImageIO.write(bi_up, "png", new File(user.getSourceFolder() + "/GraphImageUp.png"));
			ImageIO.write(bi_down, "png", new File(user.getSourceFolder() + "/GraphImageDown.png"));
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
