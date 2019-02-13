
package com.Atmapplication;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

//creating a LoginPage+
// created class name
public class LoginPage {

	// variables
	double debitCardNo;
	double pin;

	// created constructor
	LoginPage() {

		// create an object of Frame class
		Frame f = new Frame("ATM management system");
		// increase the size of frame
		f.setSize(2000, 2000);
		// display the frame
		f.setVisible(true);

		// windows closing
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		// create an object of label class
		Label lable1, lable2, lable3;
		lable1 = new Label("DebitCard No:");
		lable1.setBounds(50, 150, 100, 30);
		lable2 = new Label("Pin No:");
		lable2.setBounds(50, 200, 100, 30);
		lable3 = new Label(" WELCOME TO ATM");
		lable3.setBounds(130, 80, 150, 30);
		// adding the labels as frame
		f.add(lable1);
		f.add(lable2);
		f.add(lable3);
		f.setSize(400, 400);
		f.setLayout(null);

		// Display the frame
		f.setVisible(true);

		// create an object of text field class
		TextField t1;
		t1 = new TextField();
		t1.setBounds(150, 150, 180, 20);
		// create an object of password field class
		JPasswordField t21 = new JPasswordField();
		t21.setBounds(150, 200, 180, 20);
		f.add(t1);
		f.add(t21);
		f.setSize(400, 400);
		f.setLayout(null);
		// display the frame
		f.setVisible(true);

		// create an object of button class
		Button b = new Button("Login");
		b.setBounds(150, 250, 70, 30);
		f.add(b);
		f.setSize(400, 400);
		f.setLayout(null);
		// display the frame
		f.setVisible(true);

		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// check if the condition is null or not
				if (t1.getText().length() == 0 || t21.getText().length() == 0) {
					JOptionPane.showMessageDialog(f, "Please enter debitCardNo and pinNo");
					// checking the number for length
				} else if (t1.getText().length() != 16 || (t21.getText().length() != 4))
					JOptionPane.showMessageDialog(f, "Please  debitCardNo 16 digits  and pin No 4 digits");

				else {
					// type conversion
					debitCardNo = Double.parseDouble(t1.getText());
					pin = Double.parseDouble(t21.getText());

					try {
						Class.forName("oracle.jdbc.driver.OracleDriver");
						// getconnection from database
						Connection cn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr",
								"hr");
						java.sql.Statement st = cn.createStatement();
						// sql query
						String sql = "select * from account where DebitcardNo ='" + debitCardNo + "' and PinNo='" + pin
								+ "'";
						// checking for database connection and executing the query
						ResultSet r = st.executeQuery(sql);
						// check user data correct or not
						if (r.next() == true) {
							JOptionPane.showMessageDialog(f, " You are successfully logged in.... ");
							// passing the values debitcard,pinno
							// values to welcome page
							WelcomePage w = new WelcomePage(debitCardNo, pin);
							// background window close
							f.dispose();
						}
					}
					// catching exception
					catch (Exception ee) {
						System.out.println(ee.getMessage());
					}
				} // else
			}// action performed
		});
	}

	public static void main(String[] args) {
		LoginPage lg = new LoginPage(); // object creation
	}
}
