package cmr.dea.mail.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CarrierOrderEntity  implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String nameSeller;

    @Column(nullable = false)
    private String wareName;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String subCategory;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String marketPlace;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private MailEntity mailEntity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameSeller() {
        return nameSeller;
    }

    public void setNameSeller(String nameSeller) {
        this.nameSeller = nameSeller;
    }

    public String getWareName() {
        return wareName;
    }

    public void setWareName(String wareName) {
        this.wareName = wareName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMarketPlace() {
        return marketPlace;
    }

    public void setMarketPlace(String marketPlace) {
        this.marketPlace = marketPlace;
    }

    public MailEntity getMailEntity() {
        return mailEntity;
    }

    public void setMailEntity(MailEntity mailEntity) {
        this.mailEntity = mailEntity;
    }
}
