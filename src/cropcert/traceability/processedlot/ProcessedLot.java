package cropcert.traceability.processedlot;

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
@Table(name = "processed_lot")
@XmlRootElement
@JsonIgnoreProperties
public class ProcessedLot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1520798245020829559L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "processed_lot_id_generator")
	@SequenceGenerator(name = "processed_lot_id_generator", sequenceName = "processed_lot_id_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
		
	@Column(name = "lot_name")
	private String lotName;
	
	@Column(name = "processing_time")
	private Timestamp processingTime;
	
	@Column(name = "out_turn")
	private String outTurn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLotName() {
		return lotName;
	}

	public void setLotName(String lotName) {
		this.lotName = lotName;
	}
	
	public Timestamp getProcessingTime() {
		return processingTime;
	}
	
	public void setProcessingTime(Timestamp processingTime) {
		this.processingTime = processingTime;
	}

	public String getOutTurn() {
		return outTurn;
	}

	public void setOutTurn(String outTurn) {
		this.outTurn = outTurn;
	}
}
