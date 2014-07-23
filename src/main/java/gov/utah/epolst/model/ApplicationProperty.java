package gov.utah.epolst.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "APPLICATION_PROPERTY")
public class ApplicationProperty {

	
 
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
 
	@Column(name="Name")
	private String name;
	
 
	@Column(name="Value", length = 8000)
	private String value;

 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
