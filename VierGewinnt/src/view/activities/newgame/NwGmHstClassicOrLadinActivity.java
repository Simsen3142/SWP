package view.activities.newgame;

import javax.swing.JButton;

import controller.listener.ChangeActivityOnClickListener;
import view.activities.CustomAppCompatActivity;


public class NwGmHstClassicOrLadinActivity extends CustomAppCompatActivity {

	@Override
	public void onCreate(Object... messages) {
        JButton btnClassic=new JButton("Klassisch");
        btnClassic.addActionListener(new ChangeActivityOnClickListener(new NwGmHstSettingsActivity()));
        this.add(btnClassic);
        
        JButton btnLadin=new JButton("Ladinisch");
        btnLadin.addActionListener(new ChangeActivityOnClickListener(new NwGmHstSettingsActivity()));
        this.add(btnLadin);
    }
}
