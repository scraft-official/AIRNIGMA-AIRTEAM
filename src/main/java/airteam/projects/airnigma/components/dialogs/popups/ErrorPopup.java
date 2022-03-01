package airteam.projects.airnigma.components.dialogs.popups;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import airteam.projects.airnigma.components.dialogs.CustomDialogFrame;
import airteam.projects.airnigma.components.dialogs.CustomDialogPanel;
import airteam.projects.airnigma.utilities.GraphicsUtility;

@SuppressWarnings("serial")
public class ErrorPopup extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 2643527015794338927L;
	private int width = 600;
	private int height = 250;

	public ErrorPopup(String errorMessage) {
		setOpaque(false);

		Dialog dialog = new ErrorPopup.Dialog();
		CustomDialogPanel panel = dialog.frame.getDialogPanel();

		JTextPane text = new JTextPane();
		StyledDocument doc = text.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		text.setText(errorMessage);
		text.setFont(new Font("Tahoma", Font.PLAIN, 19));
		text.setEditable(false);
		text.setOpaque(false);
		add(text);

		JButton acceptButton = panel.getAcceptButton();
		acceptButton.setText("DOBRZE");
		acceptButton.setIcon(new ImageIcon(
				GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/check-mark-icon.png"), 13, 13)));
		acceptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.frame.dispose();
			}
		});

		panel.setContentPanel(this);
		dialog.frame.setVisible(true);
	}

	public class Dialog extends JPanel {
		/**
		 *
		 */
		private static final long serialVersionUID = 4757880426783162979L;
		public CustomDialogFrame frame = new CustomDialogFrame("WYSTAPIŁ BŁĄD!", width, height, true, false);
	}
}
