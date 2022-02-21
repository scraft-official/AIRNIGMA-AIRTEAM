package airteam.projects.airnigma.components.templates;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

public class CustomButtonUI extends BasicButtonUI {
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
		if(b.getModel().isPressed() && doClick)
			paintBackground(g, b, 2);
		else 
			paintBackground(g, b, 0);
		super.paint(g, c);
	}

	private void paintBackground(Graphics g, JComponent c, int yOffset) {
		Dimension size = c.getSize();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(c.getBackground().darker());
		g2d.fillRoundRect(0, yOffset, size.width, size.height - yOffset/2, 10, 10);
		g2d.setColor(c.getBackground());
		g2d.fillRoundRect(0, yOffset, size.width-1, size.height + yOffset/2 - 2, 10, 10);
	}
}
