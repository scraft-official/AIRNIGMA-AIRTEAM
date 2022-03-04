package airteam.projects.airnigma.components.templates;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

@SuppressWarnings("serial")
public class CustomComboBox<E> extends JComboBox<E> {
	private String placeholder = "TEXT";

	private Color foreground;
	private Color background;

	private boolean mouseOver;

	private BasicComboPopup pop;

	private class ComboUI extends BasicComboBoxUI {
		@SuppressWarnings("rawtypes")
		private CustomComboBox combo;

		@SuppressWarnings("rawtypes")
		public ComboUI(CustomComboBox combo) {
			this.combo = combo;
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent me) {
					mouseOver = true;
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent me) {
					mouseOver = false;
					repaint();
				}
			});
			addPopupMenuListener(new PopupMenuListener() {
				@Override
				public void popupMenuCanceled(PopupMenuEvent pme) {
				}

				@Override
				public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) {
					arrowButton.setBackground(new Color(150, 150, 150));
				}

				@Override
				public void popupMenuWillBecomeVisible(PopupMenuEvent pme) {
					arrowButton.setBackground(new Color(200, 200, 200));
				}
			});
		}

		@Override
		protected JButton createArrowButton() {
			return new ArrowButton();
		}

		private void createHintText(Graphics2D g2d) {
			Insets in = getInsets();
			g2d.setColor(new Color(150, 150, 150));
			FontMetrics ft = g2d.getFontMetrics();
			Rectangle2D r2 = ft.getStringBounds(combo.getTitleText(), g2d);
			double height = getHeight() - in.top - in.bottom;
			double textY = (height - r2.getHeight()) / 2;
			g2d.drawString(combo.getTitleText(), in.right, (int) ((in.top + textY + ft.getAscent()) - 18));
		}

		private void createLineStyle(Graphics2D g2d) {
			if (isFocusOwner()) {
				int width = getWidth();
				int height = getHeight();
				g2d.fillRect(0, height - 2, width - 1, 2);
			}
		}

		@Override
		protected ComboPopup createPopup() {
			pop = new BasicComboPopup(comboBox) {
				/**
				 *
				 */
				private static final long serialVersionUID = 443241751728482865L;

				@Override
				protected JScrollPane createScroller() {
					JScrollPane scroll = new JScrollPane(list);
					scroll.setBackground(Color.WHITE);
					JScrollBar sb = new JScrollBar();
					sb.setUI(new JScrollBarUI());
					sb.setUnitIncrement(3);
					sb.setPreferredSize(new Dimension(3, 25));
					sb.setOpaque(false);
					sb.setForeground(foreground);
					sb.setBackground(new Color(0, 0, 0, 0));
					scroll.setVerticalScrollBar(sb);
					scroll.getViewport().setOpaque(false);
					scroll.setOpaque(false);
					return scroll;
				}

				@Override
				public void paintComponent(Graphics g) {
					Graphics2D g2d = (Graphics2D) g;
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

					g2d.setColor(background);
					g2d.fillRoundRect(0, -8, getWidth() - 2, getHeight() + 7, 15, 15);

					g2d.setColor(foreground);
					g2d.setStroke(new BasicStroke(2));

					g2d.drawRoundRect(0, -8, getWidth() - 2, getHeight() + 7, 15, 15);
				}

			};
			pop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			pop.setOpaque(false);
			pop.setBorder(new EmptyBorder(0, 0, 0, 0));
			return pop;
		}

		@Override
		public void paint(Graphics g, JComponent jc) {
			super.paint(g, jc);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			int width = getWidth();
			int height = getHeight();
			if (mouseOver) {
				g2d.setColor(foreground);
			} else {
				g2d.setColor(new Color(150, 150, 150));
			}
			g2d.fillRect(2, height - 1, width - 4, 1);
			createHintText(g2d);
			createLineStyle(g2d);
			g2d.dispose();
		}

		@Override
		public void paintCurrentValueBackground(Graphics g, Rectangle rctngl, boolean bln) {

		}
	}

	public CustomComboBox(Color foreground, Color background) {
		setOpaque(false);
		setBorder(new EmptyBorder(15, 0, 5, 3));
		setUI(new ComboUI(this));
		setForeground(foreground);
		setRenderer(new DefaultListCellRenderer() {
			/**
			 *
			 */
			private static final long serialVersionUID = 3460205392467137965L;

			@Override
			public Component getListCellRendererComponent(JList<?> jlist, Object o, int i, boolean bln, boolean bln1) {
				JComponent com = (JComponent) super.getListCellRendererComponent(jlist, o, i, bln, bln1);
				setBorder(new EmptyBorder(5, 5, 5, 5));
				if (bln) {
					com.setForeground(new Color(77, 209, 115));
					com.setFont(new Font("Tahoma", Font.BOLD, 12));
				} else {
					com.setForeground(foreground);
					com.setFont(new Font("Tahoma", Font.BOLD, 12));
				}
				jlist.setSelectionForeground(foreground);
				jlist.setOpaque(false);
				com.setOpaque(false);
				return com;
			}
		});
		this.foreground = foreground;
		this.background = background;
	}

	public String getTitleText() {
		return placeholder;
	}

	public void setTitleText(String placeholder) {
		this.placeholder = placeholder;
	}

	private class ArrowButton extends JButton {
		private static final long serialVersionUID = -3834123168212626435L;

		public ArrowButton() {
			setContentAreaFilled(false);
			setBorder(new EmptyBorder(5, 5, 5, 5));
			setBackground(new Color(150, 150, 150));
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			int width = getWidth();
			int height = getHeight();
			int size = 8;
			int x = (width - size) / 2;
			int y = (height - size) / 2;
			int px[] = { x, x + size, x + (size / 2) };
			int py[] = { y, y, y + size };
			g2d.setColor(getBackground());
			g2d.fillPolygon(px, py, px.length);
			g2d.dispose();
		}
	}
}