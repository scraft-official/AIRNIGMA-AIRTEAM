package airteam.projects.airnigma.ciphermanager.ciphers;

import javax.swing.JPanel;

public abstract class CipherObject extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = -6849250443602788812L;

	public abstract String getCipherName();

	public abstract String encode(String text) throws Exception;

	public abstract String decode(String text) throws Exception;

	public abstract Boolean doAnalize();

	public abstract void showOptions();
}
