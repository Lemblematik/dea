package cmr.dea.mail.service;

import cmr.dea.mail.shared.MailDto;

import java.util.List;

public interface MailService {
    MailDto createMail(MailDto mailDto);

    List<MailDto> getAllMyReceivedMails(String receiverId);

    void deleteMyReceivedMails(String receiverId, String mailId);

    void deleteMySendMails(String senderId, String mailId);

    MailDto getInfosMail(String mailId);

    List<MailDto> getAll();
}
