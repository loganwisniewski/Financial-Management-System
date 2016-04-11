package unl.cse;

import java.util.Comparator;


public class PortfolioList<T> implements Comparator<Portfolio>{
	private PortfolioListNode head = null;
	private PortfolioListNode holderNode =null;

    public void clear() {
    	head = null;
    }

    /*
     *        Remove a Portfolio From 
     *        portfolioList by passing "portfolioCode"
     * 
     */
    public void remove(String portfolioCode) {
    	holderNode = head;
    	if(portfolioCode.equals(head.getPortfolio().getPortfolioCode())){
    		head = head.getNext();
    	}
    	else{
    		while(holderNode.getNext() != null){
    			if(portfolioCode.equals(holderNode.getNext().getPortfolio().getPortfolioCode())){
    				holderNode.setNext(holderNode.getNext().getNext());
    			} else {
    				holderNode = holderNode.getNext();
    			}
    		}
    	}
    }
    
    /*
     * 		Returns PortfolioListNode
     * 		after passing a portfolio 
     * 		position "int"
     * 	
     */
    private PortfolioListNode getPortfolioListNode(int position) {
    	holderNode = head;
    	if(position == 1){
    		return head;
    	}
    	else{
    		for(int i = 1; i < position; i++){
    			holderNode = holderNode.getNext();
    		}
    		return holderNode;
    	}
    	
    }
    
    /*
     * 		Returns a Portfolio
     * 		after passing a portfolio
     * 		position "int" by using 
     * 		getPortfolioListNode
     */
    public Portfolio getPortfolio(int position) {
    	holderNode = getPortfolioListNode(position);
    	return (Portfolio) holderNode.getPortfolio();
    }

    //Prints...
    public void print() {
    	holderNode = head;
    	while(holderNode != null){
    		holderNode = holderNode.getNext();
    	}
    }

    // Returns size of the portfolioList
	public int size() {
		int count = 1;
		holderNode = head;
		while(holderNode.getNext() != null){
			count ++;
			holderNode = holderNode.getNext();
		}
		count ++;
		return count;
	}

	/*
     * 		Returns a Portfolio
     * 		after passing a portfolio
     * 		position "int" 
     */
	public Portfolio get(int i) {
		return this.getPortfolio(i);
	}

	/*
	 * 		Adds a Portfolio
     * 		after passing a Portfolio "p"
     * 		and Comparator "comp"
	 */
	public void add(unl.cse.Portfolio p, Comparator comp) {
		if(comp.getClass().toString().contains("class unl.cse.ComparatorOwner")){
			addByOwner(p, comp);
		} else if(comp.getClass().toString().contains("class unl.cse.ComparatorTotalValue")){
			addByTotalValue(p, comp);
		} else {
			addByManager(p, comp);
		}
	}
	
	/*
	 * 		Adds a Portfolio
     * 		after passing a Portfolio "p"
     * 		and Comparator "comp"
     * 		but maintains order with
     * 		respect to Owner
	 */
	public void addByOwner(unl.cse.Portfolio p, Comparator comp) {
		PortfolioListNode newPortfolioNode = new PortfolioListNode(p);
		holderNode = head;
		String nPN = newPortfolioNode.getPortfolio().getOwnerName();
    	
    	
    	
    	if(holderNode == null){
    		head = newPortfolioNode;
    	} else if(nPN.compareTo(holderNode.getPortfolio().getOwnerName()) <  0){
    		newPortfolioNode.setNext(holderNode);
    		head = newPortfolioNode;
    	} else if(holderNode.getNext() == null){
    		holderNode.setNext(newPortfolioNode);
    	} else {
    		while(holderNode.getNext() != null){
    			if(nPN.compareTo(holderNode.getNext().getPortfolio().getOwnerName()) < 0){
    				newPortfolioNode.setNext(holderNode.getNext());
    				holderNode.setNext(newPortfolioNode);
    				break;
    			} else if(holderNode.getNext().getNext() == null){
    				holderNode.getNext().setNext(newPortfolioNode);
    				break;
    			} else {
    				holderNode = holderNode.getNext();
    			}
    		}
    	}
    }
    	
	/*
	 * 		Adds a Portfolio
     * 		after passing a Portfolio "p"
     * 		and Comparator "comp"
     * 		but maintains order with
     * 		respect to TotalValue
	 */
	public void addByTotalValue(unl.cse.Portfolio p, Comparator comp) {
		PortfolioListNode newPortfolioNode = new PortfolioListNode(p);
		holderNode = head;
		double nPN = newPortfolioNode.getPortfolio().getTotalValue();
    	
    	
    	
    	if(holderNode == null){
    		head = newPortfolioNode;
    	} else if(nPN < holderNode.getPortfolio().getTotalValue()){
    		newPortfolioNode.setNext(holderNode);
    		head = newPortfolioNode;
    	} else if(holderNode.getNext() == null){
    		holderNode.setNext(newPortfolioNode);
    	} else {
    		while(holderNode.getNext() != null){
    			if(nPN < holderNode.getNext().getPortfolio().getTotalValue()){
    				newPortfolioNode.setNext(holderNode.getNext());
    				holderNode.setNext(newPortfolioNode);
    				break;
    			} else if(holderNode.getNext().getNext() == null){
    				holderNode.getNext().setNext(newPortfolioNode);
    				break;
    			} else {
    				holderNode = holderNode.getNext();
    			}
    		}
    	}
	}
    	
	
	/*
	 * 		Adds a Portfolio
     * 		after passing a Portfolio "p"
     * 		and Comparator "comp"
     * 		but maintains order with
     * 		respect to Manager
	 */
	public void addByManager(unl.cse.Portfolio p, Comparator comp) {
		PortfolioListNode newPortfolioNode = new PortfolioListNode(p);
		holderNode = head;
		String managerName = newPortfolioNode.getPortfolio().getManagerName();
		String managerType = newPortfolioNode.getPortfolio().getBrokerType();
    	
    	
    	
    	if(holderNode == null){
    		head = newPortfolioNode;
    	} else if((managerName.compareTo(holderNode.getPortfolio().getManagerName()) <  0) 
    			&& (managerType.compareTo(holderNode.getPortfolio().getBrokerType()) <= 0)){
    		
    		newPortfolioNode.setNext(holderNode);
    		head = newPortfolioNode;
    	} else if(holderNode.getNext() == null){
    		holderNode.setNext(newPortfolioNode);
    	} else {
    		while(holderNode.getNext() != null){
    			if((managerName.compareTo(holderNode.getNext().getPortfolio().getManagerName()) < 0) 
    					&& (managerType.compareTo(holderNode.getPortfolio().getBrokerType()) <= 0)){
    				newPortfolioNode.setNext(holderNode.getNext());
    				holderNode.setNext(newPortfolioNode);
    				break;
    			} else if(holderNode.getNext() !=null
    					&& holderNode.getNext().getPortfolio().getBrokerType().equals("J")
    					&& managerType.equals("E")){
    				newPortfolioNode.setNext(holderNode.getNext());
    				holderNode.setNext(newPortfolioNode);
    				break;
    			} else if(holderNode.getNext().getNext() !=null
    					&&holderNode.getNext().getNext().getPortfolio().getBrokerType().equals("J")
    					&& managerName.compareTo(holderNode.getNext().getNext().getPortfolio().getManagerName()) < 0){
    				newPortfolioNode.setNext(holderNode.getNext().getNext());
    				holderNode.getNext().setNext(newPortfolioNode);
    				break;
    			} else if(holderNode.getNext().getNext() == null){
    				holderNode.getNext().setNext(newPortfolioNode);
    				break;
    			} else {
    				holderNode = holderNode.getNext();
    			}
    		}
    	}
    }

	@Override
	public int compare(Portfolio o1, Portfolio o2) {
		return o1.getPortfolioCode().compareTo(o2.getPortfolioCode());
	};
}
