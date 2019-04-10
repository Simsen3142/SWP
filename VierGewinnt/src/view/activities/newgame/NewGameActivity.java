package view.activities.newgame;

import javax.swing.JButton;

import controller.listener.ChangeActivityOnClickListener;
import view.activities.CustomAppCompatActivity;

public class NewGameActivity extends CustomAppCompatActivity {

    @Override
    public void onCreate(Object... messages) {
        JButton btnJoin=new JButton("Beitreten");
        btnJoin.addActionListener(new ChangeActivityOnClickListener(new NwGmJoinIPAddrActivity()));
        this.add(btnJoin);
        
        JButton btnHost=new JButton("Erstellen");
        btnHost.addActionListener(new ChangeActivityOnClickListener(new NwGmHostActivity()));
        this.add(btnHost);
    }
}
