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

import cropcert.traceability.ActionStatus;
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
	private Float quantity;
	
	@Column(name = "type")
	private String type;

	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "time_to_factory")
	private Timestamp timeToFactory;
	
	@Column(name = "weight_leaving_cooperative")
	private Float weightLeavingCooperative;
	
	@Column(name = "mc_leaving_cooperative")
	private Float mcLeavingCooperative;
	
	private ActionStatus coopStatus;
	
	@Column(name = "weight_arriving_factory")
	private Float weightArrivingFactory;
	
	@Column(name = "mc_arriving_factory")
	private Float mcArrivingFactory;
	
	@Column(name = "milling_time")
	private Timestamp millingTime;

	@Column(name = "weight_Leaving_factory")
	private Float weightLeavingFactory;
	
	@Column(name = "mc_Leaving_factory")
	private Float mcLeavingFactory;
	
	@Column(name = "out_turn")
	private Float outTurn;
	
	private ActionStatus millingStatus;
	
	@Column(name = "grn_number")
	private String grnNumber;
	
	@Column(name = "grn_timestamp")
	private Timestamp grnTimestamp;
	
	@Column(name = "factory_report_id")
	private Long factoryReportId;
	
	private ActionStatus factoryStatus;
	
	@Column(name = "green_analysis_id")
	private Long greenAnalysisId;
	
	private ActionStatus greenAnalysisStatus;
	
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
	
	public Float getQuantity() {
		return quantity;
	}
	public void setQuantity(Float quantity) {
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
	
	public Float getWeightLeavingCooperative() {
		return weightLeavingCooperative;
	}
	public void setWeightLeavingCooperative(Float weightLeavingCooperative) {
		this.weightLeavingCooperative = weightLeavingCooperative;
	}
	
	public Float getMcLeavingCooperative() {
		return mcLeavingCooperative;
	}
	public void setMcLeavingCooperative(Float mcLeavingCooperative) {
		this.mcLeavingCooperative = mcLeavingCooperative;
	}
	
	public ActionStatus getCoopStatus() {
		if(coopStatus == null) 
			coopStatus = ActionStatus.NOTDONE;
		if(this.timeToFactory == null && 
				this.weightLeavingCooperative == null && 
				this.mcLeavingCooperative == null) 
			coopStatus = ActionStatus.ADD;
		else if(coopStatus != ActionStatus.DONE) 
			coopStatus = ActionStatus.EDIT;
		else coopStatus = ActionStatus.DONE;
		return coopStatus;
	}
	public void setCoopStatus(ActionStatus coopStatus) {
		this.coopStatus = coopStatus;
	}
	
	/*
	 * Factory action
	 */
	public Float getWeightArrivingFactory() {
		return weightArrivingFactory;
	}
	public void setWeightArrivingFactory(Float weightArrivingFactory) {
		this.weightArrivingFactory = weightArrivingFactory;
	}
	
	public Float getMcArrivingFactory() {
		return mcArrivingFactory;
	}
	public void setMcArrivingFactory(Float mcArrivingFactory) {
		this.mcArrivingFactory = mcArrivingFactory;
	}

	public Timestamp getMillingTime() {
		return millingTime;
	}
	public void setMillingTime(Timestamp millingTime) {
		this.millingTime = millingTime;
	}
	
	public Float getWeightLeavingFactory() {
		return weightLeavingFactory;
	}
	public void setWeightLeavingFactory(Float weightLeavingFactory) {
		this.weightLeavingFactory = weightLeavingFactory;
	}
	
	public Float getMcLeavingFactory() {
		return mcLeavingFactory;
	}
	public void setMcLeavingFactory(Float mcLeavingFactory) {
		this.mcLeavingFactory = mcLeavingFactory;
	}

	public Float getOutTurn() {
		return outTurn;
	}
	public void setOutTurn(Float outTurn) {
		this.outTurn = outTurn;
	}
	
	public ActionStatus getMillingStatus() {
		if(coopStatus != ActionStatus.DONE || millingStatus == null) 
			millingStatus =  ActionStatus.NOTDONE;
		else if(weightArrivingFactory == null &&
				weightLeavingFactory == null &&
				mcArrivingFactory == null &&
				mcLeavingFactory == null &&
				millingTime == null) 
			millingStatus = ActionStatus.ADD;
		else if(millingStatus != ActionStatus.DONE) 
			millingStatus = ActionStatus.EDIT;
		else 
			millingStatus = ActionStatus.DONE;
		return millingStatus;
	}
	public void setMillingStatus(ActionStatus millingStatus) {
		this.millingStatus = millingStatus;
	}
	
	/*
	 * Action  at union factory
	 */
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

	public ActionStatus getFactoryStatus() {
		
		if(millingStatus != ActionStatus.DONE || factoryStatus == null)
			factoryStatus = ActionStatus.NOTDONE;
		else if(factoryReportId == null)
			factoryStatus = ActionStatus.ADD;
		else if(factoryStatus != ActionStatus.DONE)
			factoryStatus = ActionStatus.EDIT;
		else 
			factoryStatus = ActionStatus.DONE;
		
		return factoryStatus;
	}
	public void setFactoryStatus(ActionStatus factoryStatus) {
		this.factoryStatus = factoryStatus;
	}

	
	/*
	 * Quality report
	 */
	public Long getGreenAnalysisId() {
		return greenAnalysisId;
	}
	public void setGreenAnalysisId(Long greenAnalysisId) {
		this.greenAnalysisId = greenAnalysisId;
	}

	public ActionStatus getGreenAnalysisStatus() {
		if(factoryStatus != ActionStatus.DONE || greenAnalysisStatus == null) 
			greenAnalysisStatus = ActionStatus.NOTDONE;
		else if(greenAnalysisId == null)
			greenAnalysisStatus = ActionStatus.ADD;
		else if(greenAnalysisStatus != ActionStatus.DONE)
			greenAnalysisStatus = ActionStatus.EDIT;
		else
			greenAnalysisStatus = ActionStatus.DONE;
		
		return greenAnalysisStatus;
	}
	public void setGreenAnalysisStatus(ActionStatus greenAnalysisStatus) {
		this.greenAnalysisStatus = greenAnalysisStatus;
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
