package cmr.dea.mail.modell;


import java.util.List;

public class MailRequest {
    private String nameSender;
    private List<CarrierOrder> carrierOrderRespons;
    private String receiverId;
    private String message;
    private String date;
    private String subject;
    private String senderId;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNameSender() {
        return nameSender;
    }

    public void setNameSender(String nameSender) {
        this.nameSender = nameSender;
    }

    public List<CarrierOrder> getCarrierOrderRespons() {
        return carrierOrderRespons;
    }

    public void setCarrierOrderRespons(List<CarrierOrder> carrierOrderRespons) {
        this.carrierOrderRespons = carrierOrderRespons;
    }

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
}
