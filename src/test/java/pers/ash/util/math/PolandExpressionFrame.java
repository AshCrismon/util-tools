package pers.ash.util.math;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import pers.ash.util.exception.InvalidOperatorException;

public class PolandExpressionFrame extends JFrame{
	
	private static final long serialVersionUID = -1364893631763334187L;
	
	private JPanel p = new JPanel();
	private JPanel p2 = new JPanel();
	private JTextArea remark = new JTextArea();
	private JTextField input = new JTextField(45);
	private JTextField output = new JTextField(45);
	private JLabel inputLabel = new JLabel("数学表达式");
	private JLabel outputLabel = new JLabel("逆波兰表达式");
	private JButton confirmBtn = new JButton("转换");
	
	public PolandExpressionFrame(){
		initComponents();
		addEventListener();
	}
	
	private void initComponents(){
		
		Font font = new Font("宋体", Font.PLAIN, 13);
		
		p.setSize(280, 30);
		p.setFont(font);
		p.setLayout(new FlowLayout());
		p.setBorder(BorderFactory.createTitledBorder("注意事项"));
		
		p2.setSize(280, 80);
		p2.setFont(font);
		p2.setLayout(new FlowLayout());
		p2.setBorder(BorderFactory.createTitledBorder("输入要转换的数学表达式"));
		
		Font font2 = new Font("宋体", Font.BOLD, 15);
		remark.setEditable(false);
		remark.setText("请输入合法的数学表达式，注意数字之间要有空格");
		remark.append("\n\r例如：\n\r表达式 9 + ( 3 - 1 ) * 3 + 10 / 2  的逆波兰式为：\n\r " + "9 3 1 - 3 * + 10 2 / +");
		remark.append("\n\r表达式 a + ( b - c ) * e + f / g 的逆波兰式为： \n\r" + "a b c - e * + f g / +");
		remark.setBackground(new java.awt.Color(244, 241, 241));
		remark.setEditable(false);
		remark.setColumns(55);
		remark.setRows(4);
		remark.setLineWrap(true);
		remark.setFont(font2);
		
		inputLabel.setFont(font);
		input.setEditable(true);
		outputLabel.setFont(font);
		output.setEditable(false);
	
		p.add(remark);
		p2.add(inputLabel);
		p2.add(input);
		p2.add(outputLabel);
		p2.add(output);
		p2.add(confirmBtn);
		
		getContentPane().setLayout(new GridLayout(2, 1));
		getContentPane().add(p2);
		getContentPane().add(p);
		
		setTitle("逆波兰转换式");
		setSize(550, 400);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	private void addEventListener(){
		confirmBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String expression = input.getText().trim();
				try {
					String nifixExpr = ExpressionUtils.toNifixExpressionString(expression);
					output.setText(nifixExpr);
				} catch (InvalidOperatorException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "错误提示", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
}
