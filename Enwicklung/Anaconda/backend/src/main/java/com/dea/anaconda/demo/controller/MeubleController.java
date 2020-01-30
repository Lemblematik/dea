package com.dea.anaconda.demo.controller;

import com.dea.anaconda.demo.model.MeubleRequestModel;
import com.dea.anaconda.demo.model.MeubleResponseModel;
import com.dea.anaconda.demo.service.MeubleService;
import com.dea.anaconda.demo.shared.MeubleDto;
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
@RequestMapping("/meubles")
public class MeubleController {
    @Autowired
    private Environment environment;

    @Autowired
    MeubleService meubleService;



    @GetMapping("/status")
    public String status(){
        return "meuble microservice working on port " + environment.getProperty("local.server.port");
    }

    @PostMapping
    public ResponseEntity<MeubleResponseModel> createMeuble(@Valid @RequestBody MeubleRequestModel details){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //data from frontend to db modell
        MeubleDto productDto = modelMapper.map(details, MeubleDto.class);
        MeubleDto createdClient = meubleService.createMeuble(productDto);
        //for response modell
        MeubleResponseModel returnValue = modelMapper.map(createdClient,MeubleResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping(value = "/{meubleId}")
    public ResponseEntity<MeubleResponseModel> getMeuble(@PathVariable("meubleId") String meubleId){
        MeubleDto clientDto = meubleService.getInfosMeuble(meubleId);
        MeubleResponseModel returnValue = new ModelMapper().map(clientDto,MeubleResponseModel.class);
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @PutMapping(path = "/{meubleId}")
    public ResponseEntity<MeubleResponseModel> updateMeuble(@PathVariable("meubleId") String clientId, @Valid @RequestBody MeubleRequestModel wareDetails){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //data from frontend to db modell
        MeubleDto meubleDto = modelMapper.map(wareDetails, MeubleDto.class);
        MeubleDto createdClient = meubleService.updateInsertedMeubleInfos(clientId, meubleDto);

        //for response modell
        MeubleResponseModel returnValue = modelMapper.map(createdClient,MeubleResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @DeleteMapping(value = "/{meubleId}")
    public ResponseEntity<Void> deleteWare(@PathVariable("meubleId") String clientId){
        meubleService.deleteMeuble(clientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/all")
    public List<MeubleDto> getAllClients(){
        List<MeubleDto> clientDtos = meubleService.getAllMeuble();
        return clientDtos;
    }

    @GetMapping(value = "/name/{searchName}")
    public List<MeubleDto> getMeubleWithSearch(@PathVariable("searchName") String meubleId){
       List<MeubleDto> meubleDtos = meubleService.getInfoMeubleWithInputName(meubleId);
        return meubleDtos;
    }

    @GetMapping(value = "/category/{searchName}")
    public List<MeubleDto> getMeubleWithSearchCategory(@PathVariable("searchName") String category){
        return meubleService.getInfoMeubleWithInputCategory(category);
    }

}
