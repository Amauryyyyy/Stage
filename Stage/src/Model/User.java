package Model;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable{ // création d'une classe user pour la sérialisation avec attribut PRIVATE obligé
										   // avec un geter et un seter
	private String sourceFolder = "aucun";

	public String getSourceFolder() {
		return this.sourceFolder;
	}
	
	public void setSourceFolder(String path) {
		 this.sourceFolder = path;
	}
	
	

}
