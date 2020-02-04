package com.cmr.dea.wareapi.demo.service;

import com.cmr.dea.wareapi.demo.shared.WareDto;

import java.util.List;

public interface WareService {
    WareDto createWare(WareDto wareDto);
    WareDto getInfosWare(String wareId);
    WareDto updateInsertedClientInfos(String wareId, WareDto wareDetails);
    void deleteClient(String wareId);
    List<WareDto> getAllWare();
    List<WareDto> getAllWareForSpecialSeller(String sellerId);
    List<WareDto> getAllWareInCart(String sellerId);
    List<WareDto> getAllWAreWithSpecialCategory(String marketPlace, String category);
    List<WareDto> getSearchOfWare(String marketPlace, String category, String name);
}
