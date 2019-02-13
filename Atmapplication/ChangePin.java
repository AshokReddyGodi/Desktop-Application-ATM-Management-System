package com.Atmapplication;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

//creating class for Changepin
public class ChangePin {
	// declaring vriables
	double Debit;
	double pinn;
	int oldpin, newpin, newpin1;

	public ChangePin(double a, double b) {
		Debit = a;
		pinn = b;

		// create an object of Frame class
		Frame f = new Frame(" ATM management system ");
		// increase the size of frame
		f.setSize(800, 800);
		// display the frame
		f.setVisible(true);

		// window closing
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		// create an object of label class
		Label l1, l2, l3, l4;
		l1 = new Label("Old Pin:");
		l1.setBounds(50, 150, 100, 30);
		l2 = new Label("New Pin:");
		l2.setBounds(50, 200, 100, 30);
		l3 = new Label("Re enter:");
		l3.setBounds(50, 250, 100, 30);
		l4 = new Label(" ChangePin ");
		l4.setBounds(150, 80, 100, 30);
		// adding labels to the frame
		f.add(l1);
		f.add(l2);
		f.add(l3);
		f.add(l4);
		f.setSize(400, 400);
		f.setLayout(null);
		// Display the frame
		f.setVisible(true);

		TextField t1, t2, t3;

		// create an object of text field class
		JPasswordField t11 = new JPasswordField();
		t11.setBounds(150, 150, 100, 30);
		JPasswordField t21 = new JPasswordField();
		t21.setBounds(150, 200, 100, 30);
		JPasswordField t31 = new JPasswordField();
		t31.setBounds(150, 250, 100, 30);
		f.add(t11);
		f.add(t21);
		f.add(t31);
		f.setSize(400, 400);
		f.setLayout(null);
		// display the frame
		f.setVisible(true);

		// create an object of button class
		Button b1 = new Button(" confirm ");
		b1.setBounds(110, 300, 70, 30);
		// adding button to the frame
		f.add(b1);
		f.setSize(400, 400);
		f.setLayout(null);
		// display the frame
		f.setVisible(true);

		// create an object of button class
		Button b2 = new Button(" cancel ");
		b2.setBounds(210, 300, 70, 30);
		// adding button to the frame
		f.add(b2);
		f.setSize(400, 400);
		f.setLayout(null);
		// display the frame
		f.setVisible(true);

		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// background frame closing
				f.dispose();
				WelcomePage lp = new WelcomePage(Debit, pinn);
			}
		});

		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// type conversion
				oldpin = Integer.parseInt(t11.getText());
				newpin = Integer.parseInt(t21.getText());
				newpin1 = Integer.parseInt(t31.getText());

				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					// getting connection from database
					Connection cn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr", "hr");
					java.sql.Statement st = cn.createStatement();
					String sql = "select * from Account where debitcardno ='" + Debit + "' and PinNo='" + oldpin + "'";
					// executing sql query
					ResultSet r = st.executeQuery(sql);
					// checking the values to the database correct or not
					if (r.next() == true) {

						String sql1 = "update account set pinno =  '" + newpin + "'  where debitcardno ='" + Debit
								+ "'";
						PreparedStatement st1 = cn.prepareStatement(sql1);
						int r1 = st1.executeUpdate();
						if (r1 != 0) {
							JOptionPane.showMessageDialog(f, "pin change successfully completed ");
							WelcomePage wp = new WelcomePage(Debit, pinn);
						}
					} else {
						JOptionPane.showMessageDialog(f, " Invalid username or password");
					}
					WelcomePage lp = new WelcomePage(Debit, pinn);
				}
				// exception catching
				catch (Exception ee) {
					JOptionPane.showMessageDialog(f, " not success ");
					System.out.println(ee.getMessage());
				}
			}
		});
	}
}
