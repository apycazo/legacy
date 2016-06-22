package Elements;

import javax.xml.bind.annotation.XmlRootElement;

	//	Al tener esta etiqueta identifica que este objeto es un xml / JSON
	//	Al tener solo métodos get y set lo interpreta como un POJO
	
@XmlRootElement
public class Player {

	private String name;
	private String type;
	
	public Player(){
		this.type = "loaded";
		this.name = "John Doe";
	}
	
	public Player(String name){
		this.type = "normal";
		this.name = name;
	}
	// getters
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	// setters
	public void setName(String name) {
		this.name = name;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
