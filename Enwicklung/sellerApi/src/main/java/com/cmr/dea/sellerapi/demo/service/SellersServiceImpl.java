package com.cmr.dea.sellerapi.demo.service;

import com.cmr.dea.sellerapi.demo.data.SellerEntity;
import com.cmr.dea.sellerapi.demo.data.SellersRepository;

import com.cmr.dea.sellerapi.demo.shared.SellerDto;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SellersServiceImpl implements SellersService{

    public SellersRepository sellerRepository;

    @Autowired
    RestTemplate restTemplate;


    @Autowired
    public SellersServiceImpl(SellersRepository sellerRepository, RestTemplate restTemplate){
        this.sellerRepository = sellerRepository;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public SellerDto createSeller(SellerDto sellerDto) {
        String generatedId = UUID.randomUUID().toString();
        //sellerDto.setSellerId(generatedId);
        sellerDto.setSellerId("Paolo");

/*
        //set generatedId to all ware
        for (WareEntity wareEntity : sellerDto.getWareList()){
            wareEntity.setWareSellerId(generatedId);
        }
*/

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        SellerEntity carrierEntity = modelMapper.map(sellerDto, SellerEntity.class);
        sellerRepository.save(carrierEntity);
        SellerDto returnValue = modelMapper.map(carrierEntity,SellerDto.class);
        return returnValue;
    }

    @Override
    public SellerDto getInfosSeller(String sellerId) {
        if (sellerId != null) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

            SellerEntity wareEntity = sellerRepository.findBySellerId(sellerId);
            SellerDto sellerDto = modelMapper.map(wareEntity,SellerDto.class);
            return sellerDto;
        }
        return null;
    }

    @Override
    public SellerDto updateInsertedSellerInfos(String sellerId, SellerDto sellerDetails) {
        SellerEntity sellerEntity = sellerRepository.findBySellerId(sellerId);
        sellerEntity.setAge(sellerDetails.getAge());
        sellerEntity.setFirstName(sellerDetails.getFirstName());
        sellerEntity.setFoto(sellerDetails.getFoto());
        sellerEntity.setLastName(sellerDetails.getLastName());
        sellerEntity.setMarketPosition(sellerDetails.getMarketPosition());
        sellerEntity.setOwnDescription(sellerDetails.getOwnDescription());
        sellerEntity.setMarketName(sellerDetails.getMarketName());
        sellerEntity.setPasswort(sellerDetails.getPasswort());
        sellerEntity.setPhoneNbr(sellerDetails.getPhoneNbr());
        sellerRepository.save(sellerEntity);
        return new ModelMapper().map(sellerEntity,SellerDto.class);
    }

    @Override
    public void deleteSellerId(String sellerId) {
        if(sellerId !=null) {
            sellerRepository.delete(sellerRepository.findBySellerId(sellerId));
        }
    }

    @Override
    public List<SellerDto> getAllSeller() {
        List<SellerDto> results = new ArrayList<>();
        for(SellerEntity sellerEntity: sellerRepository.findAll()){
            results.add(new ModelMapper().map(sellerEntity,SellerDto.class));
        }
        return results;
    }
}
