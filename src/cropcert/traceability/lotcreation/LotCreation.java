package cropcert.traceability.lotcreation;

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
@Table(name = "lot_creation")
@XmlRootElement
@JsonIgnoreProperties
public class LotCreation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1520798245020829559L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lot_creation_id_generator")
	@SequenceGenerator(name = "lot_creation_id_generator", sequenceName = "lot_creation_id_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "batch_name")
	private String batchName;
	
	@Column(name = "lot_name")
	private String lotName;
	
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

	public String getBatchNumber() {
		return batchName;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchName = batchNumber;
	}

	public String getLotNumber() {
		return lotName;
	}

	public void setLotNumber(String lotNumber) {
		this.lotName = lotNumber;
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
