package airteam.projects.airnigma.components.dialogs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import airteam.projects.airnigma.components.templates.CustomButton.CustomButtonUI;
import airteam.projects.airnigma.utilities.GraphicsUtility;

@SuppressWarnings("serial")
public class CustomDialogPanel extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = -2694439844986770220L;
	private int borderRadius = 15;
	private int borderPadding = 18;

	private JLabel title = new JLabel();

	private JButton buttonAccept = new JButton();
	private JButton buttonCancel = new JButton();

	private JLabel closeButton = new JLabel(new ImageIcon(
			GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/close-icon-dark.png"), 18, 18)));

	public CustomDialogPanel(CustomDialogFrame dialog, String name, boolean onlyAcceptButton, boolean addDefaultIcons) {
		setBorder(new EmptyBorder((borderPadding / 2) - 1, borderPadding, borderPadding, borderPadding));
		setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("27px"), ColumnSpec.decode("8dlu"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("50px:grow"), ColumnSpec.decode("15dlu"), ColumnSpec.decode("50px:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("15dlu"), ColumnSpec.decode("10dlu"), },
				new RowSpec[] { RowSpec.decode("30px"), RowSpec.decode("18px"), RowSpec.decode("50dlu:grow"),
						RowSpec.decode("8dlu"), RowSpec.decode("30dlu"), FormSpecs.PARAGRAPH_GAP_ROWSPEC, }));

		title.setText(name);
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(title, "2, 1, 5, 1, left, top");

		closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dialog.dispose();
			}
		});
		add(closeButton, "8, 1, 2, 2, left, bottom");

		if (addDefaultIcons) {
			buttonAccept.setIcon(new ImageIcon(
					GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/check-mark-icon.png"), 17, 17)));
		}
		buttonAccept.setUI(new CustomButtonUI());
		buttonAccept.setForeground(new Color(234, 234, 234));
		buttonAccept.setFont(new Font("Tahoma", Font.PLAIN, 18));
		buttonAccept.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonAccept.setBackground(new Color(74, 176, 101));
		buttonAccept.setText("ZAPISZ");

		buttonAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				buttonAccept.setBackground(new Color(45, 150, 73));
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent me) {
				buttonAccept.setBackground(new Color(74, 176, 101));
				repaint();
			}
		});

		if (!onlyAcceptButton) {
			if (addDefaultIcons) {
				buttonCancel.setIcon(new ImageIcon(
						GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/close-icon.png"), 14, 14)));
			}
			buttonCancel.setUI(new CustomButtonUI());
			buttonCancel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			buttonCancel.setForeground(new Color(234, 234, 234));
			buttonCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			buttonCancel.setBackground(new Color(209, 85, 69));
			buttonCancel.setText("ANULUJ");

			buttonCancel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent me) {
					buttonCancel.setBackground(new Color(181, 72, 58));
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent me) {
					buttonCancel.setBackground(new Color(209, 85, 69));
					repaint();
				}
			});

			add(buttonCancel, "6, 5, 2, 1, fill, fill");
			add(buttonAccept, "3, 5, 2, 1, fill, fill");
			return;
		}

		add(buttonAccept, "3, 5, 5, 1, fill, fill");
	}

	public void setContentPanel(JPanel panel) {
		add(panel, "3, 2, 5, 2, center, center");
	}

	public JButton getAcceptButton() {
		return buttonAccept;
	}

	public JButton getCancelButton() {
		return buttonCancel;
	}

	public JLabel getCloseButton() {
		return closeButton;
	}

	@Override
	public void paintComponent(Graphics g) {
		int w = getBounds().width;
		int h = getBounds().height;

		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

		// Graphics_Utilies.drawRoundFadedBorder(g2d, new Color(0,0,0), shadowSize + 1,
		// 1, 1, w-1, h-1, borderRadius);
		GraphicsUtility.setGradientPaint(g2d, new Color(255, 255, 255), new Color(237, 232, 232), 0, h);

		g2d.fillRoundRect(0, 0, w, h, borderRadius, borderRadius);

		g2d.setColor(new Color(0, 0, 0));
		g2d.setStroke(new BasicStroke(2));
		g2d.drawRoundRect(borderPadding, borderPadding, w - (borderPadding * 2), h - (borderPadding * 2), borderRadius - 10,
				borderRadius - 10);

		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(new Color(255, 255, 255));
		g2d.drawLine(borderPadding + 24, borderPadding, 46 + title.getBounds().width, borderPadding);
	}

}
