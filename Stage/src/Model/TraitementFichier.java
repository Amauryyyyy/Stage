package Model;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class TraitementFichier {
	
	//public static HashMap<String,ArrayList<String>> hm=new HashMap<>();
	
	public TraitementFichier(HashMap<String,ArrayList<String>> hm, String url) throws IOException {
		remplir(hm,url);
	}
	
	public static ArrayList<String> récupérer_valeur(HashMap<String,ArrayList<String>> hm,String nom) {
		return hm.get(nom);
	}
	
	public static void remplir(HashMap<String,ArrayList<String>> hm, String url) throws IOException {
		
		String line=Files.readAllLines(Paths.get(url)).get(0);
		String parts[]=line.split("   ");
	    String nbre_ions=parts[1];
	    String nbre_ions2=parts[2];
	    String flag=parts[3];
	    String num=parts[4];
	    
	    String line2=Files.readAllLines(Paths.get(url)).get(1);
	    String parts2[]=line2.split("  ");
	    String volume=parts2[1];
	    String x=parts2[2];
	    String y=parts2[3];
	    String z=parts2[4];
	    String POTIM=parts2[5];
	    
	    String line3=Files.readAllLines(Paths.get(url)).get(2);
	    String parts3[]=line3.split("  ");
	    String Temperature=parts3[1];
	    
	    String line6=Files.readAllLines(Paths.get(url)).get(5);
	    String parts4[]=line6.split("    ");
	    String E_max=parts4[1];
	    String parts5[]=parts4[2].split("  ");
	    String E_min=parts5[0];
	    String NEDOS=parts5[1];
	    String Efermi=parts4[3];
	    
	    ArrayList<String> lE=new ArrayList<>();
	    ArrayList<String> lf_up=new ArrayList<>();
	    ArrayList<String> lf_down=new ArrayList<>();
	    ArrayList<String> lg_up=new ArrayList<>();
	    ArrayList<String> lg_down=new ArrayList<>();
	    FileReader fr=new FileReader(url);
	    BufferedReader br=new BufferedReader(fr);
	    for (int i = 1; i<308; i++) {
            if((i >= 7 && i<=92) || i>=155) {
                String lineF = br.readLine();
                String parts6[]=lineF.split("    ");
                String parts7[]=parts6[1].split("  ");
                String E=parts7[0];
                String f_up=parts7[1];
                String f_down=parts7[2];
                String g_up=parts7[3];
                String g_down=parts7[4];
                lE.add(E);
                lf_up.add(f_up);
                lf_down.add(f_down);
                lg_up.add(g_up);
                lg_down.add(g_down);
                hm.put("E",lE);
                hm.put("f_up",lf_up);
                hm.put("f_down",lf_down);
                hm.put("g_up",lg_up);
                hm.put("g_down",lg_down);
            } else if (i>=93 && i<=154) {
            	String lineF = br.readLine();
                String parts6[]=lineF.split("      ");
                String parts7[]=parts6[1].split("  ");
                String E=parts7[0];
                String f_up=parts7[1];
                String f_down=parts7[2];
                String g_up=parts7[3];
                String g_down=parts7[4];
                lE.add(E);
                lf_up.add(f_up);
                lf_down.add(f_down);
                lg_up.add(g_up);
                lg_down.add(g_down);
                hm.put("E",lE);
                hm.put("f_up",lf_up);
                hm.put("f_down",lf_down);
                hm.put("g_up",lg_up);
                hm.put("g_down",lg_down);
            } else {
            	br.readLine();
            }
        }
	    
	    ArrayList<String> lnbre_ions=new ArrayList<>();
	    ArrayList<String> lnbre_ions2=new ArrayList<>();
	    ArrayList<String> lflag=new ArrayList<>();
	    ArrayList<String> lnum=new ArrayList<>();
	    ArrayList<String> lvolume=new ArrayList<>();
	    ArrayList<String> lx=new ArrayList<>();
	    ArrayList<String> ly=new ArrayList<>();
	    ArrayList<String> lz=new ArrayList<>();
	    ArrayList<String> lPOTIM=new ArrayList<>();
	    ArrayList<String> lTemperature=new ArrayList<>();
	    ArrayList<String> lE_max=new ArrayList<>();
	    ArrayList<String> lE_min=new ArrayList<>();
	    ArrayList<String> lNEDOS=new ArrayList<>();
	    ArrayList<String> lEfermi=new ArrayList<>();
	    lnbre_ions.add(nbre_ions);
	    lnbre_ions2.add(nbre_ions2);
	    lflag.add(flag);
	    lnum.add(num);
	    lvolume.add(volume);
	    lx.add(x);
	    ly.add(y);
	    lz.add(z);
	    lPOTIM.add(POTIM);
	    lTemperature.add(Temperature);
	    lE_max.add(E_max);
	    lE_min.add(E_min);
	    lNEDOS.add(NEDOS);
	    lEfermi.add(Efermi);
	    hm.put("nombre d'ions",lnbre_ions);
	    hm.put("nombre d'ions 2",lnbre_ions2);
	    hm.put("flag",lflag);
	    hm.put("numéro",lnum);
	    hm.put("volume",lvolume);
	    hm.put("1x",lx);
	    hm.put("1y",ly);
	    hm.put("1z",lz);
	    hm.put("POTIM",lPOTIM);
	    hm.put("Temperature",lTemperature);
	    hm.put("E(max)",lE_max);
	    hm.put("E(min)",lE_min);
	    hm.put("NEDOS",lNEDOS);
	    hm.put("Efermi",lEfermi);
	    
	    System.out.println(hm);
	    
	    System.out.println(récupérer_valeur(hm,"E"));
	    
	}
}
