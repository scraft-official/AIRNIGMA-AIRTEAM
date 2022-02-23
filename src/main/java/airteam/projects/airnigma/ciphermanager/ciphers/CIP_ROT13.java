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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import airteam.projects.airnigma.ciphermanager.CipherManager;
import airteam.projects.airnigma.ciphermanager.CipherManager.CIPHER_MODE;
import airteam.projects.airnigma.components.InputPanel;
import airteam.projects.airnigma.components.OutputPanel;
import airteam.projects.airnigma.components.templates.CustomButtonUI;
import airteam.projects.airnigma.components.templates.CustomTextField;
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

public class CIP_ROT13 extends CipherObject {
	private String cipherName = "ROT13 (Szyfr Cezara)";
	
	private String alphabetText = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String lowcaseAlphabet = alphabetText.toLowerCase();
	private String upcaseAlphabet = alphabetText.toUpperCase();
	
	JLabel cipherChart = new JLabel();
	
	private int displacement = 13; 
	
	//Map of letter frequency in english alphabet.
	@SuppressWarnings("serial")
	HashMap<Character, Double> englishLetterFrequency = new HashMap<Character, Double>()
	{{
     put('e', 12.7);
     put('t', 9.06);
     put('a', 8.17);
     put('o', 7.51);
     put('i', 6.97);
     put('n', 6.75);
     put('s', 6.33);
     put('h', 6.89);
     put('r', 5.99);
     put('d', 4.25);
     put('l', 4.03);
     put('c', 2.78);
     put('u', 2.76);
     put('m', 2.41);
     put('w', 2.36);
     put('f', 2.23);
     put('g', 2.02);
     put('y', 1.97);
     put('p', 1.93);
     put('b', 1.29);
     put('v', 0.98);
     put('k', 0.77);
     put('j', 0.15);
     put('x', 0.15);
     put('q', 0.10);
     put('z', 0.07);
	}};
	
	
	public CIP_ROT13() {
		setOpaque(false);
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("45px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("45px"),
				RowSpec.decode("bottom:24px"),
				RowSpec.decode("115px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("35px"),}));
	}
	
	@Override
	public void showOptions() {
		removeAll();
		
		CustomTextField displacementField = new CustomTextField("PRZESKOK LITER (KLUCZ)");
		displacementField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		displacementField.setLineColor(new Color(150,150,150), new Color(250,250,250));
		displacementField.setForeground(new Color(250,250,250));
		displacementField.setCaretColor(new Color(250,250,250));
		displacementField.setText(String.valueOf(displacement));
		displacementField.setRquiredHint("* Wprowadź liczbę!");
		displacementField.getDocument().addDocumentListener(new DocumentListener() {
		  public void changedUpdate(DocumentEvent e) {
		  	updateDisplacement();
		  }
		  public void removeUpdate(DocumentEvent e) {
		  	updateDisplacement();
		  }
		  public void insertUpdate(DocumentEvent e) {
		  	updateDisplacement();
		  }

		  public void updateDisplacement() {
		  	try {
		  		int value = Integer.valueOf(displacementField.getText());
		  		if(value >= alphabetText.length()) value = (value - ((value / alphabetText.length()) * alphabetText.length()));
		  		displacement = value;
		  		displacementField.showRequiredHint(false);
		  		CipherManager.updateOutput();
		  		CipherManager.updateInput();
		  	} catch(Exception e) {
		  		displacementField.showRequiredHint(true);
		  	}
		  }
		});
		
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
		  	
		  	
		  	try {
		  		int value = Integer.valueOf(displacementField.getText());
		  		if(value >= alphabetText.length()) value = (value - ((value / alphabetText.length()) * alphabetText.length()));
		  		displacement = value; 
		  	} catch(Exception e) { displacement = (displacement - ((displacement / alphabetText.length()) * alphabetText.length())); }
		  	
		  	alphabetField.showRequiredHint(false);
		  	
		  	CipherManager.updateOutput();
	  		CipherManager.updateInput();
		  }
		});
		
		cipherChart.setIcon(new ImageIcon(renderCipherChar()));
		
		JLabel cipherCharTitle = new JLabel("ANALIZA STATYCZNA LITER");
		cipherCharTitle.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cipherCharTitle.setForeground(new Color(150,150,150));
		
		add(cipherCharTitle, "1, 4, left bottom");
		add(displacementField, "1, 1, fill, fill");
		add(alphabetField, "1, 3, fill, fill");
		add(cipherChart, "1, 5, center, center");
		
		if(CipherManager.getSelectedMode() == CIPHER_MODE.DECODE) {
			JButton buttonFindKey = new JButton("ZNAJDŹ MOŻLIWY KLUCZ");
			buttonFindKey.setIconTextGap(4);
			buttonFindKey.setIcon(new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/magnifier-icon.png"), 15, 15)));
			buttonFindKey.setUI(new CustomButtonUI());
			buttonFindKey.setForeground(new Color(22,22,22));
			buttonFindKey.setFont(new Font("Tahoma", Font.BOLD, 14));
			buttonFindKey.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			buttonFindKey.setBackground(new Color(250,250,250));
			buttonFindKey.addActionListener(new ActionListener() {
	      @Override
	      public void actionPerformed(ActionEvent e) {
	      	displacementField.setText(String.valueOf(getPossibleDisplacementKey(InputPanel.getInputText())));
	      }
	    });
	    
			buttonFindKey.addMouseListener(new MouseAdapter() {
				@Override
	      public void mouseEntered(MouseEvent me) {
					buttonFindKey.setBackground(new Color(200,200,200));
			  }

			  @Override
			  public void mouseExited(MouseEvent me) {
			  	buttonFindKey.setBackground(new Color(250,250,250));
			  }
	    });
			
			add(buttonFindKey, "1, 7, fill, fill");
		}
	}
	
	public int getPossibleDisplacementKey(String text) {
		if(text.length() == 0) return 0;
		double lowestDifference = Double.POSITIVE_INFINITY;
		int possibleKey = 0;
		
		for(int k = 1; k <= englishLetterFrequency.size(); k++) {
			String newDecodedText = decode(text.toLowerCase(), k, "abcdefghijklmnopqrstuvwxyz");
			double newDifference = getDifferenceFromAlpha(newDecodedText);
			
			if(newDifference < lowestDifference) {
				lowestDifference = newDifference;
				possibleKey = k;
			}
		}
		return possibleKey;
	}
	
	public double getDifferenceFromAlpha(String text) {
		double sum = 0;
		for(char c : englishLetterFrequency.keySet()) {
			int count = text.length() - text.replace(String.valueOf(c), "").length();
			sum = sum + Math.abs( count * 100 / text.length() - englishLetterFrequency.get(c));
		}
		return (sum / englishLetterFrequency.size());
	}
	
	public BufferedImage renderCipherChar() {
		String inputText = InputPanel.getInputText().toUpperCase();
		HashMap<Character, Integer> alphaCountMap = new HashMap<>();
		
		int width = getWidth();
		if(getWidth() == 0) width = 242;
		
		int highestNumber = 1;
		for(char c : upcaseAlphabet.toCharArray()) {
			int count = inputText.length() - inputText.replace(String.valueOf(c), "").length();
			alphaCountMap.put(c, count);
			if(count > highestNumber) highestNumber = count;
			
		}
		
		BufferedImage chartImage = new BufferedImage(width, 110, 2);
		Graphics2D g2d = chartImage.createGraphics();
		
		g2d.setFont(new Font("Tahoma", Font.PLAIN, 9));
		FontMetrics ft = g2d.getFontMetrics();
		
		int leftPadding = ft.stringWidth(String.valueOf(highestNumber)) + 3;
		int spacing = (int) Math.floor(((width - leftPadding - alphabetText.length()) / (float) alphabetText.length()));
		
		leftPadding = (int) Math.floor((width + leftPadding - (spacing*alphabetText.length()) - alphabetText.length())/2);
		
		int i = 0;
		for(char c : alphaCountMap.keySet()) {
			int columnHeight = (int) Math.ceil(((Math.ceil(1000000/highestNumber) * alphaCountMap.get(c)) / 1000000) * 95);
			if(alphaCountMap.get(c) > 0) columnHeight = columnHeight - 2;
			
			g2d.setColor(new Color(186, 74, 54));
			g2d.fillRect(leftPadding + (spacing*i) + i, 100-columnHeight-2, spacing, columnHeight+2);
			
			g2d.setColor(new Color(180,180,180));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.drawString(String.valueOf(c), leftPadding + 1 + (spacing*i) + i + (((spacing) - ft.stringWidth(String.valueOf(c))) / 2), 110);
			i++;
			
		}
		
		g2d.setColor(new Color(80,80,80));
		g2d.fillRect(0, 100, width, 1);
		
		int limitOfLines = (int) Math.ceil(highestNumber/2);
		if(limitOfLines > 8) limitOfLines = 8;
		else if(limitOfLines == 0) limitOfLines = 1;
		for(int x = 1; x < limitOfLines + 1; x++) {
			g2d.setColor(new Color(80,80,80));
			g2d.fillRect(leftPadding , (int) (Math.ceil(Math.floor(100 / limitOfLines) * x) - ((Math.ceil(Math.floor(100 / limitOfLines) * 1))) + 5), width, 1);
			
			g2d.setColor(new Color(180,180,180));
			String barValue = String.valueOf((int) Math.round(((highestNumber/limitOfLines) * (limitOfLines))) - Math.round(((highestNumber/limitOfLines) * (x-1))));
			g2d.drawString(barValue, leftPadding - ft.stringWidth(barValue) - 3, (int) (Math.ceil(Math.floor(100 / limitOfLines) * x) - ((Math.ceil(Math.floor(100 / limitOfLines) * 1))) + 5 + (ft.getHeight()/2)));
		}
		
		g2d.dispose();
		return chartImage;
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
		cipherChart.setIcon(new ImageIcon(renderCipherChar()));
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
		cipherChart.setIcon(new ImageIcon(renderCipherChar()));
		return stbuilder.toString();
	}
	
	public String decode(String text, int displacement, String alpha) {
		StringBuilder stbuilder = new StringBuilder();
		for(int i = 0; i < text.length(); i++) {	
			char ch = text.charAt(i);
			
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
