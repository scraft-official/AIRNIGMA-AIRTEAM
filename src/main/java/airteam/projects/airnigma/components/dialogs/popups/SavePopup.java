package airteam.projects.airnigma.components.dialogs.popups;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import airteam.projects.airnigma.AirNigma;
import airteam.projects.airnigma.savemanager.SaveManager;
import airteam.projects.airnigma.components.dialogs.CustomDialogFrame;
import airteam.projects.airnigma.components.dialogs.CustomDialogPanel;
import airteam.projects.airnigma.utilities.GraphicsUtility;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class SavePopup extends JPanel {
	private int width = 600;
	private int height = 250;
	
	public SavePopup() {
		setOpaque(false);
		
		Dialog dialog = new SavePopup.Dialog();
		CustomDialogPanel panel = dialog.frame.getDialogPanel();
		
		
		
		JTextPane text = new JTextPane();
		StyledDocument doc = text.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		text.setText("ZAMKNIĘCIE APLIKACJI BEZ ZAPISU PLANSZY\nBĘDZIE SKUTKOWAĆ UTRATĄ WSZELKICH DANYCH!");
		text.setFont(new Font("Tahoma", Font.PLAIN, 19));
		text.setEditable(false);
		text.setOpaque(false);
		add(text);
		
		
		JButton cancelButton = panel.getCancelButton();
		cancelButton.setText("NIE ZAPISUJ");
		cancelButton.setIcon(new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/logout-icon.png"), 17, 17)));
		cancelButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
      	AirNigma.getFrame().dispose();
      	System.exit(0);
      }
    });
		
		
		JButton acceptButton = panel.getAcceptButton();
		acceptButton.setText("ZAPISZ");
		acceptButton.setIcon(new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/save-icon.png"), 16, 16)));
		acceptButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
      	if(SaveManager.saveWorkspace()) {
      		try { Thread.sleep(1000); } catch (InterruptedException ex) {}
      		AirNigma.getFrame().dispose();
      		System.exit(0);
      	}
      	else dialog.frame.dispose();
      }
    });
		
		panel.setContentPanel(this);
		dialog.frame.setVisible(true);
	}
	
	
	public class Dialog extends JPanel {
		public CustomDialogFrame frame = new CustomDialogFrame("CZY CHCESZ ZAPISAĆ PLANSZĘ?", width, height, false, false);
	}
}
