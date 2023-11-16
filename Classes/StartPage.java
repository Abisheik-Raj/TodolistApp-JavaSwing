package Classes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.LineBorder;

import Entities.*;
import mailClasses.JavaMailUtil;

public class StartPage{

    //Images
    ImageIcon loginImageIcon = new ImageIcon("Images\\LoginImage.jpg");
    ImageIcon signupImageIcon = new ImageIcon("Images\\LoginImage1.jpg");
    //Labels
    JLabel loginImage = new JLabel(loginImageIcon);
    JLabel signupImage = new JLabel(signupImageIcon);
    
    JLabel EmailLabel = new JLabel("Email");
    JLabel PasswordLabel = new JLabel("Password");

    JLabel EmailLabel2 = new JLabel("Email");
    JLabel PasswordLabel2 = new JLabel("Password");

    JLabel EmailLabelForForgotPassword = new JLabel();

    //Frames
    JFrame startPageFrame;
    JFrame forgotPasswordFrame;
    //Panels
    JPanel loginPanel;
    JPanel LSLoginPanel;
    JPanel RSLoginPanel;

    JPanel signinPanel;
    JPanel LSSigninPanel;
    JPanel RSSigninPanel;

    

    //TextFields
    JTextField emailtTextField = new JTextField();
    JPasswordField passwordTextField = new JPasswordField();

    JTextField emailtTextField2 = new JTextField();
    JPasswordField passwordTextField2 = new JPasswordField();

    JTextField otpTextField = new JTextField();

    //Buttons
    JButton LoginButton = new JButton("Log In");
    JButton SigninButton = new JButton("Sign Up");

    int otp;

    public StartPage() throws SQLException{

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","root");
        Statement statement = connection.createStatement();

        statement.execute("create database if not exists todolistDB");
        statement.execute("use todolistDB");

        statement.execute("create table if not exists usercredentials (email varchar(100),password varchar(100))");

        startPageFrame = new JFrame();
        startPageFrame.setTitle("theTodolistApp");
        startPageFrame.setSize(1000,700);
        startPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startPageFrame.setLocationRelativeTo(null);

        //Login Panel
        loginPanel = new JPanel();
        loginPanel.setBounds(0,0,1000,700);
        loginPanel.setBackground(Color.white);
        loginPanel.setVisible(true);

        LSLoginPanel = new JPanel();
        LSLoginPanel.setBounds(10,12,400,640);
        LSLoginPanel.setBackground(Color.white);
        LSLoginPanel.add(loginImage);

        RSLoginPanel = new JPanel();
        RSLoginPanel.setBounds(420,12,555,640);
        RSLoginPanel.setBackground(Color.white);

        JLabel welcometotodolist = new JLabel("Welcome back to TodoList,");
        welcometotodolist.setFont(new Font("Helvetica",Font.PLAIN,20));
        welcometotodolist.setBounds(60,130,500,25);

        JLabel sigintocontinue = new JLabel("Sign In to Continue.");
        sigintocontinue.setFont(new Font("Helvetica",Font.PLAIN,20));
        sigintocontinue.setBounds(60,157,500,25);

        JLabel doesnthaveaaccount = new JLabel("Doesn't have an account?");
        doesnthaveaaccount.setFont(new Font("Helvetica",Font.BOLD,12));
        doesnthaveaaccount.setBounds(60,200,500,12);

        JLabel createanaccount = new JLabel("Create a account");
        createanaccount.setFont(new Font("Helvetica",Font.BOLD,12));
        createanaccount.setBounds(60,215,500,14);

        createanaccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e){
                createanaccount.setText("<html><u>Create a account</u></html>");
            }
            public void mouseExited(MouseEvent e){
                createanaccount.setText("Create a account");
            }
            public void mouseClicked(MouseEvent e){
                loginPanel.setVisible(false);
                signinPanel.setVisible(true);

                emailtTextField.setText("");
                passwordTextField.setText("");
            }
        });

        EmailLabel.setFont(new Font("Helvetica",Font.BOLD,13));
        EmailLabel.setBounds(60,250,500,15);

        emailtTextField.setFont(new Font("Helvetica",Font.BOLD,12));
        emailtTextField.setBounds(60,270,300,40);
        emailtTextField.setBorder(new LineBorder(Color.gray,2));

        emailtTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e){
                emailtTextField.setBorder(new LineBorder(Color.black,2));
                emailtTextField.setBackground(new Color(239,239,239));
            }
            public void mouseExited(MouseEvent e){
                emailtTextField.setBorder(new LineBorder(Color.gray,2));
                emailtTextField.setBackground(Color.white); 
            }
            public void mouseClicked(MouseEvent e){
                
            }
        });
        
        PasswordLabel.setFont(new Font("Helvetica",Font.BOLD,13));
        PasswordLabel.setBounds(60,330,500,15);

        passwordTextField.setFont(new Font("Helvetica",Font.BOLD,12));
        passwordTextField.setBounds(60,350,300,40);
        passwordTextField.setBorder(new LineBorder(Color.gray,2));

        passwordTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e){
                passwordTextField.setBorder(new LineBorder(Color.black,2));
                passwordTextField.setBackground(new Color(239,239,239));
            }
            public void mouseExited(MouseEvent e){
                passwordTextField.setBorder(new LineBorder(Color.gray,2));
                passwordTextField.setBackground(Color.white);
            }
            public void mouseClicked(MouseEvent e){
                
            }
        });

        JLabel forgotpassword = new JLabel("Forgot Password?");
        forgotpassword.setFont(new Font("Helvetica",Font.BOLD,12));
        forgotpassword.setBounds(155,410,500,15);
        forgotpassword.setForeground(Color.GRAY);

        forgotpassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e){
                if(!emailtTextField.getText().isBlank()){
                    forgotpassword.setForeground(Color.black);
                    forgotpassword.setText("<html><u>Forgot Password?</u></html>");}
            }
            public void mouseExited(MouseEvent e){
                forgotpassword.setForeground(Color.GRAY);
            }
            public void mouseClicked(MouseEvent e){
                if(!emailtTextField.getText().isBlank()){
                    EmailLabelForForgotPassword.setText("Email sent to " + emailtTextField.getText());
                    Random random = new Random();
                    otp = random.nextInt(9999) + 1000; 
                    String mailContext = Integer.toString(otp);

                    JavaMailUtil.sendMail(emailtTextField.getText(), mailContext);
                    forgotPasswordFrame.setVisible(true);
                }
            }
        });

        forgotPasswordFrame = new JFrame();
        forgotPasswordFrame.setTitle("theTodolistApp");
        forgotPasswordFrame.setSize(500,300);
        forgotPasswordFrame.setLocationRelativeTo(null);
        forgotPasswordFrame.setBackground(Color.white);

        EmailLabelForForgotPassword.setFont(new Font("Helvetica",Font.BOLD,15));
        EmailLabelForForgotPassword.setBounds(60,80,500,18);

        otpTextField.setFont(new Font("Helvetica",Font.BOLD,12));
        otpTextField.setBounds(60,110,300,40);
        otpTextField.setText("Enter OTP");
        otpTextField.setBorder(new LineBorder(Color.gray,2));

        otpTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e){
                otpTextField.setBorder(new LineBorder(Color.black,2));
                otpTextField.setBackground(new Color(239,239,239));
            }
            public void mouseExited(MouseEvent e){
                otpTextField.setBorder(new LineBorder(Color.gray,2));
                otpTextField.setBackground(Color.white);
            }
            public void mouseClicked(MouseEvent e){
                otpTextField.setText("");
            }
        });

        JButton otpSubmitButton = new JButton("Submit");
        otpSubmitButton.setBounds(280,170,80,40);
        otpSubmitButton.setForeground(Color.white);
        otpSubmitButton.setBackground(new Color(10,102,194));

        otpSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e){
                otpSubmitButton.setBackground(new Color(0, 65, 130));
            }
            public void mouseExited(MouseEvent e){
                otpSubmitButton.setBackground(new Color(10,102,194));
            }
            public void mouseClicked(MouseEvent e){
                System.out.println(otp);
                if(otpTextField.getText().equals(Integer.toString(otp))){
                    try {
                        statement.execute("delete from usercredentials where email = '" + emailtTextField.getText() + "'");
                        forgotPasswordFrame.setVisible(false);
                        loginPanel.setVisible(false);
                        signinPanel.setVisible(true);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                else{
                    textFieldToRed(otpTextField);
                }
            }
        });


        forgotPasswordFrame.add(EmailLabelForForgotPassword);
        forgotPasswordFrame.add(otpTextField);
        forgotPasswordFrame.add(otpSubmitButton);
        forgotPasswordFrame.setLayout(null);


        

        LoginButton.setBounds(60,445,300,40);
        LoginButton.setForeground(Color.white);
        LoginButton.setBackground(new Color(10,102,194));

        LoginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e){
                LoginButton.setBackground(new Color(0, 65, 130));
            }
            public void mouseExited(MouseEvent e){
                LoginButton.setBackground(new Color(10,102,194));
            }
            public void mouseClicked(MouseEvent e){
                String emailID = emailtTextField.getText();
                String password = passwordTextField.getText();

                if(emailID.isBlank())
                    textFieldToRed(emailtTextField);
                if(password.isBlank())
                    textFieldToRed(passwordTextField);
                if(!emailID.isBlank() && !password.isBlank()){
                    try {
                        ResultSet resultSet = statement.executeQuery("select * from usercredentials");

                        boolean loggedIn = false;
                        while(resultSet.next()){
                            String username = resultSet.getString(1);
                            String pass = resultSet.getString(2);
                            if(username.equals(emailID) && pass.equals(password)){
                                loggedIn = true;
                                break;
                            }
                        }

                        if(loggedIn == false){
                            textFieldToRed(emailtTextField);
                            textFieldToRed(passwordTextField);
                        }
                        else{
                            startPageFrame.setVisible(false);
                            new MainPage(new User(emailID,password));
                        }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    
                }
                
            }
        });


        RSLoginPanel.add(welcometotodolist);
        RSLoginPanel.add(sigintocontinue);
        RSLoginPanel.add(doesnthaveaaccount);
        RSLoginPanel.add(createanaccount);
        RSLoginPanel.add(EmailLabel);
        RSLoginPanel.add(emailtTextField);
        RSLoginPanel.add(PasswordLabel);
        RSLoginPanel.add(passwordTextField);
        RSLoginPanel.add(forgotpassword);
        RSLoginPanel.add(LoginButton);
        RSLoginPanel.setLayout(null);

        loginPanel.add(LSLoginPanel);
        loginPanel.add(RSLoginPanel);   
        loginPanel.setLayout(null);


        //Signin Panel
        signinPanel = new JPanel();
        signinPanel.setBounds(0,0,1000,700);
        signinPanel.setBackground(Color.white);
        signinPanel.setVisible(false);

        LSSigninPanel = new JPanel();
        LSSigninPanel.setBounds(10,12,400,640);
        LSSigninPanel.setBackground(Color.white);
        LSSigninPanel.add(signupImage);

        RSSigninPanel = new JPanel();
        RSSigninPanel.setBounds(420,12,555,640);
        RSSigninPanel.setBackground(Color.white);

        JLabel welcomebacktotodolist = new JLabel("Welcome to TodoList,");
        welcomebacktotodolist.setFont(new Font("Helvetica",Font.PLAIN,20));
        welcomebacktotodolist.setBounds(60,130,500,25);

        JLabel signuptocontinue = new JLabel("Sign Up to Continue.");
        signuptocontinue.setFont(new Font("Helvetica",Font.PLAIN,20));
        signuptocontinue.setBounds(60,157,500,25);

        JLabel alreadyhaveanaccount = new JLabel("Already have an account?");
        alreadyhaveanaccount.setFont(new Font("Helvetica",Font.BOLD,12));
        alreadyhaveanaccount.setBounds(60,200,500,12);

        JLabel logintoexistingaccount = new JLabel("Log In to existing account");
        logintoexistingaccount.setFont(new Font("Helvetica",Font.BOLD,12));
        logintoexistingaccount.setBounds(60,215,500,14);

        logintoexistingaccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e){
                logintoexistingaccount.setText("<html><u>Log In to existing account</u></html>");
            }
            public void mouseExited(MouseEvent e){
                logintoexistingaccount.setText("Log In to existing account");
            }
            public void mouseClicked(MouseEvent e){
                signinPanel.setVisible(false);
                loginPanel.setVisible(true);

                emailtTextField2.setText("");
                passwordTextField2.setText("");
            }
        });

        EmailLabel2.setFont(new Font("Helvetica",Font.BOLD,13));
        EmailLabel2.setBounds(60,250,500,15);

        emailtTextField2.setFont(new Font("Helvetica",Font.BOLD,12));
        emailtTextField2.setBounds(60,270,300,40);
        emailtTextField2.setBorder(new LineBorder(Color.gray,2));

        emailtTextField2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e){
                emailtTextField2.setBorder(new LineBorder(Color.black,2));
                emailtTextField2.setBackground(new Color(239,239,239));
            }
            public void mouseExited(MouseEvent e){
                emailtTextField2.setBorder(new LineBorder(Color.gray,2));
                emailtTextField2.setBackground(Color.white);
            }
            public void mouseClicked(MouseEvent e){
                
            }
        });

        PasswordLabel2.setFont(new Font("Helvetica",Font.BOLD,13));
        PasswordLabel2.setBounds(60,330,500,15);

        passwordTextField2.setFont(new Font("Helvetica",Font.BOLD,12));
        passwordTextField2.setBounds(60,350,300,40);
        passwordTextField2.setBorder(new LineBorder(Color.gray,2));

        passwordTextField2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e){
                passwordTextField2.setBorder(new LineBorder(Color.black,2));
                passwordTextField2.setBackground(new Color(239,239,239));
            }
            public void mouseExited(MouseEvent e){
                passwordTextField2.setBorder(new LineBorder(Color.gray,2));
                passwordTextField2.setBackground(Color.white);
            }
            public void mouseClicked(MouseEvent e){
                
            }
        });

        SigninButton.setBounds(60,445,300,40);
        SigninButton.setForeground(Color.white);
        SigninButton.setBackground(new Color(10,102,194));

        SigninButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e){
                SigninButton.setBackground(new Color(0, 65, 130));
            }
            public void mouseExited(MouseEvent e){
                SigninButton.setBackground(new Color(10,102,194));
            }
            public void mouseClicked(MouseEvent e){
                String emailID = emailtTextField2.getText();
                String password = passwordTextField2.getText();

                if(emailID.isBlank())
                    textFieldToRed(emailtTextField2);
                if(password.isBlank())
                    textFieldToRed(passwordTextField2);
                if(!emailID.isBlank() && !password.isBlank()){
                    
                    try {
                        ResultSet resultSet = statement.executeQuery("select * from usercredentials");
                        boolean userExist = false;
                        while(resultSet.next()){
                            String username = resultSet.getString(1);
                            if(username.equals(emailID)){
                                userExist  =  true;
                                break;
                            }
                        }

                        if(userExist){
                            System.out.println("User already exist!");
                            textFieldToRed(emailtTextField2);
                            textFieldToRed(passwordTextField2);
                        }
                        else{
                            String insertQuery = "INSERT INTO usercredentials (email, password) VALUES (?, ?)";
                            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                            preparedStatement.setString(1, emailID);
                            preparedStatement.setString(2, password);
                            preparedStatement.executeUpdate();
                            userExist = false;

                            signinPanel.setVisible(false);
                            loginPanel.setVisible(true);
                        }

                        emailtTextField2.setText("");
                        passwordTextField2.setText("");
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        RSSigninPanel.add(welcomebacktotodolist);
        RSSigninPanel.add(signuptocontinue);
        RSSigninPanel.add(alreadyhaveanaccount);
        RSSigninPanel.add(logintoexistingaccount);
        RSSigninPanel.add(EmailLabel2);
        RSSigninPanel.add(emailtTextField2);
        RSSigninPanel.add(PasswordLabel2);
        RSSigninPanel.add(passwordTextField2);
        RSSigninPanel.add(SigninButton);
        RSSigninPanel.setLayout(null);

        signinPanel.add(LSSigninPanel);
        signinPanel.add(RSSigninPanel);   
        signinPanel.setLayout(null);
        
        startPageFrame.add(loginPanel);
        startPageFrame.add(signinPanel);
        startPageFrame.setLayout(null);
        startPageFrame.setVisible(true);
    }

    static void textFieldToRed(JTextField textField){
        textField.setBorder(new LineBorder(Color.red));;
        new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
            Thread.sleep(70);
            return null;
        }
        @Override
        protected void done() {
            textField.setBorder(new LineBorder(new Color(145,153,157), 1));;
        }
        }.execute();
}
}