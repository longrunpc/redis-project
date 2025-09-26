public class OrderInfo{
    String productName;
    int amount;
    long orderTime;

    public OrderInfo(String productName, int amount, long orderTime) {
        this.productName = productName;
        this.amount = amount;
        this.orderTime = orderTime;
    }
}