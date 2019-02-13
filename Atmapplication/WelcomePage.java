package com.Atmapplication;

import java.awt.Button;
import java.awt.Frame;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

//created class for Welcomepage
public class WelcomePage {

	// variables
	double Debit;
	double pinn;
	String custname;

	// getting debitcard no pin no from login page
	public WelcomePage(Object debitCardNo, Object pin) {
		Debit = (double) debitCardNo;
		pinn = (double) pin;

		// create an object of Frame class
		Frame f = new Frame(" ATM management system ");
		// size of frame
		f.setSize(800, 800);
		// display the frame
		f.setVisible(true);

		// windows closing
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		Label l1, l2;
		this.custname();
		// create an object of label class
		l1 = new Label("WELCOME");
		l1.setBounds(120, 100, 100, 30);
		l2 = new Label(this.custname);
		l2.setBounds(225, 100, 100, 30);
		// adding labels to the frame
		f.add(l1);
		f.add(l2);
		f.setSize(400, 400);
		f.setLayout(null);
		// display the frame
		f.setVisible(true);

		// create an object of button class
		Button b1 = new Button("Deposit");
		b1.setBounds(120, 170, 70, 30);
		// add the button to the frame
		f.add(b1);
		f.setSize(400, 400);
		f.setLayout(null);
		// display the frame
		f.setVisible(true);

		// create an object of button class
		Button b2 = new Button("Withdrawal");
		b2.setBounds(120, 220, 70, 30);
		// adding button to the frame
		f.add(b2);
		f.setSize(400, 400);
		f.setLayout(null);
		// display the frame
		f.setVisible(true);

		// create an object of button class
		Button b3 = new Button("Transfer");
		b3.setBounds(220, 170, 70, 30);
		// adding button to the frame
		f.add(b3);
		f.setSize(400, 400);
		f.setLayout(null);
		// display the frame
		f.setVisible(true);

		// create an object of button class
		Button b4 = new Button("Change Pin");
		b4.setBounds(220, 220, 70, 30);
		// adding button to the frame
		f.add(b4);
		f.setSize(400, 400);
		f.setLayout(null);
		// display the frame
		f.setVisible(true);

		// create an object of button class
		Button b5 = new Button("Logout");
		b5.setBounds(160, 280, 70, 30);
		// adding the button to the frame
		f.add(b5);
		f.setSize(400, 400);
		f.setLayout(null);
		// display the frame
		f.setVisible(true);

		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// background window close
				f.dispose();
				Deposit d = new Deposit(Debit, pinn);
			}
		});

		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// background will close
				f.dispose();
				Withdrawal w = new Withdrawal(Debit, pinn);
			}
		});

		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// background window close
				f.dispose();
				Transfer t = new Transfer(Debit, pinn);
			}
		});

		b4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// background window close
				f.dispose();
				ChangePin t = new ChangePin(Debit, pinn);
			}
		});

		b5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// window closing
				System.exit(0);
			}
		});

		l2.getName();
	}

	public String custname() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// getting connection from database
			Connection cn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr", "hr");
			java.sql.Statement st = cn.createStatement();
			// sql query
			String sql = "select * from Account where DebitcardNo ='" + Debit + "' and PinNo='" + pinn + "'";
			// executing query
			ResultSet r = st.executeQuery(sql);
			// checking database values correct or not
			if (r.next() == true) {
				// assigning the custname to variable
				this.custname = r.getString("custname");
			}
		} catch (Exception ee) {
			// exception catching
			System.out.println(ee.getMessage());
		}
		// returning custname
		return custname;
	}
}
