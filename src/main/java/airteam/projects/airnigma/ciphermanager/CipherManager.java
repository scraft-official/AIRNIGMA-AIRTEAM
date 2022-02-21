package airteam.projects.airnigma.ciphermanager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;

import org.reflections.Reflections;

import airteam.projects.airnigma.ciphermanager.ciphers.CIP_ROT13;
import airteam.projects.airnigma.ciphermanager.ciphers.CipherObject;
import airteam.projects.airnigma.components.InputPanel;
import airteam.projects.airnigma.components.OptionsPanel;
import airteam.projects.airnigma.components.OutputPanel;
import airteam.projects.airnigma.utilities.LogUtility;

public class CipherManager {
	public static ArrayList<CipherObject> registeredCipheres = new ArrayList<>();
	public static CipherObject selectedCipher;
	
	public static void registerAllCiphers() {
		Reflections reflections = new Reflections("airteam.projects.airnigma.ciphermanager.ciphers");

		Set<Class<? extends CipherObject>> allCipherClasses = reflections.getSubTypesOf(CipherObject.class);

		for( Class<? extends CipherObject> c : allCipherClasses) {
			try {
				CipherObject cipher = c.getConstructor().newInstance();
				registeredCipheres.add(cipher);
				LogUtility.logInfo(cipher.getCipherName());
			} catch (Exception e) { LogUtility.logError("Wystapil problem z rejestracja obiektu szyfru!"); }
		}
		
		selectCipher(0);
		OptionsPanel.refreshSelected();
	}
	
	public static void updateOutput() {
		OutputPanel.updateOutputText(selectedCipher.encode(InputPanel.getInputText()));
	}
	
	public static void updateInput() {
		InputPanel.updateInputText(selectedCipher.decode(OutputPanel.getOutputText()));
	}
	
	public static void selectCipher(int id) {
		selectedCipher = registeredCipheres.get(id);
		OptionsPanel.setCipherOptionsPanel(selectedCipher);
	}
	
	public static void selectCipher(CipherObject cipher) {
		for(CipherObject c : registeredCipheres) {
			if(c == cipher) selectedCipher = cipher;
		}
		OptionsPanel.setCipherOptionsPanel(selectedCipher);
	}
	
	public static ArrayList<CipherObject> getAllCiphers() {
		return registeredCipheres;
	}
	
	public static CipherObject getSelectedCipher() {
		return selectedCipher;
	}
}
