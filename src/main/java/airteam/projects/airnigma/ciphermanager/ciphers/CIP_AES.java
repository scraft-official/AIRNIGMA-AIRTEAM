package airteam.projects.airnigma.ciphermanager.ciphers;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import airteam.projects.airnigma.ciphermanager.CipherManager;
import airteam.projects.airnigma.ciphermanager.CipherManager.CIPHER_MODE;
import airteam.projects.airnigma.components.InputPanel;
import airteam.projects.airnigma.components.templates.CustomButtonUI;
import airteam.projects.airnigma.components.templates.CustomComboBox;
import airteam.projects.airnigma.components.templates.CustomTextField;
import airteam.projects.airnigma.components.templates.CustomTextFieldWithButton;
import airteam.projects.airnigma.savemanager.SaveManager;
import airteam.projects.airnigma.utilities.GraphicsUtility;

public class CIP_AES extends CipherObject {
	private String cipherName = "SZYFR AES";
	private ArrayList<String> algorithmMethods = new ArrayList<String>(Arrays.asList("AES/ECB/PKCS5Padding","AES/CBC/PKCS5Padding"));

	private String keyText = "WprowadzTrudneHasloTutaj";
	
	
	public CIP_AES() {
		setOpaque(false);
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("min(234px;default):grow"),},
			new RowSpec[] {
				RowSpec.decode("45px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("45px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("35px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:35px"),}));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void showOptions() {
		removeAll();
		
		CustomComboBox algorithmField = new CustomComboBox();
		algorithmField.setTitleText("METODA SZYFROWANIA");
		algorithmField.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		algorithmField.setMaximumRowCount(4);
		algorithmField.addPopupMenuListener(new PopupMenuListener() {
      @Override
      public void popupMenuCanceled(PopupMenuEvent pme) {
      }

      @Override
      public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) {
      	CipherManager.selectCipher(algorithmField.getSelectedIndex());
      }
      
      @Override
      public void popupMenuWillBecomeVisible(PopupMenuEvent pme) {
      }
    });
		
		algorithmField.setModel(new DefaultComboBoxModel(algorithmMethods.toArray()));
		algorithmField.setSelectedIndex(0);
    
    add(algorithmField, "1, 1, fill, default");
		
		CustomTextFieldWithButton keyPassField = new CustomTextFieldWithButton("KLUCZ SZYFRU", new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/folder-icon.png"), 17, 17)));
		keyPassField.getTextField().getDocument().addDocumentListener(new DocumentListener() {
		  public void changedUpdate(DocumentEvent e) {
		  	updateKeyText();
		  }
		  public void removeUpdate(DocumentEvent e) {
		  	updateKeyText();
		  }
		  public void insertUpdate(DocumentEvent e) {
		  	updateKeyText();
		  }

		  public void updateKeyText() {
		  	String text = keyPassField.getTextField().getText().replaceFirst(" ","");
		  	if(text.length() == 0) {
		  		keyPassField.getTextField().setRquiredHint("Wprowadź hasło!");
		  		keyPassField.getTextField().showRequiredHint(true);
		  		return;
		  	}
		  	
		  	keyText = text.toUpperCase();
		  	
		  	keyPassField.getTextField().showRequiredHint(false);
		  	
		  	CipherManager.updateOutput();
		  }
		});
		keyPassField.getTextField().setText(keyText);
		keyPassField.getButton().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
      	String fileContent = SaveManager.importTextFile(".txt");
      	if(fileContent != null) {
      		keyPassField.getTextField().setText(fileContent);
      	}
      }
    });
		
		add(keyPassField, "1, 3, fill, fill");
		
		JButton buttonSavePass = new JButton("ZAPISZ HASŁO");
		buttonSavePass.setIconTextGap(4);
		buttonSavePass.setIcon(new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/save-icon.png"), 14, 14)));
		buttonSavePass.setUI(new CustomButtonUI());
		buttonSavePass.setForeground(new Color(250,250,250));
		buttonSavePass.setFont(new Font("Tahoma", Font.BOLD, 14));
		buttonSavePass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonSavePass.setBackground(new Color(45, 150, 73));
		buttonSavePass.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
      	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM hh-mm-ss");
      	SaveManager.exportTextFile(("AirNigma - ZAPISANY KLUCZ - " + dateFormat.format(System.currentTimeMillis()) + ".txt"), keyPassField.getTextField().getText());

      }
    });
    
		buttonSavePass.addMouseListener(new MouseAdapter() {
			@Override
      public void mouseEntered(MouseEvent me) {
				buttonSavePass.setBackground(new Color(38, 128, 62));
		  }

		  @Override
		  public void mouseExited(MouseEvent me) {
		  	buttonSavePass.setBackground(new Color(45, 150, 73));
		  }
    });
		
		if(CipherManager.getSelectedMode() == CIPHER_MODE.ENCODE) {
			JButton buttonGeneratePass = new JButton("WYGENERUJ HASŁO");
			buttonGeneratePass.setIconTextGap(4);
			buttonGeneratePass.setIcon(new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/key-icon.png"), 15, 15)));
			buttonGeneratePass.setUI(new CustomButtonUI());
			buttonGeneratePass.setForeground(new Color(250,250,250));
			buttonGeneratePass.setFont(new Font("Tahoma", Font.BOLD, 14));
			buttonGeneratePass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			buttonGeneratePass.setBackground(new Color(186, 80, 135));
			buttonGeneratePass.addActionListener(new ActionListener() {
	      @Override
	      public void actionPerformed(ActionEvent e) {
	      	
	      	
	      	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM hh-mm-ss");
	      	if(SaveManager.exportTextFile(("AirNigma - ZAPISANY KLUCZ - " + dateFormat.format(System.currentTimeMillis()) + ".txt"), generatedKey)) {
	      		keyPassField.getTextField().setText(generatedKey);
	      	}
	      }
	    });
	    
			buttonGeneratePass.addMouseListener(new MouseAdapter() {
				@Override
	      public void mouseEntered(MouseEvent me) {
					buttonGeneratePass.setBackground(new Color(173, 66, 122));
			  }

			  @Override
			  public void mouseExited(MouseEvent me) {
			  	buttonGeneratePass.setBackground(new Color(186, 80, 135));
			  }
	    });
			
			add(buttonGeneratePass, "1, 5, fill, fill");
			add(buttonSavePass, "1, 7, fill, fill");
		} else { add(buttonSavePass, "1, 5, fill, fill"); }
		
	}
	
	
	@Override
	public String encode(String text) {	
	  Cipher cipher = Cipher.getInstance(algorithm);
	  cipher.init(Cipher.ENCRYPT_MODE, key, iv);
	  byte[] cipherText = cipher.doFinal(text.getBytes());
	  return Base64.getEncoder()
	      .encodeToString(cipherText);
	}
	
	@Override
	public String decode(String text) {
		StringBuilder stbuilder = new StringBuilder();
		int specialCharDisplacement = 0;
		
		for(int i = 0; i < text.length(); i++) {
			String alpha;
			
			char ch = text.charAt(i);
			
			if(Character.isLowerCase(ch)) alpha = lowcaseAlphabet;
			else alpha = upcaseAlphabet;
			
			int index = alpha.indexOf(ch);
			if(index < 0) {
				stbuilder.append(ch);
				specialCharDisplacement++;
				continue;
			}
			
			int keyIndex = upcaseAlphabet.indexOf(keyText.charAt(Math.floorMod(i - specialCharDisplacement, keyText.length())));
			if(keyIndex < 0) keyIndex = 0;

			index = index - keyIndex;
			
			if(index < 0) index = index + alpha.length();
			
			stbuilder.append(alpha.charAt(index));
		}
		return stbuilder.toString();
	}
	
	@Override
	public String getCipherName() {
		return cipherName;
	}
}
