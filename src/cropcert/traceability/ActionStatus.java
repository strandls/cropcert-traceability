package cropcert.traceability;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "actionStatus")
@XmlEnum
public enum ActionStatus {

	@XmlEnumValue("NotApplicable")
	NOTAPPLICABLE("NOTAPPLICABLE"),
	@XmlEnumValue("Add")
	ADD("ADD"),
	@XmlEnumValue("Edit")
	EDIT("EDIT"),
	@XmlEnumValue("Done")
	DONE("DONE");
	
	
	private String value;
	
	ActionStatus(String value) {
		this.value = value;
	}
	
	public static ActionStatus fromValue(String value) {
		for(ActionStatus pageType : ActionStatus.values()) {
			if(pageType.value.equals(value))
				return pageType;
		}
		throw new IllegalArgumentException(value);
	}
}
