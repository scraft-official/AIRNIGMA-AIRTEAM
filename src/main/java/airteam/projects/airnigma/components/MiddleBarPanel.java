package airteam.projects.airnigma.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import airteam.projects.airnigma.ciphermanager.CipherManager;
import airteam.projects.airnigma.ciphermanager.CipherManager.CIPHER_MODE;
import airteam.projects.airnigma.components.dialogs.popups.AnalizePopup;
import airteam.projects.airnigma.components.templates.CustomButton;
import airteam.projects.airnigma.components.templates.CustomButton.CustomButtonUI;
import airteam.projects.airnigma.utilities.GraphicsUtility;

public class MiddleBarPanel extends JPanel {
	private static final long serialVersionUID = 2169698302685063539L;
	private int dotSpacing = 25;
	private int dotSize = 4;

	public MiddleBarPanel() {
		setBorder(new EmptyBorder(30, 25, 30, 25));
		setOpaque(false);
		setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("15px"), ColumnSpec.decode("center:default:grow"),
						ColumnSpec.decode("15px"), ColumnSpec.decode("center:190px:grow"), ColumnSpec.decode("15px"),
						ColumnSpec.decode("center:default:grow"), ColumnSpec.decode("15px"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("50px"), RowSpec.decode("10px"),
						FormSpecs.DEFAULT_ROWSPEC, RowSpec.decode("10px"), RowSpec.decode("25px"),
						RowSpec.decode("fill:default:grow"), FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("fill:45px"),
						FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("fill:45px"), }));

		JLabel title = new JLabel("AIRNIGMA");
		title.setForeground(new Color(250, 250, 250));
		title.setFont(new Font("Tahoma", Font.BOLD, 43));
		add(title, "2, 2, 5, 1, center, default");

		JSeparator separatorTop = new JSeparator();
		separatorTop.setForeground(Color.WHITE);
		add(separatorTop, "4, 3, fill, default");

		JLabel optionsTitle = new JLabel("??? USTAWIENIA ???");
		optionsTitle.setForeground(new Color(250, 250, 250));
		optionsTitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
		add(optionsTitle, "4, 4");

		JSeparator separatorBottom = new JSeparator();
		separatorBottom.setForeground(Color.WHITE);
		add(separatorBottom, "4, 5, fill, default");

		JPanel panel = new OptionsPanel();
		add(panel, "3, 7, 3, 1, fill, fill");

		CustomButton buttonAnalize = new CustomButton("ANALIZUJ SZYFRY", new Color(250, 250, 250), new ImageIcon(
				GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/analize-icon.png"), 18, 18)));
		buttonAnalize.setFont(new Font("Tahoma", Font.BOLD, 18));
		buttonAnalize.setForeground(new Color(22, 22, 22));
		buttonAnalize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (InputPanel.getInputText().length() == 0) {
					new AnalizePopup(
							"Aby przeanalizowa?? konkretny tekst, wpisz go lub importuj do pola wej??ciowego szyfrowania przed klikni??ciem na przycisk analizuj.");
				} else {
					new AnalizePopup(InputPanel.getInputText());
				}
			}
		});

		add(buttonAnalize, "2, 9, 5, 1");

		ImageIcon lockIcon = new ImageIcon(
				GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/lock-icon.png"), 18, 18));
		ImageIcon unlockIcon = new ImageIcon(
				GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/unlock-icon.png"), 18, 18));

		JButton buttonMode = new JButton("TRYB: SZYFROWANIE");
		buttonMode.setIconTextGap(4);
		buttonMode.setIcon(lockIcon);
		buttonMode.setUI(new CustomButtonUI());
		buttonMode.setForeground(new Color(250, 250, 250));
		buttonMode.setFont(new Font("Tahoma", Font.BOLD, 18));
		buttonMode.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonMode.setBackground(new Color(199, 124, 74));
		buttonMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (CipherManager.getSelectedMode() == CIPHER_MODE.DECODE) {
					buttonMode.setBackground(new Color(181, 93, 34));
					buttonMode.setIcon(lockIcon);
					buttonMode.setText("TRYB: SZYFROWANIE");

					String inputText = InputPanel.getInputText();

					InputPanel.setTitleAndHintText("TEKST", "Wprowadz tekst do zaszyfrowania...");
					InputPanel.updateInputText(OutputPanel.getOutputText());
					OutputPanel.setTitleAndHintText("ZASZYFROWANY TEKST", "Zaszyfrowany tekst...");
					OutputPanel.updateOutputText(inputText);
					CipherManager.selectCipherMode(CIPHER_MODE.ENCODE);
					repaint();
				} else {
					buttonMode.setBackground(new Color(23, 105, 156));
					buttonMode.setIcon(unlockIcon);
					buttonMode.setText("TRYB: ODSZYFROWANIE");

					String inputText = InputPanel.getInputText();

					InputPanel.setTitleAndHintText("ZASZYFROWANY TEKST", "Wprowadz tekst do odszyfrowania...");
					InputPanel.updateInputText(OutputPanel.getOutputText());
					OutputPanel.setTitleAndHintText("ODSZYFROWANY TEKST", "Odszyfrowany tekst...");
					OutputPanel.updateOutputText(inputText);
					CipherManager.selectCipherMode(CIPHER_MODE.DECODE);
					repaint();
				}
			}
		});

		buttonMode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				if (CipherManager.getSelectedMode() == CIPHER_MODE.ENCODE) {
					buttonMode.setBackground(new Color(181, 93, 34));
				} else {
					buttonMode.setBackground(new Color(23, 105, 156));
				}
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent me) {
				if (CipherManager.getSelectedMode() == CIPHER_MODE.ENCODE) {
					buttonMode.setBackground(new Color(199, 124, 74));
				} else {
					buttonMode.setBackground(new Color(60, 139, 189));
				}
				repaint();
			}
		});

		add(buttonMode, "2, 11, 5, 1");

	}

	@Override
	public void paintComponent(Graphics g) {
		int w = getWidth();
		int h = getHeight();

		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

		g2d.setColor(new Color(28, 28, 28));
		for (int x = ((w / (dotSpacing + dotSize))) + dotSpacing + dotSize; x < (w - dotSpacing); x += dotSpacing) {
			for (int y = dotSpacing + (dotSize / 2) + 10; y < h; y += dotSpacing) {
				g2d.fillOval(x - (dotSize / 2), y - (dotSize / 2), dotSize, dotSize);
			}
		}

		g2d.setColor(new Color(255, 255, 255));
		g2d.setStroke(new BasicStroke(2));
		g2d.drawRoundRect(14, 14, w - 30, h - 30, 15, 15);

		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(new Color(56, 57, 59));
		// g2d.drawLine(34, 10, 44 + title.getBounds().width, 10);
	}
}
