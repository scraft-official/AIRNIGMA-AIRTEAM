package airteam.projects.airnigma.ciphermanager.ciphers;

import javax.swing.JPanel;

public abstract class CipherObject extends JPanel {
	public abstract String getCipherName();
	public abstract String encode(String text);
	public abstract String decode(String text);
}
