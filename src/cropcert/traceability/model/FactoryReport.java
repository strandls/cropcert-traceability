package cropcert.traceability.model;

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

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "report_factory")
@XmlRootElement
@JsonIgnoreProperties
@ApiModel("FactoryReport")
public class FactoryReport {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "factory_report_id_generator")
	@SequenceGenerator(name = "factory_report_id_generator", sequenceName = "factory_report_id_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "lot_id", unique = true, nullable = false)
    private Long lotId;
	
	@Column(name = "date")
	private Timestamp date;
	
	@Column(name = "mc_in")
	private float mcIn;
	
	@Column(name = "mc_out")
	private float mcOut;
	
	@Column(name = "input_weight")
	private float inputWeight;
	
	@Column(name = "spill_priv_batch")
	private float spillPrivBatch;
	
	@Column(name = "spill_cf")
	private float spillCF;
	
	@Column(name = "net_input_weight")
	private float netInputWeight;
	
	/**
	 * High grade values
	 */
	@Column(name = "grade_aa")
	private float gradeAA;
	
	@Column(name = "grade_ab")
	private float gradeAB;
	
	@Column(name = "grade_c_pb")
	private float gradeCAndPB;
	
	@Column(name = "high_grade_weight")
	private float highGradeWeight;
	
	/**
	 * Low grade values
	 */
	@Column(name = "triage")
	private float triage;
	
	@Column(name = "pods")
	private float pods;
	
	@Column(name = "arabica1899")
	private float arabica1899;
	
	@Column(name = "sweepings_or_spillages")
	private float sweeppingsOrSpillages;
	
	@Column(name = "low_grade_weight")
	private float lowGradeWeight;
	
	/**
	 * Black beans
	 */
	@Column(name = "blackBeans_aa")
	private float blackBeansAA;
	
	@Column(name = "blackBeans_ab")
	private float blackBeansAB;
	
	@Column(name = "blackBeans_c")
	private float blackBeansC;
	
	@Column(name = "total_blackBeans")
	private float totalBlackBeans;
	
	/**
	 * Waste 
	 */
	@Column(name = "stone")
	private float stone;
	
	@Column(name = "pre_cleaner")
	private float preCleaner;
	
	@Column(name = "grader_husks")
	private float graderHusks;
	
	@Column(name = "waste_sub_total")
	private float wasteSubTotal;
	
	/**
	 * Other losses
	 */
	@Column(name = "handling_loss")
	private float handlingLoss;
	
	@Column(name = "drying_loss")
	private float dryingLoss;
	
	@Column(name = "other_loss_sub_total")
	private float otherLossSubTotal;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getLotId() {
		return lotId;
	}
	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public float getMcIn() {
		return mcIn;
	}

	public void setMcIn(float mcIn) {
		this.mcIn = mcIn;
	}

	public float getMcOut() {
		return mcOut;
	}

	public void setMcOut(float mcOut) {
		this.mcOut = mcOut;
	}

	public float getInputWeight() {
		return inputWeight;
	}

	public void setInputWeight(float inputWeight) {
		this.inputWeight = inputWeight;
	}

	public float getSpillPrivBatch() {
		return spillPrivBatch;
	}

	public void setSpillPrivBatch(float spillPrivBatch) {
		this.spillPrivBatch = spillPrivBatch;
	}

	public float getSpillCF() {
		return spillCF;
	}

	public void setSpillCF(float spillCF) {
		this.spillCF = spillCF;
	}

	public float getNetInputWeight() {
		return netInputWeight;
	}

	public void setNetInputWeight(float netInputWeight) {
		this.netInputWeight = netInputWeight;
	}

	public float getGradeAA() {
		return gradeAA;
	}

	public void setGradeAA(float gradeAA) {
		this.gradeAA = gradeAA;
	}

	public float getGradeAB() {
		return gradeAB;
	}

	public void setGradeAB(float gradeAB) {
		this.gradeAB = gradeAB;
	}

	public float getGradeCAndPB() {
		return gradeCAndPB;
	}

	public void setGradeCAndPB(float gradeCAndPB) {
		this.gradeCAndPB = gradeCAndPB;
	}

	public float getHighGradeWeight() {
		return highGradeWeight;
	}

	public void setHighGradeWeight(float highGradeWeight) {
		this.highGradeWeight = highGradeWeight;
	}

	public float getTriage() {
		return triage;
	}

	public void setTriage(float triage) {
		this.triage = triage;
	}

	public float getPods() {
		return pods;
	}

	public void setPods(float pods) {
		this.pods = pods;
	}

	public float getArabica1899() {
		return arabica1899;
	}

	public void setArabica1899(float arabica1899) {
		this.arabica1899 = arabica1899;
	}

	public float getSweeppingsOrSpillages() {
		return sweeppingsOrSpillages;
	}

	public void setSweeppingsOrSpillages(float sweeppingsOrSpillages) {
		this.sweeppingsOrSpillages = sweeppingsOrSpillages;
	}

	public float getLowGradeWeight() {
		return lowGradeWeight;
	}

	public void setLowGradeWeight(float lowGradeWeight) {
		this.lowGradeWeight = lowGradeWeight;
	}

	public float getBlackBeansAA() {
		return blackBeansAA;
	}

	public void setBlackBeansAA(float blackBeansAA) {
		this.blackBeansAA = blackBeansAA;
	}

	public float getBlackBeansAB() {
		return blackBeansAB;
	}

	public void setBlackBeansAB(float blackBeansAB) {
		this.blackBeansAB = blackBeansAB;
	}

	public float getBlackBeansC() {
		return blackBeansC;
	}

	public void setBlackBeansC(float blackBeansC) {
		this.blackBeansC = blackBeansC;
	}

	public float getTotalBlackBeans() {
		return totalBlackBeans;
	}

	public void setTotalBlackBeans(float totalBlackBeans) {
		this.totalBlackBeans = totalBlackBeans;
	}

	public float getStone() {
		return stone;
	}

	public void setStone(float stone) {
		this.stone = stone;
	}

	public float getPreCleaner() {
		return preCleaner;
	}

	public void setPreCleaner(float preCleaner) {
		this.preCleaner = preCleaner;
	}

	public float getGraderHusks() {
		return graderHusks;
	}

	public void setGraderHusks(float graderHusks) {
		this.graderHusks = graderHusks;
	}

	public float getWasteSubTotal() {
		return wasteSubTotal;
	}

	public void setWasteSubTotal(float wasteSubTotal) {
		this.wasteSubTotal = wasteSubTotal;
	}

	public float getHandlingLoss() {
		return handlingLoss;
	}

	public void setHandlingLoss(float handlingLoss) {
		this.handlingLoss = handlingLoss;
	}

	public float getDryingLoss() {
		return dryingLoss;
	}

	public void setDryingLoss(float dryingLoss) {
		this.dryingLoss = dryingLoss;
	}

	public float getOtherLossSubTotal() {
		return otherLossSubTotal;
	}

	public void setOtherLossSubTotal(float otherLossSubTotal) {
		this.otherLossSubTotal = otherLossSubTotal;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
