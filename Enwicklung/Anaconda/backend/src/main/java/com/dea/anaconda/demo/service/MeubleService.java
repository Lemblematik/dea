package com.dea.anaconda.demo.service;

import com.dea.anaconda.demo.shared.MeubleDto;

import java.util.List;

public interface MeubleService {
    MeubleDto createMeuble(MeubleDto productDto);

    MeubleDto getInfosMeuble(String meubleId);

    List<MeubleDto> getAllMeuble();

    void deleteMeuble(String clientId);

    MeubleDto updateInsertedMeubleInfos(String clientId, MeubleDto meubleDto);

    List<MeubleDto> getInfoMeubleWithInputName(String input);
    List<MeubleDto> getInfoMeubleWithInputCategory(String category);

}
