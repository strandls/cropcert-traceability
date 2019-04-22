package cropcert.traceability.batching;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="batching")
@XmlRootElement
@JsonIgnoreProperties
public class Batching implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6287810889323128536L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "batching_id_generator")
	@SequenceGenerator(name = "batching_id_generator", sequenceName = "batching_id_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private Long batchingId;
	
	@Column (name = "collection_id", nullable=false)
	private Long collectionId;
	
	@Column (name = "batch_id", nullable=false)
	private Long batchId;

	@Column (name = "quantity", nullable=false)
	private float quantity;
	
	@Column (name = "transfer_time_stamp")
	private Timestamp transferTimestamp;
	
	@Column (name = "quality")
	private String quality;

	public Long getBatchingId() {
		return batchingId;
	}

	public void setBatchingId(Long batchingId) {
		this.batchingId = batchingId;
	}

	public Long getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
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