package cmr.dea.orders.modell;

public class OrdersRequestModell {
    private String nameClient;
    private String nameProduct;
    private int numberProduct;
    private String recoveryDate;
    private String phoneNbr;
    private String wareSellerId;


    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getNumberProduct() {
        return numberProduct;
    }

    public void setNumberProduct(int numberProduct) {
        this.numberProduct = numberProduct;
    }

    public String getRecoveryDate() {
        return recoveryDate;
    }

    public void setRecoveryDate(String recoveryDate) {
        this.recoveryDate = recoveryDate;
    }

    public String getPhoneNbr() {
        return phoneNbr;
    }

    public void setPhoneNbr(String phoneNbr) {
        this.phoneNbr = phoneNbr;
    }

    public String getWareSellerId() {
        return wareSellerId;
    }

    public void setWareSellerId(String wareSellerId) {
        this.wareSellerId = wareSellerId;
    }
}
