import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.sql.*;
public class EcoMartHome extends JFrame{
    JTextField searchField;
    JButton searchButton;
    JMenuBar mb;
    JMenu file,category,account,about;
    JMenuItem home,logoutt,exit;
    JMenuItem kit,furni,fashion,care,decor;
    JMenuItem profile,changePass;
    JMenuItem aboutUs,contact,version;
    JButton cart,payment,reward,history,logoutButton;
    JPanel topPanel;
    JPanel rightPanel;
    JPanel centerPanel;
    JPanel productPanel;
    String currentCategory="Kitchen";
    
    EcoMart eco=new EcoMart();
    EcoMartHome()
    {
        setTitle("EcoMart");
        ImageIcon icon=new ImageIcon(getClass().getResource("/logo.png"));
        setIconImage(icon.getImage());
        setSize(900,600);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 250, 245));
        
        topPanel=new JPanel();
        topPanel.setBackground(new Color(46,125,50));  
        
        searchField=new JTextField(25);//decoration of search field
        searchField.setBackground(Color.WHITE);
        searchField.setForeground(Color.BLACK);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        searchButton=new JButton("Search");
        searchButton.setBackground(new Color(255,193,7));//decoration of search button
        searchButton.setForeground(Color.BLACK);
        searchButton.setFocusPainted(false);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel lbl = new JLabel("Search");
        lbl.setForeground(Color.WHITE);//decoration of search label
        lbl.setFont(new Font("Arial", Font.BOLD, 15));
        topPanel.add(lbl);
        
        topPanel.add(searchField);
        topPanel.add(searchButton);
        add(topPanel,BorderLayout.NORTH);

        mb=new JMenuBar();
        mb.setBackground(new Color(27,94,32));//decoration of menubar
        mb.setForeground(Color.WHITE);
        

        file=new JMenu("File");
        category=new JMenu("Categories");
        account=new JMenu("Account");
        about=new JMenu("About Us");
        
        file.setForeground(Color.WHITE);//decoration of JMenu text
        category.setForeground(Color.WHITE);
        account.setForeground(Color.WHITE);
        about.setForeground(Color.WHITE);
 
        file.setFont(new Font("Arial",Font.BOLD,14));//decoration of JMenu text
        category.setFont(new Font("Arial",Font.BOLD,14));
        account.setFont(new Font("Arial",Font.BOLD,14));
        about.setFont(new Font("Arial",Font.BOLD,14));

        home=new JMenuItem("Home");
        logoutt=new JMenuItem("Logout");
        exit=new JMenuItem("Exit");
        
        kit=new JMenuItem("Kitchen");
        care=new JMenuItem("Personal Care");
        decor=new JMenuItem("Home Decor");
        fashion=new JMenuItem("Fashion");
        furni=new JMenuItem("Furniture");

        profile=new JMenuItem("My Profile");
        changePass=new JMenuItem("Change Password");

        aboutUs=new JMenuItem("EcoMart Information");
        contact=new JMenuItem("Contact Us");
        version=new JMenuItem("Version");

        file.add(home);
        file.add(logoutt);
        file.add(exit);

        category.add(kit);
        category.add(care);
        category.add(decor);
        category.add(fashion);
        category.add(furni);

        account.add(profile);
        account.add(changePass);

        about.add(aboutUs);
        about.add(contact);
        about.add(version);

        mb.add(file);
        mb.add(category);
        mb.add(account);
        mb.add(about);

        setJMenuBar(mb);

        centerPanel=new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(232,245,233));
        
        JLabel title=new JLabel("Welcome to EcoMart",JLabel.CENTER);
        title.setFont(new Font("Verdana",Font.BOLD,24));
        title.setForeground(new Color(27,94,32));
        centerPanel.add(title,BorderLayout.NORTH);
        productPanel=new JPanel();
        centerPanel.add(productPanel,BorderLayout.CENTER);
        showCategory("Kitchen");

        add(centerPanel,BorderLayout.CENTER);

        rightPanel=new JPanel();
        rightPanel.setBackground(new Color(200,230,201));
        rightPanel.setBorder(new EmptyBorder(15,10,15,10));
        rightPanel.setLayout(new GridLayout(5,1,5,5));

        cart=new JButton("Go To Cart");
        payment=new JButton("Payment");
        reward=new JButton("Reward Points");
        history=new JButton("Order History");
        logoutButton=new JButton("Logout");
        
        JButton buttons[] = {cart,payment,reward,history,logoutButton};
        for(JButton b : buttons){
            b.setBackground(new Color(76,175,80));
            b.setForeground(Color.WHITE);
            b.setFont(new Font("Arial",Font.BOLD,14));
            b.setFocusPainted(false);
        }

        rightPanel.add(cart);
        rightPanel.add(payment);
        rightPanel.add(reward);
        rightPanel.add(history);
        rightPanel.add(logoutButton);

        add(rightPanel,BorderLayout.EAST);
        
// ================= SEARCH BUTTON =================
searchButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String product=searchField.getText();
        boolean found=false;
        for(int i=0;i<eco.p.products.length;i++){
            if(eco.p.products[i].equalsIgnoreCase(product)){
                JOptionPane.showMessageDialog(EcoMartHome.this,eco.p.products[i]+"\nPrice : ₹"+eco.p.price[i]);
                found=true;
                break;
            }
        }
        if(!found){
            JOptionPane.showMessageDialog(EcoMartHome.this,"Product Not Found");
        }
    }
});
// ================= CART BUTTON =================
cart.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        JTextArea area = new JTextArea(15,30);
        area.setText(eco.displayCart());
        area.setEditable(false);
        JOptionPane.showMessageDialog(EcoMartHome.this,new JScrollPane(area),"Shopping Cart",JOptionPane.INFORMATION_MESSAGE);
        int choice=JOptionPane.showConfirmDialog(
                EcoMartHome.this,"Do you want to remove an item from the cart?","Remove Item",
                JOptionPane.YES_NO_OPTION);
        if(choice==JOptionPane.YES_OPTION){
            String item=JOptionPane.showInputDialog("Enter Product Number");
            if(item!=null){
                eco.deleteItem(Integer.parseInt(item));
                JOptionPane.showMessageDialog(EcoMartHome.this,"Item Removed Successfully");
            }
        }
    }
});
// ================= PAYMENT BUTTON =================
payment.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String pass=JOptionPane.showInputDialog("Enter EcoCard Password");
        try{
            Connection con=MyConnection.getConnection();
            PreparedStatement ps=con.prepareStatement("select * from LoginData where Username=? and Password=?");
            ps.setString(1,Login.loginUser);
            ps.setString(2,pass);
            if(!ps.executeQuery().next()){
                JOptionPane.showMessageDialog(EcoMartHome.this,"Invalid Password");
                return;
            }
            con.close();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(EcoMartHome.this,ex);
            return;
        }
        String coupon =JOptionPane.showInputDialog("Enter Coupon Code");
        int rewardPoints=0;
        try{
            Connection con=MyConnection.getConnection();
            PreparedStatement ps=con.prepareStatement("select RewardPoints from LoginData where Username=?");
            ps.setString(1,Login.loginUser);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                rewardPoints=rs.getInt("RewardPoints");
            }
        con.close();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(EcoMartHome.this,ex);
        }
        String p=JOptionPane.showInputDialog("Enter Reward Points to Redeem");
        int redeem=Integer.parseInt(p);
        if(redeem>rewardPoints){
            JOptionPane.showMessageDialog(EcoMartHome.this,"Insufficient Reward Points, Money used from EcoCard");
            return;
        }
        double bill=eco.generateBill(coupon,eco.totalCarbon);
        rewardPoints=rewardPoints+eco.bill.getPoints();
        bill=bill-redeem;
        try{
            Connection con=MyConnection.getConnection();
            PreparedStatement ps=con.prepareStatement("update LoginData set RewardPoints=RewardPoints-? where Username=?");
            ps.setInt(1,redeem);
            ps.setString(2,Login.loginUser);
            ps.executeUpdate();
            con.close();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(EcoMartHome.this,ex);
        }
        rewardPoints=rewardPoints-redeem;
        if(bill<0){
            bill=0;
        }
        try{
            Connection con=MyConnection.getConnection();
            String query="insert into OrderHistory(Username,Products,TotalAmount,RewardPoints) values(?,?,?,?)";
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1,Login.loginUser);
            ps.setString(2,eco.displayCart());
            ps.setDouble(3,bill);
            ps.setInt(4,eco.bill.getPoints());
            ps.executeUpdate();
            con.close();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(EcoMartHome.this,ex);
        }
        

        double gstAmount=eco.bill.GST((eco.totalAmount-eco.totalDiscount));
        JOptionPane.showMessageDialog(EcoMartHome.this,"Total Amount : Rs."+eco.totalAmount+"\nDiscount : Rs."+eco.totalDiscount+
"\nGST : Rs."+gstAmount+"\n-----------------------"+"\nFinal Amount : Rs."+bill+"\nReward Points Earned : "+eco.bill.getPoints());
        try{
            Connection con=MyConnection.getConnection();
            PreparedStatement ps=con.prepareStatement("update LoginData set RewardPoints=RewardPoints+? where Username=?");
            ps.setInt(1,eco.bill.getPoints());
            ps.setString(2,Login.loginUser);
            ps.executeUpdate();
            con.close();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(EcoMartHome.this,ex);
        }
    }
});
// ================= REWARD BUTTON =================
reward.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        try{
            Connection con=MyConnection.getConnection();
            PreparedStatement ps=con.prepareStatement("select RewardPoints from LoginData where Username=?");
            ps.setString(1,Login.loginUser);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(EcoMartHome.this,"Reward Points : "+rs.getInt("RewardPoints"));
            }
            con.close();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(EcoMartHome.this,ex);
        }
    }
});
// ================= HISTORY BUTTON =================
history.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        try{
            Connection con=MyConnection.getConnection();
            String query="select * from OrderHistory where Username=?";
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1,Login.loginUser);
            ResultSet rs=ps.executeQuery();
            String s="";
            while(rs.next()){
                s=s+"Products : "+rs.getString("Products")+"\n";
                s=s+"Bill : ₹"+rs.getDouble("TotalAmount")+"\n";
                s=s+"Reward Points : "+rs.getInt("RewardPoints")+"\n\n";
            }
            if(s.equals("")){
                s="No Order History";
            }
            JOptionPane.showMessageDialog(EcoMartHome.this,s);
            con.close();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(EcoMartHome.this,ex);
        }
    }
});
// ================= LOGOUT BUTTON =================
logoutButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
});
// ================= MENU ITEMS =================
home.addActionListener(e->{currentCategory="Kitchen";showCategory(currentCategory);});
logoutt.addActionListener(e ->dispose());
exit.addActionListener(e ->System.exit(0));
kit.addActionListener(e->{currentCategory="Kitchen";showCategory(currentCategory);});
care.addActionListener(e->{currentCategory="Personal Care";showCategory(currentCategory);});
decor.addActionListener(e->{currentCategory="Home Decor";showCategory(currentCategory);});
fashion.addActionListener(e->{currentCategory="Fashion";showCategory(currentCategory);});
furni.addActionListener(e->{currentCategory="Furniture";showCategory(currentCategory);});

profile.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        try{
            Connection con=MyConnection.getConnection();
            String query="select * from LoginData where Username=?";
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1,Login.loginUser);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                String s="";
                s=s+"Name : "+rs.getString("Name")+"\n";
                s=s+"Username : "+rs.getString("Username")+"\n";
                s=s+"Email : "+rs.getString("Email")+"\n";
                s=s+"Mobile : "+rs.getString("Mobile")+"\n";
                s=s+"Address : "+rs.getString("Address");
                JOptionPane.showMessageDialog(EcoMartHome.this,s);
            }
            con.close();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(EcoMartHome.this,ex);
        }
    }
});
changePass.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String pass=JOptionPane.showInputDialog("Enter New Password");
        try{
            Connection con=MyConnection.getConnection();
            String query="update LoginData set Password=? where Username=?";
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1,pass);
            ps.setString(2,Login.loginUser);
            int x=ps.executeUpdate();
            if(x>0){
                JOptionPane.showMessageDialog(EcoMartHome.this,"Password Changed Successfully");
            }
            con.close();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(EcoMartHome.this,ex);
        }
    }
});

aboutUs.addActionListener(e ->JOptionPane.showMessageDialog(this,"EcoMart: A Green Shopping Platform by Ruhi Sheth & Jinisha"));
contact.addActionListener(e ->JOptionPane.showMessageDialog(this,"Contact : support@ecomart.com"));
version.addActionListener(e ->JOptionPane.showMessageDialog(this,"Version 1.0"));
    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
  void showCategory(String category){
    productPanel.removeAll();
    productPanel.setLayout(new GridLayout(2,3,15,15));
    int start=0;
    int end=0;
    if(category.equals("Kitchen")){
        start=0;
        end=4;
    }
    else if(category.equals("Personal Care")){
        start=5;
        end=7;
    }
    else if(category.equals("Fashion")){
        start=8;
        end=11;
    }
    else if(category.equals("Furniture")){
        start=12;
        end=15;
    }
    else if(category.equals("Home Decor")){
        start=16;
        end=19;
    }
    for(int i=start;i<=end;i++){
        JPanel p=new JPanel();
        p.setLayout(new BorderLayout());

        ImageIcon img=new ImageIcon(getClass().getResource("/"+(i+1)+".png"));
        Image im=img.getImage();
        Image newImg=im.getScaledInstance(120,120,Image.SCALE_SMOOTH);
        ImageIcon smallImg=new ImageIcon(newImg);
        JLabel image=new JLabel(smallImg);
        image.setHorizontalAlignment(JLabel.CENTER);

        JLabel name=new JLabel(eco.p.products[i],JLabel.CENTER);
        JLabel price=new JLabel("Rs. "+eco.p.price[i],JLabel.CENTER);
        JLabel discount=new JLabel("Discount : "+eco.dis.getDiscount(eco.p.products[i])+"%",JLabel.CENTER);
        discount.setForeground(new Color(34,139,34));
        discount.setFont(new Font("Arial",Font.BOLD,12));
        JButton add=new JButton("Add To Cart");

        int no=i+1;
        add.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                eco.addItem(no);
                JOptionPane.showMessageDialog(EcoMartHome.this,eco.p.products[no-1]+" Added To Cart");
            }
        });
        p.add(image,BorderLayout.NORTH);
        p.add(name,BorderLayout.CENTER);

        JPanel temp=new JPanel(new GridLayout(3,1));
        temp.add(price);
        temp.add(discount);
        temp.add(add);

        p.add(temp,BorderLayout.SOUTH);
        productPanel.add(p);
    }
    productPanel.revalidate();
    productPanel.repaint();
}//show category method
    public static void main(String args[]){
        new EcoMartHome();
    }
}