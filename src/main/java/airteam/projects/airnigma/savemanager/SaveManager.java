package airteam.projects.airnigma.savemanager;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import airteam.projects.airnigma.AirNigma;
import airteam.projects.airnigma.ciphermanager.CipherManager;
import airteam.projects.airnigma.components.InputPanel;
import airteam.projects.airnigma.components.dialogs.popups.ErrorPopup;
import airteam.projects.airnigma.components.dialogs.popups.InfoPopup;

public class SaveManager {
	public static String importTextFile(String fileFormat) {
		String path = null;
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setDialogTitle("IMPORTUJ PLIK");
		
    if (fileChooser.showOpenDialog(AirNigma.getFrame()) == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        if (file == null) return null;
        
        path = fileChooser.getSelectedFile().getAbsolutePath();
    } else return null;
	 
	  if(!path.contains(fileFormat)) {
	  	new ErrorPopup("WPROWADZONO NIEPRAWIDŁOWY PLIK!\nAKCEPTOWANY PLIK TYLKO W FORMACIE ("+ fileFormat +")");
	  	return null;
	  }
    
	  String fileContent = null;
	  try(FileInputStream inputStream = new FileInputStream(path)) {     
	  	fileContent = new String(inputStream.readAllBytes());
	  } catch (FileNotFoundException e) {
	  	new ErrorPopup("NIE ZNALEZIONO TAKIEGO PLIKU!");
			return null;
		} catch (Exception e) { 
			new ErrorPopup("WYSTAPIŁ BLĄD Z OTWIERANIEM PLIKU!");
			return null;
		}
	  
	  return fileContent;
	}
	
	public static boolean exportTextFile(String fileName, String fileContent) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setDialogTitle("AIRNIGMA - IMPORTUJ PLIK TEKSTOWY");
		
		String path = null;
    if (fileChooser.showOpenDialog(AirNigma.getFrame()) == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        if (file == null) return false;
        
        path = fileChooser.getSelectedFile().getAbsolutePath();
    } else return false;
    
		String filePath = (path + "\\" + fileName);
		
		File file = new File(filePath);
		try {
			file.createNewFile();
		} catch (Exception e) {
			new ErrorPopup("NIE MOŻNA ZAPISAĆ PLIKU W PODANEJ LOKALIZACJI!");
			return false;
		}
		
		try (PrintWriter fileWriter = new PrintWriter(filePath)) {
			fileWriter.print(fileContent);
		} catch (Exception e) {
			new ErrorPopup("WYSTĄPIŁ PROBLEM Z ZAPISYWANIEM PLIKU!" );
			return false;
		}
		new InfoPopup("POMYŚLENIE ZAPISANO PLIK!\n(" + fileName +")");
		return true;
	}
	
	public static boolean exportTextFile(HashMap<String, String> filesDataMap) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setDialogTitle("AIRNIGMA - IMPORTUJ PLIK TEKSTOWY");
		
		String path = null;
    if (fileChooser.showOpenDialog(AirNigma.getFrame()) == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        if (file == null) return false;
        
        path = fileChooser.getSelectedFile().getAbsolutePath();
    } else return false;
    
    for(String fileName : filesDataMap.keySet()) {
			String filePath = (path + "\\" + fileName);
			
			File file = new File(filePath);
			try {
				file.createNewFile();
			} catch (Exception e) {
				new ErrorPopup("NIE MOŻNA ZAPISAĆ PLIKU W PODANEJ LOKALIZACJI!");
				return false;
			}
			
			try (PrintWriter fileWriter = new PrintWriter(filePath)) {
				fileWriter.print(filesDataMap.get(fileName));
			} catch (Exception e) {
				new ErrorPopup("WYSTĄPIŁ PROBLEM Z ZAPISYWANIEM PLIKU!" );
				return false;
			}
    }
		new InfoPopup("POMYŚLENIE ZAPISANO PLIKI!\n(" + String.join("\n", filesDataMap.keySet()) +")");
		return true;
	}
}
