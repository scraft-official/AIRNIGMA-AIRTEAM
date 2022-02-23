package airteam.projects.airnigma.components;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import airteam.projects.airnigma.ciphermanager.CipherManager;
import airteam.projects.airnigma.ciphermanager.ciphers.CipherObject;
import airteam.projects.airnigma.components.templates.CustomComboBox;
import airteam.projects.airnigma.components.templates.JScrollBarUI;

import com.jgoodies.forms.layout.FormSpecs;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class OptionsPanel extends JPanel {
	private static CustomComboBox combobox = new CustomComboBox();

	private static JScrollPane cipherScrollPane = new JScrollPane();
	
	public OptionsPanel() {
		setOpaque(false);
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("left:default:grow"),
				ColumnSpec.decode("8px"),},
			new RowSpec[] {
				RowSpec.decode("55px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("10px"),}));
		
		combobox.setTitleText("WYBRANY SZYFR");
    combobox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    combobox.setMaximumRowCount(4);
    combobox.addPopupMenuListener(new PopupMenuListener() {
      @Override
      public void popupMenuCanceled(PopupMenuEvent pme) {
      }

      @Override
      public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) {
      	CipherManager.selectCipher(combobox.getSelectedIndex());
      }
      
      @Override
      public void popupMenuWillBecomeVisible(PopupMenuEvent pme) {
      }
    });
    
    add(combobox, "1, 1, fill, default");
    
    JScrollBar cipherScrollbar = new JScrollBar();
    cipherScrollbar.setUI(new JScrollBarUI());
    cipherScrollbar.setUnitIncrement(10);
    cipherScrollbar.setPreferredSize(new Dimension(5, 200));
    cipherScrollbar.setOpaque(false);
    cipherScrollbar.setForeground(new Color(250, 250, 250));
    cipherScrollbar.setBackground(new Color(57, 135, 80, 0));
		
		cipherScrollPane.setBorder(new EmptyBorder(0,0,0,6));
		cipherScrollPane.getViewport().setOpaque(false);
		cipherScrollPane.setOpaque(false);
		cipherScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		cipherScrollPane.setVerticalScrollBar(cipherScrollbar);
		
    add(cipherScrollPane, "1, 3, 2, 1, fill, fill");
	}
	
	public static void refreshSelected() {
		ArrayList<String> cipherNames = new ArrayList<>();
		
		int selectedID = 0;
		int i = 0;
		
		for(CipherObject c : CipherManager.getAllCiphers()) {
			if(c == CipherManager.getSelectedCipher()) { selectedID = i; }
			cipherNames.add(c.getCipherName());
			i++;
		}
		combobox.setModel(new DefaultComboBoxModel(cipherNames.toArray()));
		combobox.setSelectedIndex(selectedID);
	}
	
	public static void setCipherOptionsPanel(JPanel panel) {
		cipherScrollPane.setViewportView(panel);
	}
	
	
	
	
//	@Override
//	public void paintComponent(Graphics g) {
//		Graphics2D g2d = (Graphics2D) g;
//		
//		g2d.setColor(new Color(87, 87, 89, 170));
//		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
//		
//	}

}
