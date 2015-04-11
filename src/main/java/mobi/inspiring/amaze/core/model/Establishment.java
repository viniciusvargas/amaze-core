package mobi.inspiring.amaze.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the establishment database table.
 * 
 */
@Entity
@NamedQuery(name="Establishment.findAll", query="SELECT e FROM Establishment e")
public class Establishment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_establishment")
	private int idEstablishment;
	
	@Column(name="code")
	private String code;

	@Column(name="formal_name")
	private String formalName;

	@Column(name="long_name")
	private String longName;

	@Column(name="short_name")
	private String shortName;
	
	@OneToMany(cascade=CascadeType.REMOVE, fetch=FetchType.EAGER, mappedBy="establishment")
	private List<Beacon> beaconCollection;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFormalName() {
		return formalName;
	}

	public void setFormalName(String formalName) {
		this.formalName = formalName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public int getIdEstablishment() {
		return this.idEstablishment;
	}

	public void setIdEstablishment(int idEstablishment) {
		this.idEstablishment = idEstablishment;
	}
	
	public List<Beacon> getBeaconCollection() {
		return beaconCollection;
	}
	
	public void setBeaconCollection(List<Beacon> beaconCollection) {
		this.beaconCollection = beaconCollection;
	}
	
	public void addBeacon(Beacon beacon) {
		if(this.beaconCollection == null)
			this.beaconCollection = new ArrayList<Beacon>();
		this.beaconCollection.add(beacon);
	}
	
	public void removeBeacon(Beacon beacon) {
		if(!this.beaconCollection.isEmpty())
			if(this.beaconCollection.contains(beacon))
				this.beaconCollection.remove(beacon);
	}

}