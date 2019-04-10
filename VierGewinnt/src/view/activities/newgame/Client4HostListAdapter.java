package view.activities.newgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import controller.server.Server;


/**
 * Created by Simsen on 27.03.2018.
 */
public class Client4HostListAdapter extends ArrayList<ClientForHostPanel> implements MouseListener {

    // child data in format of header title, child title
	private ArrayList<JPanel> panels=new ArrayList<>();
    
    public Client4HostListAdapter(List<Server.ClientForHost> listData) {
        super();
        for(Server.ClientForHost item:listData) {
        	ClientForHostPanel pnl=new ClientForHostPanel(item);
        	this.add(pnl);
        }
    }

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		ClientForHostPanel pnl=(ClientForHostPanel)arg0.getSource();
        Server.ClientForHost client=pnl.getClient();				
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
