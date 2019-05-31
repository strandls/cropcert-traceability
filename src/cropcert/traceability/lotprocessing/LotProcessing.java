package cropcert.traceability.lotprocessing;

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
@Table(name = "lot_processing")
@XmlRootElement
@JsonIgnoreProperties
public class LotProcessing implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1520798245020829559L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lot_processing_id_generator")
	@SequenceGenerator(name = "lot_processing_id_generator", sequenceName = "lot_processing_id_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "lot_name")
	private String lotName;
	
	@Column(name = "processed_lot_name")
	private String processedLotName;
	
	@Column(name = "timestamp")
	private Timestamp timestamp;
	
	@Column(name = "note")
	private String note;

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

	public String getProcessedLotName() {
		return processedLotName;
	}

	public void setProcessedLotName(String processedLotName) {
		this.processedLotName = processedLotName;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
}