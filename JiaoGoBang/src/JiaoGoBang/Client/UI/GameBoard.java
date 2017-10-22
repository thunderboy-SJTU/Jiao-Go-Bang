package JiaoGoBang.Client.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JFrame;

import javax.swing.JPanel;

import JiaoGoBang.Client.Action.Send.CancelPrepare;
import JiaoGoBang.Client.Action.Send.Lose;
import JiaoGoBang.Client.Action.Send.Pass;
import JiaoGoBang.Client.Action.Send.Prepare;
import JiaoGoBang.Client.Action.Send.ReturnBack;
import JiaoGoBang.Client.Action.Send.WithdrawRequire;

public class GameBoard extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 580;
	private ChessBoard chessBoard;
	private UserBoard userBoard;
	private Button prepareButton;
	private Button passButton;
	private Button returnButton;
	private Button withdrawButton;
	private String mode;

	public GameBoard(String rivalname, String mode) {
		setTitle("JiaoGoBang");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		chessBoard = new ChessBoard(this);
		userBoard = new UserBoard(UI.getUser().getUsername(), rivalname);
		this.mode = mode;
		Color color = new Color(245, 222, 179);
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, 500));
		panel.setBackground(color);
		panel.add(userBoard, BorderLayout.NORTH);
		JPanel buttons = new JPanel();
		JPanel passPanel = new JPanel();
		passPanel.setPreferredSize(new Dimension(300, 50));
		passPanel.setBackground(null);
		passPanel.setOpaque(false);
		JPanel withdrawPanel = new JPanel();
		withdrawPanel.setPreferredSize(new Dimension(300, 50));
		withdrawPanel.setBackground(null);
		withdrawPanel.setOpaque(false);
		JPanel giveUpPanel = new JPanel();
		giveUpPanel.setPreferredSize(new Dimension(300, 50));
		giveUpPanel.setBackground(null);
		giveUpPanel.setOpaque(false);
		JPanel preparePanel = new JPanel();
		preparePanel.setPreferredSize(new Dimension(300, 50));
		preparePanel.setBackground(null);
		preparePanel.setOpaque(false);
		buttons.setPreferredSize(new Dimension(300, 300));
		prepareButton = new Button("准备", 1);
		passButton = new Button("PASS", 1);
		passButton.setEnabled(false);
		withdrawButton = new Button("悔棋", 1);
		withdrawButton.setEnabled(false);
		returnButton = new Button("返回", 1);
		
		PrepareAction prepareAction = new PrepareAction();
		prepareButton.addActionListener(prepareAction);
		
		PassAction passAction = new PassAction();
		passButton.addActionListener(passAction);
		
		WithdrawAction withdrawAction = new WithdrawAction();
		withdrawButton.addActionListener(withdrawAction);
		
		ReturnAction returnAction = new ReturnAction();
		returnButton.addActionListener(returnAction);
		passPanel.add(passButton);
		withdrawPanel.add(withdrawButton);
		giveUpPanel.add(returnButton);
		preparePanel.add(prepareButton);
		buttons.add(preparePanel);
		buttons.add(passPanel);
		buttons.add(withdrawPanel);
		buttons.add(giveUpPanel);
		buttons.setBackground(null);
		buttons.setOpaque(false);
		panel.add(buttons, BorderLayout.SOUTH);
		JPanel whole = new JPanel();
		whole.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		whole.setLayout(new BorderLayout());
		whole.add(chessBoard, BorderLayout.WEST);
		whole.add(panel, BorderLayout.CENTER);
		add(whole);
	}

	public void setRivalname(String rivalname) {
		userBoard.setUser2(rivalname);
	}
	
	public void setColor(Color color)
	{
		if(color == Color.BLACK)
		userBoard.chooseBlack(true);
		else if(color == Color.WHITE)
			userBoard.chooseBlack(false);
	}
	
	public Color getColor()
	{
		return userBoard.getColor();
	}
	
	public Button getPrepareButton()
	{
		return prepareButton;
	}
	
	public Button getReturnButton()
	{
		return returnButton;
	}
	
	public Button getWithdrawButton()
	{
		return withdrawButton;
	}
	
	public ChessBoard getChessBoard()
	{
		return chessBoard;
	}
	
	public UserBoard getUserBoard()
	{
		return userBoard;
	}
	
	public void setMode(String mode)
	{
		this.mode = mode;
	}
	
	public String getMode()
	{
		return mode;
	}
    
	public void reset()
	{
		chessBoard.reset();
	    userBoard.resetColor();
	    prepareButton.setEnabled(true);
	    prepareButton.setText("准备");
	    passButton.setEnabled(false);
	    withdrawButton.setEnabled(false);
	    returnButton.setText("返回");
	    repaint();
	}
	
	public void setStatus(int status)
	{
		if(status == 1)
		{
		  getChessBoard().setStatus(1);
		  passButton.setEnabled(true);
		}
		else if(status == 0)
		{
			getChessBoard().setStatus(0);
			passButton.setEnabled(false);
		}
	}
	private class PrepareAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Button button = GameBoard.this.prepareButton;
			if (button.getText().equals("准备")){
				GameBoard.this.prepareButton.setText("取消");
				Prepare prepare = new Prepare(mode);
				prepare.execute();
			}	
			else
			{
				CancelPrepare cancelPrepare = new CancelPrepare(mode);
				cancelPrepare.execute();
				
			}

		}
	}
	
	private class PassAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Button button = GameBoard.this.passButton;
			button.setEnabled(false);
			UI.getGameBoard().setStatus(0);
			Pass pass = new Pass(mode);
			pass.execute();
		}
	}
	
	private class ReturnAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Button button = GameBoard.this.returnButton;
			if (button.getText().equals("返回")){
				UI.getGameBoard().setEnabled(false);
				ReturnBack returnBack = new ReturnBack();
				returnBack.execute();
			}	
			else
			{
				Lose lose = new Lose(mode);
				lose.execute();
			}
		}
	}
	
	private class WithdrawAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			WithdrawRequire withdraw = new WithdrawRequire(mode);
			withdraw.execute();
		}
	}
	
	
	
	
	

}
