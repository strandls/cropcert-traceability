package cropcert.traceability.collection;

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

@Entity
@Table(name="collection")
@XmlRootElement
public class Collection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6287810889323128536L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "collection_id_generator")
	@SequenceGenerator(name = "collection_id_generator", sequenceName = "collection_id_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private Long collectionId;
	
	@Column(name = "farmer_id")
	private String membershipId;
	
	@Column(name = "cc_code")
	private int ccCode;
	
	/*
	 * This weight is measured in kilogram.
	 */
	@Column (name = "quantity", nullable=false)
	private float quantity;
	
	@Column (name = "date")
	private Date date;
	
	@Column (name = "timestamp")
	private Timestamp timestamp;
	
	@Column (name = "batch_id")
	private Long batchId;

	public Long getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}

	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

	public int getCcCode() {
		return ccCode;
	}

	public void setCcCode(int ccCode) {
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

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
}
