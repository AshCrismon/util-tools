package pers.ash.util.multithread;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Timer extends JFrame implements Runnable{

	private static final long serialVersionUID = 7367982049776816681L;
	
	private String name;
	private static final String DEFAULT_NAME = "anonymous timer";
	private int hour = 0;
	private int minute = 0;
	private int second = 0;
	private int ssecond = 0;
	
	private JPanel p = new JPanel();
	private JTextField hourText = new JTextField("00");
	private JTextField minuteText = new JTextField("00");
	private JTextField secondText = new JTextField("00");
	private JTextField ssecondText = new JTextField("00");
	private JLabel colon1 = new JLabel(":");
	private JLabel colon2 = new JLabel(":");
	
	public Timer(){
		this(DEFAULT_NAME);
	}
	
	public Timer(String name){
		setTitle("计时器");
		setSize(400, 115);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p.setSize(280, 50);
		p.setLayout(new FlowLayout());
		p.setBorder(BorderFactory.createTitledBorder(name));
		//设置宽高
		hourText.setPreferredSize(new Dimension(80, 50));
		minuteText.setPreferredSize(new Dimension(80, 50));
		secondText.setPreferredSize(new Dimension(80, 50));
		ssecondText.setPreferredSize(new Dimension(40, 20));
		//设置只读
		hourText.setEditable(false);
		minuteText.setEditable(false);
		secondText.setEditable(false);
		ssecondText.setEditable(false);
		//设置字体
		Font font = new Font("宋体", Font.BOLD, 40);
		Font font2 = new Font("宋体", Font.PLAIN, 20);
		hourText.setFont(font);
		minuteText.setFont(font);
		secondText.setFont(font);
		ssecondText.setFont(font2);
		//设置冒号
		colon1.setFont(font);
		colon2.setFont(font);
		
		p.add(hourText);
		p.add(colon1);
		p.add(minuteText);
		p.add(colon2);
		p.add(secondText);
		p.add(ssecondText, BorderLayout.SOUTH);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p, BorderLayout.CENTER);
		this.name = name;
	}
	
	public void run() {
		while(true){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (++ssecond == 100) {
				ssecond = 0;
				if (++second == 60) {
					second = 0;
					if (++minute == 60) {
						minute = 0;
						hour++;
					}
				}
			}
			refreshTime();
		}
	}

	private void refreshTime() {
		String h, m, s, ss;
		h = hour < 10 ? "0" + hour : "" + hour; 
		m = minute < 10 ? "0" + minute : "" + minute; 
		s = second < 10 ? "0" + second : "" + second;
		ss = ssecond < 10 ? "0" + ssecond : "" + ssecond;
		hourText.setText(h);
		minuteText.setText(m);
		secondText.setText(s);
		ssecondText.setText(ss);
	}

	public String getName() {
		return name;
	}

}
