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

//creating class for tansfer
public class Transfer {

	// declaring the variables
	double Debit;
	double pinn;
	private double accno1;
	private double amount;
	private double custid;
	double check_amount1;

	public Transfer(double a, double b) {
		Debit = a;
		pinn = b;

		// create an object of Frame class
		Frame f = new Frame("ATM management system ");
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

		this.check_bal();

		Label l1, l2, l3, l4, l5, l6;
		// create an object of label class
		l1 = new Label("Enter amount:");
		l1.setBounds(50, 150, 100, 30);
		l2 = new Label("Enter acc No:");
		l2.setBounds(50, 200, 100, 30);
		l3 = new Label("Customer Id:");
		l3.setBounds(50, 250, 100, 30);
		l4 = new Label(" Transfer ");
		l4.setBounds(160, 50, 100, 30);
		l5 = new Label(String.valueOf(check_amount1));
		l5.setBounds(160, 100, 100, 30);
		Label l61 = new Label("Account balance: ");
		l61.setBounds(55, 100, 95, 30);

		// adding labels to the frame
		f.add(l61);
		f.setSize(400, 400);
		f.setLayout(null);
		// display the frame
		f.setVisible(true);
		f.add(l1);
		f.add(l2);
		f.add(l3);
		f.add(l4);
		f.add(l5);
		f.setSize(400, 400);
		f.setLayout(null);
		// Display the frame
		f.setVisible(true);

		TextField t1, t2, t3, t4;
		// create an object of text field class
		t1 = new TextField();
		t1.setBounds(150, 150, 100, 30);
		t2 = new TextField();
		t2.setBounds(150, 200, 100, 30);
		t3 = new TextField();
		t3.setBounds(150, 250, 100, 30);
		f.add(t1);
		f.add(t2);
		f.add(t3);
		f.setSize(400, 400);
		f.setLayout(null);
		// display the frame
		f.setVisible(true);

		// create an object of button class
		Button b1 = new Button(" confirm ");

		b1.setBounds(110, 300, 70, 30);
		f.add(b1);
		f.setSize(400, 400);
		f.setLayout(null);
		// display the frame
		f.setVisible(true);

		// create an object of button class
		Button b2 = new Button(" cancel ");
		b2.setBounds(210, 300, 70, 30);
		// adding the button to the frame
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
			@SuppressWarnings("null")
			@Override
			public void actionPerformed(ActionEvent e) {
				if (t1.getText().length() == 0 || t2.getText().length() == 0 || t3.getText().length() == 0) {
					JOptionPane.showMessageDialog(f, "Please enter numbers");
				}
				// type conversion
				amount = Double.parseDouble(t1.getText());
				accno1 = Double.parseDouble(t2.getText());
				custid = Double.parseDouble(t3.getText());

				if (check_amount1 <= amount) {
					JOptionPane.showMessageDialog(f, "You have insufficient balance");
				} else {
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver");
						Connection cn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr",
								"hr");
						String sql1 = "update account set accbal = accbal+ '" + amount + "'  where accno ='" + accno1
								+ "'";
						String sql2 = "update account set accbal = accbal- '" + amount + "'  where debitcardno ='"
								+ Debit + "'";
						PreparedStatement st1 = cn.prepareStatement(sql1);
						PreparedStatement st2 = cn.prepareStatement(sql2);
						int r1 = st1.executeUpdate();
						int r2 = st2.executeUpdate();
						if (r1 != 0 && r2 != 0) {
							JOptionPane.showMessageDialog(f, "Transfer successful ");
							WelcomePage wp = new WelcomePage(Debit, pinn);
						} else {
							JOptionPane.showMessageDialog(f, "Transfer not successful ");
						}
					} catch (Exception ee) {
						JOptionPane.showMessageDialog(f, " Transfer  not success ");
						System.out.println(ee.getMessage());
					}
				}
			}
		});

	}

	public double check_bal() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// getting connection from database
			Connection cn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr", "hr");
			java.sql.Statement st = cn.createStatement();
			String sql = "select * from Account where DebitcardNo ='" + Debit + "' and PinNo='" + pinn + "'";
			// executing sql query
			ResultSet r = st.executeQuery(sql);
			// checking values to the database correct or not
			if (r.next() == true) {
				this.check_amount1 = r.getDouble("accbal");
			}
		} catch (Exception ee) {
			System.out.println(ee.getMessage());
		}
		return check_amount1;
	}
}
