package airteam.projects.airnigma.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class AnalizeResultItem extends JPanel {
	private static final long serialVersionUID = -3789699868978669716L;

	public AnalizeResultItem(String title, long keyGenerationTimespan, long encryptionTimespan, long decryptionTimespan) {
		setLayout(new FormLayout(new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("700px"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("35px"), RowSpec.decode("90px"), }));

		setOpaque(false);

		JLabel cipherTitle = new JLabel(title);
		cipherTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
		cipherTitle.setForeground(new Color(25, 129, 209));
		cipherTitle.setOpaque(false);

		add(cipherTitle, "2, 2");

		JTextPane textPane = new JTextPane();
		if ((encryptionTimespan < 0) || (decryptionTimespan < 0)) {
			textPane.setText("Czas trwania generacji klucza: " + keyGenerationTimespan + "ms"
					+ "\nCzas trwania szyfrowania: ✘" + "\nCzas trwania deszyfrowania: ✘" + "\nLączny czas trwania operacji: ✘");
		} else {
			textPane.setText("Czas trwania generacji klucza: " + keyGenerationTimespan + "ms" + "\nCzas trwania szyfrowania: "
					+ encryptionTimespan + "ms" + "\nCzas trwania deszyfrowania: " + decryptionTimespan + "ms"
					+ "\nLączny czas trwania operacji: " + (keyGenerationTimespan + encryptionTimespan + decryptionTimespan)
					+ "ms");
		}

		textPane.setFont(new Font("Tahoma", Font.BOLD, 16));
		textPane.setEditable(false);
		textPane.setForeground(new Color(24, 57, 82));
		textPane.setOpaque(false);

		add(textPane, "2, 3, fill, center");
	}

}
