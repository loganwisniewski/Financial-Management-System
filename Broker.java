package unl.cse;

public abstract class Broker extends Persons{
	
	private String brokerData;
	private String code;
	private String name;
	private String address;
	private String emailAddress;

	public Broker(String code, String name, String address,
			String emailAddress, String brokerData) {
		this.code = code;
		this.name = name;
		this.address = address;
		this.emailAddress = emailAddress;
		this.brokerData = brokerData;
	}
	
	public abstract String getCode();
	public abstract String getName();
	public abstract String getBrokerData();
	public abstract String getEmail();
	
}
