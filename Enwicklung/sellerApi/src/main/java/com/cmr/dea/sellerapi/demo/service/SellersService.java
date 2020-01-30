package com.cmr.dea.sellerapi.demo.service;

import com.cmr.dea.sellerapi.demo.shared.SellerDto;

import java.util.List;

public interface SellersService {
    SellerDto createSeller(SellerDto sellerDto);

    SellerDto getInfosSeller(String sellerId);

    SellerDto updateInsertedSellerInfos(String clientId, SellerDto wareDto);

    void deleteSellerId(String sellerId);

    List<SellerDto> getAllSeller();
}
