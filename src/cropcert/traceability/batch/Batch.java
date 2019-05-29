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
	
	@Column(name = "available_quantity")
	@ColumnDefault("0.0")
	private float availableQuantity;

	@Column(name = "date")
	private Date date;

	@Column(name = "transfer_time_stamp")
	private Timestamp transferTimestamp;

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
	
	public float getAvailableQuantity() {
		return availableQuantity;
	}
	
	public void setAvailableQuantity(float availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Timestamp getTransferTimestamp() {
		return transferTimestamp;
	}

	public void setTransferTimestamp(Timestamp transferTimestamp) {
		this.transferTimestamp = transferTimestamp;
	}

	public String getType() {
		DiscriminatorValue value = this.getClass().getAnnotation(DiscriminatorValue.class);
		return value == null ? null : value.value();
	}

}
