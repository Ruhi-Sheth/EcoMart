import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
class Login extends JFrame implements ActionListener{
    public static String loginUser;
JLabel lblTitle,lblUser,lblPass,lblNew;
JTextField txtUsername;
JPasswordField txtPassword;
JButton btnLogin,btnReset,btnSignup;
JPanel panel;
    Login(){
        setTitle("EcoMart Login");
        ImageIcon icon=new ImageIcon(getClass().getResource("/logo.png"));
        setIconImage(icon.getImage());
        setSize(500,350);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(230,255,230));

        lblTitle=new JLabel("EcoMart Login",SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial",Font.BOLD,24));
        lblTitle.setForeground(new Color(0,100,0));
        add(lblTitle,BorderLayout.NORTH);

        panel=new JPanel();
        panel.setLayout(new GridLayout(5,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));
        panel.setBackground(new Color(240,255,240));

        lblUser=new JLabel("Username");
        lblUser.setFont(new Font("Arial",Font.BOLD,14));

        lblPass=new JLabel("Password");
        lblPass.setFont(new Font("Arial",Font.BOLD,14));

        txtUsername=new JTextField();
        txtUsername.setFont(new Font("Arial",Font.PLAIN,14));

        txtPassword=new JPasswordField();
        txtPassword.setFont(new Font("Arial",Font.PLAIN,14));

        btnLogin=new JButton("Login");
        btnLogin.setBackground(new Color(34,139,34));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);

        btnReset=new JButton("Reset");
        btnReset.setBackground(Color.GRAY);
        btnReset.setForeground(Color.WHITE);
        btnReset.setFocusPainted(false);

        lblNew=new JLabel("New to EcoMart?");
        lblNew.setFont(new Font("Arial",Font.BOLD,13));

        btnSignup=new JButton("Sign Up");
        btnSignup.setBackground(new Color(60,179,113));
        btnSignup.setForeground(Color.WHITE);
        btnSignup.setFocusPainted(false);

        btnLogin.addActionListener(this);
        btnReset.addActionListener(this);
        btnSignup.addActionListener(this);

        panel.add(lblUser);
        panel.add(txtUsername);
        panel.add(lblPass);
        panel.add(txtPassword);
        panel.add(btnLogin);
        panel.add(btnReset);
        panel.add(lblNew);
        panel.add(btnSignup);
        add(panel,BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==btnLogin){
            String username=txtUsername.getText();
            String password=String.valueOf(txtPassword.getPassword());

            String sql="SELECT * FROM LoginData WHERE username=? AND password=?";
            try{
                Connection con=MyConnection.getConnection();
                PreparedStatement pst=con.prepareStatement(sql);
                pst.setString(1,username);
                pst.setString(2,password);
                ResultSet rs=pst.executeQuery();
                if(rs.next()){
                    JOptionPane.showMessageDialog(this,"Welcome "+username+"!");
                    dispose();
                    loginUser=username;
                    new EcoMartHome();
                }
                else{
                    JOptionPane.showMessageDialog(this,"Invalid Username or Password");
                }
                rs.close();
                pst.close();
                con.close();
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(this,ex.getMessage());
            }
        }
        if(e.getSource()==btnReset){
            txtUsername.setText("");
            txtPassword.setText("");
        }
        if(e.getSource()==btnSignup){
            dispose();
            new SignUp();
         }
        }
    public static void main(String args[]){
        new Login();
    }
}