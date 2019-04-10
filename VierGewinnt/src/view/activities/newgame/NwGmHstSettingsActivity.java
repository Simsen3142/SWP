package view.activities.newgame;

import javax.swing.JButton;

import controller.listener.ChangeActivityOnClickListener;
import view.activities.CustomAppCompatActivity;

public class NwGmHstSettingsActivity extends CustomAppCompatActivity {

	@Override
	public void onCreate(Object... messages) {
        JButton btnCreate=new JButton("Erstellen");
        btnCreate.addActionListener(new ChangeActivityOnClickListener(new NwGmHostActivity()));
        this.add(btnCreate);
	}
}
