public class Billing{
    private int points=0;
    private double gst=0;
    public double GST(double item){
        if(item>10000){
            gst=18;
        }
        else if(item>5000){
            gst=12;
        }
        else if(item>1500){
            gst=8;
        }
        else{
            gst=4.7;
        }
        double gstAmount=item*gst/100;
        return gstAmount;
    }
    public double TotalAmount(double amount,double gstAmount){
        double total=amount+gstAmount;
        if(total>50000){
            points=150;
        }
        else if(total>25000){
            points=100;
        }
        else if(total>10000){
            points=50;
        }
            return total;
    }
    public double Coupons(String c,double amt){
        String c1="NEWUSER910";
        String c2="NEWeco10";
        if(c1.equalsIgnoreCase(c)){
            amt=amt-amt*0.15;
        }
        else if(c2.equalsIgnoreCase(c)){
            amt=amt-amt*0.10;
        }
        if(amt<0){
            amt=0;
        }
        return amt;
}
    public int RewardPoints(int totalPoints){
        totalPoints=totalPoints+points;
        return totalPoints;
    }
    public int CarbonFootprint(int carbon){
        if(carbon>500){
            points=points+170;
        }
        else if(carbon>300){
            points=points+100;
        }
        else if(carbon>100){
            points=points+50;
        }
        else{
            points=points+5;
        }
        return points;
    }
    public double getGST(){
        return gst;
    }
    public int getPoints(){
        return points;
    }
    public void resetGST(){
        gst=0;
    }
    public void resetPoints(){
        points=0;
    }
}
