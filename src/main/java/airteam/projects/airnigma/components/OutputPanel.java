package airteam.projects.airnigma.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import airteam.projects.airnigma.components.templates.CustomButtonUI;
import airteam.projects.airnigma.components.templates.CustomTextArea;
import airteam.projects.airnigma.components.templates.JScrollBarUI;
import airteam.projects.airnigma.utilities.GraphicsUtility;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class OutputPanel extends JPanel{
	private int borderPadding = 14;
	private int borderRadius = 20;
	private int borderSize = 2;
	
	public OutputPanel() {
		setOpaque(false);
		setBorder(new EmptyBorder(borderPadding+28,0,borderPadding,borderPadding+borderSize));
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("min(200px;default):grow"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),
				RowSpec.decode("fill:55px"),}));
		
		CustomTextArea textPane = new CustomTextArea();
		textPane.setBorder(new EmptyBorder(5,15,15,10));
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 22));
		textPane.setForeground(new Color(83, 84, 87));
		textPane.setCaretColor(new Color(83, 84, 87));
		textPane.setUnfilledText("Wprowadź tekst do odszyfrowania...");
		
		JScrollPane textScrollPane = new JScrollPane();

		JScrollBar textScrollbar = new JScrollBar();
		textScrollbar.setUI(new JScrollBarUI());
		textScrollbar.setUnitIncrement(10);
		textScrollbar.setPreferredSize(new Dimension(9, 200));
		textScrollbar.setOpaque(false);
		textScrollbar.setForeground(new Color(58, 58, 61));
		textScrollbar.setBackground(new Color(57, 135, 80, 0));
		
		textScrollPane.setBorder(new EmptyBorder(0,0,0,6));
		textScrollPane.getViewport().setOpaque(false);
		textScrollPane.setOpaque(false);
		textScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		textScrollPane.setVerticalScrollBar(textScrollbar);
		textScrollPane.setViewportView(textPane);
		
		add(textScrollPane, "1, 1, fill, fill");
		
		JButton buttonExport = new JButton("EKSPORTUJ");
		buttonExport.setIconTextGap(8);
		buttonExport.setIcon(new ImageIcon(GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/save-icon.png"), 21, 21)));
		buttonExport.setUI(new CustomButtonUI());
		buttonExport.setForeground(new Color(250,250,250));
		buttonExport.setFont(new Font("Tahoma", Font.BOLD, 27));
		buttonExport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonExport.setBackground(new Color(45, 150, 73));
		buttonExport.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        
      }
    });
    
		buttonExport.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent me) {
      	buttonExport.setBackground(new Color(38, 128, 62));
        repaint();
      }

      @Override
      public void mouseExited(MouseEvent me) {
      	buttonExport.setBackground(new Color(50, 150, 77));
        repaint();
      }
    });
		
		add(buttonExport, "1, 2, fill, fill");
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
		
		g2d.setColor(new Color(150, 150, 150));
		g2d.fillRoundRect(0, borderPadding, w-borderPadding-borderSize, h-(borderPadding*2), borderRadius, borderRadius);
		
		GraphicsUtility.setGradientPaint(g2d, new Color(245, 245, 245), new Color(225, 225, 225), 0, h);
		g2d.fillRoundRect(borderSize, borderPadding+borderSize, w-borderPadding-(borderSize*3), h-(borderPadding*2)-(borderSize*2), borderRadius, borderRadius);
		
		g2d.setFont(new Font("Tahoma", Font.BOLD, 14));
		g2d.setColor(new Color(56, 57, 59));
		g2d.drawString("ODSZYFROWYWANIE", 10, 35);
		
		g2d.fillRoundRect(10, 40, g2d.getFontMetrics().stringWidth("ODSZYFROWYWANIE"), 2, 2, 2);
	}
}
