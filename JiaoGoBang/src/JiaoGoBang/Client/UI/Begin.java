package JiaoGoBang.Client.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Begin extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 625;

	public Begin() {
		setTitle("JiaoGoBang");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		Background background = new Background();
		JPanel buttons = new JPanel();
		buttons.setPreferredSize(new Dimension(400, 100));
		Button login = new Button("µÇÂ¼", 2);
		Button replay = new Button("¸´ÅÌ", 2);
		Button exit = new Button("ÍË³ö", 2);
		
		LoginAction loginAction = new LoginAction();
		ExitAction  exitAction = new ExitAction();
		
		login.addActionListener(loginAction);
		exit.addActionListener(exitAction);
		
		buttons.add(login);
		buttons.add(replay);
		buttons.add(exit);
		
		
		
		
		background.setLayout(new BorderLayout());
		background.add(buttons, BorderLayout.SOUTH);
		buttons.setBackground(null);
		buttons.setOpaque(false);
		add(background);
	}
	
	private class LoginAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{

			LoginBoard loginBoard = new LoginBoard(Begin.this);
			UI.setLoginBoard(loginBoard);
			Begin.this.setEnabled(false);
			loginBoard.setVisible(true);
		}
	}
	
	private class ExitAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Begin.this.dispose();
		}
	}
	


	public static void main(String[] args) {
		Begin begin = new Begin();
		UI.setBegin(begin);
		begin.setVisible(true);
	}
}
