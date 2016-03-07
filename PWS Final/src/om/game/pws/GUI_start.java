package om.game.pws;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.Checkbox;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class GUI_start {

	public static JFrame startframe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_start window = new GUI_start();
					window.startframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_start() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		startframe = new JFrame();
		startframe.getContentPane().setBackground(new Color(186, 85, 54));
		startframe.getContentPane().setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.setBounds(139, 207, 149, 48);
		btnStart.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnStart.setBackground(new Color(81, 179, 81));
		startframe.getContentPane().add(btnStart);
		
		JTextPane txtpnAsd = new JTextPane();
		txtpnAsd.setBackground(new Color(186, 85, 54));
		txtpnAsd.setFont(new Font("Bodoni MT Black", Font.PLAIN, 40));
		txtpnAsd.setEditable(false);
		txtpnAsd.setText("Ate O'Clock");
		txtpnAsd.setName("");
		txtpnAsd.setBounds(104, 6, 243, 41);
		startframe.getContentPane().add(txtpnAsd);
		
		TextField name = new TextField();
		name.setBounds(139, 155, 149, 23);
		startframe.getContentPane().add(name);
		
		JTextPane txtpnName = new JTextPane();
		txtpnName.setText("Name:");
		txtpnName.setName("");
		txtpnName.setFont(new Font("Bodoni MT Black", Font.PLAIN, 20));
		txtpnName.setEditable(false);
		txtpnName.setBackground(new Color(186, 85, 54));
		txtpnName.setBounds(175, 108, 70, 23);
		startframe.getContentPane().add(txtpnName);
		Checkbox checkbox = new Checkbox("\"Secret\" mode");
		checkbox.setBounds(306, 217, 101, 23);
		startframe.getContentPane().add(checkbox);
		startframe.setBounds(100, 100, 450, 300);
		
		
		startframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		checkbox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					Game.secretMode = true;
				};
			}
		});


		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.name = name.getText(); 
				new Game().start();
			}
		});
		
	}
}
