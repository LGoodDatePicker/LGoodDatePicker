package com.lgooddatepicker.demo;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

public class DemoPanel extends JPanel {
	public DemoPanel() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		buttonPanel = new JPanel();
		dateLabel1 = new JLabel();
		clickHereLabel = new JLabel();
		dateLabel2 = new JLabel();
		dateLabel3 = new JLabel();
		label1 = new JLabel();
		messageTextAreaScrollPane = new JScrollPane();
		messageTextArea = new JTextArea();
		informationTextAreaScrollPane = new JScrollPane();
		informationTextArea = new JTextArea();

		//======== this ========
		setLayout(new FormLayout(
			"$ugap, [default,110dlu], 3*($lcgap, default:grow), $ugap",
			"$ugap, 2*(fill:default, 20dlu), fill:default, fill:20dlu, fill:default, 8dlu, $ugap, fill:[60dlu,default], $lgap, top:default, $ugap"));

		//======== buttonPanel ========
		{
			buttonPanel.setLayout(new FormLayout(
				"default:grow",
				"min, 4*(default, $lgap), default"));
		}
		add(buttonPanel, CC.xywh(8, 2, 1, 6));

		//---- dateLabel1 ----
		dateLabel1.setText("Date 1:");
		dateLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		add(dateLabel1, CC.xy(2, 2));

		//---- clickHereLabel ----
		clickHereLabel.setText("Click here  _____________/\\    ");
		clickHereLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		clickHereLabel.setForeground(Color.blue);
		add(clickHereLabel, CC.xy(4, 3));

		//---- dateLabel2 ----
		dateLabel2.setText("Date 2:");
		dateLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		add(dateLabel2, CC.xy(2, 4));

		//---- dateLabel3 ----
		dateLabel3.setText("<html>With Customized Settings:<br/>First weekday is Monday. Has Veto Policy, Highlight Policy, and a Change Listener.</html>");
		dateLabel3.setHorizontalAlignment(SwingConstants.LEFT);
		add(dateLabel3, CC.xywh(2, 5, 1, 3));

		//---- label1 ----
		label1.setText("<html>With A Different Locale:<br/>(Russian)</html>");
		label1.setHorizontalAlignment(SwingConstants.LEFT);
		add(label1, CC.xywh(2, 8, 1, 2));

		//======== messageTextAreaScrollPane ========
		{
			messageTextAreaScrollPane.setBorder(new TitledBorder("Messages"));

			//---- messageTextArea ----
			messageTextArea.setLineWrap(true);
			messageTextArea.setWrapStyleWord(true);
			messageTextArea.setFont(new Font("Arial", Font.BOLD, 13));
			messageTextArea.setMargin(new Insets(4, 6, 2, 6));
			messageTextAreaScrollPane.setViewportView(messageTextArea);
		}
		add(messageTextAreaScrollPane, CC.xywh(2, 11, 7, 1));

		//======== informationTextAreaScrollPane ========
		{
			informationTextAreaScrollPane.setBorder(new TitledBorder("Information"));

			//---- informationTextArea ----
			informationTextArea.setText("\nInterface: Most items in a date picker are clickable. These include... The buttons for previous and next month, the buttons for previous and next year, the \"today\" text, the \"clear\" text, and individual dates. A click on the month or year label (at the top), will open a menu for changing the month or year.\n\nGeneral features: \n* Automatic internationalization. (Month names, weekday names, the default first day of the week,  default date formats, and button text.)\n* Translations so far include 12 languages.\n* Fonts and colors can be changed. (For example the highlight color, or the invalid date font). \n* Relatively compact source code (3 main classes).\n* Creating a DatePicker requires only one line of code.\n* Open source code base.\n\nData types: The standard Java 8 time library is used to store dates, and they are convertible to other data types. (The Java 8 time package is also called \"java.time\" or \"JSR-310\", and was developed by the author of Joda Time.)\n\nVeto and Highlight Policies: These policies are optional. A veto policy restricts the dates that can be selected. A highlight policy provides a visual highlight on desired dates, with optional tooltips. The customized date picker above has a highlight policy for weekends and the 3rd (with tooltips added), and a veto policy for every 5th day. If today is vetoed, the \"today\" button will be grey and disabled.\n\nDate values and automatic validation: Every date picker stores its current text, and its last valid date. The last valid date is returned when you call DatePicker.getDate(). If the user types into the text field, any text that is not a valid date will be displayed in red, any vetoed date will have a strikethrough, and valid dates will display in black. When the focus on a date picker is lost, the text is always set to match the last valid date.\n");
			informationTextArea.setEditable(false);
			informationTextArea.setLineWrap(true);
			informationTextArea.setWrapStyleWord(true);
			informationTextAreaScrollPane.setViewportView(informationTextArea);
		}
		add(informationTextAreaScrollPane, CC.xywh(2, 13, 7, 1));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	public JPanel buttonPanel;
	private JLabel dateLabel1;
	private JLabel clickHereLabel;
	private JLabel dateLabel2;
	private JLabel dateLabel3;
	private JLabel label1;
	private JScrollPane messageTextAreaScrollPane;
	public JTextArea messageTextArea;
	private JScrollPane informationTextAreaScrollPane;
	private JTextArea informationTextArea;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
