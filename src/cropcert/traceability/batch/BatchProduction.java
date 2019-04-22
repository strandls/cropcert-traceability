package cropcert.traceability.batch;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="batch_production")
@XmlRootElement
@JsonIgnoreProperties
public class BatchProduction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6287810889323128536L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "batch_id_generator")
	@SequenceGenerator(name = "batch_id_generator", sequenceName = "batch_id_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private Long batchId;
	
	@Column (name = "cc_code")
	private Long ccCode;
	
	@Column (name = "quantity")
	@ColumnDefault("0.0")
	private float quantity;
	
	@Column (name = "available_quantity")
	@ColumnDefault("0.0")
	private float availableQuantity;
	
	@Column (name = "moistureContent", nullable=false)
	private float moistureContent;
	
	@Column (name = "date")
	private Date date;
	
	@Column (name = "transfer_time_stamp")
	private Timestamp transferTimestamp;
	
	@Column (name = "quality")
	private String quality;

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public Long getCcCode() {
		return ccCode;
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

	public float getMoistureContent() {
		return moistureContent;
	}

	public void setMoistureContent(float moistureContent) {
		this.moistureContent = moistureContent;
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

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}


}
