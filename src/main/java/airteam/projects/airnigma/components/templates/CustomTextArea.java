package airteam.projects.airnigma.components.templates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextArea;

import airteam.projects.airnigma.utilities.LogUtility;

public class CustomTextArea extends JTextArea {
	private boolean isFocused = false;
	private String unfilledText = "Wprowadz tekst...";
	
	public CustomTextArea() {
		setOpaque(false);
		setLineWrap(true);
		
		addFocusListener(new FocusAdapter( ) {
			@Override
			public void focusGained(FocusEvent e) {
				isFocused = true;
				repaint();
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				isFocused = false;
				repaint();
			}
		});
	}
	
	public void setUnfilledText(String text) {
		unfilledText = text;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(!isFocused && getText().equals("")) {
			Graphics2D g2d = (Graphics2D) g.create();
			
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
			Insets in = getInsets();
	    g2d.setColor(new Color(120, 120, 120));
	    g2d.setFont(getFont());
	    g2d.drawString(unfilledText, in.left, in.top+getFont().getSize());
		}
	}
}
