package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.BoxLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import java.awt.Font;

public class Settingsbar extends JPanel {
	private static JSpinner spnrNgewinnt;
	private JLabel lblNgewinnt;
	
	private static JSpinner spnrHeight;
	private JLabel lblHeight;
	
	private static JSpinner spnrWidth;
	private JLabel lblWidth;
	

	/**
	 * Create the panel.
	 */
	public Settingsbar() {

		initialize();
	}
	private void initialize() {
		setLayout(new MigLayout("gap 0", "[][grow]", "[10%][10%][10%]"));
		
		lblNgewinnt = new JLabel("n-Gewinnt ");
		spnrNgewinnt = new JSpinner();
		addLblNSpinner(lblNgewinnt,spnrNgewinnt,4);
		
		lblWidth = new JLabel("Breite");
		spnrWidth = new JSpinner();
		addLblNSpinner(lblWidth,spnrWidth,7);
		
		lblHeight = new JLabel("Höhe");
		spnrHeight = new JSpinner();
		addLblNSpinner(lblHeight,spnrHeight,6);
	}
	
	private int row=0;
	private void addLblNSpinner(JLabel lbl, JSpinner spinner, int predevalue) {
		add(lbl, "cell 0 "+row);
		lbl.setFont(new Font("Calibri", Font.PLAIN, 16));
		spinner.setValue(predevalue);
		spinner.setFont(new Font("Calibri", Font.PLAIN, 16));
		((SpinnerNumberModel) spinner.getModel()).setMinimum(1);
		add(spinner, "cell 1 "+row+",growx,aligny center");
		row++;
	}
	
	public static int getNGewinnt() {
		return (int) spnrNgewinnt.getValue();
	}
	
	public static int getPlaygrndHeight() {
		return (int) spnrHeight.getValue();
	}
	
	public static int getPlaygrndWidth() {
		return (int) spnrWidth.getValue();
	}

}
