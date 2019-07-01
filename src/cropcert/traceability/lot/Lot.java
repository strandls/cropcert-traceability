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
	
	@Column(name = "type")
	private String type;

	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "time_to_factory")
	private Date timeToFactory;
	
	@Column(name = "milling_time")
	private Timestamp millingTime;
	
	@Column(name = "out_turn")
	private float outTurn;
	
	@Column(name = "grn_number")
	private String grnNumber;
	
	@Column(name = "quality_report")
	private Long reportId;
	
	@Column(name = "lot_status")
	private LotStatus lotStatus;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;

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

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Date getTimeToFactory() {
		return timeToFactory;
	}

	public void setTimeToFactory(Date timeToFactory) {
		this.timeToFactory = timeToFactory;
	}

	public Timestamp getMillingTime() {
		return millingTime;
	}

	public void setMillingTime(Timestamp millingTime) {
		this.millingTime = millingTime;
	}

	public float getOutTurn() {
		return outTurn;
	}

	public void setOutTurn(float outTurn) {
		this.outTurn = outTurn;
	}

	public String getGrnNumber() {
		return grnNumber;
	}

	public void setGrnNumber(String grnNumber) {
		this.grnNumber = grnNumber;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	
	public LotStatus getLotStatus() {
		return lotStatus;
	}
	
	public void setLotStatus(LotStatus lotStatus) {
		this.lotStatus = lotStatus;
	}

	public Boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
