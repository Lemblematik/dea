package com.dea.anaconda.demo.service;

import com.dea.anaconda.demo.data.MeubleEntity;
import com.dea.anaconda.demo.data.MeubleRepository;
import com.dea.anaconda.demo.shared.MeubleDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MeubleServiceImpl implements MeubleService {

    public MeubleRepository meubleRepository;


    @Autowired
    public MeubleServiceImpl(MeubleRepository meubleRepository){
        this.meubleRepository = meubleRepository;
    }

    @Override
    public MeubleDto createMeuble(MeubleDto productDto) {
        productDto.setMeubleId(UUID.randomUUID().toString());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        MeubleEntity carrierEntity = modelMapper.map(productDto, MeubleEntity.class);
        meubleRepository.save(carrierEntity);
        MeubleDto returnValue = modelMapper.map(carrierEntity,MeubleDto.class);
        return returnValue;
    }

    @Override
    public MeubleDto getInfosMeuble(String meubleId) {
        if (meubleId != null) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            MeubleEntity meubleEntity = meubleRepository.findByMeubleId(meubleId);
            return modelMapper.map(meubleEntity,MeubleDto.class);
        }
        return null;
    }

    @Override
    public List<MeubleDto> getAllMeuble() {
        //when add ware in seller
        List<MeubleDto> results = new ArrayList<>();
        for(MeubleEntity wareEntity: meubleRepository.findAll()){
            results.add(new ModelMapper().map(wareEntity,MeubleDto.class));
        }
        return results;
    }

    @Override
    public void deleteMeuble(String meubleId) {
        meubleRepository.delete(meubleRepository.findByMeubleId(meubleId));
    }

    @Override
    public MeubleDto updateInsertedMeubleInfos(String meubleId, MeubleDto meubleDto) {
        MeubleEntity meubleEntity = meubleRepository.findByMeubleId(meubleId);
        meubleEntity.setDescription(meubleDto.getDescription());
        meubleEntity.setFotos(meubleDto.getFotos());
        meubleEntity.setName(meubleDto.getName());
        meubleEntity.setLastPrice(meubleDto.getLastPrice());
        meubleEntity.setNewPrice(meubleDto.getNewPrice());
        meubleEntity.setPromo(meubleDto.isPromo());
        meubleRepository.save(meubleEntity);
        return new ModelMapper().map(meubleEntity,MeubleDto.class);

    }

    @Override
    public List<MeubleDto> getInfoMeubleWithInputName(String input) {
        if (input != null) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            List<MeubleEntity> meubleEntity = meubleRepository.findByName(input);
            List<MeubleDto> results = new ArrayList<>();
            for(MeubleEntity wareEntity: meubleEntity){
                results.add(new ModelMapper().map(wareEntity,MeubleDto.class));
            }
            return results;
        }
        return null;
    }

    @Override
    public List<MeubleDto> getInfoMeubleWithInputCategory(String category) {
        if (category != null) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            List<MeubleEntity> meubleEntities= meubleRepository.findByCategory(category);
            List<MeubleDto> results = new ArrayList<>();
            for(MeubleEntity wareEntity: meubleEntities){
                results.add(new ModelMapper().map(wareEntity,MeubleDto.class));
            }
            return results;
        }
        return null;
    }
}
