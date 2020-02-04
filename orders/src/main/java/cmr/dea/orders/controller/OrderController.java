package cmr.dea.orders.controller;

import cmr.dea.orders.modell.OrdersRequestModell;
import cmr.dea.orders.modell.OrdersResponseModell;
import cmr.dea.orders.service.OrdersService;
import cmr.dea.orders.shared.OrderDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private Environment environment;

    @Autowired
    OrdersService ordersService;


    @GetMapping("/status")
    public String status(){
        return "orders microservice working on port " + environment.getProperty("local.server.port");
    }

    @GetMapping(value = "/wareSellerId/{wareSellerId}")
    public List<OrderDto> getAllWareSellerId(@PathVariable("wareSellerId") String wareSellerId){
        List<OrderDto> wareDtos = ordersService.getAllOrdersFromWareSeller(wareSellerId);
        return wareDtos;

    }

    @PostMapping
    public ResponseEntity<OrdersResponseModell> createNewOrder(@Valid @RequestBody OrdersRequestModell ordersRequestModell){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //data from frontend to db modell
        OrderDto orderDto = modelMapper.map(ordersRequestModell, OrderDto.class);
        OrderDto createdClient = ordersService.createNewOrder(orderDto);
        //for response modell
        OrdersResponseModell returnValue = modelMapper.map(createdClient,OrdersResponseModell.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @DeleteMapping(value = "/{ordersId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("ordersId") String ordersId){
        ordersService.deleteOrder(ordersId);
        return ResponseEntity.noContent().build();
    }


}
