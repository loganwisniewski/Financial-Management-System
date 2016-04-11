package unl.cse;

public abstract class Asset{
	
	private String code;
	private String label;
	private String type;
	
	public Asset(String code, String label, String type){
		this.code = code;
		this.label = label;
		this.type = type;
	}
	
	public abstract String getCode();
	public abstract String getLabel();
	public abstract String getType();
	public abstract double getApr();
	public abstract double getQuarterlyDividend();
	public abstract double getBaseRateofReturn();
	public abstract double getOmegaMeasure();
	public abstract double getTotalValue();
	public abstract String getStockSymbol();
	public abstract double getSharePrice();

}
