package JiaoGoBang.Client.UI;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import JiaoGoBang.Client.Action.Send.Login;
import JiaoGoBang.Common.Model.User;

public class LoginBoard extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private JTextField jTextField;
	

	public LoginBoard(Begin begin) {
		user = null;
		setSize(300, 200);
		setTitle("Login");
		setResizable(false);
		jTextField = new JTextField(12);
		JLabel username = new JLabel("用户名:");
		JLabel label = new JLabel("Login");
		username.setFont(new Font("楷体", 1, 14));
		label.setFont(new Font("Arial", 1, 25));
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				Image background = Toolkit.getDefaultToolkit().getImage("src/board.jpg");
				g.drawImage(background, 0, 0, 300, 200, null);
				MediaTracker t = new MediaTracker(this);
				t.addImage(background, 0);
				try {
					t.waitForAll();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		panel.setPreferredSize(new Dimension(300, 200));
		JPanel title = new JPanel();
		title.setPreferredSize(new Dimension(300, 50));
		title.setBackground(null);
		title.setOpaque(false);
		title.add(label);
		JPanel text = new JPanel();
		text.setPreferredSize(new Dimension(300, 50));
		text.setBackground(null);
		text.setOpaque(false);
		JPanel buttons = new JPanel();
		buttons.setPreferredSize(new Dimension(300, 50));
		buttons.setBackground(null);
		buttons.setOpaque(false);
		Button login = new Button("登录", 1);
		Button cancel = new Button("取消", 1);
		LoginAction loginAction = new LoginAction();
		login.addActionListener(loginAction);
		CancelAction cancelAction = new CancelAction();
		cancel.addActionListener(cancelAction);
		panel.setLayout(new BorderLayout());
		text.add(username);
		text.add(jTextField);
		buttons.add(login);
		buttons.add(cancel);
		panel.add(title, BorderLayout.NORTH);
		panel.add(text, BorderLayout.CENTER);
		panel.add(buttons, BorderLayout.SOUTH);
		add(panel);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				begin.setEnabled(true);
			}
		});
	}

	private class LoginAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (!jTextField.getText().equals("")) {
				LoginBoard.this.setEnabled(false);
				Socket socket = createSocket("127.0.0.1", 10000);
				if (socket == null)
					return;
				user = new User(jTextField.getText(), socket);
				UI.setUser(user);
				Login login = new Login(user);
				login.execute();
			} else {
				Icon icon = new ImageIcon("src/sign-error-icon.png");
				JOptionPane.showMessageDialog(null, "用户名不能为空!", "ERROR", JOptionPane.PLAIN_MESSAGE, icon);
			}
		}
	}

	private class CancelAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			UI.getBegin().setEnabled(true);
			LoginBoard.this.dispose();
		}
	}

	private Socket createSocket(String address, int port) {
		try {
			return new Socket(address, port);
		} catch (Exception e) {
			return null;
		}
	}
	

	
	public void setUser(String userId,String username)
	{
		user.setId(userId);
		user.setUsername(username);
	}
	
	public User getUser()
	{
		return user;
	}
	
}
