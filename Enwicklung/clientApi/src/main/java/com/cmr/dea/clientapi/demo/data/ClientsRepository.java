package com.cmr.dea.clientapi.demo.data;

import org.springframework.data.repository.CrudRepository;
// to administrate the persistance of data
public interface ClientsRepository extends CrudRepository<ClientEntity,Long> {
    ClientEntity findByClientId(String clientId);
    ClientEntity findByPhone(String number);
}
