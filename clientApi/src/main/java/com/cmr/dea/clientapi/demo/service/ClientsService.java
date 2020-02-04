package com.cmr.dea.clientapi.demo.service;

import com.cmr.dea.clientapi.demo.shared.ClientDto;

import java.util.List;

public interface ClientsService {
    ClientDto createClient(ClientDto clientDetails);
    ClientDto getInfosClient(String clientId);
    ClientDto updateInsertedClientInfos(String clientId, ClientDto clientDetails);
    void deleteClient(String clientId);
    List<ClientDto> getAllClient();
    ClientDto getClientWithNumber(String number);

    //CRUD in Ware List<Ware> cart; man kann vielleicht danach searchWithName local einfügen
    //List<WareResponseModell> getAllWare(String sellerId);
    //void addWareInCart(String wareId);  //Command
    //void removeWareInCart(String wareId);
}
