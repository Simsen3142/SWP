package view.activities;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import view.MainFrame;

public abstract class CustomAppCompatActivity extends JPanel {
	
	public CustomAppCompatActivity() {
		super();
	}

	public abstract void onCreate(Object...messages);
    
    public void showToast(String text) {
        JOptionPane.showMessageDialog(this, text);
    }
    
    public static void startActivity(CustomAppCompatActivity activity) {
		activity.onCreate();
		MainFrame.getInstance().setActiveActivity(activity);
	}
    
    public void onDestroy() {
    	
    }
}
