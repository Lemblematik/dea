package cmr.dea.mail.controller;

import cmr.dea.mail.modell.MailRequest;
import cmr.dea.mail.modell.MailResponse;
import cmr.dea.mail.service.MailService;
import cmr.dea.mail.shared.MailDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/mails")
public class mailController {

    @Autowired
    private Environment environment;

    @Autowired
    MailService mailService;


    @GetMapping("/status")
    public String status(){
        return "mail microservice working on port " + environment.getProperty("local.server.port");
    }

    @PostMapping
    public ResponseEntity<MailResponse> createNewMail(@Valid @RequestBody MailRequest mailRequest){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //data from frontend to db modell
        MailDto mailDto = modelMapper.map(mailRequest, MailDto.class);
        MailDto createdMail = mailService.createMail(mailDto);
        //for response modell
        MailResponse returnValue = modelMapper.map(createdMail,MailResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping("receivers/{receiverId}")
    public List<MailDto> getAllMyMails(@PathVariable("receiverId") String receiverId){
        List<MailDto> mailDtos = mailService.getAllMyReceivedMails(receiverId);
       return mailDtos;
    }

    @DeleteMapping("{receiverId}/{mailId}")
    public ResponseEntity<Void> deleteMyMailFromReceiver(@PathVariable("receiverId") String receiverId,@PathVariable("mailId") String mailId){
        mailService.deleteMyReceivedMails(receiverId,mailId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{senderId}/{mailId}")
    public ResponseEntity<Void> deleteMyMailFromSender(@PathVariable("senderId") String senderId,@PathVariable("mailId") String mailId){
        mailService.deleteMySendMails(senderId,mailId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{mailId}")
    public ResponseEntity<MailResponse> getMailContent(@PathVariable("mailId") String mailId){
        MailDto mailDto = mailService.getInfosMail(mailId);
        MailResponse returnValue = new ModelMapper().map(mailDto,MailResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);

    }

    @GetMapping("/all")
    public List<MailDto> getAll(){
        return mailService.getAll();
    }
}
