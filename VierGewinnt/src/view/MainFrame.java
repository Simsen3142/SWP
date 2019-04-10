package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import view.activities.CustomAppCompatActivity;


public class MainFrame extends JFrame {

	private JPanel contentPane;
	private static MainFrame instance;
	private CustomAppCompatActivity activeActivity;

	public static MainFrame getInstance() {
		if (instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		MainFrame frame = MainFrame.getInstance();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	public void setActiveActivity(CustomAppCompatActivity activity) {
		contentPane.removeAll();
		contentPane.add(activity, BorderLayout.CENTER);
		contentPane.setVisible(false);
		contentPane.setVisible(true);
	}

}
