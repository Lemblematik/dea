package com.dea.anaconda.demo.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MeubleRepository extends CrudRepository<MeubleEntity,Long> {
    MeubleEntity findByMeubleId(String meubleId);
    List<MeubleEntity> findByName(String input);
    List<MeubleEntity> findByCategory(String category);

}
