package airteam.projects.airnigma.components.templates;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import airteam.projects.airnigma.utilities.LogUtility;

@SuppressWarnings("serial")
public class CustomTextField extends JTextField {
	private boolean mouseOver = false;
	private boolean isFocused = false;
	private boolean showRequiredHint = false;
	
	private String hintRequiredText = "* To pole jest wymagane!";
	
	private Color lineColor = new Color(150, 150, 150);
	private Color lineColorHover = new Color(250, 250, 250);
	private String title;
	
	public CustomTextField(String title) {
		this.title = title;
		
		setOpaque(false);
		setBorder(new EmptyBorder(20, 3, 10, 3));
		setFont(new Font("Tahoma", Font.PLAIN, 15));
		addMouseListener( new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mouseOver = true;
				repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				mouseOver = false;
				repaint();
			}
		});
		
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
	
	public void drawHintText(Graphics2D g2d) {
		Insets in = getInsets();
    g2d.setColor(new Color(150, 150, 150));
 
    double height = getHeight() - in.top - in.bottom;
		double textY;
		if(isFocused || !getText().equals("")) {
			g2d.setFont(new Font("Tahoma", Font.PLAIN, 11));
			textY = (height - 18) / 2;
		}
    else {
    	g2d.setFont(new Font("Tahoma", Font.PLAIN, 13));
    	textY = height;
    }
		
    FontMetrics ft = g2d.getFontMetrics();
    g2d.drawString(title, in.right, (int) (in.top + textY + ft.getAscent() - 18));
    
    if(showRequiredHint) {
    	g2d.setColor(new Color(209, 69, 67));
    	int posX;
    	if(isFocused || !getText().equals("")) {
    		posX = in.right + ft.stringWidth(title) + 10;
    	}  else {
    		posX = in.right;
    	}
			g2d.drawString(hintRequiredText, posX, (int) (in.top + ((height - 18) / 2) + ft.getAscent() - 18));
    }
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	
		int width = getWidth();
		int height = getHeight();
		if(mouseOver || isFocused) {
			g2d.setColor(lineColorHover);
			g2d.fillRect(2, height - 1, width - 4, height);
		} else {
			g2d.setColor(lineColor);
			g2d.fillRect(2, height - 1, width - 4, height);
		}
		
		drawHintText(g2d);
		g2d.dispose();
	}
	
	public void setRquiredHint(String text) {
		hintRequiredText = text;
	}
	
	public void showRequiredHint(boolean show) {
		showRequiredHint = show;
	}
	
	public void setLineColor(Color unhover, Color hover) {
		lineColorHover = hover;
		lineColor = unhover;
	}
	 
	
}
