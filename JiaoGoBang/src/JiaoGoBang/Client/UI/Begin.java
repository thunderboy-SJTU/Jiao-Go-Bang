package JiaoGoBang.Client.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import JiaoGoBang.Client.ClientThread;
import JiaoGoBang.Client.Action.Send.Login;
import JiaoGoBang.Common.Model.User;

public class Begin extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 625;
	private RoundTextField jTextField;
	private User user;

	public Begin() {
		setTitle("JiaoGoBang");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		Background background = new Background();
		jTextField = new RoundTextField(16);
		JPanel buttons = new JPanel();
		JPanel nicknamePanel = new JPanel();
		buttons.setPreferredSize(new Dimension(400, 100));
		nicknamePanel.setPreferredSize(new Dimension(400, 100));
		Button begin = new Button("开始游戏", 2);
		Button replay = new Button("复盘", 2);
		Button exit = new Button("退出", 2);

		LoginAction loginAction = new LoginAction();
		ExitAction exitAction = new ExitAction();
        
		ReplayAction replayAction = new ReplayAction();
		replay.addActionListener(replayAction);
		
		begin.addActionListener(loginAction);
		exit.addActionListener(exitAction);

		JLabel nickname = new JLabel("昵称:");
		nickname.setForeground(Color.WHITE);
		nickname.setFont(new Font("幼圆", 1, 20));
		nicknamePanel.add(nickname);
		nicknamePanel.add(jTextField);
		buttons.add(begin);
		buttons.add(replay);
		buttons.add(exit);

		background.setLayout(null);
		background.add(nicknamePanel);
		nicknamePanel.setBounds(330, 350, 300, 200);
		background.setLayout(new BorderLayout());
		background.add(buttons, BorderLayout.SOUTH);
		buttons.setBackground(null);
		buttons.setOpaque(false);
		nicknamePanel.setBackground(null);
		nicknamePanel.setOpaque(false);
		add(background);
	}

	private class LoginAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!jTextField.getText().equals("")) {
				if (jTextField.getText().length() > 8) {
					Icon icon = new ImageIcon("img/sign-error-icon.png");
					JOptionPane.showMessageDialog(null, "用户名太长，请重新输入!", "ERROR", JOptionPane.PLAIN_MESSAGE, icon);
					return;
				}
				Begin.this.setEnabled(false);
				Socket socket = createSocket("127.0.0.1", 10000);
				if (socket == null)
				{
					Icon icon = new ImageIcon("img/sign-error-icon.png");
					JOptionPane.showMessageDialog(null, "服务器内部错误!", "ERROR", JOptionPane.PLAIN_MESSAGE, icon);
					Begin.this.setEnabled(true);
					Begin.this.setVisible(true);
					return;
				}
				user = new User(jTextField.getText(), socket);
				UI.setUser(user);
				new ClientThread().start();
				Login login = new Login(user);
				login.execute();
			} else {
				Icon icon = new ImageIcon("img/sign-error-icon.png");
				JOptionPane.showMessageDialog(null, "用户名不能为空!", "ERROR", JOptionPane.PLAIN_MESSAGE, icon);
			}
		}
	}

	private class ExitAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Begin.this.dispose();
		}
	}
	
	private class ReplayAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ReplayBoard replayBoard = new ReplayBoard();
			UI.setReplayBoard(replayBoard);
			UI.getBegin().setVisible(false);
			replayBoard.setVisible(true);			
		}
	}

	private Socket createSocket(String address, int port) {
		try {
			return new Socket(address, port);
		} catch (Exception e) {
			return null;
		}
	}

	public void setUser(String userId, String username) {
		user.setId(userId);
		user.setUsername(username);
	}

	public User getUser() {
		return user;
	}

	public static void main(String[] args) {
		Begin begin = new Begin();
		UI.setBegin(begin);
		begin.setVisible(true);
	}
}
