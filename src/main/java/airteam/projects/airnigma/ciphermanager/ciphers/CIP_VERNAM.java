package airteam.projects.airnigma.ciphermanager.ciphers;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import airteam.projects.airnigma.ciphermanager.CipherManager;
import airteam.projects.airnigma.ciphermanager.CipherManager.CIPHER_MODE;
import airteam.projects.airnigma.components.InputPanel;
import airteam.projects.airnigma.components.OutputPanel;
import airteam.projects.airnigma.components.templates.CustomButton.CustomButtonUI;
import airteam.projects.airnigma.components.templates.CustomTextField;
import airteam.projects.airnigma.components.templates.CustomTextFieldWithButton;
import airteam.projects.airnigma.savemanager.SaveManager;
import airteam.projects.airnigma.utilities.GraphicsUtility;
import airteam.projects.airnigma.utilities.LogUtility;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JSpinner;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class CIP_VERNAM extends CipherObject {
	private String cipherName = "SZYFR VERNAMA (One-Time-Pad)";
	
	private String keyText = "WprowadzTrudneHasloTutaj";
	
	private String alphabetText = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String lowcaseAlphabet = alphabetText.toLowerCase();
	private String upcaseAlphabet = alphabetText.toUpperCase();
	
	public CIP_VERNAM() {
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
	
	@Override
	public void showOptions() {
		removeAll();
		
		CustomTextField alphabetField = new CustomTextField("ZASTOSOWANY ALFABET");
		alphabetField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		alphabetField.setLineColor(new Color(150,150,150), new Color(250,250,250));
		alphabetField.setForeground(new Color(250,250,250));
		alphabetField.setCaretColor(new Color(250,250,250));
		alphabetField.setText(alphabetText);
		alphabetField.getDocument().addDocumentListener(new DocumentListener() {
		  public void changedUpdate(DocumentEvent e) {
		  	updateAlphabet();
		  }
		  public void removeUpdate(DocumentEvent e) {
		  	updateAlphabet();
		  }
		  public void insertUpdate(DocumentEvent e) {
		  	updateAlphabet();
		  }

		  public void updateAlphabet() {
		  	String text = alphabetField.getText().replaceFirst(" ","");
		  	if(text.length() == 0) {
		  		alphabetField.setRquiredHint("Wprowadź alfabet!");;
		  		alphabetField.showRequiredHint(true);
		  		return;
		  	}
		  	
		  	alphabetText = text;
		  	lowcaseAlphabet = alphabetText.toLowerCase();
		  	upcaseAlphabet = alphabetText.toUpperCase();
		  	
		  	alphabetField.showRequiredHint(false);
		  	
		  	CipherManager.updateOutput();
		  }
		});
		
		CustomTextFieldWithButton keyPassField = new CustomTextFieldWithButton("KLUCZ SZYFRU (HASŁO)", new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/folder-icon.png"), 17, 17)));
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
		
		add(alphabetField, "1, 1, fill, fill");
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
	      	StringBuilder stbuilder = new StringBuilder();
	      	int passLength = 10;
	      	if(InputPanel.getInputText().length() > passLength) passLength = InputPanel.getInputText().length();
	      	
	      	Random random = new Random();
	      	for(int i = 0; i < passLength; i++) {
	      		stbuilder.append(alphabetText.charAt(random.nextInt((alphabetText.length() - 1) - 0) + 0));
	      	}
	      	
	      	String generatedKey = stbuilder.toString();
	      	
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
		StringBuilder stbuilder = new StringBuilder();
		int specialCharDisplacement = 0; //This is to maintain the key format only on alpha characters. So ' ' don't get encoded.
		
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

			index = index + keyIndex;
			
			if(index >= alpha.length()) index = index - alpha.length();
			
			stbuilder.append(alpha.charAt(index));
		}
		return stbuilder.toString();
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
