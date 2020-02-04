package cmr.dea.orders.service;

import cmr.dea.orders.data.OrdersEntity;
import cmr.dea.orders.data.OrdersRepository;
import cmr.dea.orders.shared.OrderDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class OrdersServiceImpl implements OrdersService {
    public OrdersRepository ordersRepository;

    @Autowired
    public OrdersServiceImpl(OrdersRepository ordersRepository){
        this.ordersRepository = ordersRepository;
    }
    @Override
    public OrderDto createNewOrder(OrderDto orderDto) {
        orderDto.setOrdersId(UUID.randomUUID().toString());
        orderDto.setNameClient(orderDto.getNameClient());
        orderDto.setNameProduct(orderDto.getNameProduct());
        orderDto.setNumberProduct(orderDto.getNumberProduct());
        orderDto.setPhoneNbr(orderDto.getPhoneNbr());
        orderDto.setRecoveryDate(orderDto.getRecoveryDate());
        orderDto.setWareSellerId(orderDto.getWareSellerId());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrdersEntity ordersEntity = modelMapper.map(orderDto,OrdersEntity.class);
        ordersRepository.save(ordersEntity);
        OrderDto result = modelMapper.map(ordersEntity,OrderDto.class);
        return result;
    }

    @Override
    public List<OrderDto> getAllOrdersFromWareSeller(String wareSellerId) {
        List<OrderDto> results = new ArrayList<>();
        for(OrdersEntity ordersEntity: ordersRepository.findByWareSellerId(wareSellerId)){
            results.add(new ModelMapper().map(ordersEntity,OrderDto.class));
        }
        return results;
    }

    @Override
    public void deleteOrder(String orderId) {
        if(orderId !=null) {
            ordersRepository.delete(ordersRepository.findByOrdersId(orderId));
        }
    }
}
