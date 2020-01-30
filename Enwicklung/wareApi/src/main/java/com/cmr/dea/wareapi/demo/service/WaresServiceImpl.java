package com.cmr.dea.wareapi.demo.service;


import com.cmr.dea.wareapi.demo.data.WareEntity;
import com.cmr.dea.wareapi.demo.data.WareRepository;
import com.cmr.dea.wareapi.demo.shared.WareDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WaresServiceImpl implements WareService  {

    public WareRepository waresRepository;


    @Autowired
    public WaresServiceImpl(WareRepository waresRepository){
        this.waresRepository = waresRepository;
    }

    @Override
    public WareDto createWare(WareDto wareDto) {
        wareDto.setWareId(UUID.randomUUID().toString());
        wareDto.setCart(false);
        wareDto.setCount(1);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        WareEntity carrierEntity = modelMapper.map(wareDto, WareEntity.class);
        waresRepository.save(carrierEntity);
        WareDto returnValue = modelMapper.map(carrierEntity,WareDto.class);

        //for test without db
        return returnValue;
    }

    @Override
    public WareDto getInfosWare(String wareId) {
        if (wareId != null) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            WareEntity wareEntity = waresRepository.findByWareId(wareId);
            WareDto wareDto = modelMapper.map(wareEntity,WareDto.class);
            return wareDto;
        }
        return null;
    }

    @Override
    public WareDto updateInsertedClientInfos(String wareId, WareDto wareDetails) {
        WareEntity wareEntity = waresRepository.findByWareId(wareId);
        wareEntity.setDescription(wareDetails.getDescription());
        wareEntity.setFotos(wareDetails.getFotos());
        wareEntity.setName(wareDetails.getName());
        wareEntity.setPrice(wareDetails.getPrice());
        wareEntity.setCart(wareDetails.isCart());
        wareEntity.setCategory(wareDetails.getCategory());
        wareEntity.setCount(wareDetails.getCount());
        wareEntity.setSubCategory(wareDetails.getSubCategory());
        wareEntity.setMarketPosition(wareDetails.getMarketPosition());
        wareEntity.setPhone(wareDetails.getPhone());
        wareEntity.setWareSellerId(wareDetails.getWareSellerId());
        wareEntity.setMarketPlace(wareDetails.getMarketPlace());
        wareEntity.setRightAgree(wareDetails.isRightAgree());

        wareEntity.setDate(wareDetails.getDate());
        waresRepository.save(wareEntity);
        return new ModelMapper().map(wareEntity,WareDto.class);
    }



    @Override
    public void deleteClient(String wareId) {
        if(wareId !=null) {
            waresRepository.delete(waresRepository.findByWareId(wareId));
        }
    }

    @Override
    public List<WareDto> getAllWare() {

        //when add ware in seller
        List<WareDto> results = new ArrayList<>();
        for(WareEntity wareEntity: waresRepository.findAll()){
            results.add(new ModelMapper().map(wareEntity,WareDto.class));
        }
        return results;
    }


    @Override
    public List<WareDto> getAllWareForSpecialSeller(String sellerId){
        List<WareDto> results = new ArrayList<>();
        for(WareEntity wareEntity: waresRepository.findByWareSellerId(sellerId)){
            results.add(new ModelMapper().map(wareEntity,WareDto.class));
        }
        return results;
    }


    @Override
    public List<WareDto> getAllWareInCart(String sellerId) {
        List<WareDto> results = new ArrayList<>();
        for(WareEntity wareEntity: waresRepository.findByCartTrueAndWareSellerId(sellerId)){
            results.add(new ModelMapper().map(wareEntity,WareDto.class));
        }
        return results;
    }

    @Override
    public List<WareDto> getAllWAreWithSpecialCategory(String marketPlace, String category) {
        List<WareDto> results = new ArrayList<>();
        for(WareEntity wareEntity: waresRepository.findByMarketPlaceAndCategory(marketPlace,category)){
            results.add(new ModelMapper().map(wareEntity,WareDto.class));
        }
        return results;
    }

    @Override
    public List<WareDto> getSearchOfWare(String marketPlace, String category, String name) {
        List<WareDto> results = new ArrayList<>();
        for(WareEntity wareEntity: waresRepository.findByMarketPlaceAndCategoryStartingWithOrNameStartingWith(marketPlace,"%"+category+"%","%"+name+"%")){
            results.add(new ModelMapper().map(wareEntity,WareDto.class));
        }
        return results;
    }
}
