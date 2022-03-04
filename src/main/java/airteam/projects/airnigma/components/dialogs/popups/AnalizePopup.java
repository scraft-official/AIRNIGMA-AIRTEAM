package airteam.projects.airnigma.components.dialogs.popups;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.reflections.Reflections;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import airteam.projects.airnigma.ciphermanager.ciphers.CipherObject;
import airteam.projects.airnigma.components.AnalizeResultItem;
import airteam.projects.airnigma.components.dialogs.CustomDialogFrame;
import airteam.projects.airnigma.components.dialogs.CustomDialogPanel;
import airteam.projects.airnigma.components.templates.CustomComboBox;
import airteam.projects.airnigma.components.templates.JScrollBarUI;
import airteam.projects.airnigma.utilities.GraphicsUtility;
import airteam.projects.airnigma.utilities.LogUtility;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class AnalizePopup extends JPanel {
	private int width = 700;
	private int height = 550;

	private Map<CipherObject, Long> keyGenTimespanMap = new HashMap<>();
	private Map<CipherObject, Long> encodeTimespanMap = new HashMap<>();
	private Map<CipherObject, Long> decodeTimespanMap = new HashMap<>();
	private Map<CipherObject, Long> fullTimespanMap = new HashMap<>();

	private CustomComboBox sortOrderCombobox = new CustomComboBox(new Color(22, 22, 22), new Color(240, 240, 240));

	private JScrollPane statsScrollPane = new JScrollPane();

	public AnalizePopup(String inputText) {
		setOpaque(false);

		Dialog dialog = new AnalizePopup.Dialog();
		CustomDialogPanel panel = dialog.frame.getDialogPanel();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
		// doc.setParagraphAttributes(0, doc.getLength(), center, false);
		setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("300px"), ColumnSpec.decode("100px"), ColumnSpec.decode("200px"), },
				new RowSpec[] { RowSpec.decode("32px"), RowSpec.decode("120px"), FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("fill:35px"), FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("200px"), }));

		JButton acceptButton = panel.getAcceptButton();
		acceptButton.setText("ZAMKNIJ");
		acceptButton.setIcon(new ImageIcon(
				GraphicsUtility.getSizedImage(GraphicsUtility.getInternalIcon("icons/check-mark-icon.png"), 13, 13)));
		acceptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.frame.dispose();
			}
		});

		JLabel inputTitle = new JLabel("ANALIZOWANY TEKST:");
		inputTitle.setForeground(new Color(65, 65, 65));
		inputTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(inputTitle, "1, 1, left, default");

		JTextArea textPane = new JTextArea();
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textPane.setOpaque(false);
		textPane.setLineWrap(true);
		textPane.setEditable(false);
		textPane.setText(inputText);

		JScrollBar inputTextScrollbar = new JScrollBar();
		inputTextScrollbar.setUI(new JScrollBarUI());
		inputTextScrollbar.setUnitIncrement(10);
		inputTextScrollbar.setPreferredSize(new Dimension(5, 200));
		inputTextScrollbar.setOpaque(false);
		inputTextScrollbar.setForeground(new Color(22, 22, 22));
		inputTextScrollbar.setBackground(new Color(57, 135, 80, 0));

		JScrollPane inputTextScrollPane = new JScrollPane();
		inputTextScrollPane.setBorder(new EmptyBorder(0, 0, 0, 6));
		inputTextScrollPane.getViewport().setOpaque(false);
		inputTextScrollPane.setOpaque(false);
		inputTextScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		inputTextScrollPane.setVerticalScrollBar(inputTextScrollbar);
		inputTextScrollPane.setViewportView(textPane);

		JPanel inputBackPanel = new JPanel() {
			/**
			 *
			 */
			private static final long serialVersionUID = 1731985939398169163L;

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

				int width = getWidth();
				int height = getHeight();

				g2d.setColor(new Color(180, 180, 180));
				g2d.fillRect(0, 0, width, 3);
				g2d.fillRect(0, height - 3, width, height);

				g2d.dispose();
			}
		};
		inputBackPanel.setBorder(new EmptyBorder(3, 3, 3, 0));
		inputBackPanel.setLayout(new BorderLayout(0, 0));
		inputBackPanel.setBackground(new Color(230, 230, 230));
		inputBackPanel.add(inputTextScrollPane);

		add(inputBackPanel, "1, 2, 3, 1, fill, fill");

		JLabel statsTitle = new JLabel("STATYSTYKI SZYFRÓW:");
		statsTitle.setForeground(new Color(65, 65, 65));
		statsTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(statsTitle, "1, 4, left, default");

		sortOrderCombobox.setTitleText("SORTUJ WEDŁUG:");
		sortOrderCombobox.setModel(new DefaultComboBoxModel(
				new String[] { "CZASU GENERACJI KLUCZA", "CZASU SZYFROWANIA", "CZASU DESZYFROWANIA", "CZASU CAŁEJ OPERACJI" }));
		sortOrderCombobox.setSelectedIndex(3);
		sortOrderCombobox.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuCanceled(PopupMenuEvent pme) {
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) {
				showAnalizedStats();
			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent pme) {
			}
		});
		add(sortOrderCombobox, "3, 4, fill, default");

		JScrollBar statsScrollbar = new JScrollBar();
		statsScrollbar.setUI(new JScrollBarUI());
		statsScrollbar.setUnitIncrement(10);
		statsScrollbar.setPreferredSize(new Dimension(5, 200));
		statsScrollbar.setOpaque(false);
		statsScrollbar.setForeground(new Color(22, 22, 22));
		statsScrollbar.setBackground(new Color(57, 135, 80, 0));

		statsScrollPane.setBorder(new EmptyBorder(0, 0, 0, 6));
		statsScrollPane.getViewport().setOpaque(false);
		statsScrollPane.setOpaque(false);
		statsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		statsScrollPane.setVerticalScrollBar(statsScrollbar);

		Reflections reflections = new Reflections("airteam.projects.airnigma.ciphermanager.ciphers");

		Set<Class<? extends CipherObject>> allCipherClasses = reflections.getSubTypesOf(CipherObject.class);

		for (Class<? extends CipherObject> c : allCipherClasses) {
			try {
				long timestamp, keyGenTimespan, encodeTimespan, decodeTimespan;

				timestamp = System.currentTimeMillis();
				CipherObject cipher = c.getConstructor().newInstance();

				if (!cipher.doAnalize())
				 {
					continue; // If do not analize, skip this cipher
				}

				keyGenTimespan = System.currentTimeMillis() - timestamp;
				keyGenTimespanMap.put(cipher, keyGenTimespan);

				timestamp = System.currentTimeMillis();
				String encodedText = null;
				try {
					encodedText = cipher.encode(inputText);
					encodeTimespan = System.currentTimeMillis() - timestamp;
				} catch (Exception e) {
					encodeTimespan = -1;
				}

				encodeTimespanMap.put(cipher, encodeTimespan);

				timestamp = System.currentTimeMillis();
				try {
					cipher.decode(encodedText);
					decodeTimespan = System.currentTimeMillis() - timestamp;
				} catch (Exception e) {
					decodeTimespan = -1;
				}

				decodeTimespanMap.put(cipher, decodeTimespan);

				fullTimespanMap.put(cipher, keyGenTimespan + encodeTimespan + decodeTimespan);

			} catch (Exception e) {
				e.printStackTrace();
				LogUtility.logError("Wystapil problem z rejestracja obiektu szyfru!");
			}
		}

		showAnalizedStats();

		JPanel statsBackPanel = new JPanel() {
			/**
			 *
			 */
			private static final long serialVersionUID = -2997337836278824715L;

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

				int width = getWidth();
				int height = getHeight();

				g2d.setColor(new Color(165, 165, 165));
				g2d.fillRect(0, 0, width, 3);
				g2d.fillRect(0, height - 3, width, height);

				g2d.dispose();
			}
		};
		statsBackPanel.setBorder(new EmptyBorder(3, 0, 3, 0));
		statsBackPanel.setLayout(new BorderLayout(0, 0));
		statsBackPanel.setBackground(new Color(200, 200, 200));
		statsBackPanel.add(statsScrollPane);
		add(statsBackPanel, "1, 6, 3, 1, fill, fill");

		panel.setContentPanel(this);
		dialog.frame.setVisible(true);
	}

	public void showAnalizedStats() {
		JPanel statsContainer = new JPanel();
		statsContainer.setLayout(new MigLayout("", "[grow,leading]", ""));
		statsContainer.setOpaque(false);

		Map<CipherObject, Long> sortedValuesMap = null;

		if (sortOrderCombobox.getSelectedIndex() == 0) {
			sortedValuesMap = keyGenTimespanMap.entrySet().stream().sorted(Entry.comparingByValue())
					.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		} else if (sortOrderCombobox.getSelectedIndex() == 1) {
			sortedValuesMap = encodeTimespanMap.entrySet().stream().sorted(Entry.comparingByValue())
					.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		} else if (sortOrderCombobox.getSelectedIndex() == 2) {
			sortedValuesMap = decodeTimespanMap.entrySet().stream().sorted(Entry.comparingByValue())
					.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		} else if (sortOrderCombobox.getSelectedIndex() == 3) {
			sortedValuesMap = fullTimespanMap.entrySet().stream().sorted(Entry.comparingByValue())
					.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		}

		int i = 0;
		for (CipherObject key : sortedValuesMap.keySet()) {
			AnalizeResultItem item = new AnalizeResultItem(key.getCipherName(), keyGenTimespanMap.get(key),
					encodeTimespanMap.get(key), decodeTimespanMap.get(key));
			statsContainer.add(item, "cell 0 " + (999 - i));
			i++;
		}

		statsScrollPane.setViewportView(statsContainer);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				statsScrollPane.getVerticalScrollBar().setValue(0);
			}
		});
	}

	public class Dialog extends JPanel {
		/**
		 *
		 */
		private static final long serialVersionUID = -7001176625701757L;
		public CustomDialogFrame frame = new CustomDialogFrame("POWIADOMIENIE", width, height, true, false);
	}
}
