package cropcert.traceability;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "status")
@XmlEnum
public enum LotStatus {
	
	@XmlEnumValue("AtCollectionCenter")
	AT_COLLECTION_CENTER("AtCollectionCenter"),
	@XmlEnumValue("AtCoOperative")
	AT_CO_OPERATIVE("AtCoOperative"),
	@XmlEnumValue("InTransport")
	IN_TRANSPORT("InTransport"),
	@XmlEnumValue("AtFactory")
	AT_FACTORY("AtFactory"),
	@XmlEnumValue("AtUnion")
	AT_UNION("AtUnion");
	
	private String value;
	
	LotStatus(String value) {
		this.value = value;
	}
	
	public static LotStatus fromValue(String value) {
		for(LotStatus status : LotStatus.values()) {
			if(status.value.equals(value))
				return status;
		}
		throw new IllegalArgumentException(value);
	}
};