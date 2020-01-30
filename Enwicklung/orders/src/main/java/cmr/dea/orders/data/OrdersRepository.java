package cmr.dea.orders.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrdersRepository extends CrudRepository<OrdersEntity,Long> {
    OrdersEntity findByOrdersId(String orderId);
    List<OrdersEntity> findByWareSellerId(String wareSellerId);
}
