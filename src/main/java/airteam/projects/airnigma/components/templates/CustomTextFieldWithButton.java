package airteam.projects.airnigma.components.templates;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import airteam.projects.airnigma.ciphermanager.CipherManager;
import airteam.projects.airnigma.ciphermanager.CipherManager.CIPHER_MODE;
import airteam.projects.airnigma.components.InputPanel;
import airteam.projects.airnigma.utilities.GraphicsUtility;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CustomTextFieldWithButton extends JPanel {
	private CustomTextField textField;
	private JButton importButton;
	
	public CustomTextFieldWithButton(String textFieldTitle, ImageIcon buttonIcon) {
		setOpaque(false);
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("32px"),},
			new RowSpec[] {
				RowSpec.decode("13px"),
				RowSpec.decode("fill:32px"),}));
		
		textField = new CustomTextField(textFieldTitle);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setLineColor(new Color(150,150,150), new Color(250,250,250));
		textField.setForeground(new Color(250,250,250));
		textField.setCaretColor(new Color(250,250,250));
		textField.setRquiredHint("* Wprowadź liczbę!");
		
		importButton = new JButton();
		importButton.setIconTextGap(4);
		importButton.setIcon(buttonIcon);
		importButton.setUI(new CustomButton.CustomButtonUI());
		importButton.setForeground(new Color(22,22,22));
		importButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		importButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		importButton.setBackground(new Color(250,250,250));
    
		importButton.addMouseListener(new MouseAdapter() {
			@Override
      public void mouseEntered(MouseEvent me) {
				importButton.setBackground(new Color(200,200,200));
		  }

		  @Override
		  public void mouseExited(MouseEvent me) {
		  	importButton.setBackground(new Color(250,250,250));
		  }
		});
		
		add(textField, "1, 1, 1, 2, fill, default");
		add(importButton, "2, 2");
	}
	
	public CustomTextField getTextField() {
		return textField;
	}
	
	public JButton getButton() {
		return importButton;
	}

}
