package view.activities.newgame;

import javax.swing.JPanel;

import controller.server.Server;
import controller.server.Server.ClientForHost;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;

public class ClientForHostPanel extends JPanel {
	private Server.ClientForHost client;
	private JLabel lblId;
	private JLabel lblName;
	
	
	/**
	 * @return the client
	 */
	public Server.ClientForHost getClient() {
		return client;
	}   
	
	public ClientForHostPanel(ClientForHost client) {
		this.client=client;
		initialize();
	}
	private void initialize() {
		setLayout(new MigLayout("", "[][]", "[]"));
		
		lblName = new JLabel(client.getClientName());
		add(lblName, "cell 0 0");
		
		lblId = new JLabel("XYZ");
		add(lblId, "cell 1 0");
	}
}
