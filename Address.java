package unl.cse;

public class Address {
	
	private String[] addressCollection; 
	private String street;
	private String city;
	private String state;
	private String zip;
	private String country;
	
	public Address(String address){
		this.addressCollection = address.split(",");
		this.street = this.addressCollection[0];
		this.city = this.addressCollection[1];
		this.state = this.addressCollection[2];
		this.zip = this.addressCollection[3];
		this.country = this.addressCollection[4];
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public String getCountry() {
		return country;
	}
	
	
		

}
