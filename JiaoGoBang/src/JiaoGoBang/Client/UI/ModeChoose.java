package JiaoGoBang.Client.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import javax.swing.JPanel;

import JiaoGoBang.Client.Action.Send.EnterRoom;
import JiaoGoBang.Client.Action.Send.Logout;
import JiaoGoBang.Common.Model.User;

public class ModeChoose extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 625;

	public ModeChoose(User user) {
		setTitle("JiaoGoBang");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		Background background = new Background();
		JPanel buttons = new JPanel();
		buttons.setPreferredSize(new Dimension(400, 100));
		Button pve = new Button("人机对战", 2);
		Button pvp = new Button("双人对战", 2);
		Button replay = new Button("复盘", 2);
		Button logout = new Button("登出", 2);
		PVPAction pvpAction = new PVPAction();
		pvp.addActionListener(pvpAction);		
		LogoutAction logoutAction = new LogoutAction();
		logout.addActionListener(logoutAction);
		buttons.add(pve);
		buttons.add(pvp);
		buttons.add(replay);
		buttons.add(logout);
		background.setLayout(new BorderLayout());
		background.add(buttons, BorderLayout.SOUTH);
		buttons.setBackground(null);
		buttons.setOpaque(false);
		add(background);
	}

	private class PVPAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			EnterRoom enterRoom = new EnterRoom("PVP");
			enterRoom.execute();
		}
	}

	private class LogoutAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Logout logout = new Logout();
			logout.execute();
		}
	}
}
