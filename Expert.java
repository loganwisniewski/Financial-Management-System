package unl.cse;

public class Expert extends Broker{

	private String brokerData;
	private String code;
	private String name;
	private String address;
	private String emailAddress;
	
	public Expert(String code, String brokerData, String name, String address,
			String emailAddress) {
		super(code, brokerData, name, address, emailAddress);
		
		this.code = code;
		this.name = name;
		this.address = address;
		this.emailAddress = emailAddress;
		this.brokerData = brokerData;
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
