package airteam.projects.airnigma.ciphermanager.ciphers;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class CipherObject extends JPanel {
	public abstract String getCipherName();

	public abstract String encode(String text) throws Exception;

	public abstract String decode(String text) throws Exception;

	public abstract Boolean doAnalize();

	public abstract void showOptions();
}
