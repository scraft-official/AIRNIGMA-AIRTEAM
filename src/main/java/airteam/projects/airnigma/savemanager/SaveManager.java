package airteam.projects.airnigma.savemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;

import airteam.projects.airnigma.ciphermanager.CipherManager;
import airteam.projects.airnigma.components.InputPanel;
import airteam.projects.airnigma.components.dialogs.popups.ErrorPopup;

public class SaveManager {
	public static boolean importText() {
		String path = null;
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setDialogTitle("IMPORTUJ PLIK TEKSTU");
		
    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        if (file == null) return false;
        
        path = fileChooser.getSelectedFile().getAbsolutePath();
    } else return false;
	 
	  if(!path.contains(".txt")) {
	  	new ErrorPopup("WPROWADZONO NIEPRAWIDŁOWY PLIK!\nAKCEPTOWANY PLIK TYLKO W FORMACIE (.txt)");
	  	return false;
	  }
    
	  String fileContent = null;
	  try(FileInputStream inputStream = new FileInputStream(path)) {     
	  	fileContent = new String(inputStream.readAllBytes());
	  } catch (FileNotFoundException e) {
	  	new ErrorPopup("NIE ZNALEZIONO TAKIEGO PLIKU!");
			return false;
		} catch (Exception e) { 
			new ErrorPopup("WYSTAPIŁ BLĄD Z OTWIERANIEM PLIKU!");
			return false;
		}
	  
	  InputPanel.updateInputText(fileContent);
	  CipherManager.updateOutput();
	  
	  return true;
	}
}
