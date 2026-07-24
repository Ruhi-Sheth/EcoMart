import java.util.Vector;
import java.util.HashMap;
import Payment.Billing;
class ProductCatalog{
String products[]={"Organic Rice","Organic Wheat Flour","Organic Honey","Millets",
"Cold Pressed Coconut Oil","Bamboo Toothbrush","Natural Soap","Herbal Shampoo",
"Organic Cotton T-Shirt","Jute Bag","Bamboo Socks","Hemp Shirt",
"Bamboo Chair","Wooden Table","Cane Sofa","Recycled Plastic Stool",
"Clay Flower Pot","Bamboo Lamp","Wooden Wall Clock","Jute Door Mat"};

double price[]={1200,1496,499,850,
485,48,350,2800,
1700,300,96,1800,
600,3000,80000,450,
799,1200,1238,699};
}
class ShoppingList{
    boolean addItem(String str,Vector<String> v){
        v.add(str);
        return true;
    }
    boolean deleteItem(String str,Vector<String> v){
        for(int i=0;i<v.size();i++){
            if(str.equals(v.get(i))){
                v.remove(i);
                return true;
            }
        }
        return false;
    }
    int searchItem(String str,Vector<String> v){
        for(int i=0;i<v.size();i++){
            if(str.equals(v.get(i))){
                return i;
            }
        }
        return -1;
    }
    String displayItem(Vector<String> v){
        String items="";
        for(int i=0;i<v.size();i++){
            items=items+(i+1)+". "+v.get(i)+"\n";
        }
        return items;
    }
}
    interface Discount{
        double calculateDiscount(String product,double amount);
    }
class CategoryDiscount implements Discount{
    HashMap<String,Integer> discountMap=new HashMap<>();
    HashMap<String,String> categoryMap=new HashMap<>();
    CategoryDiscount(){
discountMap.put("Grocery",5);
        discountMap.put("Kitchen",10);
        discountMap.put("Personal Care",12);
        discountMap.put("Home Decor",15);
        discountMap.put("Fashion",20);
        discountMap.put("Furniture",25);

        categoryMap.put("Organic Rice","Grocery");//1200
        categoryMap.put("Organic Wheat Flour","Grocery");//1496
        categoryMap.put("Organic Honey","Grocery");//326
        categoryMap.put("Millets","Grocery");//
        categoryMap.put("Cold Pressed Coconut Oil","Grocery");//485

        categoryMap.put("Bamboo Toothbrush","Personal Care");//48
        categoryMap.put("Natural Soap","Personal Care");//350
        categoryMap.put("Herbal Shampoo","Personal Care");//2800

        categoryMap.put("Organic Cotton T-Shirt","Fashion");//2500
        categoryMap.put("Jute Bag","Fashion");//100
        categoryMap.put("Bamboo Socks","Fashion");//96
        categoryMap.put("Hemp Shirt","Fashion");//1800

        categoryMap.put("Bamboo Chair","Furniture");//500
        categoryMap.put("Wooden Table","Furniture");//3000
        categoryMap.put("Cane Sofa","Furniture");//80000
        categoryMap.put("Recycled Plastic Stool","Furniture");//450

        categoryMap.put("Clay Flower Pot","Home Decor");//863
        categoryMap.put("Bamboo Lamp","Home Decor");//740
        categoryMap.put("Wooden Wall Clock","Home Decor");//1238
        categoryMap.put("Jute Door Mat","Home Decor");//699
}
    public int getDiscount(String product){
        String category=categoryMap.get(product);
        if(category==null){
            return 0;
        }
        return discountMap.get(category);
    }
    public double calculateDiscount(String product,double amount){
        int discount=getDiscount(product);
            return amount-(amount*discount/100.0);
        }
    }
    public class EcoMart{
        Vector<String> cart=new Vector<String>(10,5);
        ShoppingList obj=new ShoppingList();
        ProductCatalog p=new ProductCatalog();
        CategoryDiscount dis=new CategoryDiscount();
        Billing bill=new Billing();
        double totalAmount=0;
        double totalDiscount=0;
        int totalCarbon=0;
        void addItem(int productNo){
            if(productNo>=1 && productNo<=p.products.length){
                obj.addItem(p.products[productNo-1],cart);
                totalAmount=totalAmount+p.price[productNo-1];
                totalCarbon=totalCarbon+50;
                int d=dis.getDiscount(p.products[productNo-1]);
                totalDiscount=totalDiscount+(p.price[productNo-1]*d/100.0);
            }
        }
        void deleteItem(int productNo){
            if(productNo>=1 && productNo<=p.products.length){
                if(obj.deleteItem(p.products[productNo-1],cart)){
                    totalAmount=totalAmount-p.price[productNo-1];
                    totalCarbon=totalCarbon-50;
                    if(totalCarbon<0){
                        totalCarbon=0;
                    }
                    int d=dis.getDiscount(p.products[productNo-1]);
                    totalDiscount=totalDiscount-(p.price[productNo-1]*d/100.0);
                    if(totalAmount<0){
                        totalAmount=0;
                    }
                    if(totalDiscount<0){
                        totalDiscount=0;
                    }
                }
            }
        }
        int searchItem(String str){
            return obj.searchItem(str,cart);
        }
        String displayCart(){
            return obj.displayItem(cart);
        }
        String displayProducts(){
            String s="";
            for(int i=0;i<p.products.length;i++){
                s=s+(i+1)+". "+p.products[i]+"    Rs."+p.price[i]+"\n";
            }
            return s;
        }
    double generateBill(String couponCode,int carbon){
        double amountAfterDiscount=totalAmount-totalDiscount;
        amountAfterDiscount=bill.Coupons(couponCode,amountAfterDiscount);
        double gst=bill.GST(amountAfterDiscount);
        double finalAmount=bill.TotalAmount(amountAfterDiscount,gst);
        bill.CarbonFootprint(carbon);
        bill.RewardPoints(0);
        return finalAmount;
    }
    void clearCart(){
        cart.clear();
        totalAmount=0;
        totalDiscount=0;
        totalCarbon=0;
    }
}