package airteam.projects.airnigma.ciphermanager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;

import org.reflections.Reflections;

import airteam.projects.airnigma.AirNigma;
import airteam.projects.airnigma.ciphermanager.ciphers.CIP_ROT13;
import airteam.projects.airnigma.ciphermanager.ciphers.CipherObject;
import airteam.projects.airnigma.components.InputPanel;
import airteam.projects.airnigma.components.OptionsPanel;
import airteam.projects.airnigma.components.OutputPanel;
import airteam.projects.airnigma.utilities.LogUtility;

public class CipherManager {
	private static ArrayList<CipherObject> registeredCipheres = new ArrayList<>();
	private static CipherObject selectedCipher;
	
	private static CIPHER_MODE cipherMode = CIPHER_MODE.ENCODE;
	
	
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
		if(cipherMode == CIPHER_MODE.ENCODE) OutputPanel.updateOutputText(selectedCipher.encode(InputPanel.getInputText()));
		else OutputPanel.updateOutputText(selectedCipher.decode(InputPanel.getInputText()));
	}
	
	public static void updateInput() {
		if(cipherMode == CIPHER_MODE.ENCODE) InputPanel.updateInputText(selectedCipher.decode(OutputPanel.getOutputText()));
		else InputPanel.updateInputText(selectedCipher.encode(OutputPanel.getOutputText()));
	}
	
	public static void selectCipher(int id) {
		selectedCipher = registeredCipheres.get(registeredCipheres.size()-1);
		OptionsPanel.setCipherOptionsPanel(selectedCipher);
		selectedCipher.showOptions();
	}
	
	public static void selectCipher(CipherObject cipher) {
		for(CipherObject c : registeredCipheres) {
			if(c == cipher) selectedCipher = cipher;
		}
		if(AirNigma.getFrame().isVisible()) selectedCipher.showOptions();
		OptionsPanel.setCipherOptionsPanel(selectedCipher);
	}
	
	public static ArrayList<CipherObject> getAllCiphers() {
		return registeredCipheres;
	}
	
	public static CipherObject getSelectedCipher() {
		return selectedCipher;
	}
	
	public static CIPHER_MODE getSelectedMode() {
		return cipherMode;
	}
	
	public static void selectCipherMode(CIPHER_MODE mode) {
		cipherMode = mode;
		selectedCipher.showOptions();
	}
	
	public enum CIPHER_MODE {
		DECODE,
		ENCODE
	}
}
