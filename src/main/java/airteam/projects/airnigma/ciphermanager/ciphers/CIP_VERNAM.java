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

import airteam.projects.airnigma.ciphermanager.CipherManager;
import airteam.projects.airnigma.components.InputPanel;
import airteam.projects.airnigma.components.templates.CustomTextField;
import airteam.projects.airnigma.utilities.LogUtility;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JSpinner;

public class CIP_VERNAM extends CipherObject {
	private String cipherName = "VERNAM (One-Time-Pad)";
	
	private String alphabetText = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String lowcaseAlphabet = alphabetText.toLowerCase();
	private String upcaseAlphabet = alphabetText.toUpperCase();
	
	
	public CIP_VERNAM() {
		setOpaque(false);
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("45px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("45px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
	}
	
	@Override
	public void showOptions() {
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
		  		alphabetField.setRquiredHint("Wprowadz alfabet!");;
		  		alphabetField.showRequiredHint(true);
		  		return;
		  	}
		  	
		  	alphabetText = text;
		  	lowcaseAlphabet = alphabetText.toLowerCase();
		  	upcaseAlphabet = alphabetText.toUpperCase();
		  	
		  	alphabetField.showRequiredHint(false);
		  	
		  	CipherManager.updateOutput();
	  		CipherManager.updateInput();
		  }
		});
		
		add(alphabetField, "1, 1, fill, fill");
		
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
			index = index + displacement;
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
			index = index - displacement;
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
