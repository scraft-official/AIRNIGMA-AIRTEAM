package airteam.projects.airnigma.ciphermanager.ciphers;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import airteam.projects.airnigma.components.templates.CustomTextField;
import airteam.projects.airnigma.utilities.LogUtility;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CIP_ROT13 extends CipherObject {
	private String cipherName = "ROT13";
	
	private String alphabetText = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String lowcaseAlphabet = alphabetText.toLowerCase();
	private String upcaseAlphabet = alphabetText.toUpperCase();
	
	
	public CIP_ROT13() {
		setOpaque(false);
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("45px"),}));
		
		CustomTextField textField = new CustomTextField("ZASTOSOWANY ALFABET");
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setLineColor(new Color(150,150,150), new Color(250,250,250));
		textField.setForeground(new Color(250,250,250));
		textField.setCaretColor(new Color(250,250,250));
		textField.setText(alphabetText);
		textField.getDocument().addDocumentListener(new DocumentListener() {
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
		  	alphabetText = textField.getText();
		  	lowcaseAlphabet = alphabetText.toLowerCase();
		  	upcaseAlphabet = alphabetText.toUpperCase();
		  }
		});
		
		add(textField, "1, 1, fill, fill");
		
	}
	
	@Override
	public String encode(String text) {	
		StringBuilder stbuilder = new StringBuilder();
		for(int i = 0; i < text.length(); i++) {
			String alpha;
			
			char ch = text.charAt(i);
			
			if(Character.isLowerCase(ch)) alpha = lowcaseAlphabet;
			else alpha = upcaseAlphabet;
			
			int index = alpha.indexOf(ch);
			if(index < 0) {
				stbuilder.append(ch);
				continue;
			}
			index = index + 13;
			if(index >= alpha.length()) index = index - alpha.length();
			
			stbuilder.append(alpha.charAt(index));
		}
		return stbuilder.toString();
	}
	
	@Override
	public String decode(String text) {
		StringBuilder stbuilder = new StringBuilder();
		for(int i = 0; i < text.length(); i++) {
			String alpha;
			
			char ch = text.charAt(i);
			
			if(Character.isLowerCase(ch)) alpha = lowcaseAlphabet;
			else alpha = upcaseAlphabet;
			
			int index = alpha.indexOf(ch);
			if(index < 0) {
				stbuilder.append(ch);
				continue;
			}
			index = index - 13;
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
