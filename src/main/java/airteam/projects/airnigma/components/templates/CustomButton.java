package airteam.projects.airnigma.components.templates;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

public class CustomButton extends JButton {
	/**
	 *
	 */
	private static final long serialVersionUID = -1411686065785936638L;

	public CustomButton(String title, Color buttonColor, ImageIcon icon) {
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setFont(new Font("Tahoma", Font.BOLD, 14));
		setForeground(new Color(250, 250, 250));
		setUI(new CustomButtonUI());
		setBackground(buttonColor);
		setIconTextGap(4);
		setIcon(icon);
		setText(title);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				setBackground(new Color(buttonColor.getRed() - 20, buttonColor.getGreen() - 20, buttonColor.getBlue() - 20));
			}

			@Override
			public void mouseExited(MouseEvent me) {
				setBackground(buttonColor);
			}
		});
	}

	public static class CustomButtonUI extends BasicButtonUI {
		private boolean doClick = true;

		public void allowClick(boolean allow) {
			doClick = allow;
		}

		@Override
		public void installUI(JComponent c) {
			super.installUI(c);
			AbstractButton button = (AbstractButton) c;
			button.setOpaque(false);
			button.setBorder(new EmptyBorder(5, 15, 5, 15));
		}

		@Override
		public void paint(Graphics g, JComponent c) {
			AbstractButton b = (AbstractButton) c;
			if (b.getModel().isPressed() && doClick) {
				paintBackground(g, b, 2);
			} else {
				paintBackground(g, b, 0);
			}
			super.paint(g, c);
		}

		private void paintBackground(Graphics g, JComponent c, int yOffset) {
			Dimension size = c.getSize();
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(c.getBackground().darker());
			g2d.fillRoundRect(0, yOffset, size.width, size.height - (yOffset / 2), 10, 10);
			g2d.setColor(c.getBackground());
			g2d.fillRoundRect(0, yOffset, size.width - 1, (size.height + (yOffset / 2)) - 2, 10, 10);
		}
	}
}
