package airteam.projects.airnigma.components.templates;

import java.awt.Adjustable;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class JScrollBarUI extends BasicScrollBarUI {

	@SuppressWarnings("serial")
	private class ScrollBarButton extends JButton {

		public ScrollBarButton() {
			setBorder(BorderFactory.createEmptyBorder());
		}

		@Override
		public void paint(Graphics grphcs) {
		}
	}

	private final int THUMB_SIZE = 40;

	@Override
	protected JButton createDecreaseButton(int i) {
		return new ScrollBarButton();
	}

	@Override
	protected JButton createIncreaseButton(int i) {
		return new ScrollBarButton();
	}

	@Override
	protected Dimension getMaximumThumbSize() {
		if (scrollbar.getOrientation() == Adjustable.VERTICAL) {
			return new Dimension(0, THUMB_SIZE);
		} else {
			return new Dimension(THUMB_SIZE, 0);
		}
	}

	@Override
	protected Dimension getMinimumThumbSize() {
		if (scrollbar.getOrientation() == Adjustable.VERTICAL) {
			return new Dimension(0, THUMB_SIZE);
		} else {
			return new Dimension(THUMB_SIZE, 0);
		}
	}

	@Override
	protected void paintThumb(Graphics grphcs, JComponent jc, Rectangle rctngl) {
		Graphics2D g2 = (Graphics2D) grphcs;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int x = rctngl.x;
		int y = rctngl.y;
		int width = rctngl.width;
		int height = rctngl.height;
		if (scrollbar.getOrientation() == Adjustable.VERTICAL) {
			y += 8;
			height -= 16;
		} else {
			x += 8;
			width -= 16;
		}
		g2.setColor(scrollbar.getForeground());
		g2.fillRoundRect(x, y, width, height, 10, 10);
	}

	@Override
	protected void paintTrack(Graphics grphcs, JComponent jc, Rectangle rctngl) {
		Graphics2D g2 = (Graphics2D) grphcs;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int orientation = scrollbar.getOrientation();
		int size;
		int x;
		int y;
		int width;
		int height;
		if (orientation == Adjustable.VERTICAL) {
			size = rctngl.width / 2;
			x = rctngl.x + ((rctngl.width - size) / 2);
			y = rctngl.y;
			width = size;
			height = rctngl.height;
		} else {
			size = rctngl.height / 2;
			y = rctngl.y + ((rctngl.height - size) / 2);
			x = 0;
			width = rctngl.width;
			height = size;
		}
		g2.setColor(scrollbar.getBackground());
		g2.fillRoundRect(x, y, width, height, width, width);
	}
}
