package com.cmr.dea.sellerapi.demo.controller;

import com.cmr.dea.sellerapi.demo.modell.SellerRequestModell;
import com.cmr.dea.sellerapi.demo.modell.SellerResponseModell;
import com.cmr.dea.sellerapi.demo.service.SellersService;
import com.cmr.dea.sellerapi.demo.shared.SellerDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    @Autowired
    private Environment environment;

    @Autowired
    SellersService sellersService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/status")
    public String status(){
        return "seller microservice working on port " + environment.getProperty("local.server.port");
    }

    @PostMapping
    public ResponseEntity<SellerResponseModell> createCarrier(@Valid @RequestBody SellerRequestModell carrierDetails){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //data from frontend to db modell
        SellerDto clientDto = modelMapper.map(carrierDetails, SellerDto.class);
        SellerDto createdClient = sellersService.createSeller(clientDto);

        //for response modell
        SellerResponseModell returnValue = modelMapper.map(createdClient,SellerResponseModell.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping(value = "/{sellerId}")
    public ResponseEntity<SellerResponseModell> getClient(@PathVariable("sellerId") String sellerId){

        SellerDto clientDto = sellersService.getInfosSeller(sellerId);
        SellerResponseModell returnValue = new ModelMapper().map(clientDto,SellerResponseModell.class);
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @PutMapping(path = "/{sellerId}")
    public ResponseEntity<SellerResponseModell> updateClient(@PathVariable("sellerId") String sellerId, @Valid @RequestBody SellerRequestModell wareDetails){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //data from frontend to db modell
        SellerDto sellerDto = modelMapper.map(wareDetails, SellerDto.class);
        SellerDto createdClient = sellersService.updateInsertedSellerInfos(sellerId, sellerDto);

        //for response modell
        SellerResponseModell returnValue = modelMapper.map(createdClient,SellerResponseModell.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @DeleteMapping(value = "/{sellerId}")
    public ResponseEntity<Void> deleteWare(@PathVariable("sellerId") String sellerId){
        sellersService.deleteSellerId(sellerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/all")
    public List<SellerDto> getAllClients(){
        List<SellerDto> sellerDtos = sellersService.getAllSeller();
        return sellerDtos;
    }
}
