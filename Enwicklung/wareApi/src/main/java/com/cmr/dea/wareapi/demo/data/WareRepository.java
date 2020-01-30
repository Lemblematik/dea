package com.cmr.dea.wareapi.demo.data;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WareRepository extends CrudRepository<WareEntity,Long> {
    WareEntity findByWareId(String wareId);
    List<WareEntity> findByWareSellerId(String sellerId);
    List<WareEntity> findByCartTrueAndWareSellerId(String sellerId);
    List<WareEntity> findByMarketPlaceAndCategory(String marketPlace, String category);
    List<WareEntity> findByMarketPlaceAndCategoryStartingWithOrNameStartingWith(String marketPlace, String category,String name);

}
