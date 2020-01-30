package com.cmr.dea.carrierapi.demo.service;

import com.cmr.dea.carrierapi.demo.data.CarriersRepository;
import com.cmr.dea.carrierapi.demo.data.CarrierEntity;
import com.cmr.dea.carrierapi.demo.shared.CarrierDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CarriersServiceImpl implements CarriersService{

    public CarriersRepository carriersRepository;
    //BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public CarriersServiceImpl(CarriersRepository carriersRepository){
                               //BCryptPasswordEncoder bCryptPasswordEncoder){

        this.carriersRepository = carriersRepository;
        //this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public CarrierDto createCarrier(CarrierDto carrierDto) {
        carrierDto.setCarrierId(UUID.randomUUID().toString());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CarrierEntity carrierEntity = modelMapper.map(carrierDto, CarrierEntity.class);



        carriersRepository.save(carrierEntity);
        CarrierDto returnValue = modelMapper.map(carrierEntity,CarrierDto.class);
        return returnValue;
    }

    @Override
    public CarrierDto getInfosCarrier(String carrierId) {
        if (carrierId != null) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            CarrierEntity wareEntity = carriersRepository.findByCarrierId(carrierId);
            CarrierDto wareDto = modelMapper.map(wareEntity,CarrierDto.class);
            return wareDto;
        }
        return null;
    }


    @Override
    public CarrierDto updateInsertedClientInfos(String wareId, CarrierDto carrierDetails) {
        CarrierEntity carrierEntity = carriersRepository.findByCarrierId(wareId);
        carrierEntity.setAge(carrierDetails.getAge());
        carrierEntity.setFirstName(carrierDetails.getFirstName());
        carrierEntity.setFoto(carrierDetails.getFoto());
        carrierEntity.setCarrierId(carrierDetails.getCarrierId());
        carrierEntity.setLastName(carrierDetails.getLastName());
        carrierEntity.setMarketPosition(carrierDetails.getMarketPosition());
        carrierEntity.setOwnDescription(carrierDetails.getOwnDescription());
        carrierEntity.setMarketName(carrierDetails.getMarketName());
        carrierEntity.setFunction(carrierDetails.getFunction());
        carrierEntity.setPhoneNbr(carrierDetails.getPhoneNbr());
        carrierEntity.setRightInfos(carrierDetails.isRightInfos());
        carriersRepository.save(carrierEntity);
        return new ModelMapper().map(carrierEntity,CarrierDto.class);
    }

    @Override
    public void deleteCarrier(String carrierId) {
        if(carrierId !=null) {
            carriersRepository.delete(carriersRepository.findByCarrierId(carrierId));
        }
    }

    @Override
    public List<CarrierDto> getAllWare() {
        List<CarrierDto> results = new ArrayList<>();
        for(CarrierEntity wareEntity: carriersRepository.findAll()){
            results.add(new ModelMapper().map(wareEntity,CarrierDto.class));
        }
        return results;
    }

    @Override
    public List<CarrierDto> getAllCarrierInMarket(String marketName) {
        List<CarrierDto> results = new ArrayList<>();
        for(CarrierEntity wareEntity: carriersRepository.findByMarketName(marketName)){
            results.add(new ModelMapper().map(wareEntity,CarrierDto.class));
        }
        return results;
    }

}
