package com.cmr.dea.clientapi.demo.Controller;


import com.cmr.dea.clientapi.demo.modell.ClientRequestModel;
import com.cmr.dea.clientapi.demo.modell.ClientResponseModel;
import com.cmr.dea.clientapi.demo.service.ClientsService;
import com.cmr.dea.clientapi.demo.shared.ClientDto;
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
@RequestMapping("/clients")
public class ClientsController {
    //define whish instanse it should be launched

    @Autowired
    private Environment environment;

    @Autowired
    ClientsService clientsService;

    @GetMapping("/status")
    public String status(){
        return "client microservice working on port " + environment.getProperty("local.server.port");
    }

    @PostMapping
    public ResponseEntity<ClientResponseModel> createClient(@Valid @RequestBody ClientRequestModel clientDetails){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //data from frontend to db modell
        ClientDto clientDto = modelMapper.map(clientDetails, ClientDto.class);
        ClientDto createdClient = clientsService.createClient(clientDto);

        //for response modell
        ClientResponseModel returnValue = modelMapper.map(createdClient,ClientResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping(value = "/{clientId}")
    public ResponseEntity<ClientResponseModel> getClient(@PathVariable("clientId") String clientId){
        ClientDto clientDto = clientsService.getInfosClient(clientId);
        ClientResponseModel returnValue = new ModelMapper().map(clientDto,ClientResponseModel.class);
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @PutMapping(path = "/{clientId}")
    public ResponseEntity<ClientResponseModel> updateClient(@PathVariable("clientId") String clientId, @Valid @RequestBody ClientRequestModel clientDetails){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //data from frontend to db modell
        ClientDto clientDto = modelMapper.map(clientDetails, ClientDto.class);
        ClientDto createdClient = clientsService.updateInsertedClientInfos(clientId, clientDto);

        //for response modell
        ClientResponseModel returnValue = modelMapper.map(createdClient,ClientResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @DeleteMapping(value = "/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable("clientId") String clientId){
        clientsService.deleteClient(clientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/all")
    public List<ClientDto> getAllClients(){
        List<ClientDto> clientDtos = clientsService.getAllClient();
        return clientDtos;
    }

    @GetMapping(value = "/phone/{phone}")
    public ResponseEntity<ClientResponseModel> getClientWithNumber(@PathVariable("phone") String phone){
        ClientDto clientDto = clientsService.getClientWithNumber(phone);
        ClientResponseModel returnValue = new ModelMapper().map(clientDto,ClientResponseModel.class);
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

}
