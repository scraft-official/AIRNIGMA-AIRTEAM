package airteam.projects.airnigma.ciphermanager.ciphers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.HashMap;

import javax.crypto.IllegalBlockSizeException;
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
import airteam.projects.airnigma.components.dialogs.popups.ErrorPopup;
import airteam.projects.airnigma.components.templates.CustomButton;
import airteam.projects.airnigma.components.templates.CustomTextFieldWithButton;
import airteam.projects.airnigma.savemanager.SaveManager;
import airteam.projects.airnigma.utilities.EncryptionUtility;
import airteam.projects.airnigma.utilities.GraphicsUtility;

public class CIP_RSA extends CipherObject {
	private static final long serialVersionUID = -7611347891910120568L;
	private String cipherName = "SZYFR RSA (KEY-PAIR)";
	private Boolean doAnalize = true;

	private KeyPair cipherKeyPair = EncryptionUtility.generateKeyPair(2048, "RSA");
	private PublicKey cipherPublicKey = cipherKeyPair.getPublic();
	private PrivateKey cipherPrivateKey = cipherKeyPair.getPrivate();

	public CIP_RSA() {
		setOpaque(false);
		setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("min(234px;default):grow"), },
				new RowSpec[] { RowSpec.decode("45px"), FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("45px"),
						FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("fill:35px"), FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("35px"), }));
	}

	@Override
	public void showOptions() {
		removeAll();

		CustomTextFieldWithButton fieldPublicKey = new CustomTextFieldWithButton("KLUCZ PUBLICZNY SZYFRU",
				new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/folder-icon.png"), 17, 17)));
		fieldPublicKey.getTextField().setText(Base64.getEncoder().encodeToString(cipherPublicKey.getEncoded()));
		fieldPublicKey.getTextField().getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
			}

			public void update() {
				String text = fieldPublicKey.getTextField().getText().replaceFirst(" ", "");
				if (text.length() == 0) {
					fieldPublicKey.getTextField().setRquiredHint("Wprowad?? klucz!");
					fieldPublicKey.getTextField().showRequiredHint(true);
					return;
				}

				try {
					cipherPublicKey = EncryptionUtility
							.convertBytesToPublicKey(Base64.getDecoder().decode(fieldPublicKey.getTextField().getText()));
				} catch (Exception er) {
					fieldPublicKey.getTextField().setRquiredHint("B??edny klucz!");
					fieldPublicKey.getTextField().showRequiredHint(true);
					return;
				}

				fieldPublicKey.getTextField().showRequiredHint(false);

				CipherManager.updateOutput();
			}
		});
		fieldPublicKey.getButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String fileContent = SaveManager.importTextFile(".txt");
				if (fileContent != null) {
					try {
						cipherPublicKey = EncryptionUtility.convertBytesToPublicKey(Base64.getDecoder().decode(fileContent));
						if (fileContent != null) {
							fieldPublicKey.getTextField().setText(fileContent);
						}
					} catch (Exception er) {
						new ErrorPopup(
								"Nie uda??o si?? odczyta?? tego klucza!\nUpewnij si??, ??e u??yto klucza do szyfru \"RSA KLUCZ PUBLICZNY\".");
					}
				}
			}
		});

		CustomTextFieldWithButton fieldPrivateKey = new CustomTextFieldWithButton("KLUCZ PRYWATNY SZYFRU",
				new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/folder-icon.png"), 17, 17)));
		fieldPrivateKey.getTextField().setText(Base64.getEncoder().encodeToString(cipherPrivateKey.getEncoded()));
		fieldPrivateKey.getTextField().getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
			}

			public void update() {
				String text = fieldPrivateKey.getTextField().getText().replaceFirst(" ", "");
				if (text.length() == 0) {
					fieldPrivateKey.getTextField().setRquiredHint("Wprowad?? klucz!");
					fieldPrivateKey.getTextField().showRequiredHint(true);
					return;
				}

				try {
					cipherPrivateKey = EncryptionUtility
							.convertBytesToPrivateKey((Base64.getDecoder().decode(fieldPrivateKey.getTextField().getText())));
				} catch (Exception er) {
					fieldPrivateKey.getTextField().setRquiredHint("B??edny klucz!");
					fieldPrivateKey.getTextField().showRequiredHint(true);
					return;
				}

				fieldPrivateKey.getTextField().showRequiredHint(false);

				CipherManager.updateOutput();
			}
		});
		fieldPrivateKey.getButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String fileContent = SaveManager.importTextFile(".txt");
				if (fileContent != null) {
					try {
						cipherPrivateKey = EncryptionUtility.convertBytesToPrivateKey(Base64.getDecoder().decode(fileContent));
						if (fileContent != null) {
							fieldPrivateKey.getTextField().setText(fileContent);
						}
					} catch (Exception er) {
						new ErrorPopup(
								"Nie uda??o si?? odczyta?? tego klucza!\nUpewnij si??, ??e u??yto klucza do szyfru \"RSA KLUCZ PRYWATNY\".");
					}
				}
			}
		});

		add(fieldPublicKey, "1, 1, fill, fill");
		add(fieldPrivateKey, "1, 3, fill, fill");

		JButton buttonSaveKey = new CustomButton("ZAPISZ KLUCZ", new Color(45, 150, 73),
				new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/save-icon.png"), 14, 14)));
		buttonSaveKey.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String pbKeyText = Base64.getEncoder().encodeToString(cipherPublicKey.getEncoded());
				String pvKeyText = Base64.getEncoder().encodeToString(cipherPrivateKey.getEncoded());

				SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM hh-mm-ss");

				HashMap<String, String> filesDataMap = new HashMap<>();
				filesDataMap.put(("AirNigma - KLUCZ PUBLICZNY - " + dateFormat.format(System.currentTimeMillis()) + ".txt"),
						pbKeyText);
				filesDataMap.put(("AirNigma - KLUCZ PRYWATNY - " + dateFormat.format(System.currentTimeMillis()) + ".txt"),
						pvKeyText);

				SaveManager.exportTextFile(filesDataMap);
			}
		});

		if (CipherManager.getSelectedMode() == CIPHER_MODE.ENCODE) {
			JButton buttonGenKey = new CustomButton("WYGENERUJ KLUCZ", new Color(186, 80, 135),
					new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/key-icon.png"), 14, 14)));
			buttonGenKey.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					KeyPair newKeyPair = EncryptionUtility.generateKeyPair(2048, "RSA");

					String pbKeyText = Base64.getEncoder().encodeToString(newKeyPair.getPublic().getEncoded());
					String pvKeyText = Base64.getEncoder().encodeToString(newKeyPair.getPrivate().getEncoded());

					SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM hh-mm-ss");

					HashMap<String, String> filesDataMap = new HashMap<>();
					filesDataMap.put(("AirNigma - KLUCZ PUBLICZNY - " + dateFormat.format(System.currentTimeMillis()) + ".txt"),
							pbKeyText);
					filesDataMap.put(("AirNigma - KLUCZ PRYWATNY - " + dateFormat.format(System.currentTimeMillis()) + ".txt"),
							pvKeyText);

					if (SaveManager.exportTextFile(filesDataMap)) {
						cipherPublicKey = newKeyPair.getPublic();
						cipherPrivateKey = newKeyPair.getPrivate();

						fieldPublicKey.getTextField().setText(pbKeyText);
						fieldPrivateKey.getTextField().setText(pvKeyText);
					}
				}
			});

			add(buttonGenKey, "1, 5, fill, fill");
			add(buttonSaveKey, "1, 7, fill, fill");
		} else {
			add(buttonSaveKey, "1, 5, fill, fill");
		}
	}

	@Override
	public String encode(String text) throws Exception {
		if (text.length() == 0) {
			return null;
		}
		try {
			return EncryptionUtility.encode("RSA", text, cipherPublicKey);
		} catch (IllegalBlockSizeException e) {
			throw new Exception("Przekroczono maksymaln?? d??ugo???? szyfrowanej wiadomo??ci!");
		} catch (Exception e) {
			throw new Exception("Nie mo??na zakodowa?? szyfru! Upewnij si??, ??e wprowadzono odpowiedni klucz publiczny!");
		}
	}

	@Override
	public String decode(String text) throws Exception {
		if (text.length() == 0) {
			return null;
		}
		try {
			return EncryptionUtility.decode("RSA", text, cipherPrivateKey);
		} catch (IllegalBlockSizeException e) {
			throw new Exception("Przekroczono maksymaln?? d??ugo???? szyfrowanej wiadomo??ci!");
		} catch (Exception e) {
			throw new Exception("Nie mo??na odczyta?? szyfru! Upewnij si??, ??e wprowadzono odpowiedni klucz prywatny!");
		}
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
