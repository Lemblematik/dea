package com.cmr.dea.carrierapi.demo.Controller;

import com.cmr.dea.carrierapi.demo.modell.CarrierRequestModell;
import com.cmr.dea.carrierapi.demo.modell.CarrierResponseModel;
import com.cmr.dea.carrierapi.demo.service.CarriersService;
import com.cmr.dea.carrierapi.demo.shared.CarrierDto;
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
@RequestMapping("/carriers")
public class CarrierController {
    @Autowired
    private Environment environment;

    @Autowired
    CarriersService carriersService;


    @GetMapping("/status")
    public String status(){
        return "carrier microservice working on port " + environment.getProperty("local.server.port");
    }

    @PostMapping
    public ResponseEntity<CarrierResponseModel> createCarrier(@Valid @RequestBody CarrierRequestModell carrierDetails){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //data from frontend to db modell
        CarrierDto clientDto = modelMapper.map(carrierDetails, CarrierDto.class);
        CarrierDto createdClient = carriersService.createCarrier(clientDto);

        //for response modell
        CarrierResponseModel returnValue = modelMapper.map(createdClient,CarrierResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping(value = "/{wareId}")
    public ResponseEntity<CarrierResponseModel> getClient(@PathVariable("wareId") String wareId){
        CarrierDto clientDto = carriersService.getInfosCarrier(wareId);
        CarrierResponseModel returnValue = new ModelMapper().map(clientDto,CarrierResponseModel.class);
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @PutMapping(path = "/{clientId}")
    public ResponseEntity<CarrierResponseModel> updateClient(@PathVariable("clientId") String clientId, @Valid @RequestBody CarrierRequestModell wareDetails){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //data from frontend to db modell
        CarrierDto wareDto = modelMapper.map(wareDetails, CarrierDto.class);
        CarrierDto createdClient = carriersService.updateInsertedClientInfos(clientId, wareDto);

        //for response modell
        CarrierResponseModel returnValue = modelMapper.map(createdClient,CarrierResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @DeleteMapping(value = "/{clientId}")
    public ResponseEntity<Void> deleteWare(@PathVariable("clientId") String clientId){
        carriersService.deleteCarrier(clientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/all")
    public List<CarrierDto> getAllClients(){
        List<CarrierDto> clientDtos = carriersService.getAllWare();
        return clientDtos;
    }

    @GetMapping(value = "/marketName/{marketName}")
    public List<CarrierDto> getAllClients(@PathVariable("marketName") String marketName){
        List<CarrierDto> clientDtos = carriersService.getAllCarrierInMarket(marketName);
        return clientDtos;
    }


}
