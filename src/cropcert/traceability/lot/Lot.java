package cropcert.traceability.lot;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "lot")
@XmlRootElement
@JsonIgnoreProperties
public class Lot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1639867883436385824L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lot_id_generator")
	@SequenceGenerator(name = "lot_id_generator", sequenceName = "lot_id_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "lot_name")
	private String lotName;
	
	@Column(name = "quantity")
	private float quantity;
	
	@Column(name = "time_to_factory")
	private Date timeToFactory;
	
	@Column(name = "timestamp")
	private Timestamp timestamp;
	
	@Column(name = "type")
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLotName() {
		return lotName;
	}

	public void setLotName(String lotNumber) {
		this.lotName = lotNumber;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public Date getTimeToFactory() {
		return timeToFactory;
	}

	public void setTimeToFactory(Date timeToFactory) {
		this.timeToFactory = timeToFactory;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
