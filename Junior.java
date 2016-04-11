package unl.cse;

public class Junior extends Broker{
	
	private String brokerData;
	private String code;
	private String name;
	private String address;
	private String emailAddress;

	public Junior(String code, String brokerData, String name, String address,
			String emailAddress) {
		
		super(code, brokerData, name, address, emailAddress);
		
		this.name = name;
		this.brokerData = brokerData;
		this.code = code;
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
		return brokerData;
	}
	public String getAddress(){
		return address;
	}
	public String getEmail(){
		return emailAddress;
	}
}
