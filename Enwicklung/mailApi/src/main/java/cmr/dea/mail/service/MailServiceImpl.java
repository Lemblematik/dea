package cmr.dea.mail.service;

import cmr.dea.mail.data.CarrierOrderEntity;
import cmr.dea.mail.data.MailEntity;
import cmr.dea.mail.data.MailRepository;
import cmr.dea.mail.data.OrderRepository;
import cmr.dea.mail.shared.MailDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MailServiceImpl implements MailService {
    public MailRepository mailRepository;
    public OrderRepository orderRepository;

    @Autowired
    public MailServiceImpl(MailRepository mailRepository, OrderRepository orderRepository) {
        this.mailRepository = mailRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public MailDto createMail(MailDto mailDto) {
        mailDto.setMailId(UUID.randomUUID().toString());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //CarrierOrderEntity carrierOrderEntity = modelMapper.map(mailDto.getCarrierOrderResponse(),CarrierOrderEntity.class);
        //mailDto.setCarrierOrderResponse(orderRepository.save(carrierOrderEntity));
        MailEntity mailEntity = modelMapper.map(mailDto, MailEntity.class);
        mailRepository.save(mailEntity);
        MailDto returnValue = modelMapper.map(mailEntity,MailDto.class);

        //for test without db
        return returnValue;
    }

    @Override
    public List<MailDto> getAllMyReceivedMails(String receiverId) {
        //when add ware in seller
        List<MailDto> results = new ArrayList<>();
        for(MailEntity wareEntity: mailRepository.findByReceiverId(receiverId)){
            results.add(new ModelMapper().map(wareEntity,MailDto.class));
        }
        return results;
    }

    //TODO
    @Override
    public void deleteMyReceivedMails(String receiverId, String mailId) {
        mailRepository.delete(mailRepository.findByReceiverIdAndMailId(receiverId,mailId));
    }

    //TODO
    @Override
    public void deleteMySendMails(String senderId, String mailId) {
        mailRepository.delete(mailRepository.findByReceiverIdAndMailId(senderId,mailId));
    }

    @Override
    public MailDto getInfosMail(String mailId) {
        if (mailId != null) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            MailEntity wareEntity = mailRepository.findByMailId(mailId);
            MailDto mailDto = modelMapper.map(wareEntity,MailDto.class);
            return mailDto;
        }
        return null;
    }

    @Override
    public List<MailDto> getAll(){
        List<MailDto> results = new ArrayList<>();
        for(MailEntity wareEntity: mailRepository.findAll()){
            results.add(new ModelMapper().map(wareEntity,MailDto.class));
        }
        return results;
    }
}
