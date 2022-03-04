package airteam.projects.airnigma.ciphermanager.ciphers;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import airteam.projects.airnigma.ciphermanager.CipherManager;
import airteam.projects.airnigma.ciphermanager.CipherManager.CIPHER_MODE;
import airteam.projects.airnigma.components.InputPanel;
import airteam.projects.airnigma.components.templates.CustomButton;
import airteam.projects.airnigma.components.templates.CustomTextField;
import airteam.projects.airnigma.components.templates.CustomTextFieldWithButton;
import airteam.projects.airnigma.savemanager.SaveManager;
import airteam.projects.airnigma.utilities.GraphicsUtility;

public class CIP_VERNAM extends CipherObject {
	private static final long serialVersionUID = 3061941595280247380L;
	private String cipherName = "SZYFR VERNAMA (One-Time-Pad)";
	private Boolean doAnalize = false;

	private String keyText = "WprowadzTrudneHasloTutaj";

	private String alphabetText = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String lowcaseAlphabet = alphabetText.toLowerCase();
	private String upcaseAlphabet = alphabetText.toUpperCase();

	public CIP_VERNAM() {
		setOpaque(false);
		setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("min(234px;default):grow"), },
				new RowSpec[] { RowSpec.decode("45px"), FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("45px"),
						FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("35px"), FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("fill:35px"), }));
	}

	@Override
	public void showOptions() {
		removeAll();

		CustomTextField alphabetField = new CustomTextField("ZASTOSOWANY ALFABET");
		alphabetField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		alphabetField.setLineColor(new Color(150, 150, 150), new Color(250, 250, 250));
		alphabetField.setForeground(new Color(250, 250, 250));
		alphabetField.setCaretColor(new Color(250, 250, 250));
		alphabetField.setText(alphabetText);
		alphabetField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				updateAlphabet();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateAlphabet();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateAlphabet();
			}

			public void updateAlphabet() {
				String text = alphabetField.getText().replaceFirst(" ", "");
				if (text.length() == 0) {
					alphabetField.setRquiredHint("Wprowadź alfabet!");
					;
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

		CustomTextFieldWithButton keyPassField = new CustomTextFieldWithButton("KLUCZ SZYFRU (HASŁO)",
				new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/folder-icon.png"), 17, 17)));
		keyPassField.getTextField().getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				updateKeyText();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateKeyText();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateKeyText();
			}

			public void updateKeyText() {
				String text = keyPassField.getTextField().getText().replaceFirst(" ", "");
				if (text.length() == 0) {
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
				if (fileContent != null) {
					keyPassField.getTextField().setText(fileContent);
				}
			}
		});

		add(alphabetField, "1, 1, fill, fill");
		add(keyPassField, "1, 3, fill, fill");

		JButton buttonSavePass = new CustomButton("ZAPISZ HASŁO", new Color(45, 150, 73),
				new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/save-icon.png"), 14, 14)));
		buttonSavePass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM hh-mm-ss");
				SaveManager.exportTextFile(
						("AirNigma - ZAPISANY KLUCZ - " + dateFormat.format(System.currentTimeMillis()) + ".txt"),
						keyPassField.getTextField().getText());

			}
		});

		if (CipherManager.getSelectedMode() == CIPHER_MODE.ENCODE) {
			JButton buttonGenKey = new CustomButton("WYGENERUJ KLUCZ", new Color(186, 80, 135),
					new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/key-icon.png"), 14, 14)));
			buttonGenKey.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					StringBuilder stbuilder = new StringBuilder();
					int passLength = 10;
					if (InputPanel.getInputText().length() > passLength) {
						passLength = InputPanel.getInputText().length();
					}

					Random random = new Random();
					for (int i = 0; i < passLength; i++) {
						stbuilder.append(alphabetText.charAt(random.nextInt((alphabetText.length() - 1) - 0) + 0));
					}

					String generatedKey = stbuilder.toString();

					SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM hh-mm-ss");
					if (SaveManager.exportTextFile(
							("AirNigma - ZAPISANY KLUCZ - " + dateFormat.format(System.currentTimeMillis()) + ".txt"),
							generatedKey)) {
						keyPassField.getTextField().setText(generatedKey);
					}

				}
			});

			add(buttonGenKey, "1, 5, fill, fill");
			add(buttonSavePass, "1, 7, fill, fill");
		} else {
			add(buttonSavePass, "1, 5, fill, fill");
		}

	}

	@Override
	public String encode(String text) {
		StringBuilder stbuilder = new StringBuilder();
		int specialCharDisplacement = 0; // This is to maintain the key format only on alpha characters. So ' ' don't get
																			// encoded.

		for (int i = 0; i < text.length(); i++) {
			String alpha;

			char ch = text.charAt(i);

			if (Character.isLowerCase(ch)) {
				alpha = lowcaseAlphabet;
			} else {
				alpha = upcaseAlphabet;
			}

			int index = alpha.indexOf(ch);
			if (index < 0) {
				stbuilder.append(ch);
				specialCharDisplacement++;
				continue;
			}

			int keyIndex = upcaseAlphabet
					.indexOf(keyText.charAt(Math.floorMod(i - specialCharDisplacement, keyText.length())));
			if (keyIndex < 0) {
				keyIndex = 0;
			}

			index = index + keyIndex;

			if (index >= alpha.length()) {
				index = index - alpha.length();
			}

			stbuilder.append(alpha.charAt(index));
		}
		return stbuilder.toString();
	}

	@Override
	public String decode(String text) {
		StringBuilder stbuilder = new StringBuilder();
		int specialCharDisplacement = 0;

		for (int i = 0; i < text.length(); i++) {
			String alpha;

			char ch = text.charAt(i);

			if (Character.isLowerCase(ch)) {
				alpha = lowcaseAlphabet;
			} else {
				alpha = upcaseAlphabet;
			}

			int index = alpha.indexOf(ch);
			if (index < 0) {
				stbuilder.append(ch);
				specialCharDisplacement++;
				continue;
			}

			int keyIndex = upcaseAlphabet
					.indexOf(keyText.charAt(Math.floorMod(i - specialCharDisplacement, keyText.length())));
			if (keyIndex < 0) {
				keyIndex = 0;
			}

			index = index - keyIndex;

			if (index < 0) {
				index = index + alpha.length();
			}

			stbuilder.append(alpha.charAt(index));
		}
		return stbuilder.toString();
	}

	@Override
	public String getCipherName() {
		return cipherName;
	}

	@Override
	public Boolean doAnalize() {
		return doAnalize;
	}
}
