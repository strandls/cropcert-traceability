package cropcert.traceability.model;

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

import cropcert.traceability.LotStatus;
import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "lot")
@XmlRootElement
@JsonIgnoreProperties
@ApiModel("Lot")
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
	
	@Column(name = "co_code")
	private Long coCode;
	
	@Column(name = "quantity")
	private float quantity;
	
	@Column(name = "type")
	private String type;

	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "time_to_factory")
	private Timestamp timeToFactory;
	
	@Column(name = "weight_leaving_cooperative")
	private float weightLeavingCooperative;
	
	@Column(name = "mc_leaving_cooperative")
	private float mcLeavingCooperative;
	
	@Column(name = "weight_arriving_factory")
	private float weightArrivingFactory;
	
	@Column(name = "mc_arriving_factory")
	private float mcArrivingFactory;
	
	@Column(name = "milling_time")
	private Timestamp millingTime;

	@Column(name = "weight_Leaving_factory")
	private float weightLeavingFactory;
	
	@Column(name = "mc_Leaving_factory")
	private float mcLeavingFactory;
	
	@Column(name = "out_turn")
	private float outTurn;
	
	@Column(name = "grn_number")
	private String grnNumber;
	
	@Column(name = "grn_timestamp")
	private Timestamp grnTimestamp;
	
	@Column(name = "factory_report_id")
	private Long factoryReportId;
	
	@Column(name = "green_analysis_id")
	private Long greenAnalysisId;
	
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

	public Long getCoCode() {
		return coCode;
	}
	public void setCoCode(Long coCode) {
		this.coCode = coCode;
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

	public Timestamp getTimeToFactory() {
		return timeToFactory;
	}
	public void setTimeToFactory(Timestamp timeToFactory) {
		this.timeToFactory = timeToFactory;
	}
	
	public float getWeightLeavingCooperative() {
		return weightLeavingCooperative;
	}
	public void setWeightLeavingCooperative(float weightLeavingCooperative) {
		this.weightLeavingCooperative = weightLeavingCooperative;
	}
	
	public float getMcLeavingCooperative() {
		return mcLeavingCooperative;
	}
	public void setMcLeavingCooperative(float mcLeavingCooperative) {
		this.mcLeavingCooperative = mcLeavingCooperative;
	}
	
	public float getWeightArrivingFactory() {
		return weightArrivingFactory;
	}
	public void setWeightArrivingFactory(float weightArrivingFactory) {
		this.weightArrivingFactory = weightArrivingFactory;
	}
	
	public float getMcArrivingFactory() {
		return mcArrivingFactory;
	}
	public void setMcArrivingFactory(float mcArrivingFactory) {
		this.mcArrivingFactory = mcArrivingFactory;
	}

	public Timestamp getMillingTime() {
		return millingTime;
	}
	public void setMillingTime(Timestamp millingTime) {
		this.millingTime = millingTime;
	}
	
	public float getWeightLeavingFactory() {
		return weightLeavingFactory;
	}
	public void setWeightLeavingFactory(float weightLeavingFactory) {
		this.weightLeavingFactory = weightLeavingFactory;
	}
	
	public float getMcLeavingFactory() {
		return mcLeavingFactory;
	}
	public void setMcLeavingFactory(float mcLeavingFactory) {
		this.mcLeavingFactory = mcLeavingFactory;
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
	
	public Timestamp getGrnTimestamp() {
		return grnTimestamp;
	}
	public void setGrnTimestamp(Timestamp grnTimestamp) {
		this.grnTimestamp = grnTimestamp;
	}
	
	public Long getFactoryReportId() {
		return factoryReportId;
	}
	public void setFactoryReportId(Long factoryReportId) {
		this.factoryReportId = factoryReportId;
	}
	
	public Long getGreenAnalysisId() {
		return greenAnalysisId;
	}
	public void setGreenAnalysisId(Long greenAnalysisId) {
		this.greenAnalysisId = greenAnalysisId;
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
