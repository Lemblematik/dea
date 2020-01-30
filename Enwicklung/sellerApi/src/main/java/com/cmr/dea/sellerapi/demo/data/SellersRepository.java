package com.cmr.dea.sellerapi.demo.data;

import org.springframework.data.repository.CrudRepository;

public interface SellersRepository extends CrudRepository<SellerEntity,Long> {

    SellerEntity findBySellerId(String sellerId);
}
