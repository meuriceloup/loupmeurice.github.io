public class Client {
	private String name;
	private String street;
	private String box;
	private String city;
	private String country;
	private String tva;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		String nameTemp = new String(name);
		this.name = "";
		for(int i = 0; i < nameTemp.length();i++){
			this.name = this.name + nameTemp.charAt(i);
			//on double les espaces car il y a un prbl lors de l'impression.
			if(nameTemp.charAt(i) ==' '){
				this.name = this.name + nameTemp.charAt(i);
			}
		}
		System.out.println(this.name);
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBox() {
		return box;
	}

	public void setBox(String box) {
		this.box = box;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Client() {

	}

	public Client(String name, String street, String box, String city, String country, String tva){
		this.setName(name);
		this.street = street;
		this.box = box;
		this.city = city;
		this.country = country;
		this.tva = tva;
	}

	public String getTva() {
		return tva;
	}

	public void setTva(String tva) {
		this.tva = tva;
	}
}
