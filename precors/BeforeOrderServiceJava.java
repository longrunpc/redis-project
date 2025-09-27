import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BeforeOrderServiceJava {

    private static final Logger logger = LoggerFactory.getLogger(BeforeOrderServiceJava.class);

    // 상품 DB
    private final Map<String, AtomicInteger> productDatabase = new ConcurrentHashMap<>();
    // 가장 최근 주문 정보를 저장하는 DB
    private final Map<String, OrderInfo> latestOrderDatabase = new ConcurrentHashMap<>();

    private final ThreadLocal<OrderInfo> threadLocalOrder = new ThreadLocal<>();


    public BeforeOrderServiceJava() {
        // 초기 상품 데이터
        productDatabase.put("apple", new AtomicInteger(100));
        productDatabase.put("banana", new AtomicInteger(50));
        productDatabase.put("orange", new AtomicInteger(75));
    }

    // 주문 처리 메서드
    public void order(String productName, int amount) {
        AtomicInteger stock = productDatabase.get(productName);

        try {
            Thread.sleep(1); // 동시성 이슈 유발을 위한 인위적 지연
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        while(true) {
            int currentStock = stock.get();
            if(currentStock < amount) {
                break;
            }
            if (stock.compareAndSet(currentStock, currentStock - amount)) {
                OrderInfo orderInfo = new OrderInfo(productName, amount, System.currentTimeMillis());
                threadLocalOrder.set(orderInfo);
    
                latestOrderDatabase.put(productName, threadLocalOrder.get());
    
                logger.info("Thread {} 주문 정보:", Thread.currentThread().getName().split("-")[3] +"\n"+
                            " - CurrentStock: " + (currentStock - amount) + "\n" +
                            productName + ": 1 건 ([" + amount +"])");

                threadLocalOrder.remove();
                break;
            }
        }
        
    }

    // 재고 조회
    public int getStock(String productName) {
        return productDatabase.get(productName).get();
    }
}