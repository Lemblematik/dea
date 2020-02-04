package com.cmr.dea.carrierapi.demo.service;

import com.cmr.dea.carrierapi.demo.shared.CarrierDto;

import java.util.List;

public interface CarriersService {
    CarrierDto createCarrier(CarrierDto clientDto);
    CarrierDto getInfosCarrier(String carrierId);
    CarrierDto updateInsertedClientInfos(String wareId, CarrierDto carrierDetails);
    void deleteCarrier(String carrierId);
    List<CarrierDto> getAllWare();

    List<CarrierDto> getAllCarrierInMarket(String marketName);
}
