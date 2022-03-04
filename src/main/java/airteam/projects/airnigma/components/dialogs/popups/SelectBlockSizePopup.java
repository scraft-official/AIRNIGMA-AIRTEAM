package airteam.projects.airnigma.components.dialogs.popups;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import airteam.projects.airnigma.ciphermanager.ciphers.CIP_AES;
import airteam.projects.airnigma.ciphermanager.ciphers.CipherObject;
import airteam.projects.airnigma.components.dialogs.CustomDialogFrame;
import airteam.projects.airnigma.components.dialogs.CustomDialogPanel;
import airteam.projects.airnigma.components.templates.CustomTextField;
import airteam.projects.airnigma.utilities.GraphicsUtility;

@SuppressWarnings("serial")
public class SelectBlockSizePopup extends JPanel {
	private int width = 600;
	private int height = 250;

	private String[] blockSizes = new String[] { "128", "192", "256" };
	private int blockSize = 256;

	private boolean isCanceled = false;

	public SelectBlockSizePopup(CipherObject cipher) {
		setOpaque(false);

		Dialog dialog = new SelectBlockSizePopup.Dialog();
		CustomDialogPanel panel = dialog.frame.getDialogPanel();

		JTextPane text = new JTextPane();
		StyledDocument doc = text.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		text.setText("WYBIERZ WIELKOŚĆ BLOKU KLUCZA\n(128, 192 LUB 256)");
		text.setFont(new Font("Tahoma", Font.PLAIN, 19));
		text.setEditable(false);
		text.setOpaque(false);
		setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("80px"), FormSpecs.LINE_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("30px"), }));

		CustomTextField fieldBlockSize = new CustomTextField("WIELKOŚĆ BLOKU");
		fieldBlockSize.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fieldBlockSize.setLineColor(new Color(150, 150, 150), new Color(20, 20, 20));
		fieldBlockSize.setText(String.valueOf(blockSize));
		fieldBlockSize.setRquiredHint("* Wprowadź prawidłową wielkość!");

		add(text, "1, 2, left, center");
		add(fieldBlockSize, "1, 4, fill, top");

		JButton acceptButton = panel.getAcceptButton();
		acceptButton.setText("WYGENERUJ KLUCZ");
		acceptButton.setIcon(
				new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/key-icon.png"), 15, 15)));
		acceptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fieldBlockSize.getText().length() == 0) {
					fieldBlockSize.setRquiredHint("Wprowadź wielkość bloku!");
					fieldBlockSize.showRequiredHint(true);
					fieldBlockSize.repaint();
					return;
				}

				if (!fieldBlockSize.getText().equals("128") && !fieldBlockSize.getText().equals("192")
						&& !fieldBlockSize.getText().equals("256")) {
					fieldBlockSize.setRquiredHint("Dostępne wielkości to 128, 192 i 256!");
					fieldBlockSize.showRequiredHint(true);
					fieldBlockSize.repaint();
					return;
				}

				if (cipher instanceof CIP_AES) {
					((CIP_AES) cipher).setCipherBlockSize(Integer.valueOf(fieldBlockSize.getText()));
				}
				dialog.frame.dispose();
			}
		});

		panel.getCloseButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				isCanceled = true;
			}
		});

		panel.setContentPanel(this);

		dialog.frame.setVisible(true);
	}

	public boolean isCanceled() {
		return isCanceled;
	}

	public String[] getSizes() {
		return blockSizes;
	}

	public class Dialog extends JPanel {
		/**
		 *
		 */
		private static final long serialVersionUID = -81726178921043813L;
		public CustomDialogFrame frame = new CustomDialogFrame("WYBIERZ WIELKOŚĆ BLOKU", width, height, true, false);
	}
}
