package com.Atmapplication;

import java.awt.Button;
import java.sql.*;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.management.Query;
import javax.swing.JOptionPane;

public class Deposit {
	double Debit;
	double pinn;
	private int accno;
	private int amount;

	public Deposit(double a, double b) {
		Debit = a;
		pinn = b;
		Frame f = new Frame("ATM management system "); // create an object of Frame class
		f.setSize(800, 800); // increase the size of frame
		f.setVisible(true); // display the frame

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		Label l1, l2;
		l1 = new Label(" Enter amount: "); // create an object of label class
		l1.setBounds(120, 100, 100, 30);
		l2 = new Label(" Deposit ");
		l2.setBounds(200, 50, 100, 30);
		f.add(l1);
		f.add(l2);
		f.setSize(400, 400);
		f.setLayout(null);
		f.setVisible(true);

		TextField t;
		t = new TextField(); // create an object of text field class
		t.setBounds(220, 105, 150, 20);
		f.add(t);
		f.setSize(400, 400);
		f.setLayout(null);
		f.setVisible(true); // display the frame

		Button b1 = new Button(" confirm "); // create an object of button class
		b1.setBounds(170, 175, 70, 30);
		f.add(b1);
		f.setSize(400, 400);
		f.setLayout(null);
		f.setVisible(true); // display the frame
		Button b2 = new Button(" cancel "); // create an object of button class
		b2.setBounds(270, 175, 70, 30);
		f.add(b2);
		f.setSize(400, 400);
		f.setLayout(null);
		f.setVisible(true); // display the frame

		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				f.dispose();
				WelcomePage lp = new WelcomePage(Debit, pinn);
			}
		});
		b1.addActionListener(new ActionListener() {
			private Label t2;

			@SuppressWarnings("null")
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (t.getText().length() == 0) {
						JOptionPane.showMessageDialog(f, "Please enter amount");
					} else {
						try {
							amount = Integer.parseInt(t.getText());
							Class.forName("oracle.jdbc.driver.OracleDriver");
							Connection cn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr",
									"hr");
							String sql = "update account set accbal = accbal+ '" + amount + "'  where debitcardno ='"
									+ Debit + "'";
							PreparedStatement st = cn.prepareStatement(sql);
							int r = st.executeUpdate();
							if (r != 0) {
								JOptionPane.showMessageDialog(f, "Deposite successfully completed ");
								WelcomePage wp = new WelcomePage(Debit, pinn);
							}
						} catch (Exception ee) {
							JOptionPane.showMessageDialog(f, " not success ");
							System.out.println(ee.getMessage());
						}
					}
				} catch (Exception ee) {
					System.out.println(ee.getMessage());
				}
			}
		});
	}

}
