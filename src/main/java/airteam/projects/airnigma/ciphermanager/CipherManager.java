package airteam.projects.airnigma.ciphermanager;

import java.util.ArrayList;
import java.util.Set;

import org.reflections.Reflections;

import airteam.projects.airnigma.ciphermanager.ciphers.CipherObject;
import airteam.projects.airnigma.components.InputPanel;
import airteam.projects.airnigma.components.OptionsPanel;
import airteam.projects.airnigma.components.OutputPanel;
import airteam.projects.airnigma.utilities.LogUtility;

public class CipherManager {
	private static ArrayList<CipherObject> registeredCipheres = new ArrayList<>();
	private static CipherObject selectedCipher;

	private static CIPHER_MODE cipherMode = CIPHER_MODE.ENCODE;

	public static void registerAllCiphers() throws Exception {
		Reflections reflections = new Reflections("airteam.projects.airnigma.ciphermanager.ciphers");

		Set<Class<? extends CipherObject>> allCipherClasses = reflections.getSubTypesOf(CipherObject.class);

		for (Class<? extends CipherObject> c : allCipherClasses) {
			try {
				CipherObject cipher = c.getConstructor().newInstance();
				registeredCipheres.add(cipher);
				LogUtility.logInfo(cipher.getCipherName());
			} catch (Exception e) {
				LogUtility.logError("Wystapil problem z rejestracja obiektu szyfru!");
				throw new CipherException("Wystapil problem z rejestracja obiektu szyfru!");
			}
		}

		selectCipher(registeredCipheres.size() - 1);
		OptionsPanel.refreshSelected();
	}

	public static void updateOutput() {
		if (cipherMode == CIPHER_MODE.ENCODE) {
			try {
				OutputPanel.updateOutputText(selectedCipher.encode(InputPanel.getInputText()));
			} catch (Exception e) {
				OutputPanel.updateOutputText(e.getMessage());
			}
		} else {
			try {
				OutputPanel.updateOutputText(selectedCipher.decode(InputPanel.getInputText()));
			} catch (Exception e) {
				OutputPanel.updateOutputText(e.getMessage());
			}
		}
	}

	public static void updateInput() {
		if (cipherMode == CIPHER_MODE.ENCODE) {
			try {
				InputPanel.updateInputText(selectedCipher.decode(OutputPanel.getOutputText()));
			} catch (Exception e) {
				InputPanel.updateInputText(e.getMessage());
			}
		} else {
			try {
				InputPanel.updateInputText(selectedCipher.encode(OutputPanel.getOutputText()));
			} catch (Exception e) {
				InputPanel.updateInputText(e.getMessage());
			}
		}
	}

	public static void selectCipher(int id) {
		selectedCipher = registeredCipheres.get(id);
		selectedCipher.showOptions();
		OptionsPanel.setCipherOptionsPanel(selectedCipher);
		updateOutput();
	}

	public static void selectCipher(CipherObject cipher) {
		for (CipherObject c : registeredCipheres) {
			if (c == cipher) {
				selectedCipher = cipher;
			}
		}
		selectedCipher.showOptions();
		OptionsPanel.setCipherOptionsPanel(selectedCipher);
		updateOutput();
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
		DECODE, ENCODE
	}
	
	@SuppressWarnings("serial")
	public static class CipherException extends Exception {
		public CipherException(String msg) {
			super(msg);
		}
	}
	
}
