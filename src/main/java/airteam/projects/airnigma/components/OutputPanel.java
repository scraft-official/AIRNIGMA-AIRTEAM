package airteam.projects.airnigma.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import airteam.projects.airnigma.AirNigma;
import airteam.projects.airnigma.ciphermanager.CipherManager;
import airteam.projects.airnigma.ciphermanager.CipherManager.CIPHER_MODE;
import airteam.projects.airnigma.components.templates.CustomButton;
import airteam.projects.airnigma.components.templates.CustomTextArea;
import airteam.projects.airnigma.components.templates.JScrollBarUI;
import airteam.projects.airnigma.savemanager.SaveManager;
import airteam.projects.airnigma.utilities.GraphicsUtility;

public class OutputPanel extends JPanel {
	private static CustomTextArea textPane = new CustomTextArea();
	private static OutputPanel instance;

	private static String title = "ZASZYFROWANY TEKST";

	private int borderPadding = 14;
	private int borderRadius = 20;
	private int borderSize = 2;

	public OutputPanel() {
		setOpaque(false);
		setBorder(new EmptyBorder(borderPadding + 28, 0, borderPadding, borderPadding + borderSize));
		setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("min(200px;default):grow"), },
				new RowSpec[] { RowSpec.decode("default:grow"), RowSpec.decode("fill:55px"), }));

		textPane.setBorder(new EmptyBorder(5, 15, 15, 10));
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 22));
		textPane.setForeground(new Color(83, 84, 87));
		textPane.setCaretColor(new Color(83, 84, 87));
		textPane.setUnfilledText("Zaszyfrowany tekst...");
		textPane.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				if (AirNigma.getFrame().getFocusOwner() == textPane) {
					CipherManager.updateInput();
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (AirNigma.getFrame().getFocusOwner() == textPane) {
					CipherManager.updateInput();
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (AirNigma.getFrame().getFocusOwner() == textPane) {
					CipherManager.updateInput();
				}
			}
		});

		JScrollPane textScrollPane = new JScrollPane();

		JScrollBar textScrollbar = new JScrollBar();
		textScrollbar.setUI(new JScrollBarUI());
		textScrollbar.setUnitIncrement(10);
		textScrollbar.setPreferredSize(new Dimension(9, 200));
		textScrollbar.setOpaque(false);
		textScrollbar.setForeground(new Color(58, 58, 61));
		textScrollbar.setBackground(new Color(57, 135, 80, 0));

		textScrollPane.setBorder(new EmptyBorder(0, 0, 0, 6));
		textScrollPane.getViewport().setOpaque(false);
		textScrollPane.setOpaque(false);
		textScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		textScrollPane.setVerticalScrollBar(textScrollbar);
		textScrollPane.setViewportView(textPane);

		add(textScrollPane, "1, 1, fill, fill");

		JButton buttonExport = new CustomButton("EKSPORTUJ", new Color(45, 150, 73),
				new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/save-icon.png"), 21, 21)));
		
		buttonExport.setFont(new Font("Tahoma", Font.BOLD, 27));
		buttonExport.setIconTextGap(8);
		
		buttonExport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM hh-mm-ss");
				String fileName = null;
				if (CipherManager.getSelectedMode() == CIPHER_MODE.DECODE) {
					fileName = "AirNigma - ODSZYFROWANY TEKST - ";
				} else {
					fileName = "AirNigma - ZASZYFROWANY TEKST - ";
				}

				SaveManager.exportTextFile((fileName + dateFormat.format(System.currentTimeMillis()) + ".txt"),
						OutputPanel.getOutputText());
			}
		});

		instance = this;

		add(buttonExport, "1, 2, fill, fill");
	}

	public static String getOutputText() {
		return textPane.getText();
	}

	public static void setTitleAndHintText(String titleText, String hintText) {
		title = titleText;
		textPane.setUnfilledText(hintText);
		instance.repaint();
	}

	public static void updateOutputText(String text) {
		textPane.setText(text);
	}

	@Override
	public void paintComponent(Graphics g) {
		int w = getWidth();
		int h = getHeight();

		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

		g2d.setColor(new Color(150, 150, 150));
		g2d.fillRoundRect(0, borderPadding, w - borderPadding - borderSize, h - (borderPadding * 2), borderRadius,
				borderRadius);

		GraphicsUtility.setGradientPaint(g2d, new Color(245, 245, 245), new Color(225, 225, 225), 0, h);
		g2d.fillRoundRect(borderSize, borderPadding + borderSize, w - borderPadding - (borderSize * 3),
				h - (borderPadding * 2) - (borderSize * 2), borderRadius, borderRadius);

		g2d.setFont(new Font("Tahoma", Font.BOLD, 14));
		g2d.setColor(new Color(56, 57, 59));
		g2d.drawString(title, 10, 35);

		g2d.fillRoundRect(10, 40, g2d.getFontMetrics().stringWidth(title), 2, 2, 2);
	}
}
