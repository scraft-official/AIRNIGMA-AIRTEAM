package airteam.projects.airnigma.components.templates;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class CustomTextFieldWithButton extends JPanel {
	private CustomTextField textField;
	private JButton importButton;

	public CustomTextFieldWithButton(String textFieldTitle, ImageIcon buttonIcon) {
		setOpaque(false);
		setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"), ColumnSpec.decode("32px"), },
				new RowSpec[] { RowSpec.decode("13px"), RowSpec.decode("fill:32px"), }));

		textField = new CustomTextField(textFieldTitle);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setLineColor(new Color(150, 150, 150), new Color(250, 250, 250));
		textField.setForeground(new Color(250, 250, 250));
		textField.setCaretColor(new Color(250, 250, 250));
		textField.setRquiredHint("* Wprowadź liczbę!");

		importButton = new CustomButton("", new Color(250, 250, 250), buttonIcon);

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
