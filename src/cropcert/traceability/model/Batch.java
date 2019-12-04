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

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cropcert.traceability.ActionStatus;
import cropcert.traceability.BatchType;
import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "batch")
@XmlRootElement
@JsonIgnoreProperties
@ApiModel("Batch")
public class Batch implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6287810889323128536L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "batch_id_generator")
	@SequenceGenerator(name = "batch_id_generator", sequenceName = "batch_id_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "batch_name", nullable = false)
	private String batchName;

	@Column(name = "cc_code", nullable = false)
	private Long ccCode;

	@Column(name = "batch_type", nullable = false)
	private BatchType type;

	@Column(name = "quantity")
	@ColumnDefault("0.0")
	private Float quantity;

	@Column(name = "date")
	private Timestamp date;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "note")
	private String note;

	@Column(name = "start_time")
	private Timestamp startTime;

	@Column(name = "fermentation_end_time")
	private Timestamp fermentationEndTime;

	@Column(name = "drying_end_time")
	private Timestamp dryingEndTime;

	@Column(name = "perchment_quantity")
	private Float perchmentQuantity;

	@Column(name = "is_ready_for_lot")
	private Boolean isReadyForLot;

	@Column(name = "lot_id")
	private Long lotId;
	
	private ActionStatus batchStatus;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public Long getCcCode() {
		return ccCode;
	}

	public void setCcCode(Long ccCode) {
		this.ccCode = ccCode;
	}

	public BatchType getType() {
		return type;
	}

	public void setType(BatchType type) {
		this.type = type;
	}

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getFermentationEndTime() {
		return fermentationEndTime;
	}

	public void setFermentationEndTime(Timestamp fermentationEndTime) {
		this.fermentationEndTime = fermentationEndTime;
	}

	public Timestamp getDryingEndTime() {
		return dryingEndTime;
	}

	public void setDryingEndTime(Timestamp dryingEndTime) {
		this.dryingEndTime = dryingEndTime;
	}

	public Float getPerchmentQuantity() {
		return perchmentQuantity;
	}

	public void setPerchmentQuantity(Float perchmentQuantity) {
		this.perchmentQuantity = perchmentQuantity;
	}

	public Boolean getIsReadyForLot() {
		return isReadyForLot;
	}

	public void setIsReadyForLot(Boolean isReadyForLot) {
		this.isReadyForLot = isReadyForLot;
	}

	public Long getLotId() {
		return lotId;
	}

	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}
	
	public ActionStatus getBatchStatus() {
		if(batchStatus == null) {
			batchStatus = ActionStatus.NOTDONE;
		}
		if(BatchType.DRY.equals(type)) {
			if(batchStatus != ActionStatus.DONE)
				batchStatus = ActionStatus.EDIT;
		} else {
			if(startTime == null&&
					fermentationEndTime == null &&
					dryingEndTime == null &&
					perchmentQuantity == null)
				batchStatus = ActionStatus.ADD;
			else if(batchStatus != ActionStatus.DONE)
				batchStatus = ActionStatus.EDIT;
			else 
				batchStatus = ActionStatus.DONE;
		}
		return batchStatus;
	}
	
	public void setBatchStatus(ActionStatus batchStatus) {
		this.batchStatus = batchStatus;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
