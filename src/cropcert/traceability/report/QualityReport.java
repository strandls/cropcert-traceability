package cropcert.traceability.report;

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
@Table(name = "quality_report")
@XmlRootElement
@JsonIgnoreProperties
public class QualityReport implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6287810889323128536L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_id_generator")
    @SequenceGenerator(name = "report_id_generator", sequenceName = "report_id_seq", allocationSize = 50)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    
    @Column(name = "lot_id")
    private Long lotId;
    
    @Column(name = "lot_reception_date")
    private Date date;
    
    @Column(name = "time_stamp")
    private Timestamp timestamp;
    
    @Column(name = "cfa")
    private String cfa;
    
    @Column(name = "cc_code")
    private int ccCode;
    
    @Column( name = "coffee_type")
    private String coffeeType;
    
    @Column ( name = "over_turn_percentage")
    private float overtTurnPercentage;
    
    @Column ( name = "mc")
    private float mc;
    
    @Column( name = "grade_aa")
    private float gradeAA;
    
    @Column( name = "grade_a")
    private float gradeA;
    
    @Column ( name = "grade_b")
    private float gradeB;
    
    @Column (name = "grade_ab")
    private float gradeAB;
    
    @Column(name = "c")
    private float gradeC;
    
    @Column(name = "pb")
    private float gradePB;
    
    @Column(name = "triage")
    private float g1radeTriage;
    
    /** 
     * Severe defects fields
     */
    @Column(name = "full_black")
    private float fullBlack;
    
    @Column(name = "full_sour")
    private float fullSour;
    
    @Column(name = "pods")
    private float pods;
    
    @Column(name = "fungas_damaged")
    private float fungasDamaged;
    
    @Column(name = "em")
    private float em;
    
    @Column(name = "severe_insect")
    private float severeInsect;
    
    /**
     * Less severe defects
     */
    @Column(name = "partial_black")
    private float partialBlack;
    
    @Column(name = "partial_sour")
    private float partialSour;
    
    @Column(name = "patchment")
    private float patchment;
    
    @Column(name = "floaters_chalky")
    private float floatersChalky;
    
    @Column(name = "immature")
    private float immature;
    
    @Column(name = "withered")
    private float withered;
    
    @Column(name = "shells")
    private float shells;
    
    @Column(name = "broken_chipped")
    private float brokenChipped;
    
    @Column(name = "husks")
    private float husks;
    
    @Column(name = "pinHole")
    private float pinHole;
    
    @Column(name = "percentage_out_turn")
    private float percentageOutTurn;
    
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getCfa() {
		return cfa;
	}

	public void setCfa(String cfa) {
		this.cfa = cfa;
	}

	public int getCcCode() {
		return ccCode;
	}

	public void setCcCode(int ccCode) {
		this.ccCode = ccCode;
	}

	public String getCoffeeType() {
		return coffeeType;
	}

	public void setCoffeeType(String coffeeType) {
		this.coffeeType = coffeeType;
	}

	public float getOvertTurnPercentage() {
		return overtTurnPercentage;
	}

	public void setOvertTurnPercentage(float overtTurnPercentage) {
		this.overtTurnPercentage = overtTurnPercentage;
	}

	public float getMc() {
		return mc;
	}

	public void setMc(float mc) {
		this.mc = mc;
	}

	public float getGradeAA() {
		return gradeAA;
	}

	public void setGradeAA(float gradeAA) {
		this.gradeAA = gradeAA;
	}

	public float getGradeA() {
		return gradeA;
	}

	public void setGradeA(float gradeA) {
		this.gradeA = gradeA;
	}

	public float getGradeB() {
		return gradeB;
	}

	public void setGradeB(float gradeB) {
		this.gradeB = gradeB;
	}

	public float getGradeAB() {
		return gradeAB;
	}

	public void setGradeAB(float gradeAB) {
		this.gradeAB = gradeAB;
	}

	public float getGradeC() {
		return gradeC;
	}

	public void setGradeC(float gradeC) {
		this.gradeC = gradeC;
	}

	public float getGradePB() {
		return gradePB;
	}

	public void setGradePB(float gradePB) {
		this.gradePB = gradePB;
	}

	public float getG1radeTriage() {
		return g1radeTriage;
	}

	public void setG1radeTriage(float g1radeTriage) {
		this.g1radeTriage = g1radeTriage;
	}

	public float getFullBlack() {
		return fullBlack;
	}

	public void setFullBlack(float fullBlack) {
		this.fullBlack = fullBlack;
	}

	public float getFullSour() {
		return fullSour;
	}

	public void setFullSour(float fullSour) {
		this.fullSour = fullSour;
	}

	public float getPods() {
		return pods;
	}

	public void setPods(float pods) {
		this.pods = pods;
	}

	public float getFungasDamaged() {
		return fungasDamaged;
	}

	public void setFungasDamaged(float fungasDamaged) {
		this.fungasDamaged = fungasDamaged;
	}

	public float getEm() {
		return em;
	}

	public void setEm(float em) {
		this.em = em;
	}

	public float getSevereInsect() {
		return severeInsect;
	}

	public void setSevereInsect(float severeInsect) {
		this.severeInsect = severeInsect;
	}

	public float getPartialBlack() {
		return partialBlack;
	}

	public void setPartialBlack(float partialBlack) {
		this.partialBlack = partialBlack;
	}

	public float getPartialSour() {
		return partialSour;
	}

	public void setPartialSour(float partialSour) {
		this.partialSour = partialSour;
	}

	public float getPatchment() {
		return patchment;
	}

	public void setPatchment(float patchment) {
		this.patchment = patchment;
	}

	public float getFloatersChalky() {
		return floatersChalky;
	}

	public void setFloatersChalky(float floatersChalky) {
		this.floatersChalky = floatersChalky;
	}

	public float getImmature() {
		return immature;
	}

	public void setImmature(float immature) {
		this.immature = immature;
	}

	public float getWithered() {
		return withered;
	}

	public void setWithered(float withered) {
		this.withered = withered;
	}

	public float getShells() {
		return shells;
	}

	public void setShells(float shells) {
		this.shells = shells;
	}

	public float getBrokenChipped() {
		return brokenChipped;
	}

	public void setBrokenChipped(float brokenChipped) {
		this.brokenChipped = brokenChipped;
	}

	public float getHusks() {
		return husks;
	}

	public void setHusks(float husks) {
		this.husks = husks;
	}

	public float getPinHole() {
		return pinHole;
	}

	public void setPinHole(float pinHole) {
		this.pinHole = pinHole;
	}

	public float getPercentageOutTurn() {
		return percentageOutTurn;
	}

	public void setPercentageOutTurn(float percentageOutTurn) {
		this.percentageOutTurn = percentageOutTurn;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

    
}
