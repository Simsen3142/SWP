package view;
import java.awt.Color;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;

import controller.MainController;
import controller.SpielHandler;
import controller.SpielListener;
import controller.UserActionHandler;
import controller.UserActionListener;
import controller.ZugListener;
import controller.client.SpielMessageObserver;
import controller.client.UserUiMesssageObserver;
import controller.client.VierGewinntClientThread;
import controller.server.Server;
import controller.server.controller.VierGewinntServerController;
import controller.server.handler.SpielServerHandler;
import models.Spieler;
import models.Zug;
import models.wifimessages.SpielMessage;
import models.wifimessages.UserUIMessage;
import models.wifimessages.WifiMessage;
import net.miginfocom.swing.MigLayout;
import view.activities.CustomAppCompatActivity;
import view.components.Namepanel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainActivity extends CustomAppCompatActivity implements SpielListener, ZugListener, UserActionListener, SpielMessageObserver, UserUiMesssageObserver{

//	private JPanel contentPane;
	private Namepanel npnlSpieler1;
	private Namepanel npnlSpieler2;
	private JLabel lblGewinnt;
	private Spielfeld spielfeld;
	private JScrollPane scrollPane;
	private Spieler sp1;
	private Spieler sp2;
	private Spieler winner;
	private JButton btnReset;
	private JButton btnBack;
	private Settingsbar settingsbar;
	private JButton btnWeiter;
	private boolean redoing=false;
	private static MainActivity instance;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//				| UnsupportedLookAndFeelException e) {
//			e.printStackTrace();
//		}
//		
//		MainFrame frame = new MainFrame();
//		frame.setVisible(true);
//
//		frame.spiel=new Spiel();
//		
//		frame.sp1.setAmZug(true);
//		
//		frame.setSpielerLabels();
//		
//		playSound("Void.wav");
//		
//		SpielHandler.getInstance().addSpielListener(frame);		
//		SpielHandler.getInstance().addSpielListener(frame.spiel);		
//
//	}

	public Spielfeld getSpielfeld() {		
		return spielfeld;
	}
	
//	public Spiel getSpiel() {
//		return spiel;
//	}
	

	/**
	 * Create the frame.
	 */
	private MainActivity(Spieler sp1, Spieler sp2) {
		this.sp2=sp2;
		this.sp1=sp1;
	}
	
	public static void initInstance(Spieler sp1, Spieler sp2) {
		instance=new MainActivity(sp1, sp2);
	}
	
	public static MainActivity getInstance() {
		return instance;
	}
	
	private void initialize() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 300);
//		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.addMouseListener(new ThisMouseListener());
		this.setBackground(new Color(255,255,255));
//		setContentPane(contentPane);
		this.setLayout(new MigLayout("gap 0, insets 0", "[15%][80%][5%]", "[][grow][]"));
		
		settingsbar = new Settingsbar();
		this.add(settingsbar, "cell 0 0 1 2,grow");
		
		lblGewinnt = new JLabel(SpielHandler.getInstance().getAmtToWin()+" Gewinnt");
		lblGewinnt.setFont(new Font("Tahoma", Font.PLAIN, 28));
		this.add(lblGewinnt, "cell 1 0,alignx center");		
		
		btnReset = new JButton("RESET");
		btnReset.addActionListener(new BtnResetActionListener());
		this.add(btnReset, "flowy,cell 2 0,alignx right,aligny top");
		
		spielfeld = new Spielfeld(this);
		spielfeld.setOpaque(false);
		
		scrollPane = new JScrollPane(spielfeld);
		this.add(scrollPane, "cell 1 1,grow");
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		npnlSpieler1 = new Namepanel(sp1);
		//lblSpieler1.setFont(new Font("Calibri", Font.BOLD, 20));
		this.add(npnlSpieler1, "cell 0 2,alignx left,aligny bottom");
		
		npnlSpieler2 = new Namepanel(sp2);
		this.add(npnlSpieler2, "cell 2 2,alignx right");
		
		this.addKeyListener(spielfeld.getRahmen().getRowListener());
		
		btnBack = new JButton("Rückgängig");
		btnBack.addActionListener(new BtnBackActionListener());
		this.add(btnBack, "cell 2 0,alignx right");
		
		btnWeiter = new JButton("Weiter");
		btnWeiter.addActionListener(new BtnWeiterActionListener());
		this.add(btnWeiter, "cell 2 0,alignx right");
	}

	public void setSpielerLabels() {
		npnlSpieler1.setLabel();
		npnlSpieler2.setLabel();
	}
	
	
	private void doZug(int column, int row) {
		System.out.println(")ODI/KJWZFDUIOZOLk");
		Spieler spielerAmZug=Spieler.getSpielerAmZug();
		System.out.println("SP AM ZUG: "+spielerAmZug);
		int[] coordinates=new int[] {column,row};
		
		
		playSound("click.wav");
		new Zug(spielerAmZug, coordinates);
	}	
	
	public void doWin(ArrayList<int[]> wonCoords,int result) {
		Spieler winner=Spieler.getSpielerByNr(result);
		playSound("winning.wav");
		JOptionPane.showMessageDialog(null, winner.getName()+" hat gewonnen!", "Gratullation",JOptionPane.PLAIN_MESSAGE);
		
		spielfeld.getChipView().visualizeWonChips(wonCoords);
	}
	
	public JButton getButtonReset() {
		return btnReset;
	}
	
	private class BtnResetActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			UserActionHandler.getInstance().triggerResetClicked(null);
		}
	}
	
	private class ThisMouseListener extends MouseAdapter{
		@Override
		public void mouseEntered(MouseEvent arg0) {
			requestFocus();
		}
	}
	
	private class BtnBackActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			UserActionHandler.getInstance().triggerOnBackClicked(null);
		}
	}
	private class BtnWeiterActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			Zug.redo();
		}
	}
	
	public static synchronized void playSound(String filename) {
		new Thread(new Runnable() { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing,
									// see comments
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(new File("sounds/" + filename).getAbsoluteFile());
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();		
	}

	@Override
	public void onChipPlaced(int column, int row, int user) {
		System.out.println(column);
		Spieler sp=Spieler.getSpielerByNr(user);
		int[] coords=new int[] {column,row};
		
		getSpielfeld().addChip(coords, sp.getColor(),
				(Void)->{
					playSound("coin-drop-4.wav");
//						int result=getSpiel().checkWon();
//						if(result>0) {
//							doWin(result);
//						}
					
					sp.getNextSpieler().setAmZug(true);

					setSpielerLabels();
					return null;
				}
		);
		
		if(!redoing) {
			doZug(column,row);
		}
		redoing=false;
		sp.setAmZug(false);
		
//		System.out.println("SENDING WIFI MESSAGE:");
//		WifiMessage.sendWifiMessageJSONToServer(new SpielMessage.AddChipMessage(VierGewinntClientThread.getInstance().getName(), column, row));
	}

	@Override
	public void onChipRemoved(int column, int row, int user) {
		playSound("Swing.wav");
		
		Spieler sp=Spieler.getSpielerByNr(user);
		getSpielfeld().removeChip(new int[] {column,row}, sp.getColor(),
				(Void)->{
					sp.setAmZug(true);
					setSpielerLabels();
					
					return null;
				}
			);
		sp.setAmZug(false);
	}

	@Override
	public void onWon(ArrayList<int[]> wonCoords, int user) {
		winner=Spieler.getSpielerByNr(user);
		doWin(wonCoords,user);		
	}

	@Override
	public void onNewGame(int amtToWin, int columns, int rows) {
		spielfeld.initialize();
		spielfeld.resizeThings();
		addKeyListener(spielfeld.getRahmen().getRowListener());


		lblGewinnt.setText(SpielHandler.getInstance().getAmtToWin()+" Gewinnt");
		
		playSound("page-flip-01a.wav");


		if(winner!=null && sp2!=winner) {
			instance.sp1=instance.sp2;
			instance.sp2=winner;
		}
					
		sp1.setAmZug(true);
		
		winner=null;
		setSpielerLabels();
		requestFocus();
	}

	@Override
	public void onColumnClicked(Spieler user, int column) {
		if(user!=null) {
			SpielHandler.getInstance().triggerOnChipPlaced(column,user.getNumber());
		}
	}

	@Override
	public void onColumnSelected(Spieler user, int column) {
		spielfeld.getRahmen().markColumnToHovering(column, true);
	}

	@Override
	public void onChipPlacementDone(Spieler user) {
	}

	@Override
	public void onNameChanged(Spieler user, String oldName, String newName) {
		
	}

	@Override
	public void onColorChanged(Spieler user, int[] newColor) {
		
	}

	@Override
	public void onResetClicked(Spieler user) {
		removeKeyListener(spielfeld.getRahmen().getRowListener());
		Zug.reset();
		SpielHandler.getInstance().triggerOnNewGame(settingsbar.getNGewinnt(), settingsbar.getPlaygrndWidth(),  settingsbar.getPlaygrndHeight());
	}

	@Override
	public void onBackClicked(Spieler user) {
		Zug.undo();
	}

	@Override
	public void onZugRedone(Spieler sp, int[] coords) {		
		redoing=true;
		SpielHandler.getInstance().triggerOnChipPlaced(coords[0], coords[1],sp.getNumber());
	}

	@Override
	public void onZugUndone(Spieler sp, int[] coords) {
		SpielHandler.getInstance().triggerOnChipRemoved(coords[0], coords[1]);
	}

	@Override
	public void onCreate(Object... messages) {
		System.out.println("CREATED");
		initialize();
	}

	@Override
	public void onMessageReceived(UserUIMessage message) {
		System.out.println(message);
	}

	@Override
	public void onMessageReceived(SpielMessage message) {
		System.out.println(message);
	}
}



