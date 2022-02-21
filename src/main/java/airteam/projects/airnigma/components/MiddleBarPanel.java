package airteam.projects.airnigma.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import airteam.projects.airnigma.utilities.GraphicsUtility;
import airteam.projects.airnigma.utilities.LogUtility;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import java.awt.Font;
import javax.swing.border.EmptyBorder;

public class MiddleBarPanel extends JPanel{
	private int dotSpacing = 25;
	private int dotSize = 4;
	
	public MiddleBarPanel() {
		setBorder(new EmptyBorder(30, 25, 30, 25));
		setOpaque(false);
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("15px"),
				ColumnSpec.decode("center:default:grow"),
				ColumnSpec.decode("15px"),
				ColumnSpec.decode("center:190px:grow"),
				ColumnSpec.decode("15px"),
				ColumnSpec.decode("center:default:grow"),
				ColumnSpec.decode("15px"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("50px"),
				RowSpec.decode("10px"),
				FormSpecs.DEFAULT_ROWSPEC,
				RowSpec.decode("10px"),
				RowSpec.decode("25px"),
				RowSpec.decode("fill:default:grow"),}));
		
		JLabel text = new JLabel("AIRNIGMA");
		text.setForeground(new Color(250,250,250));
		text.setFont(new Font("Tahoma", Font.BOLD, 43));
		add(text, "2, 2, 5, 1, center, default");
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.WHITE);
		add(separator, "4, 3, fill, default");
		
		JLabel lblUstawienia = new JLabel("• USTAWIENIA •");
		lblUstawienia.setForeground(new Color(250, 250, 250));
		lblUstawienia.setFont(new Font("Tahoma", Font.PLAIN, 24));
		add(lblUstawienia, "4, 4");
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.WHITE);
		add(separator_1, "4, 5, fill, default");
		
		JPanel panel = new OptionsPanel();
		add(panel, "3, 7, 3, 1, fill, fill");
	}
	
	@Override
	public void paintComponent(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		
		Graphics2D g2d = (Graphics2D) g.create();
		
		g2d.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING, 
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		
		g2d.setColor(new Color(28, 28, 28));
		for (int x = ((w / (dotSpacing + dotSize))) + dotSpacing + dotSize; x < w - dotSpacing; x += dotSpacing) {
      for (int y = dotSpacing + (dotSize/2)+10; y < h; y += dotSpacing) {
      	g2d.fillOval(x  - (dotSize/2), y  - (dotSize/2), dotSize, dotSize);
      }
		}

		g2d.setColor(new Color(255, 255, 255));
		g2d.setStroke(new BasicStroke(2));
		g2d.drawRoundRect(14, 14, w-30, h-30, 15, 15);
		
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(new Color(56, 57, 59));
		//g2d.drawLine(34, 10, 44 + title.getBounds().width, 10);
	}
}
