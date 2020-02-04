package cmr.dea.mail.shared;

import cmr.dea.mail.modell.CarrierOrder;

import java.util.List;

public class MailDto {
    private String mailId;
    private String nameSender;
    private List<CarrierOrder> carrierOrderResponse;
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

    public List<CarrierOrder> getCarrierOrderResponse() {
        return carrierOrderResponse;
    }

    public void setCarrierOrderResponse(List<CarrierOrder> carrierOrderResponse) {
        this.carrierOrderResponse = carrierOrderResponse;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
