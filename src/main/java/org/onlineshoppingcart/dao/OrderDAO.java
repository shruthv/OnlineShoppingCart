package org.onlineshoppingcart.dao;
 
import java.util.List;
 
import org.onlineshoppingcart.model.CartInfo;
import org.onlineshoppingcart.model.OrderDetailInfo;
import org.onlineshoppingcart.model.OrderInfo;
import org.onlineshoppingcart.model.PaginationResult;
 
public interface OrderDAO {
 
    public void saveOrder(CartInfo cartInfo);
 
    public PaginationResult<OrderInfo> listOrderInfo(int page, int maxResult, int maxNavigationPage);
    
    public OrderInfo getOrderInfo(String orderId);
    
    public List<OrderDetailInfo> listOrderDetailInfos(String orderId);
 
}