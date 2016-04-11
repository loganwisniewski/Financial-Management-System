package unl.cse;

public class PortfolioListNode {
	 private PortfolioListNode next;
	    private Portfolio item;

	    public PortfolioListNode(Portfolio p) {
	        this.item = p;
	        this.next = null;
	    }

	    public Portfolio getPortfolio() {
	        return item;
	    }

	    public PortfolioListNode getNext() {
	        return next;
	    }

	    public void setNext(PortfolioListNode next) {
	        this.next = next;
	    }
	    public void setPortfolio(Portfolio p){
	    	this.item = p;
	    }
}
