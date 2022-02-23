package airteam.projects.airnigma.components.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import airteam.projects.airnigma.AirNigma;

@SuppressWarnings("serial")
public class CustomDialogFrame extends JDialog {
	private CustomDialogPanel dialogPanel;
	
  public CustomDialogFrame(String name, int dialogWidth, int dialogHeight, boolean onlyAcceptButton, boolean addDefaultIcons) {
  	int w = AirNigma.getFrame().getContentPane().getBounds().width;
  	int h = AirNigma.getFrame().getContentPane().getBounds().height;
  	
  	dialogPanel = new CustomDialogPanel(this, name, onlyAcceptButton, addDefaultIcons);
  	
  	setLocation(AirNigma.getFrame().getLocation().x + 8, AirNigma.getFrame().getLocation().y + 31);
  	setSize(w, h);
  	setUndecorated(true);
  	setResizable(false);
    setModal(true);
    setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
    setIconImage(AirNigma.getFrame().getIconImage());
    setBackground(new Color(0, 0, 0, 170));
    
    JPanel borderPanel = new JPanel();
    borderPanel.setOpaque(false);
    borderPanel.setBorder(new EmptyBorder((h- dialogHeight)/2, (w - dialogWidth)/2, (h - dialogHeight)/2, (w - dialogWidth)/2));
    borderPanel.setLayout(new BorderLayout(0, 0));
    borderPanel.add(dialogPanel);

    getContentPane().add(borderPanel);
  }
  
  public CustomDialogPanel getDialogPanel() {
  	return dialogPanel;
  }
  
}
