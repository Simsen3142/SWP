package models;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.awt.Color;
import com.toedter.calendar.JCalendar;

public class SecondsAliveGUI extends JFrame {

	private static JPanel contentPane;
	private static JLabel lbl_seconds;
	private static JCalendar calendar;
	private static JLabel lbl_header;
	private static Person person;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		person=Person.getInstance();
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SecondsAliveGUI frame = new SecondsAliveGUI();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		for(;;) {
			try {
				Thread.sleep(10);
				person.setDob(calendar.getDate());
				lbl_seconds.setText(getFormttedOutputtext());
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public SecondsAliveGUI() {
		initialize();
	}
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][grow][][grow]", "[grow][grow][grow]"));
		
		lbl_header = new JLabel("W\u00E4hle dein Geburtsdatum");
		lbl_header.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
		lbl_header.setForeground(Color.WHITE);
		contentPane.add(lbl_header, "cell 1 0 2 1,alignx center");
		
		calendar = new JCalendar();
		contentPane.add(calendar, "cell 1 1,grow");
		
		lbl_seconds = new JLabel("Datum");
		lbl_seconds.setForeground(Color.WHITE);
		lbl_seconds.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
		contentPane.add(lbl_seconds, "cell 2 1");
	}
	
	private static String getFormttedOutputtext() {
		return "<HTML>Bereits<br>"+
				String.format("%,.2f", ((double)getMillisFromDateTillNow(person.getDob()))/1000.0)+
				" Sekunden<br>am Leben";
	}
	
	private static long getMillisFromDateTillNow(Date date) {
		long dateMillis=date.getTime(), now=System.currentTimeMillis();
		return now-dateMillis;
	}
	
	
}
