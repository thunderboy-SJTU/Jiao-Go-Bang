package JiaoGoBang.Client.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton{
    private static final long serialVersionUID = 1218571878182523580L;
    
    public Button(String buttonText, int mode){
        Dimension d = new Dimension(108, 31);
        this.setSize(d);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
        if(mode == 1)
        {
        ImageIcon icon1=new ImageIcon("src/green.gif");
        setIcon(icon1);

        ImageIcon icon2=new ImageIcon("src/lightblue.gif");
        setRolloverIcon(icon2);
        }
        else if(mode == 2)
        {
        	ImageIcon icon1=new ImageIcon("src/red.gif");
            setIcon(icon1);

            ImageIcon icon2=new ImageIcon("src/lightblue.gif");
            setRolloverIcon(icon2);
        }

        this.setHorizontalTextPosition(CENTER);
        this.setVerticalTextPosition(CENTER);

        setBorderPainted(false);

        setFocusPainted(false);

        setContentAreaFilled(false);

        setFocusable(true);

        setMargin(new Insets(0, 0, 0, 0));

        setText(buttonText);
       
        /*Font font=new Font("Arial",Font.BOLD,18);   
        setFont(font); */

        setForeground(Color.white);
    }
}