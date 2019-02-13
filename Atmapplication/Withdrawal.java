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

//creating class for Withdrawal
public class Withdrawal {

	// variabls
	double Debit;
	double pinn;
	private int accno;
	private int amount;
	double check_amount;

	public Withdrawal(double a, double b) {
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

		Label l1, l2, l3;
		this.check_bal();
		// create an object of label class
		l1 = new Label(" Enter amount: ");
		l1.setBounds(110, 145, 100, 30);
		l2 = new Label(" Withdrawal ");
		l2.setBounds(200, 50, 100, 30);
		l3 = new Label(String.valueOf(check_amount));
		l3.setBounds(220, 100, 100, 30);
		Label l4 = new Label("Account balance: ");
		l4.setBounds(105, 100, 95, 30);
		f.add(l4);
		f.setSize(400, 400);
		f.setLayout(null);
		f.setVisible(true);
		f.add(l1);
		f.add(l2);
		f.add(l3);
		f.add(l4);
		f.setSize(400, 400);
		f.setLayout(null);
		f.setVisible(true);

		TextField t1;
		// create an object of text field class
		t1 = new TextField();
		t1.setBounds(210, 150, 150, 20);
		// adding textfield to the frame
		f.add(t1);
		f.setSize(400, 400);
		f.setLayout(null);
		// display the frame
		f.setVisible(true);

		// create an object of button class
		Button b1 = new Button(" confirm ");
		b1.setBounds(170, 200, 70, 30);
		// adding button to the frame
		f.add(b1);
		f.setSize(400, 400);
		f.setLayout(null);
		// display the frame
		f.setVisible(true);

		// create an object of button class
		Button b2 = new Button(" cancel ");
		b2.setBounds(270, 200, 70, 30);
		// adding button to the frame
		f.add(b2);
		f.setSize(400, 400);
		f.setLayout(null);
		// display the frame
		f.setVisible(true);

		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// backgound frame will close
				f.dispose();
				// getting the values from Welcomepage
				WelcomePage lp = new WelcomePage(Debit, pinn);
			}
		});

		b1.addActionListener(new ActionListener() {
			@SuppressWarnings("null")
			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					if (t1.getText().length() == 0) {
						JOptionPane.showMessageDialog(f, "Please enter amount");
					}
					// type conversion
					amount = Integer.parseInt(t1.getText());

					// checking the condition for amount
					if (check_amount < amount) {
						JOptionPane.showMessageDialog(f, "You have insufficient balance");
					} else {
						try {
							Class.forName("oracle.jdbc.driver.OracleDriver");
							// getting connection from database
							Connection cn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr",
									"hr");
							// update sql query
							String sql = "update account set accbal = accbal- '" + amount + "'  where debitcardno ='"
									+ Debit + "'";

							PreparedStatement st = cn.prepareStatement(sql);
							// executing update query
							int r = st.executeUpdate();
							if (r != 0) {
								JOptionPane.showMessageDialog(f, "withdrawal successful ");
								WelcomePage wp = new WelcomePage(Debit, pinn);
							}
						} catch (Exception ee) {
							JOptionPane.showMessageDialog(f, " withdrawal not success ");
							System.out.println(ee.getMessage());
						}
					}
				}
				// Exception catching
				catch (Exception ee) {
					System.out.println(e);
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
			// sql query
			String sql = "select * from Account where DebitcardNo ='" + Debit + "' and PinNo='" + pinn + "'";
			// executing sql query
			ResultSet r = st.executeQuery(sql);
			// checking the values from database correct or not
			if (r.next() == true) {
				this.check_amount = r.getDouble("accbal");
			}
		} catch (Exception ee) {
			System.out.println(ee.getMessage());
		}
		// return amount
		return check_amount;
	}
}
