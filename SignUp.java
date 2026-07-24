import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
public class SignUp extends JFrame implements ActionListener{
        JLabel lblName,lblEmail,lblMob,lblTitle,lblUser,lblPass,lblReg,lblAddress;
        JTextField txtUsername, txtName,txtEmail,txtAddress,txtMobile;
        JPasswordField txtPassword;
        JButton btnBack,btnReset,btnSignUp;
        JPanel panel;
    SignUp()
    {
        setTitle("EcoMart SignUp");
        ImageIcon icon=new ImageIcon(getClass().getResource("/logo.png"));
        setIconImage(icon.getImage());
        setSize(500,350);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(230,255,230));

        lblTitle=new JLabel("EcoMart SignUp",SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial",Font.BOLD,24));
        lblTitle.setForeground(new Color(0,100,0));
        add(lblTitle,BorderLayout.NORTH);

        panel=new JPanel();
        panel.setLayout(new GridLayout(8,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));
        panel.setBackground(new Color(240,255,240));

        lblName=new JLabel("Name");
        lblName.setFont(new Font("Arial",Font.BOLD,14));

        lblUser=new JLabel("Username");
        lblUser.setFont(new Font("Arial",Font.BOLD,14));

        lblPass=new JLabel("Password");
        lblPass.setFont(new Font("Arial",Font.BOLD,14));

        lblEmail=new JLabel("Email");
        lblEmail.setFont(new Font("Arial",Font.BOLD,14));

        lblMob=new JLabel("Mobile");
        lblMob.setFont(new Font("Arial",Font.BOLD,14));

        lblAddress=new JLabel("Address");
        lblAddress.setFont(new Font("Arial",Font.BOLD,14));

        txtUsername=new JTextField();
        txtUsername.setFont(new Font("Arial",Font.PLAIN,14));

        txtName=new JTextField();
        txtName.setFont(new Font("Arial",Font.PLAIN,14));

        txtEmail=new JTextField();
        txtEmail.setFont(new Font("Arial",Font.PLAIN,14));

        txtMobile=new JTextField();
        txtMobile.setFont(new Font("Arial",Font.PLAIN,14));

        txtAddress=new JTextField();
        txtAddress.setFont(new Font("Arial",Font.PLAIN,14));

        txtPassword=new JPasswordField();
        txtPassword.setFont(new Font("Arial",Font.PLAIN,14));

        btnSignUp=new JButton("Signup");
        btnSignUp.setBackground(new Color(34,139,34));
        btnSignUp.setForeground(Color.WHITE);
        btnSignUp.setFocusPainted(false);

        btnReset=new JButton("Reset");
        btnReset.setBackground(Color.GRAY);
        btnReset.setForeground(Color.WHITE);
        btnReset.setFocusPainted(false);

        lblReg=new JLabel("Already Registered?");
        lblReg.setFont(new Font("Arial",Font.BOLD,13));

        btnBack=new JButton("Back");
        btnBack.setBackground(new Color(60,179,113));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);

       btnSignUp.addActionListener(this);
       btnReset.addActionListener(this);
       btnBack.addActionListener(this);

        panel.add(lblName);
        panel.add(txtName);
        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(lblMob);
        panel.add(txtMobile);
        panel.add(lblUser);
        panel.add(txtUsername);
        panel.add(lblPass);
        panel.add(txtPassword);
        panel.add(lblAddress);
        panel.add(txtAddress);
        panel.add(btnSignUp);
        panel.add(btnReset);
        panel.add(lblReg);
        panel.add(btnBack);
        add(panel,BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
public void actionPerformed(ActionEvent ae){
    if(ae.getSource()==btnSignUp){
        String name=txtName.getText().trim();
        String username=txtUsername.getText().trim();
        String email=txtEmail.getText().trim();
        String mobile=txtMobile.getText().trim();
        String address=txtAddress.getText().trim();
        String password=String.valueOf(txtPassword.getPassword());

    if(name.equals("") || username.equals("") || email.equals("") || mobile.equals("") || address.equals("") || password.equals("")){
        JOptionPane.showMessageDialog(this,"All fields are mandatory");
        return;
    }
    if(mobile.length()!=10){
        JOptionPane.showMessageDialog(this,"Mobile number must be 10 digits");
        return;
    }
    for(int i=0;i<mobile.length();i++){
        if(!Character.isDigit(mobile.charAt(i))){
            JOptionPane.showMessageDialog(this,"Mobile number should contain only digits");
            return;
        }
    }
    if(email.indexOf('@')==-1 || email.indexOf('.')==-1){
        JOptionPane.showMessageDialog(this,"Invalid Email ID");
        return;
    }
        try{
            Connection con = MyConnection.getConnection();
            String query="insert into LoginData values(?,?,?,?,?,?)";
            PreparedStatement ps=con.prepareStatement(query);

            ps.setString(1,txtUsername.getText());
            ps.setString(2,String.valueOf(txtPassword.getPassword()));
            ps.setString(3,txtEmail.getText());
            ps.setString(4,txtMobile.getText());
            ps.setString(5,txtAddress.getText());
            ps.setString(6,txtName.getText());

            int x=ps.executeUpdate();
            if(x>0){
                JOptionPane.showMessageDialog(this,"Registration Successful");
                JOptionPane.showMessageDialog(this,"Welcome to EcoMart!\n\n"+"Use these coupon codes on your first orders:\n\n"
                +"NEWUSER910  - 15% OFF\n"+"NEWeco10    - 10% OFF");
                txtName.setText("");
                txtEmail.setText("");
                txtMobile.setText("");
                txtUsername.setText("");
                txtPassword.setText("");
                txtAddress.setText("");
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,e);
        }
    }
    if(ae.getSource()==btnReset)
    {
        txtName.setText("");
        txtEmail.setText("");
        txtMobile.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtAddress.setText("");
    }
    if(ae.getSource()==btnBack)
    {
        dispose();
        new Login();
    }
}
}