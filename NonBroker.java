package unl.cse;

public class NonBroker extends Persons{

	private String code;
	private String name;
	private String address;
	private String emailAddress;
	
	public NonBroker(String code, String brokerData, String name, String address,
			String emailAddress) {
		this.code = code;
		this.name = name;
		this.address = address;
		this.emailAddress = emailAddress;
	}
	
	public String getName(){
		return name;
	}
	public String getCode(){
		return code;
	}
	public String getBrokerData(){
		return "";
	}
	public String getAddress(){
		return address;
	}
	public String getEmail(){
		return emailAddress;
	}
}
