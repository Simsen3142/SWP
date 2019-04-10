package view.activities.newgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JTextField;

import controller.client.VierGewinntClientThread;
import controller.server.Server;
import net.miginfocom.swing.MigLayout;
import view.MainActivity;
import view.activities.CustomAppCompatActivity;

public class NwGmJoinIPAddrActivity extends CustomAppCompatActivity {
	public NwGmJoinIPAddrActivity() {
		super();
	}
	
	
	private JTextField ipText;
	private NwGmJoinIPAddrActivity activityThis = this;

	@Override
	public void onCreate(Object... messages) {
		setLayout(new MigLayout("", "[6px,grow][77px]", "[grow][23px][grow]"));

		ipText = new JTextField();
		this.add(ipText, "cell 0 1,growx,aligny center");

		JButton btnJoin = new JButton("Beitreten");
		this.add(btnJoin, "cell 1 1,alignx left,aligny top");
		btnJoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Socket socket = new Socket();
						try {
							int portnr = Server.getPortnr();
							socket.connect(new InetSocketAddress(ipText.getText().toString(), portnr));
							VierGewinntClientThread.initClientThread(socket);
							showToast(ipText.getText().toString());
							System.out.println("Connecting: " + ipText.getText().toString());
							System.out.println("Connecting: " + socket + "");
							showToast(socket + "");
							if (socket.isConnected()) {
								//TODO: CHANGE!
								startActivity(MainActivity.getInstance());
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						VierGewinntClientThread.getInstance().start();
					}
				}).start();
			}
		});
	}

	public CharSequence filter(CharSequence source, int start, int end, int dstart, int dend) {
		if (end > start) {
			String destTxt = ipText.getText();
			String resultingTxt = destTxt.substring(0, dstart) + source.subSequence(start, end)
					+ destTxt.substring(dend);
			if (!resultingTxt.matches("^\\d{1,3}(\\." + "(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
				return "";
			} else {
				String[] splits = resultingTxt.split("\\.");
				for (int i = 0; i < splits.length; i++) {
					if (Integer.valueOf(splits[i]) > 255) {
						return "";
					}
				}
			}
		}
		return source;

	}
}
