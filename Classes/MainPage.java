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

    import javax.swing.ImageIcon;
    import javax.swing.JButton;
    import javax.swing.JFrame;
    import javax.swing.JLabel;
    import javax.swing.JPanel;
    import javax.swing.JSeparator;
    import javax.swing.JTextField;
    import javax.swing.SwingConstants;
    import javax.swing.border.LineBorder;

    import java.time.LocalDate;

    import Entities.*;
    import mailClasses.*;

    public class MainPage {

        //Images
        ImageIcon calendarIconImage = new ImageIcon("Images\\calendarIcon.png");
        ImageIcon trashIconImage = new ImageIcon("Images\\trashIcon.png");
        ImageIcon gmailIconImage = new ImageIcon("Images\\gmail.png");
        ImageIcon leftImage = new ImageIcon("Images\\cloud.jpg");

        //JLabels
        JLabel task1 = new JLabel();
        JLabel task2 = new JLabel();
        JLabel task3 = new JLabel();
        JLabel task4 = new JLabel();
        JLabel task5 = new JLabel();

        JLabel [] tasks = {task1,task2,task3,task4,task5};
        int currentTask = 0;

        JLabel leftImageLabel = new JLabel(leftImage);
        //Frames
        JFrame mainPageFrame;

        //Panels
        JPanel mainPagePanel;
        JPanel LSMainPagePanel;
        JPanel RSMainPagePanel;

        //Time
        LocalDate currentDate = LocalDate.now();

        //Button
        JButton addTaskButton = new JButton("+");
        JButton doneButton1 = new JButton(trashIconImage);
        JButton doneButton2 = new JButton(trashIconImage);
        JButton doneButton3 = new JButton(trashIconImage);
        JButton doneButton4 = new JButton(trashIconImage);
        JButton doneButton5 = new JButton(trashIconImage);
        JButton [] doneButtons = {doneButton1,doneButton2,doneButton3,doneButton4,doneButton5};
        JButton gmailButton = new JButton(gmailIconImage);
        
        //TextField
        JTextField taskTextField = new JTextField();  
        JTextField gmailTextField = new JTextField();
        
        //Entities
        User user;

        //Additionals
        Statement statement;
        int totalNumOfPosts = 0;

        public MainPage(User user) throws SQLException{

            this.user = user;

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","root");
            statement = connection.createStatement();

            statement.execute("create database if not exists todolistDB");
            statement.execute("use todolistDB");
            System.out.println(user.username);
            statement.execute("create table if not exists " +  user.username + " (task varchar(1000))");

        
            mainPageFrame = new JFrame();
            mainPageFrame.setTitle("theTodolistApp");
            mainPageFrame.setSize(1000,700);
            mainPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainPageFrame.setLocationRelativeTo(null);
            mainPageFrame.setResizable(false);

            //mainPagePanel
            mainPagePanel = new JPanel();
            mainPagePanel.setBounds(0,0,1000,700);
            mainPagePanel.setBackground(Color.white);
            mainPagePanel.setVisible(true);

        
            LSMainPagePanel = new JPanel();
            LSMainPagePanel.setBounds(20,12,400,630);
            LSMainPagePanel.setBackground(Color.white);
            LSMainPagePanel.add(leftImageLabel);

            RSMainPagePanel = new JPanel();
            RSMainPagePanel.setBounds(420,12,555,640);
            RSMainPagePanel.setBackground(Color.white);
        
            

            JLabel Todo = new JLabel("Todo");
            Todo.setFont(new Font("Helvetica",Font.BOLD,30));
            Todo.setBounds(60,120,500,35);

            JLabel today = new JLabel("Today");
            today.setFont(new Font("Helvetica",Font.BOLD,15));
            today.setBounds(350,120,500,17);

            JLabel date = new JLabel(currentDate.toString());
            date.setFont(new Font("Helvetica",Font.PLAIN,11));
            date.setBounds(338,140,500,12);

            JLabel calenderIconLabel = new JLabel(calendarIconImage);
            calenderIconLabel.setFont(new Font("Helvetica",Font.PLAIN,11));
            calenderIconLabel.setBounds(402,116,40,40);

            JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
            separator.setBounds(62, 180, 375, 5);
            separator.setForeground(new Color(250,240,229));
            
            taskTextField.setBounds(65,210,320,40);
            taskTextField.setBorder(new LineBorder(new Color(239,239,239)));
            taskTextField.setBackground(new Color(251,251,251));
            taskTextField.setForeground(new Color(189,189,189));
            taskTextField.setText("      Add a new task");
            taskTextField.setFont(new Font("Helvetica",Font.PLAIN,15));

            taskTextField.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e){
                    taskTextField.setBorder(new LineBorder(Color.black));
                    taskTextField.setForeground(Color.BLACK);
                }
                @Override
                public void mouseExited(MouseEvent e){
                    taskTextField.setBorder(new LineBorder(new Color(239,239,239)));
                    taskTextField.setForeground(new Color(189,189,189));
                }
                @Override
                public void mouseClicked(MouseEvent e){
                    if(taskTextField.getText().equals("      Add a new task")){
                        taskTextField.setText("      ");
                    }
                }
            });

            addTaskButton.setBounds(390,210,50,40);
            addTaskButton.setBackground(new Color(76,83,241));
            addTaskButton.setForeground(Color.white);
            addTaskButton.setFont(new Font("Helvetica",Font.BOLD,20));

            addTaskButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e){
                }
                @Override
                public void mouseExited(MouseEvent e){
                }
                @Override
                public void mouseClicked(MouseEvent e){

                    if(totalNumOfPosts > 4){
                        addTaskButton.setEnabled(false);
                    }

                    else if (taskTextField.getText().isBlank()!=true && !taskTextField.getText().equals("      Add a new task")){
                        String task = taskTextField.getText();
                        String insertQuery = "INSERT INTO " + user.username + " VALUES(?)";
                        
                        try {
                            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                            insertStatement.setString(1, task);
                            insertStatement.executeUpdate();
                            taskTextField.setText("      Add a new task");
                            updateTasks();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });

            doneButton1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    try {
                        statement.execute("delete from " + user.username + " where task = '" + task1.getText() + "'");
                        updateTasks();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            doneButton2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    try {
                        statement.execute("delete from " + user.username + " where task = '" + task2.getText() + "'");
                        updateTasks();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            doneButton3.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    try {
                        statement.execute("delete from " + user.username + " where task = '" + task3.getText() + "'");
                        updateTasks();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            doneButton4.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    try {
                        statement.execute("delete from " + user.username + " where task = '" + task4.getText() + "'");
                        updateTasks();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            doneButton5.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    try {
                        statement.execute("delete from " + user.username + " where task = '" + task5.getText() + "'");
                        updateTasks();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            gmailButton.setBounds(390,520,50,40);
            gmailButton.setBackground(Color.white);
            gmailButton.setBorder(new LineBorder(Color.white));

            gmailTextField.setBounds(65,520,320,40);
            gmailTextField.setBorder(new LineBorder(new Color(239,239,239)));
            gmailTextField.setBackground(new Color(251,251,251));
            gmailTextField.setForeground(new Color(189,189,189));
            gmailTextField.setText("      " + user.email);
            gmailTextField.setFont(new Font("Helvetica",Font.PLAIN,15));

            gmailTextField.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e){
                    gmailTextField.setBorder(new LineBorder(Color.black));
                    gmailTextField.setForeground(Color.BLACK);
                }
                @Override
                public void mouseExited(MouseEvent e){
                    gmailTextField.setBorder(new LineBorder(new Color(239,239,239)));
                    gmailTextField.setForeground(new Color(189,189,189));
                }
                @Override
                public void mouseClicked(MouseEvent e){
                    if(gmailTextField.getText().equals("      " + user.email)){
                        gmailTextField.setText("      ");
                    }
                }
            });

            gmailButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){

                    String emailContext = "Dear " + user.username + ",\n\n" + "I hope this message finds you well. We appreciate your continued use of Todolist App to manage your tasks and stay organized. As requested, we have compiled a list of all the tasks you have entered into the app.\n\n" +
                    "Below, you will find all of your tasks:\n";

                    int taskNumber = 1 ;
                    try {
                        ResultSet totalPosts = statement.executeQuery("select * from " + user.username);
                         
                        while (totalPosts.next()) {
                            emailContext += taskNumber + ". " + totalPosts.getString(1).stripLeading() + "\n";
                            taskNumber++;
                        }


                        emailContext += "If you have any additional tasks or updates to make, please do so in the app, and your task list will be automatically updated.\n\n" +
                        "We're here to provide you with a seamless experience, so if you have any questions or need further assistance, please don't hesitate to reach out to our support team at Todolist@gmail.com.\n\n" + "Thank you for choosing theTodoListApp for your task management needs. We are committed to continually improving our services and appreciate your valuable feedback.\n\n"+"Wishing you a productive day!\n\n" + 
                        "Sincerely,\n" + "Todolist Team";
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                        if(taskNumber>1)
                        JavaMailUtil.sendMail(gmailTextField.getText().stripLeading(), emailContext);
                        else{
                            StartPage.textFieldToRed(taskTextField);
                        }
                    
                }
            });
            

            updateTasks();
            RSMainPagePanel.add(Todo);
            RSMainPagePanel.add(today);
            RSMainPagePanel.add(date);
            RSMainPagePanel.add(calenderIconLabel);
            RSMainPagePanel.add(separator);
            RSMainPagePanel.add(taskTextField);
            RSMainPagePanel.add(addTaskButton);
            RSMainPagePanel.add(gmailButton);
            RSMainPagePanel.add(gmailTextField);
            RSMainPagePanel.setLayout(null);


            mainPagePanel.add(LSMainPagePanel);
            mainPagePanel.add(RSMainPagePanel);
            mainPagePanel.setLayout(null);


            mainPageFrame.add(mainPagePanel);
            mainPageFrame.setLayout(null);
            mainPageFrame.setVisible(true);

        }

        void updateTasks() throws SQLException {
            ResultSet totalPosts = statement.executeQuery("select * from " + user.username);
        
            totalNumOfPosts = 0;
            String[] datas = new String[100];
            int x = 0;
        
            while (totalPosts.next()) {
                totalNumOfPosts++;
                datas[x] = totalPosts.getString(1);
                System.out.println(datas[x]);
                x++;
            }
        
            for (int i = 0; i < totalNumOfPosts && i < tasks.length; i++) {
                tasks[i].setText(datas[i]);
                tasks[i].setForeground(Color.black);
                tasks[i].setBounds(65, 270 + i * 50, 320, 40);
                // doneButtons[i].setBackground(new Color(24,157,82));
                doneButtons[i].setBackground(Color.white);
                doneButtons[i].setBorder(new LineBorder(Color.white));
                doneButtons[i].setForeground(Color.white);
                doneButtons[i].setBounds(390,270 + i * 50,50,40);
                tasks[i].setOpaque(true);
                RSMainPagePanel.add(tasks[i]);
                RSMainPagePanel.add(doneButtons[i]);
            }
        
            for (int i = totalNumOfPosts; i < tasks.length; i++) {
                tasks[i].setText(""); 
                RSMainPagePanel.remove(tasks[i]);
                RSMainPagePanel.remove(doneButtons[i]);
            }
        
            RSMainPagePanel.revalidate();
            RSMainPagePanel.repaint();
        }
        
        
    }
