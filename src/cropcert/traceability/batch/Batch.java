package cropcert.traceability.batch;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "batch")
@XmlRootElement
@JsonIgnoreProperties
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "DRY")
public class Batch implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6287810889323128536L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "batch_id_generator")
    @SequenceGenerator(name = "batch_id_generator", sequenceName = "batch_id_seq", allocationSize = 50)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "batch_name")
    private String batchName;

    @Column(name = "cc_code")
    private Long ccCode;

    @Column(name = "quantity")
    @ColumnDefault("0.0")
    private float quantity;

    @Column(name = "date")
    private Date date;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "note")
    private String note;
    
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    
    @Column(name = "is_lot_Done")
    private Boolean isLotDone;

    public Long getBatchId() {
        return id;
    }

    public void setBatchId(Long batchId) {
        this.id = batchId;
    }

    public Long getCcCode() {
        return ccCode;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setCcCode(Long ccCode) {
        this.ccCode = ccCode;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getType() {
        DiscriminatorValue value = this.getClass().getAnnotation(DiscriminatorValue.class);
        return value == null ? null : value.value();
    }

	public Boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean isLotDone() {
		return isLotDone;
	}

	public void setLotDone(Boolean isLotDone) {
		this.isLotDone = isLotDone;
	}

}
