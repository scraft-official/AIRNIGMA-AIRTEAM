package airteam.projects.airnigma;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.security.Security;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import airteam.projects.airnigma.ciphermanager.CipherManager;
import airteam.projects.airnigma.components.InputPanel;
import airteam.projects.airnigma.components.MiddleBarPanel;
import airteam.projects.airnigma.components.OutputPanel;
import airteam.projects.airnigma.utilities.GraphicsUtility;
import airteam.projects.airnigma.utilities.LogUtility;

public class AirNigma extends JFrame {
	private static AirNigma AirNigma;

	public AirNigma(boolean showApp) {
		AirNigma = this;

		setIconImage(GraphicsUtility.getInternalIcon("icons/app-icon.png"));
		JPanel backPanel = new JPanel() {

			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				GraphicsUtility.setGradientPaint(g2d, new Color(56, 57, 59), new Color(28, 28, 28), 0, getHeight());
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		};

		backPanel.setLayout(
				new FormLayout(new ColumnSpec[] { ColumnSpec.decode("min(200px;default):grow"), ColumnSpec.decode("384px"),
						ColumnSpec.decode("min(200px;default):grow"), }, new RowSpec[] { RowSpec.decode("10px:grow"), }));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1280, 720));
		setMinimumSize(new Dimension(1280, 720));
		setBounds(100, 100, 1280, 720);
		setTitle("AIRNIGMA - AIRTEAM");

		backPanel.add(new MiddleBarPanel(), "2, 1, fill, fill");
		backPanel.add(new InputPanel(), "1, 1, fill, fill");
		backPanel.add(new OutputPanel(), "3, 1, fill, fill");

		getContentPane().add(backPanel);

		try {
			CipherManager.registerAllCiphers();
		} catch(Exception e) {};
		setVisible(showApp);
	}

	public static AirNigma getFrame() {
		return AirNigma;
	}

	public static void main(String[] args) {
		Security.setProperty("crypto.policy", "unlimited");
		System.setProperty("sun.java2d.opengl", "true");
		Security.addProvider( new BouncyCastleProvider());
		LogUtility.logInfo(System.getProperty("java.version"));
//		System.setProperty("sun.java2d.ddscale", "true");
//		System.setProperty("sun.java2d.translaccel", "true");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException ex) {
		}

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					AirNigma = new AirNigma(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
