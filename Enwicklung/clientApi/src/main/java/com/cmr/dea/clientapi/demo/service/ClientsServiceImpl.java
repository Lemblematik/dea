package com.cmr.dea.clientapi.demo.service;

import com.cmr.dea.clientapi.demo.data.ClientEntity;
import com.cmr.dea.clientapi.demo.data.ClientsRepository;
import com.cmr.dea.clientapi.demo.shared.ClientDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

//to transform data from clientDto to clientEntity, you need to use modelMapper
@Service
public class ClientsServiceImpl implements ClientsService {
    ClientsRepository clientsRepository;
    @Autowired
    public ClientsServiceImpl(ClientsRepository clientsRepository){
        this.clientsRepository = clientsRepository;
        setInfosAdminInDb();
    }

    //set infos admin already in db
    public void setInfosAdminInDb(){
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setLastName("DEA");
        clientEntity.setFirstName("Group");
        clientEntity.setPhone("88888888");
        clientEntity.setAdress("Yaounde");
        clientEntity.setClientId(UUID.randomUUID().toString());
        this.clientsRepository.save(clientEntity);
    }

    @Override
    public ClientDto createClient(ClientDto clientDetails) {
        clientDetails.setClientId(UUID.randomUUID().toString());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ClientEntity clientEntity = modelMapper.map(clientDetails, ClientEntity.class);

        clientsRepository.save(clientEntity);

        ClientDto returnValue = modelMapper.map(clientEntity,ClientDto.class);
        return returnValue;
    }

    @Override
    public ClientDto getInfosClient(String clientId){
        if(clientId != null){
            ClientDto clientDto = new ModelMapper().map(clientsRepository.findByClientId(clientId),ClientDto.class);
            return clientDto;
        }
        return null;
    }

    @Override
    public ClientDto updateInsertedClientInfos(String clientId, ClientDto clientDetails) {
        ClientEntity clientEntity = clientsRepository.findByClientId(clientId);
        clientEntity.setFirstName(clientDetails.getFirstName());
        clientEntity.setLastName(clientDetails.getLastName());
        clientEntity.setPhone(clientDetails.getPhone());
        clientEntity.setAdress(clientDetails.getAdress());
        clientsRepository.save(clientEntity);

        return new ModelMapper().map(clientEntity,ClientDto.class);
    }

    @Override
    public void deleteClient(String clientId) {
        if(clientId != null){
            //for db
            clientsRepository.delete(clientsRepository.findByClientId(clientId));
        }
    }



    @Override
    public List<ClientDto> getAllClient(){
        List<ClientDto> results = new ArrayList<>();
        for(ClientEntity wareEntity: clientsRepository.findAll()){
            results.add(new ModelMapper().map(wareEntity,ClientDto.class));
        }
        return results;
    }

    @Override
    public ClientDto getClientWithNumber(String number) {
        if(number != null){
            ClientDto clientDto = new ModelMapper().map(clientsRepository.findByPhone(number),ClientDto.class);
            return clientDto;
        }
        return null;
    }


}
