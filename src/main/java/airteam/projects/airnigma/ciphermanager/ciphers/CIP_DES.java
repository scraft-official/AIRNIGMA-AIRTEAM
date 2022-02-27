package airteam.projects.airnigma.ciphermanager.ciphers;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import airteam.projects.airnigma.ciphermanager.CipherManager;
import airteam.projects.airnigma.ciphermanager.CipherManager.CIPHER_MODE;
import airteam.projects.airnigma.components.InputPanel;
import airteam.projects.airnigma.components.dialogs.popups.ErrorPopup;
import airteam.projects.airnigma.components.dialogs.popups.SelectBlockSizePopup;
import airteam.projects.airnigma.components.templates.CustomButton;
import airteam.projects.airnigma.components.templates.CustomButton.CustomButtonUI;
import airteam.projects.airnigma.components.templates.CustomTextField;
import airteam.projects.airnigma.components.templates.CustomTextFieldWithButton;
import airteam.projects.airnigma.savemanager.SaveManager;
import airteam.projects.airnigma.utilities.EncryptionUtility;
import airteam.projects.airnigma.utilities.GraphicsUtility;
import airteam.projects.airnigma.utilities.LogUtility;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class CIP_DES extends CipherObject {
	private String cipherName = "SZYFR DES";
	
	private SecretKey cipherSecretKey = EncryptionUtility.generateKey(56, "DES");
	
	public CIP_DES() {
		setOpaque(false);
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("min(234px;default):grow"),},
			new RowSpec[] {
				RowSpec.decode("45px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("35px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:35px"),}));
	}
	
	@Override
	public void showOptions() {
		removeAll();
		
		CustomTextFieldWithButton fieldCipherKey = new CustomTextFieldWithButton("KLUCZ SZYFRU", new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/folder-icon.png"), 17, 17)));
		fieldCipherKey.getTextField().setText(EncryptionUtility.convertSecretKeyToString(cipherSecretKey));
		fieldCipherKey.getTextField().getDocument().addDocumentListener(new DocumentListener() {
		  public void changedUpdate(DocumentEvent e) { update(); }
		  public void removeUpdate(DocumentEvent e) { update(); }
		  public void insertUpdate(DocumentEvent e) { update(); }

		  public void update() {
		  	String text = fieldCipherKey.getTextField().getText().replaceFirst(" ","");
		  	if(text.length() == 0) {
		  		fieldCipherKey.getTextField().setRquiredHint("Wprowadź klucz!");
		  		fieldCipherKey.getTextField().showRequiredHint(true);
		  		return;
		  	}
		  	
		  	try {
	      	cipherSecretKey = EncryptionUtility.convertStringToSecretKey(text, "DES");
		  	} catch(Exception er) {
		  		fieldCipherKey.getTextField().setRquiredHint("Błedny klucz!");
		  		fieldCipherKey.getTextField().showRequiredHint(true);
		  		return;
		  	}
		  	
		  	fieldCipherKey.getTextField().showRequiredHint(false);
		  	
		  	CipherManager.updateOutput();
		  }
		});
		fieldCipherKey.getButton().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
      	String fileContent = SaveManager.importTextFile(".txt");
      	try {
	      	cipherSecretKey = EncryptionUtility.convertStringToSecretKey(fileContent, "DES");
	      	if(fileContent != null) {
	      		fieldCipherKey.getTextField().setText(fileContent);
	      	}
      	} catch(Exception er) {
      		new ErrorPopup("Nie udało się odczytać tego klucza!\nUpewnij się, że użyto klucza do szyfru \"DES\".");
      	}
      }
    });
		
		add(fieldCipherKey, "1, 1, fill, fill");
		
		JButton buttonSaveKey = new CustomButton(
				"ZAPISZ KLUCZ", 
				new Color(45, 150, 73), 
				new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/save-icon.png"), 14, 14)
		));
		buttonSaveKey.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
      	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM hh-mm-ss");
      	SaveManager.exportTextFile(("AirNigma - ZAPISANY KLUCZ - " + dateFormat.format(System.currentTimeMillis()) + ".txt"), fieldCipherKey.getTextField().getText());
      }
    });
		
		if(CipherManager.getSelectedMode() == CIPHER_MODE.ENCODE) {
			JButton buttonGenKey = new CustomButton(
					"WYGENERUJ KLUCZ", 
					new Color(186, 80, 135), 
					new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/key-icon.png"), 14, 14)
			));
			buttonGenKey.addActionListener(new ActionListener() {
	      @Override
	      public void actionPerformed(ActionEvent e) {

	      	SecretKey key = EncryptionUtility.generateKey(56, "DES");
	      	String keyText = EncryptionUtility.convertSecretKeyToString(key);
	      	
	      	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM hh-mm-ss");
	      	if(SaveManager.exportTextFile(("AirNigma - ZAPISANY KLUCZ - " + dateFormat.format(System.currentTimeMillis()) + ".txt"), keyText)) {
		      	cipherSecretKey = key;
		      	fieldCipherKey.getTextField().setText(keyText);
	      	}
	      }
	    });
			
			add(buttonGenKey, "1, 3, fill, fill");
			add(buttonSaveKey, "1, 5, fill, fill");
		} else { add(buttonSaveKey, "1, 3, fill, fill"); }
	}
	
	@Override
	public String encode(String text) {
		if(text.length() == 0) return null;
		try {
			return EncryptionUtility.encode("DES", text, cipherSecretKey);
		} catch (Exception e) {
			return "Nie można zakodować szyfru! Upewnij się, że wprowadzono odpowiedni klucz!";
		}
	}

	@Override
	public String decode(String text) {
		if(text.length() == 0) return null;
		try {
			return EncryptionUtility.decode("DES", text, cipherSecretKey);
		} catch (Exception e) {
			return "Nie można odczytać szyfru! Upewnij się, że wprowadzono odpowiedni klucz!";
		}
	}

	@Override
	public String getCipherName() {
		return cipherName;
	}


}