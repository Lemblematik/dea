package com.cmr.dea.carrierapi.demo.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarriersRepository extends CrudRepository<CarrierEntity,Long> {
    CarrierEntity findByCarrierId(String clientId);
    List<CarrierEntity> findByMarketName(String marketName);
}
