package view.activities.newgame;

import java.util.List;

import javax.swing.JPanel;

import controller.server.Server;

/**
 * Created by Katschtaler on 27.03.2018.
 */

public class Client4HostListView extends JPanel{

    private Client4HostListAdapter listAdapter;
    private List<Server.ClientForHost> listData;

    public Client4HostListView(){
    }

    public void initClientListView(List<Server.ClientForHost> listData){
        listAdapter = new Client4HostListAdapter(listData);
        
        for(ClientForHostPanel pnl:listAdapter) {
        	this.add(pnl);
        }
    }

}
