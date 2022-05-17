package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class TF2 {
	
	/*instanciation d'une HashMap (dictionnaire) ayant pour clé des éléments
	de la classe String (chaine de caractère) et pour valeur des élément
	de la classe ArrayList<String> (liste de chaines de caractères)*/
	public static HashMap<String,ArrayList<String>> hm=new HashMap<>();
	//instanciation de 3 ArrayList de chaines de caractères
	public static ArrayList<String> l1=new ArrayList<>();
	public static ArrayList<String> l2=new ArrayList<>();
	public static ArrayList<String> l3=new ArrayList<>();
	public static ArrayList<Double> l4=new ArrayList<>();
	
	//création de la méthode remplir()
	public static void remplir() throws IOException {
		
		//chemin d'accès au fichier 
		String url="C:/Users/Baptiste/Desktop/vasprun (1).xml";
		
		//lecture de la ligne 57
		String line=Files.readAllLines(Paths.get(url)).get(56);
		//division de la ligne 57 en plusieurs blocs de chaines de caractères
		String[] parts=line.split("   ");
		//récupération du nombre de points entre les points spéciaux
		String nbrePtsEntrePtsSpec=parts[3];
		
		//idem pour les lignes 58 à 62 sauf que les éléments récupérés ne sont pas les même
	    String line1=Files.readAllLines(Paths.get(url)).get(57);
	    String[] parts1=line1.split("   ");
	    String[] parts6=parts1[7].split(" ");
	    String ptsSpec1="("+parts1[3]+","+parts1[5]+","+parts6[1]+")";
	    String line2=Files.readAllLines(Paths.get(url)).get(58);
	    String[] parts2=line2.split("   ");
	    String[] parts7=parts2[7].split(" ");
	    String ptsSpec2="("+parts2[3]+","+parts2[5]+","+parts7[1]+")";
	    String line3=Files.readAllLines(Paths.get(url)).get(59);
	    String[] parts3=line3.split("   ");
	    String[] parts8=parts3[7].split(" ");
	    String ptsSpec3="("+parts3[3]+","+parts3[5]+","+parts8[1]+")";
	    String line4=Files.readAllLines(Paths.get(url)).get(60);
	    String[] parts4=line4.split("   ");
	    String[] parts9=parts4[7].split(" ");
	    String ptsSpec4="("+parts4[3]+","+parts4[5]+","+parts9[1]+")";
	    String line5=Files.readAllLines(Paths.get(url)).get(61);
	    String[] parts5=line5.split("   ");
	    String[] parts10=parts5[7].split(" ");
	    String ptsSpec5="("+parts5[3]+","+parts5[5]+","+parts10[1]+")";
	    
	    //instanciation de la classe FileReader et de la classe BufferedReader
	    FileReader file = new FileReader(url);
	    BufferedReader buffer = new BufferedReader(file);
	    //parcours de toutes les lignes du fichier
	    for (int j = 1; j < 113222; j++) {
	    	//sélection des lignes intéréssantes
    		if (j>=65 && j<=880) {
    			//lecture de la ligne 65 puis 66 puis 67...
    			String line7=buffer.readLine();
    			String[] parts11=line7.split("   ");
	    		String[] parts12=parts11[7].split(" ");
	            String kPoint="("+parts11[3]+","+parts11[5]+","+parts12[1]+")";
	            //ajout de parts11[3] dans l'ArrayList L1
	            l1.add(parts11[3]);
	            l2.add(parts11[5]);
	            l3.add(parts12[1]);
	            //instanciation d'une nouvelle ArrayList<String>
	            ArrayList<String> lKPoint=new ArrayList<>();
	            lKPoint.add(kPoint);
	            //ajout de lKPoint en valeur et des lKPointj en clé pour j allant de 65 à 880
	            hm.put("lKPoint"+String.valueOf(j), lKPoint);
	            double distance;
	            double x;
	            double y;
	            double z;
	            if (j==65) {
	            	distance=0;
	            	l4.add(distance);
	            } else {
	            	//calcul de la distance
	            	//la méthode parseDouble transforme une String en Double à condition que cela soit possible
	            	x=Double.parseDouble(l1.get(j-65))-Double.parseDouble(l1.get(j-66));
	            	y=Double.parseDouble(l2.get(j-65))-Double.parseDouble(l2.get(j-66));
	            	z=Double.parseDouble(l3.get(j-65))-Double.parseDouble(l3.get(j-66));
	            	distance=Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2)+Math.pow(z, 2));
	            	l4.add(distance);
	            }
    		} else {
    			//avancement dans le fichier pour arriver au lignes voulues ou à la fin du fichier
    			buffer.readLine();
    		}
	    }
	    
	    String line6=Files.readAllLines(Paths.get(url)).get(2256);
	    String[] parts13=line6.split("     ");
	    String[] parts14=parts13[1].split(" ");
	    String[] parts15=parts14[2].split("");
	    String spin=parts15[0];
	    
	    //voir explications plus haut
	    FileReader file2 = new FileReader(url);
	    BufferedReader buffer2 = new BufferedReader(file2);
		for (int j = 1; j < 113222; j++) {
		    if (j>=2259 && j<=16944) {
				String line8=buffer2.readLine();
				//si la ligne ne contient pas la chaine de caractère "set"
				if (!line8.contains("set")) {
					String[] parts16=line8.split("       ");
					String[] parts17=parts16[1].split("   ");
					String vk1=parts17[1];
					ArrayList<String> lvk1=new ArrayList<>();
					lvk1.add(vk1);
					hm.put("lvk "+String.valueOf(j), lvk1);
				}
			} else {
				buffer2.readLine();
			}
		}
	    
		//voir explications plus haut
	    ArrayList<String> lNbrePtsEntrePtsSpec=new ArrayList<>();
		lNbrePtsEntrePtsSpec.add(nbrePtsEntrePtsSpec);
		hm.put("nbrePtsEntrePtsSpec",lNbrePtsEntrePtsSpec);
		ArrayList<String> lPtsSpec=new ArrayList<>();
		lPtsSpec.add(ptsSpec1);
		lPtsSpec.add(ptsSpec2);
		lPtsSpec.add(ptsSpec3);
		lPtsSpec.add(ptsSpec4);
		lPtsSpec.add(ptsSpec5);
		hm.put("lPtsSpec", lPtsSpec);
		ArrayList<String> lspin=new ArrayList<>();
		lspin.add(spin);
		hm.put("lspin", lspin);	
	}
	
	public static void main(String[] args) throws IOException {
		
		//utilisation de la méthode remplir
		remplir();
	}

}