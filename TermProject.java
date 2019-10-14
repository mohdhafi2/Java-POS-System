package TermProject;

import java.awt.*;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

public class TermProject implements ActionListener{
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu = new JMenu("Login");
	private JMenuItem menuItem1 = new JMenuItem("Open");
	private JMenuItem menuItem2 = new JMenuItem("Log In");
	private static String username;
	private static String password;
	private String pos;
	private static Connection dbConn;
	private JLabel idLabel = new JLabel("Name");
	private JLabel pwdLabel = new JLabel("Password");
	private JTextField idInput = new JTextField();
	private JPasswordField pwdInput = new JPasswordField();
	private JButton loginButton = new JButton("Log In");
	private String empLoggedIn;
	
	private JLabel title = new JLabel("Restaurant POS System");
	private JPanel titlePanel = new JPanel();
	
	private JLabel tableConditionLabel = new JLabel("Table Condition");
	private JPanel tableConditionPanel = new JPanel(new GridLayout(4,5,10,10));
	private JButton[] table = new JButton[20];
	private String [] tableLabel = new String[] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
	private int j;
	
	private JLabel orderLabel = new JLabel("Order");
	private JPanel orderPanel = new JPanel();
	private JTextArea orderText = new JTextArea();
	private JLabel custName = new JLabel("Name");
	private JTextArea custNameText = new JTextArea();
	private JLabel tableListLabel = new JLabel("Table");
	String [] tableListStr = new String[] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
	private JComboBox<String> tableList= new JComboBox<String>(tableListStr);
	private JButton orderButton = new JButton("Order");
	private JButton cancelButton = new JButton("Cancel");
	private JButton payButton = new JButton("Pay");
	String displayTextArray[] = new String[20];				//Array of orderText's display
	private String displayText = "";
	private int menuNum;
	int [] totalPriceArray = new int[20];		//Array of total price
	private int totalPrice = 0;
	private int orderingTableNumber;
	
	private JLabel menuLabel = new JLabel("Menu");
	private JPanel menuPanel = new JPanel(new GridLayout(10,2,10,10));
	private JButton [] addButton = new JButton[20];
	private String [] buttonLabel;
	private int i;
	
	private JLabel registerLabel = new JLabel("Register/Info");
	private JPanel registerPanel = new JPanel();
	private JTabbedPane tabbedPane = new JTabbedPane();
	
	private JPanel tab1 = new JPanel();
	private JLabel tab1Label = new JLabel("Name");
	private JTextField tab1Text = new JTextField();
	private JButton registerButton = new JButton("Register");
	private JButton custInfoButton = new JButton("Info");
	private JTextArea tab1TextField = new JTextArea();
	private JLabel custRegLabel = new JLabel("Name");
	private JTextField custRegInput = new JTextField();
	private JLabel custBirthday = new JLabel("Birthday(4 digit)");
	private JTextField custBirthdayInput = new JTextField();
	private JLabel custContact = new JLabel("Contact");
	private JTextField custContactInput = new JTextField();
	private JLabel custGradeLabel = new JLabel("Grade");
	String [] custListStr = new String[] {"Gold","Silver","Bronze","Normal"};
	private JComboBox<String> custGrade= new JComboBox<String>(custListStr);
	private JButton custRegButton = new JButton("Register");
	private JButton custRegCancel = new JButton("Cancel");
	
	private JPanel tab2 = new JPanel();
	private JLabel tab2Label = new JLabel("Date");
	private String [] dateStringList;
	private int dateCount;
	private JComboBox<String> date = new JComboBox<String>();
	private JTextArea tab2Text = new JTextArea();
	
	private JPanel tab3 = new JPanel();
	private JLabel tab3Label = new JLabel("Name");
	private JTextField tab3Text = new JTextField();
	private JButton empRegButton = new JButton("Register");
	private JButton empInfoButton = new JButton("Info");
	private JTextArea tab3TextField = new JTextArea();
	private JLabel empReg = new JLabel("Name");
	private JTextField empRegText = new JTextField();
	private JLabel empPw = new JLabel("Password");
	private JTextField empPass = new JTextField();
	private JLabel posReg = new JLabel("Positions");
	String [] positionStr = new String[] {"Staff","Supervisor"};
	private JComboBox<Object> positionList = new JComboBox<Object>(positionStr);
	private JButton registerEmp = new JButton("Register");
	private JButton cancelEmp = new JButton("Cancel");
	
	private JPanel tab4 = new JPanel();
	private JLabel tab4Label = new JLabel("Menu");
	private JTextField tab4Text = new JTextField();
	private JButton menuRegisterButton = new JButton("Register");
	private JButton menuInfo_Button = new JButton("Info");
	private JTextArea tab4TextField = new JTextArea();
	private JLabel menuReg = new JLabel("Menu");
	private JTextField menuRegText = new JTextField();
	private JLabel priceReg = new JLabel("Price");
	private JTextField priceRegText = new JTextField();
	private JButton registerMenu = new JButton("Register");
	private JButton cancelMenu = new JButton("Cancel");
	
	public TermProject(){
		initialConnectDB();
		
		panel.setLayout(null);
		
		title.setFont(new Font("Serif", Font.PLAIN, 38));
		title.setBounds(130, 10, 615, 80);
		
		titlePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		titlePanel.setBounds(10, 10, 615, 80);
		
		tableConditionLabel.setBounds(20, 100, 100, 20);
		tableConditionPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		tableConditionPanel.setBounds(10, 120, 310, 270);
		for (j = 0; j < 20; j++){
			table[j] = new JButton(tableLabel[j]);
			table[j].setBackground(Color.WHITE);
			tableConditionPanel.add(table[j]);
		}
		
		orderPanel.setLayout(null);
		orderLabel.setBounds(330, 100, 100, 20);
		orderPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		orderPanel.setBounds(325, 120, 300, 270);
		orderText.setBounds(10, 10, 190, 250);
		orderText.setBorder(new LineBorder(Color.gray, 2));
		orderText.setEditable(false);
		custName.setBounds(220, 10, 100, 20);
		custNameText.setBounds(210, 30, 80, 25);
		custNameText.setBorder(new LineBorder(Color.gray, 2));
		tableListLabel.setBounds(220, 80, 100, 20);
		tableList.setBounds(210, 100, 80, 25);
		orderButton.setBounds(210, 150, 80, 30);
		orderButton.addActionListener(this);
		cancelButton.setBounds(210, 190, 80, 30);
		cancelButton.addActionListener(this);
		payButton.setBounds(210, 230, 80, 30);
		payButton.addActionListener(this);
		
		menuLabel.setBounds(20, 400, 100, 20);
		menuPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		menuPanel.setBounds(10, 420, 310, 360);
		for (i = 0; i < buttonLabel.length; i++){
			addButton[i] = new JButton(buttonLabel[i]);
			menuPanel.add(addButton[i]);
			addButton[i].addActionListener(this);
		}
		
		
		registerPanel.setLayout(null);
		registerLabel.setBounds(330, 400, 100, 20);
		registerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		registerPanel.setBounds(325, 420, 300, 360);
		tabbedPane.setBounds(0, 0, 300, 360);
		
		//Register panel each tab have different panel
		tab1.setLayout(null);
		tab1.setBounds(320, 440, 200, 300);
		tab1Label.setBounds(10, 10, 100, 20);
		tab1Text.setBounds(10, 30, 80, 30);
		registerButton.setBounds(100, 30, 110, 30);
		registerButton.addActionListener(this);
		custInfoButton.setBounds(220, 30, 60, 30);
		custInfoButton.addActionListener(this);
		tab1TextField.setBounds(10, 80, 275, 240);
		tab1TextField.setBorder((new LineBorder(Color.gray, 2)));
		tab1TextField.setEditable(false);
		
		tab2.setLayout(null);
		tab2.setBounds(320, 440, 200, 300);
		date.setBounds(50, 30, 120, 25);
		for(int i = 0; i<dateCount; i++){
			date.addItem(dateStringList[i]);
		}
		
		tab2Label.setBounds(10, 30, 100, 20);
		tab2Text.setBounds(10, 80, 275, 240);
		tab2Text.setBorder((new LineBorder(Color.gray, 2)));
		tab2Text.setEditable(false);
		
		tab3.setLayout(null);
		tab3.setBounds(320, 440, 200, 300);
		tab3Label.setBounds(10, 10, 100, 20);
		tab3Text.setBounds(10, 30, 80, 30);
		empRegButton.setBounds(100, 30, 110, 30);
		empRegButton.addActionListener(this);
		empInfoButton.setBounds(220, 30, 60, 30);
		empInfoButton.addActionListener(this);
		tab3TextField.setBounds(10, 80, 275, 240);
		tab3TextField.setBorder((new LineBorder(Color.gray, 2)));
		tab3TextField.setEditable(false);
		
		tab4.setLayout(null);
		tab4.setBounds(320, 440, 200, 300);
		tab4Label.setBounds(10, 10, 100, 20);
		tab4Text.setBounds(10, 30, 80, 30);
		menuRegisterButton.setBounds(100, 30, 110, 30);
		menuRegisterButton.addActionListener(this);
		menuInfo_Button.setBounds(220, 30, 60, 30);
		menuInfo_Button.addActionListener(this);
		tab4TextField.setBounds(10, 80, 275, 240);
		tab4TextField.setBorder((new LineBorder(Color.gray, 2)));
		tab4TextField.setEditable(false);
		
		//Add to panel =====================================================================================
		panel.add(title);
		panel.add(titlePanel);
		
		menuBar.add(menu);
		menu.add(menuItem1);
		menuItem1.addActionListener(this);
		menu.add(menuItem2);
		menuItem2.addActionListener(this);
		
		panel.add(tableConditionLabel);
		panel.add(tableConditionPanel);
		
		panel.add(orderLabel);
		panel.add(orderPanel);
		orderPanel.add(orderText);
		orderPanel.add(custName);
		orderPanel.add(custNameText);
		orderPanel.add(tableListLabel);
		orderPanel.add(tableList);
		tableList.addActionListener(this);
		orderPanel.add(orderButton);
		orderPanel.add(cancelButton);
		orderPanel.add(payButton);
		
		panel.add(menuLabel);
		panel.add(menuPanel);
		
		panel.add(registerLabel);
		panel.add(registerPanel);
		registerPanel.add(tabbedPane);
		tabbedPane.addTab("Customer", tab1);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		tabbedPane.addTab("Record", tab2);
		tabbedPane.addTab("Employee", tab3);
		tabbedPane.addTab("Menu", tab4);
		
		tab1.add(tab1Label);
		tab1.add(tab1Text);
		tab1.add(registerButton);
		tab1.add(custInfoButton);
		tab1.add(tab1TextField);
		
		tab2.add(tab2Label);
		tab2.add(tab2Text);
		tab2.add(date);
		date.addActionListener(this);
		
		tab3.add(tab3Label);
		tab3.add(tab3Text);
		tab3.add(empRegButton);
		tab3.add(empInfoButton);
		tab3.add(tab3TextField);
		
		tab4.add(tab4Label);
		tab4.add(tab4Text);
		tab4.add(menuRegisterButton);
		tab4.add(menuInfo_Button);
		tab4.add(tab4TextField);
		
		frame.setJMenuBar(menuBar);
		frame.add(panel);
		frame.setTitle("Restaurant POS system.");
		frame.setSize(650, 850);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == menuItem1) {
			JFileChooser fc = new JFileChooser();
			switch (fc.showOpenDialog(frame)){
			case JFileChooser.APPROVE_OPTION: break;
			}
		}
		else if(e.getSource() == menuItem2){
			employeeLogin();
		}
		else if(e.getSource() == loginButton){
			username = idInput.getText();
			password = new String(pwdInput.getPassword());
			empLoggedIn = idInput.getText();
			connectDB();
		}
		else if(e.getSource() == addButton[0]){
			try{
				menuNum = 1;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		} 
		else if(e.getSource() == addButton[1]){
			try{
				menuNum = 2;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == addButton[2]){
			try{
				menuNum = 3;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == addButton[3]){
			try{
				menuNum = 4;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == addButton[4]){
			try{
				menuNum = 5;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == addButton[5]){
			try{
				menuNum = 6;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == addButton[6]){
			try{
				menuNum = 7;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == addButton[7]){
			try{
				menuNum = 8;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == addButton[8]){
			try{
				menuNum = 9;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == addButton[9]){
			try{
				menuNum = 10;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == addButton[10]){
			try{
				menuNum = 11;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == addButton[11]){
			try{
				menuNum = 12;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == addButton[12]){
			try{
				menuNum = 13;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == addButton[13]){
			try{
				menuNum = 14;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == addButton[14]){
			try{
				menuNum = 15;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == addButton[15]){
			try{
				menuNum = 16;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == addButton[16]){
			try{
				menuNum = 17;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == addButton[17]){
			try{
				menuNum = 18;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == addButton[18]){
			try{
				menuNum = 19;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == addButton[19]){
			try{
				menuNum = 20;
				buttonAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == tableList){ 	//check box table 1-20
			orderText.setText(null);
			displayText = "";
			totalPrice = 0;
			String orderingTableStr = (String) tableList.getSelectedItem();
			orderingTableNumber = Integer.parseInt(orderingTableStr);
			orderText.setText(displayTextArray[orderingTableNumber -1]);
		}
		else if(e.getSource() == orderButton){
			orderButtonClick();
		}
		else if(e.getSource() == cancelButton){
			orderText.setText(null);
			displayText = "";
			totalPrice = 0;
			String orderingTableStr = (String) tableList.getSelectedItem();
			orderingTableNumber = Integer.parseInt(orderingTableStr);
			table[orderingTableNumber-1].setBackground(Color.white);
		}
		else if(e.getSource() == payButton){
			try{
				payButtonAct();
				JOptionPane.showMessageDialog(frame, "Order total = "+totalPriceArray[orderingTableNumber - 1]+ " paid successfully");
				custNameText.setText(null);
				orderText.setText(null);
				displayText = "";
				totalPrice = 0;
				String orderingTableStr = (String) tableList.getSelectedItem();
				orderingTableNumber = Integer.parseInt(orderingTableStr);
				table[orderingTableNumber-1].setBackground(Color.WHITE);
			}catch (SQLException se){
				se.printStackTrace();
			}
		}
		// 4th PANEL ACTION EVENT==============================================================================================
		else if(e.getSource() == registerButton){ 
			try{
				if(pos.equals("Staff")){
					JOptionPane.showMessageDialog(frame, "Log In as Supervisor To Use");
				}
				else{
					custRegButton();
				}
			} catch (Exception se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == custInfoButton){
			try{
				if(pos.equals("Staff")){
					JOptionPane.showMessageDialog(frame, "Log In as Supervisor To Use");
				}
				else{
					try{
						displayCustomer();
					} catch (SQLException se){
						JOptionPane.showMessageDialog(frame, "Log In To Use");
						se.printStackTrace();
					}
				}
		} catch (Exception se){
			JOptionPane.showMessageDialog(frame, "Please log In to use");
			se.printStackTrace();
			}
		}
		else if(e.getSource() == custRegButton){
			try{
				regCustomerAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Some Input Wrong");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == custRegCancel){
			custRegInput.setText(null);
			custBirthdayInput.setText(null);
			custContactInput.setText(null);
			frame.setVisible(false);
		}
		
		else if(e.getSource() == date){
			try{
				if(pos.equals("Staff")){
					JOptionPane.showMessageDialog(frame, "Log In as Supervisor To Use");
				}
				else{
					try{
						dateRecord();
					} catch (SQLException se){
						se.printStackTrace();
					}
				}
			}catch (Exception se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == menuRegisterButton){
			if(pos.equals("Staff")){
				JOptionPane.showMessageDialog(frame, "Log In as Supervisor To Use");
			}
			else{
				menuRegister();
			}
		}
		else if(e.getSource() == menuInfo_Button){
			if(pos.equals("Staff")){
				JOptionPane.showMessageDialog(frame, "Log In as Supervisor To Use");
			}
			else{
				try{
					displayMenu();
				} catch (SQLException se){
					JOptionPane.showMessageDialog(frame, "Log In To Use");
					se.printStackTrace();
				}
			}
		}
		else if(e.getSource() == registerMenu){
			try{
				registerMenuAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Some Input Wrong");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == cancelMenu){
			menuRegText.setText(null);
			priceRegText.setText(null);
			frame.setVisible(false);
		}
		else if(e.getSource() == empRegButton){
			try{
				if(pos.equals("Staff")){
					JOptionPane.showMessageDialog(frame, "Log In as Supervisor To Use");
				}
				else{
					empRegister();
				}
			}
			catch (Exception se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == empInfoButton){
			try{
				if(pos.equals("Staff")){
					JOptionPane.showMessageDialog(frame, "Log In as Supervisor To Use");
				}
				else{
					try{
						displayEmployee();
					} catch (SQLException se){
						JOptionPane.showMessageDialog(frame, "Log In To Use");
						se.printStackTrace();
					}
				}
			}
			catch (Exception se){
				JOptionPane.showMessageDialog(frame, "Please log In to use");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == registerEmp){
			try{
				registerEmpAct();
			} catch (SQLException se){
				JOptionPane.showMessageDialog(frame, "Some Input Wrong");
				se.printStackTrace();
			}
		}
		else if(e.getSource() == cancelEmp){
			empRegText.setText(null);
			frame.setVisible(false);
		}
	}
	
	//=======================================================================================END OF ACTION EVENT
	
	private void employeeLogin(){
		frame = new JFrame();
		panel = new JPanel();
		panel.setLayout(null);
	
		idLabel.setBounds(20, 10, 60, 30);
		idInput.setBounds(100, 10, 80, 30);
		pwdLabel.setBounds(20, 50, 60, 30);
		pwdInput.setBounds(100, 50, 80, 30);
		loginButton.setBounds(200, 30, 80, 35);
		
		loginButton.addActionListener(this);
		
		panel.add(idLabel);
		panel.add(pwdLabel);
		panel.add(idInput);
		panel.add(pwdInput);
		panel.add(loginButton);
		
		frame.add(panel);
		
		frame.setTitle("Employee Login");
		frame.setSize(320, 140);
		frame.setVisible(true);
	}
	
	private void buttonAct() throws SQLException{									//Method upon clicking menu item
		String sqlStr = "select menuname, price from menu where no = " + menuNum;
		PreparedStatement stmt = dbConn.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery(); 		//Execute the sqlStr in SQL
		rs.next();
		
		totalPrice += rs.getInt("price");
		
		displayText += rs.getString("menuname") + '\t' + rs.getString("price");
		displayText += "\n";
		
		orderText.setText(displayText);
		
		stmt.close();
		rs.close();
	}
	
	private void orderButtonClick(){
		
		String orderingTableStr = (String) tableList.getSelectedItem();
		orderingTableNumber = Integer.parseInt(orderingTableStr);		//convert string to int
		table[orderingTableNumber-1].setBackground(Color.YELLOW);
		
		displayText += "\n";
		displayText += "-------------------------------------";
		displayText += "\n";
		displayText += "Total Price" + '\t' + totalPrice;
		
		orderText.setText(displayText);
		displayTextArray[orderingTableNumber -1] = displayText;			//Assign table's menu to specific array number
		totalPriceArray[orderingTableNumber - 1] = totalPrice;			//Assign total price for each table in specific array
	}
	
	private void payButtonAct() throws SQLException{
		String orderingTableStr = (String) tableList.getSelectedItem();
		orderingTableNumber = Integer.parseInt(orderingTableStr);			//Convert string to int
		
		String sqlStr = "select name,grade from customer where name = '"+ custNameText.getText() +"'";
		PreparedStatement stmt = dbConn.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		
		if(!rs.next()){
			totalPriceArray[orderingTableNumber - 1] *= 1;
		}
		
		else{
			String custGrade = rs.getString("GRADE");
			String discountVal;
			if(custGrade.equals("Gold")){
				discountVal = "30%";
				totalPriceArray[orderingTableNumber - 1] *= 0.7;
			}else if(custGrade.equals("Silver")){
				discountVal = "20%";
				totalPriceArray[orderingTableNumber - 1] *= 0.8;
			}else if(custGrade.equals("Bronze")){
				discountVal = "10%";
				totalPriceArray[orderingTableNumber - 1] *= 0.9;
			}else{
				discountVal = "no";
				totalPriceArray[orderingTableNumber - 1] *= 1;
			}
			JOptionPane.showMessageDialog(frame, "Customer " + custNameText.getText() + ", Grade: "+ custGrade+ ", " + discountVal + " discount applied!");
		}
			
		sqlStr = "insert into orderlist values (" + orderingTableNumber + ",'" + custNameText.getText()  + "'," + totalPriceArray[orderingTableNumber - 1] +",'"+ empLoggedIn +"')";
		stmt = dbConn.prepareStatement(sqlStr);
		rs = stmt.executeQuery();
		
		LocalDate LocalDate = java.time.LocalDate.now();
		sqlStr = "select totalsale from record where tdate = '" + DateTimeFormatter.ofPattern("yyy/MM/dd").format(LocalDate) + "'";
		stmt = dbConn.prepareStatement(sqlStr);
		rs = stmt.executeQuery();
		rs.next();
		
		int totalRecord = rs.getInt("TOTALSALE");
		totalRecord += totalPriceArray[orderingTableNumber - 1];
		
		sqlStr = "update record set totalsale = " + totalRecord + " where tdate = '" + DateTimeFormatter.ofPattern("yyy/MM/dd").format(LocalDate)+ "'";
		stmt = dbConn.prepareStatement(sqlStr);
		rs = stmt.executeQuery();
		
		sqlStr = "commit";
		stmt = dbConn.prepareStatement(sqlStr);
		rs = stmt.executeQuery();
		
		stmt.close();
		rs.close();
	}
	
	private void displayCustomer() throws SQLException{
		String displayStr1 = "";
		
		String sqlStr = "select * from customer where name = '" + tab1Text.getText() + "'";
		PreparedStatement stmt = dbConn.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		
		if(!rs.next()){
			JOptionPane.showMessageDialog(frame, "No Customer Found");
			tab1Text.setText(null);
		}
		else{
			displayStr1 += "Customer Name: " + '\t' + rs.getString("NAME");
			displayStr1 += "\n";
			displayStr1 += "Birthday Date: " + '\t' + '\t'+ rs.getInt("BIRTHDAY");
			displayStr1 += "\n";
			displayStr1 += "Phone Number: " + '\t' + rs.getInt("PHONE");
			displayStr1 += "\n";
			displayStr1 += "Customer Grade: " + '\t' + rs.getString("GRADE");
			displayStr1 += "\n";
		}
		
		sqlStr = "select sum(totalprice) from orderlist where orderperson = '" + tab1Text.getText() + "'";
		stmt = dbConn.prepareStatement(sqlStr);
		rs = stmt.executeQuery();
		rs.next();
		displayStr1 += "Total Ordered: " + '\t' + '\t' + rs.getString("SUM(TOTALPRICE)");

		tab1TextField.setText(displayStr1);
		stmt.close();
		rs.close();
	}
	private void custRegButton(){
		frame = new JFrame();
		panel = new JPanel();
		panel.setLayout(null);
	
		custRegLabel.setBounds(40, 20, 100, 30);
		custRegInput.setBounds(140, 20, 100, 30);
		
		custBirthday.setBounds(40, 70, 100, 30);
		custBirthdayInput.setBounds(140, 70, 100, 30);
		
		custContact.setBounds(40, 120, 100, 30);
		custContactInput.setBounds(140, 120, 100, 30);
		
		custGradeLabel.setBounds(40, 170, 100, 30);
		custGrade.setBounds(140, 170, 100, 30);
		
		custRegButton.setBounds(20, 230, 100, 35);
		custRegCancel.setBounds(150, 230, 100, 35);
		
		custRegButton.addActionListener(this);
		custRegCancel.addActionListener(this);
		
		panel.add(custRegLabel);
		panel.add(custRegInput);
		panel.add(custBirthday);
		panel.add(custBirthdayInput);
		panel.add(custContact);
		panel.add(custContactInput);
		panel.add(custGradeLabel);
		panel.add(custGrade);
		panel.add(custRegButton);
		panel.add(custRegCancel);
		
		frame.add(panel);
		
		frame.setTitle("Customer Registration");
		frame.setSize(300, 330);
		frame.setVisible(true);
	}
	private void regCustomerAct() throws SQLException{
		String sqlStr = "select name from customer where name = '" + custRegInput.getText() + "'";
		PreparedStatement stmt = dbConn.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		
		if(!rs.next()){
			sqlStr = "insert into customer values ('" + custRegInput.getText() + "'," + Integer.parseInt(custBirthdayInput.getText()) + "," + Integer.parseInt(custContactInput.getText())+",'"+ custGrade.getSelectedItem() + "')";
			stmt = dbConn.prepareStatement(sqlStr);
			rs = stmt.executeQuery(); 
			
			sqlStr = "commit";
			stmt = dbConn.prepareStatement(sqlStr);
			rs = stmt.executeQuery();
			
			JOptionPane.showMessageDialog(frame, "Customer Registration Successful");
			frame.setVisible(false);
			custRegInput.setText(null);
			custBirthdayInput.setText(null);
			custContactInput.setText(null);
			stmt.close();
			rs.close();
		}
		else{
			JOptionPane.showMessageDialog(frame, "Customer Already Registered");
			custRegInput.setText(null);
			stmt.close();
			rs.close();
		}
	}
	
	private void dateRecord() throws SQLException{
		String dateRecordDisplay = "";
		
		String sqlStr = "select totalsale from record where tdate = '" + date.getSelectedItem() + "'";
		PreparedStatement stmt = dbConn.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		
		dateRecordDisplay += "Daily Total Sale: " + rs.getInt("TOTALSALE") + '\n';
		dateRecordDisplay += "--------------------------------" + '\n';
		
		sqlStr = "select sum(totalsale) from record";
		stmt = dbConn.prepareStatement(sqlStr);
		rs = stmt.executeQuery();
		rs.next();
		
		dateRecordDisplay += "Total Sale: " + '\t' + rs.getInt("SUM(TOTALSALE)") + '\n';
			
		tab2Text.setText(dateRecordDisplay);
		
		stmt.close();
		rs.close();
	}
	
	private void displayEmployee() throws SQLException{
		String displayStr3 = "";
		
		String sqlStr = "select name,pos from employee where name = '" + tab3Text.getText() + "'";
		PreparedStatement stmt = dbConn.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		if(!rs.next()){
			JOptionPane.showMessageDialog(frame, "No Employee Found");
			tab3Text.setText(null);
		}
		else{
			displayStr3 += "Name: " + '\t' + rs.getString("NAME");
			displayStr3 += "\n";
			displayStr3 += "Position: " + '\t' + rs.getString("POS");
			displayStr3 += "\n";
			
			sqlStr = "select sum(totalprice) from orderlist where empincharge = '" + tab3Text.getText() + "'";
			stmt = dbConn.prepareStatement(sqlStr);
			rs = stmt.executeQuery();
			rs.next();
			displayStr3 += "Total Record: " + '\t' + rs.getString("SUM(TOTALPRICE)");
		}
		tab3TextField.setText(displayStr3);
		stmt.close();
		rs.close();
	}
	private void empRegister(){
		frame = new JFrame();
		panel = new JPanel();
		panel.setLayout(null);
		
		empReg.setBounds(40, 20, 100, 30);
		empRegText.setBounds(140, 20, 100, 30);
		
		empPw.setBounds(40, 70, 100, 30);
		empPass.setBounds(140, 70, 100, 30);
		
		posReg.setBounds(40, 120, 100, 30);
		positionList.setBounds(140, 120, 100, 30);
		
		registerEmp.setBounds(20, 180, 100, 35);
		cancelEmp.setBounds(150, 180, 100, 35);
		
		registerEmp.addActionListener(this);
		cancelEmp.addActionListener(this);
		
		panel.add(empReg);
		panel.add(empRegText);
		panel.add(empPw);
		panel.add(empPass);
		panel.add(posReg);
		panel.add(positionList);
		panel.add(registerEmp);
		panel.add(cancelEmp);
		
		frame.add(panel);
		
		frame.setTitle("New Employee Registration");
		frame.setSize(300, 280);
		frame.setVisible(true);
	}
	private void registerEmpAct() throws SQLException{
		String sqlStr = "select name from employee where name = '" + empRegText.getText() + "'";
		PreparedStatement stmt = dbConn.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		
		if(!rs.next()){
			sqlStr = "insert into employee values ('" + empRegText.getText() + "'," + Integer.parseInt(empPass.getText()) + ",'" + positionList.getSelectedItem()+"')";
			stmt = dbConn.prepareStatement(sqlStr);
			rs = stmt.executeQuery(); 
			
			sqlStr = "commit";
			stmt = dbConn.prepareStatement(sqlStr);
			rs = stmt.executeQuery();
			
			JOptionPane.showMessageDialog(frame, "Employee Registration Successful");
			frame.setVisible(false);
			empRegText.setText(null);
			empPass.setText(null);
			stmt.close();
			rs.close();
		}
		else{
			JOptionPane.showMessageDialog(frame, "Employee already registered");
			empRegText.setText(null);
			empPass.setText(null);
			stmt.close();
			rs.close();
		}
	}
	
	private void displayMenu() throws SQLException{
		String displayStr4 = "";
		
		String sqlStr = "select * from menu where menuname = '" + tab4Text.getText() + "'";
		PreparedStatement stmt = dbConn.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		if(!rs.next()){
			JOptionPane.showMessageDialog(frame, "No Menu Found");
			tab1Text.setText(null);
			stmt.close();
			rs.close();
		}
		else{
			displayStr4 += "Menu: " + '\t' + rs.getString("MENUNAME");
			displayStr4 += "\n";
			displayStr4 += "Price: " + '\t' + rs.getInt("PRICE");
			
			tab4TextField.setText(displayStr4);
			stmt.close();
			rs.close();
		}
	}
	private void menuRegister(){
		frame = new JFrame();
		panel = new JPanel();
		panel.setLayout(null);
		
		menuReg.setBounds(40, 20, 100, 30);
		menuRegText.setBounds(140, 20, 100, 30);
		
		priceReg.setBounds(40, 70, 100, 30);
		priceRegText.setBounds(140, 70, 100, 30);
		
		registerMenu.setBounds(20, 120, 100, 35);
		cancelMenu.setBounds(150, 120, 100, 35);
		
		registerMenu.addActionListener(this);
		cancelMenu.addActionListener(this);
		
		panel.add(menuReg);
		panel.add(menuRegText);
		panel.add(priceReg);
		panel.add(priceRegText);
		panel.add(registerMenu);
		panel.add(cancelMenu);
		
		frame.add(panel);
		
		frame.setTitle("Menu Registration");
		frame.setSize(300, 220);
		frame.setVisible(true);
	}
	private void registerMenuAct() throws SQLException{
		String sqlStr = "select menuname from menu where menuname = '" + menuRegText.getText() + "'";
		PreparedStatement stmt = dbConn.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		
		if(!rs.next()){
			sqlStr = "select count(menuname) as total from menu";
			stmt = dbConn.prepareStatement(sqlStr);
			rs = stmt.executeQuery(); 
			rs.next();
			int total = rs.getInt("TOTAL");
			if(total <20){
				sqlStr = "insert into menu values ("+ (total+1) + ",'" + menuRegText.getText() + "'," + Integer.parseInt(priceRegText.getText()) + ")";
				stmt = dbConn.prepareStatement(sqlStr);
				rs = stmt.executeQuery(); 
				
				sqlStr = "commit";
				stmt = dbConn.prepareStatement(sqlStr);
				rs = stmt.executeQuery();
				
				JOptionPane.showMessageDialog(frame, "Menu Added Successfully");
				frame.setVisible(false);
				menuRegText.setText(null);
				priceRegText.setText(null);
			}
			else{
				JOptionPane.showMessageDialog(frame, "Menu count exceeds 20. Cannot register more menu.");
				frame.setVisible(false);
				menuRegText.setText(null);
				priceRegText.setText(null);
			}
		}
		else{
			JOptionPane.showMessageDialog(frame, "Menu Already Registered");
			menuRegText.setText(null);
			priceRegText.setText(null);
		}
		stmt.close();
		rs.close();
	}
	
	private void connectDB(){
		try{
			//	JDBC Driver Loading
			Class.forName("oracle.jdbc.OracleDriver");
			dbConn = DriverManager.getConnection("jdbc:oracle:thin:" + "@localhost:1521:XE" , "system" , "1221");
			String sqlStr = "select name,pw,pos from employee where name = '" + username + "' and pw = '" + password + "'";
			PreparedStatement stmt = dbConn.prepareStatement(sqlStr);
			ResultSet rs = stmt.executeQuery(); 
			if(!rs.next()){
				dbConn.close();
				JOptionPane.showMessageDialog(frame, "Wrong Name or Password");
			}
			else{
				pos = rs.getString("POS");
				frame.setVisible(false);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e){
			e.printStackTrace();
			System.out.println("SQLException: " + e);
		} catch (Exception e){
			System.out.println("Exception: " + e);
			
		}
	}
	
	private void initialConnectDB(){
		try{
			//	JDBC Driver Loading
			Class.forName("oracle.jdbc.OracleDriver");
			dbConn = DriverManager.getConnection("jdbc:oracle:thin:" + "@localhost:1521:XE" , "system" , "1221");
			String sqlStr = "select count(menuname) from menu";
			PreparedStatement stmt = dbConn.prepareStatement(sqlStr);
			ResultSet rs = stmt.executeQuery();
			rs.next();									
			
			int i = rs.getInt("count(menuname)");
			buttonLabel = new String[i];
			
			sqlStr = "Select menuname from menu order by no";
			stmt = dbConn.prepareStatement(sqlStr);
			rs = stmt.executeQuery();
			for(i = 0; rs.next(); i++){
				buttonLabel[i] = rs.getString("MENUNAME");
			}
			
			sqlStr = "Select count(tdate) from record";
			stmt = dbConn.prepareStatement(sqlStr);
			rs = stmt.executeQuery();
			rs.next();
			
			dateCount = rs.getInt("COUNT(TDATE)");
			dateStringList = new String[dateCount];
			
			sqlStr = "Select tdate from record order by tdate";
			stmt = dbConn.prepareStatement(sqlStr);
			rs = stmt.executeQuery();
			for(dateCount = 0; rs.next(); dateCount++){
				dateStringList[dateCount] = rs.getString("TDATE");
			}
			
			LocalDate LocalDate = java.time.LocalDate.now();
			sqlStr = "Select tdate from record where tdate ='" + DateTimeFormatter.ofPattern("yyy/MM/dd").format(LocalDate) + "'";
			stmt = dbConn.prepareStatement(sqlStr);
			rs = stmt.executeQuery();
			
			if(!rs.next()){
				sqlStr = "insert into record values ('" + DateTimeFormatter.ofPattern("yyy/MM/dd").format(LocalDate) + "', 0)";
				stmt = dbConn.prepareStatement(sqlStr);
				rs = stmt.executeQuery();
				
				sqlStr = "commit";
				stmt = dbConn.prepareStatement(sqlStr);
				rs = stmt.executeQuery();
				dbConn.close();
				rs.close();
				stmt.close();
			}
			else{
				dbConn.close();
				rs.close();
				stmt.close();
			}
		} catch (SQLException e){
			e.printStackTrace();
			System.out.println("SQLException: " + e);
		} catch (Exception e){
			System.out.println("Exception: " + e);
		}
	}
	
	public static void main(String[] args) {
		new TermProject();

	}
}
