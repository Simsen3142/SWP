package controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.activities.CustomAppCompatActivity;

public class ChangeActivityOnClickListener implements ActionListener {
    private CustomAppCompatActivity newActivity;

    public ChangeActivityOnClickListener(CustomAppCompatActivity newActivity, Object...messages){
        this.newActivity=newActivity;
    }

    public void beforeActivityChanged(){

    }

    public void afterActivityChanged(){

    }

	@Override
	public void actionPerformed(ActionEvent e) {
		beforeActivityChanged();
		CustomAppCompatActivity.startActivity(newActivity);
        afterActivityChanged();
	}
}