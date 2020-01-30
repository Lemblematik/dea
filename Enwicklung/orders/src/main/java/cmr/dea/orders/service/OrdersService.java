package cmr.dea.orders.service;

import cmr.dea.orders.shared.OrderDto;

import java.util.List;

public interface OrdersService {
    OrderDto createNewOrder(OrderDto orderDto);
    List<OrderDto> getAllOrdersFromWareSeller(String wareSellerId);
    void deleteOrder(String orderId);
}
