package view.activities.newgame;

import javax.swing.JButton;

import controller.listener.ChangeActivityOnClickListener;
import view.activities.CustomAppCompatActivity;

public class NwGmHst2Or4Activity extends CustomAppCompatActivity {

	@Override
	public void onCreate(Object... messages) {
        JButton btn2=new JButton("2 Spieler");
        btn2.addActionListener(new ChangeActivityOnClickListener(new NwGmHstClassicOrLadinActivity()));
        this.add(btn2);
        
        JButton btn4=new JButton("4 Spieler");
        btn4.addActionListener(new ChangeActivityOnClickListener(new NwGmHstClassicOrLadinActivity()));
        this.add(btn4);
    }


}
