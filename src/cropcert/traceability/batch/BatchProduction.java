package cropcert.traceability.batch;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import cropcert.traceability.collection.Collection;

@Entity
@Table(name="batch_production")
@XmlRootElement
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
	
	@Column( name = "factory_id")
	private Long factoryId;
	
	@Column (name = "weight", nullable=false)
	private float weight;
	
	@Column (name = "moistureContent", nullable=false)
	private float moistureContent;
	
	@Column (name = "transfer_time_stamp")
	private Timestamp transferTimestamp;
	
	@OneToMany (targetEntity=Collection.class)
	private List<Collection> productions;

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public Long getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(Long factoryId) {
		this.factoryId = factoryId;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getMoistureContent() {
		return moistureContent;
	}

	public void setMoistureContent(float moistureContent) {
		this.moistureContent = moistureContent;
	}

	public Timestamp getTransferTimestamp() {
		return transferTimestamp;
	}

	public void setTransferTimestamp(Timestamp transferTimestamp) {
		this.transferTimestamp = transferTimestamp;
	}

	public List<Collection> getProductions() {
		return productions;
	}

	public void setProductions(List<Collection> productions) {
		this.productions = productions;
	}
}
