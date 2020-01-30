package com.cmr.dea.wareapi.demo.controller;

import com.cmr.dea.wareapi.demo.modell.WareRequestModell;
import com.cmr.dea.wareapi.demo.modell.WareResponseModell;
import com.cmr.dea.wareapi.demo.data.WareRepository;
import com.cmr.dea.wareapi.demo.service.WareService;
import com.cmr.dea.wareapi.demo.shared.WareDto;
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
@RequestMapping("/wares")
public class WareController {
    @Autowired
    private Environment environment;

    @Autowired
    WareService wareService;


    @GetMapping("/status")
    public String status(){
        return "ware microservice working on port " + environment.getProperty("local.server.port");
    }


    @PostMapping
    public ResponseEntity<WareResponseModell> createWare(@Valid @RequestBody WareRequestModell wareDetails){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //data from frontend to db modell
        WareDto clientDto = modelMapper.map(wareDetails, WareDto.class);
        WareDto createdClient = wareService.createWare(clientDto);
        //for response modell
        WareResponseModell returnValue = modelMapper.map(createdClient,WareResponseModell.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping(value = "/{wareId}")
    public ResponseEntity<WareResponseModell> getWare(@PathVariable("wareId") String wareId){
        WareDto clientDto = wareService.getInfosWare(wareId);
        WareResponseModell returnValue = new ModelMapper().map(clientDto,WareResponseModell.class);
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @PatchMapping(path = "/{wareId}")
    public ResponseEntity<WareResponseModell> updateWare(@PathVariable("wareId") String wareId, @Valid @RequestBody WareResponseModell wareDetails){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //data from frontend to db modell
        WareDto wareDto = modelMapper.map(wareDetails, WareDto.class);
        WareDto createdClient = wareService.updateInsertedClientInfos(wareId, wareDto);

        //for response modell
        WareResponseModell returnValue = modelMapper.map(createdClient,WareResponseModell.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @DeleteMapping(value = "/{clientId}")
    public ResponseEntity<Void> deleteWare(@PathVariable("clientId") String clientId){
        wareService.deleteClient(clientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/all")
    public List<WareDto> getAllWare(){
        List<WareDto> wareDtos = wareService.getAllWare();
        return wareDtos;
    }

    @GetMapping(value = "/sellers/{sellerId}")
    public List<WareDto> getAllWareForSpecialSeller(@PathVariable("sellerId") String sellerId){
        List<WareDto> sellerDtos = wareService.getAllWareForSpecialSeller(sellerId);
        return sellerDtos;
    }


    @GetMapping(value = "/carts/{sellerId}")
    public List<WareDto> getAllWaresInCart(@PathVariable("sellerId") String sellerId){
        List<WareDto> wareDtos = wareService.getAllWareInCart(sellerId);
        return wareDtos;
    }




    @GetMapping(value = "/categories/{marketPlace}/{category}")
    public List<WareDto> getAllWaresFromCategory(@PathVariable("marketPlace")String marketPlace,@PathVariable("category") String category){
        List<WareDto> wareDtos = wareService.getAllWAreWithSpecialCategory(marketPlace,category);
        return wareDtos;
    }

    @GetMapping(value = "/search/{marketPlace}/{category}/{name}")
    public List<WareDto> getSearchOfWare(@PathVariable("marketPlace")String marketPlace, @PathVariable("category")String category,@PathVariable("name")String name ){
        List<WareDto> wareDtos = wareService.getSearchOfWare(marketPlace,category,name);
        return wareDtos;
    }



}
