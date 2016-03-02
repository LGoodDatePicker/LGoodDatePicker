package com.lgooddatepicker.demo;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class DemoPanel extends JPanel {
    
	public DemoPanel() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		labelFeatures = new JLabel();
		labelLanguages = new JLabel();
		scrollPaneFeatures = new JScrollPane();
		featuresPanel = new JPanel();
		clickHereLabel = new JLabel();
		labelDate1 = new JLabel();
		label15 = new JLabel();
		labelDate2 = new JLabel();
		label16 = new JLabel();
		labelDate3 = new JLabel();
		label17 = new JLabel();
		labelDate4 = new JLabel();
		label18 = new JLabel();
		labelDate5 = new JLabel();
		label19 = new JLabel();
		labelDate6 = new JLabel();
		label20 = new JLabel();
		labelDate7 = new JLabel();
		label21 = new JLabel();
		labelDate9 = new JLabel();
		label22 = new JLabel();
		labelDate8 = new JLabel();
		label23 = new JLabel();
		labelDate10 = new JLabel();
		label24 = new JLabel();
		scrollPaneLanguages = new JScrollPane();
		languagePanel = new JPanel();
		outerButtonPanel = new JPanel();
		scrollPaneForButtons = new JScrollPane();
		messageTextAreaScrollPane = new JScrollPane();
		messageTextArea = new JTextArea();

		//======== this ========
		setLayout(new GridBagLayout());
		((GridBagLayout)getLayout()).columnWidths = new int[] {40, 0, 30, 0, 40, 0};
		((GridBagLayout)getLayout()).rowHeights = new int[] {20, 0, 10, 200, 15, 100, 15, 193, 25, 0};
		((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0, 0.0, 1.0E-4};
		((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

		//---- labelFeatures ----
		labelFeatures.setText("Features Sample:");
		labelFeatures.setFont(labelFeatures.getFont().deriveFont(labelFeatures.getFont().getStyle() | Font.BOLD, labelFeatures.getFont().getSize() + 6f));
		labelFeatures.setHorizontalTextPosition(SwingConstants.CENTER);
		labelFeatures.setHorizontalAlignment(SwingConstants.CENTER);
		add(labelFeatures, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));

		//---- labelLanguages ----
		labelLanguages.setText("Languages Sample:");
		labelLanguages.setFont(labelLanguages.getFont().deriveFont(labelLanguages.getFont().getStyle() | Font.BOLD, labelLanguages.getFont().getSize() + 6f));
		labelLanguages.setHorizontalTextPosition(SwingConstants.CENTER);
		labelLanguages.setHorizontalAlignment(SwingConstants.CENTER);
		add(labelLanguages, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));

		//======== scrollPaneFeatures ========
		{

			//======== featuresPanel ========
			{
				featuresPanel.setLayout(new GridBagLayout());
				((GridBagLayout)featuresPanel.getLayout()).columnWidths = new int[] {10, 0, 10, 200, 20, 0};
				((GridBagLayout)featuresPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
				((GridBagLayout)featuresPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0, 1.0E-4};
				((GridBagLayout)featuresPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

				//---- clickHereLabel ----
				clickHereLabel.setText("Click here  -------V   ");
				clickHereLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				clickHereLabel.setForeground(Color.blue);
				clickHereLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
				featuresPanel.add(clickHereLabel, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- labelDate1 ----
				labelDate1.setText("Date 1, Default Settings:");
				featuresPanel.add(labelDate1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- label15 ----
				label15.setText(" ");
				featuresPanel.add(label15, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- labelDate2 ----
				labelDate2.setText("Date 2, Highlight Policy:");
				featuresPanel.add(labelDate2, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- label16 ----
				label16.setText(" ");
				featuresPanel.add(label16, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- labelDate3 ----
				labelDate3.setText("Date 3, Veto Policy:");
				featuresPanel.add(labelDate3, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- label17 ----
				label17.setText(" ");
				featuresPanel.add(label17, new GridBagConstraints(3, 7, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- labelDate4 ----
				labelDate4.setText("Date 4, Both Policies:");
				featuresPanel.add(labelDate4, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- label18 ----
				label18.setText(" ");
				featuresPanel.add(label18, new GridBagConstraints(3, 9, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- labelDate5 ----
				labelDate5.setText("Date 5, Set First Day Of Week (Mon):");
				featuresPanel.add(labelDate5, new GridBagConstraints(1, 10, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- label19 ----
				label19.setText(" ");
				featuresPanel.add(label19, new GridBagConstraints(3, 11, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- labelDate6 ----
				labelDate6.setText("Date 6, Change Calendar Size:");
				featuresPanel.add(labelDate6, new GridBagConstraints(1, 12, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- label20 ----
				label20.setText(" ");
				featuresPanel.add(label20, new GridBagConstraints(3, 13, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- labelDate7 ----
				labelDate7.setText("Date 7, Change Colors:");
				featuresPanel.add(labelDate7, new GridBagConstraints(1, 14, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- label21 ----
				label21.setText(" ");
				featuresPanel.add(label21, new GridBagConstraints(3, 15, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- labelDate9 ----
				labelDate9.setText("Date 8, Custom Date Format:");
				featuresPanel.add(labelDate9, new GridBagConstraints(1, 16, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- label22 ----
				label22.setText(" ");
				featuresPanel.add(label22, new GridBagConstraints(3, 17, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- labelDate8 ----
				labelDate8.setText("Date 9, Disallow Empty Dates:");
				featuresPanel.add(labelDate8, new GridBagConstraints(1, 18, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- label23 ----
				label23.setText(" ");
				featuresPanel.add(label23, new GridBagConstraints(3, 19, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- labelDate10 ----
				labelDate10.setText("Date 10, Custom Font:");
				featuresPanel.add(labelDate10, new GridBagConstraints(1, 20, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- label24 ----
				label24.setText(" ");
				featuresPanel.add(label24, new GridBagConstraints(3, 21, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			scrollPaneFeatures.setViewportView(featuresPanel);
		}
		add(scrollPaneFeatures, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));

		//======== scrollPaneLanguages ========
		{

			//======== languagePanel ========
			{
				languagePanel.setLayout(new GridBagLayout());
				((GridBagLayout)languagePanel.getLayout()).columnWidths = new int[] {10, 0, 10, 200, 20, 0};
				((GridBagLayout)languagePanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
				((GridBagLayout)languagePanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0, 1.0E-4};
				((GridBagLayout)languagePanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
			}
			scrollPaneLanguages.setViewportView(languagePanel);
		}
		add(scrollPaneLanguages, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));

		//======== outerButtonPanel ========
		{
			outerButtonPanel.setBorder(new TitledBorder(null, "Programmatic Control:", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
				new Font("Segoe UI", Font.BOLD, 14)));
			outerButtonPanel.setLayout(new BoxLayout(outerButtonPanel, BoxLayout.X_AXIS));
			outerButtonPanel.add(scrollPaneForButtons);
		}
		add(outerButtonPanel, new GridBagConstraints(1, 5, 3, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));

		//======== messageTextAreaScrollPane ========
		{
			messageTextAreaScrollPane.setBorder(new TitledBorder(null, "Messages:", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
				new Font("Segoe UI", Font.BOLD, 14)));

			//---- messageTextArea ----
			messageTextArea.setLineWrap(true);
			messageTextArea.setWrapStyleWord(true);
			messageTextArea.setFont(new Font("Arial", Font.BOLD, 13));
			messageTextArea.setMargin(new Insets(4, 6, 2, 6));
			messageTextAreaScrollPane.setViewportView(messageTextArea);
		}
		add(messageTextAreaScrollPane, new GridBagConstraints(1, 7, 3, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel labelFeatures;
	private JLabel labelLanguages;
	private JScrollPane scrollPaneFeatures;
	public JPanel featuresPanel;
	private JLabel clickHereLabel;
	public JLabel labelDate1;
	private JLabel label15;
	public JLabel labelDate2;
	private JLabel label16;
	public JLabel labelDate3;
	private JLabel label17;
	public JLabel labelDate4;
	private JLabel label18;
	public JLabel labelDate5;
	private JLabel label19;
	public JLabel labelDate6;
	private JLabel label20;
	public JLabel labelDate7;
	private JLabel label21;
	public JLabel labelDate9;
	private JLabel label22;
	public JLabel labelDate8;
	private JLabel label23;
	public JLabel labelDate10;
	private JLabel label24;
	private JScrollPane scrollPaneLanguages;
	public JPanel languagePanel;
	private JPanel outerButtonPanel;
	public JScrollPane scrollPaneForButtons;
	private JScrollPane messageTextAreaScrollPane;
	public JTextArea messageTextArea;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
