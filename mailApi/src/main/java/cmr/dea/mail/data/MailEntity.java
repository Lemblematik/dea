package cmr.dea.mail.data;

import cmr.dea.mail.modell.CarrierOrder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Mails")
public class MailEntity  implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String mailId;

    @Column(nullable = false)
    private String nameSender;

    //@OneToMany(mappedBy="mailEntity")
    //private List<CarrierOrderEntity> carrierOrderResponse;

    @Column(nullable = false)
    private String receiverId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String senderId;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getNameSender() {
        return nameSender;
    }

    public void setNameSender(String nameSender) {
        this.nameSender = nameSender;
    }

    /*
    public List<CarrierOrderEntity> getCarrierOrderResponse() {
        return carrierOrderResponse;
    }

    public void setCarrierOrderResponse(List<CarrierOrderEntity> carrierOrderResponse) {
        this.carrierOrderResponse = carrierOrderResponse;
    }

     */

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
