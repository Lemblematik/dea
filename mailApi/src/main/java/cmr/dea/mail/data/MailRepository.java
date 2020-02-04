package cmr.dea.mail.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MailRepository extends CrudRepository<MailEntity,Long> {
    List<MailEntity>findByReceiverId(String receiverId);
    MailEntity findByReceiverIdAndMailId(String receiverId, String mailId);
    MailEntity findByMailId(String mailId);
}
