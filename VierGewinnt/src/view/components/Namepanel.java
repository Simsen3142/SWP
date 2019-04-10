package view.components;

import javax.swing.JPanel;
import javax.swing.JTextField;

import models.Spieler;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Namepanel extends JPanel {
	private JLabel label;
	private JTextField textField;
	private Spieler spieler;
	private static Font font=new Font("Calibri", Font.BOLD, 20);

	/**
	 * Create the panel.
	 */
	public Namepanel(Spieler spieler) {
		this.spieler=spieler;
		initialize();
	}
	private void initialize() {
		addMouseListener(new ThisMouseListener());
		setOpaque(false);
		setLayout(new MigLayout("", "[]", "[]"));
		
		label = new JLabel(spieler.getName());
		label.setFont(font);
		setLabel();
		add(label, "cell 0 0,alignx center,growy");
		
		textField=new JTextField(spieler.getName());
		textField.setFont(font);
		textField.setForeground(spieler.getColor());
		textField.addKeyListener(new TextFieldKeyListener());
		textField.addFocusListener(new TextFieldFocusListener());
		//add(textField, "cell 0 0,alignx center,growy");
	}

	private class ThisMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			remove(label);
			add(textField, "cell 0 0,alignx center,growy");
			textField.requestFocus();
			textField.selectAll();
			EventQueue.invokeLater(()->{
				revalidate();
				repaint();
			});
		}
	}
	
	private class TextFieldFocusListener extends FocusAdapter {
		@Override
		public void focusLost(FocusEvent arg0) {
			changeToLabel();
		}
	}
	
	private class TextFieldKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER) {
				String newName=textField.getText();
				newName=newName.replace(" ", "_");
				if(newName.length()<1) {
					newName=spieler.getName();
				}else if(newName.length()>10) {
					newName=newName.substring(0, 9);
				}
				textField.setText(newName);
				spieler.setName(newName);
				changeToLabel();
			}else if(arg0.getKeyCode()==KeyEvent.VK_ESCAPE) {
				changeToLabel();
			}
		}
	}
	

	private void changeToLabel() {
		remove(textField);
		setLabel();
		add(label, "cell 0 0,alignx center,growy");
		setVisible(false);
		setVisible(true);
	}
	
	public void setLabel() {
		label.setForeground(spieler.getColor());
		label.setText((spieler.isAmZug()?"\u2022 ":"")+spieler.getName());
	}
}
