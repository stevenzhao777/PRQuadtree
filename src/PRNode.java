/**
 * 
 * @author xuanhaozhao
 *
 */

abstract class PRNode {
	
	 protected NodeType nodeType;
	 
     private PRInternalNode parent;
     
     protected PRNode(){
    	 
     }
     
     protected PRNode(PRInternalNode parent){
    	 this.parent=parent;
     }
     
     public NodeType getNodeType(){
    	 return this.nodeType;
     }
     
     
     public PRNode getParent(){
    	 return parent;
     }
     
     public void setParent(PRInternalNode parent){
    	 this.parent=parent;
     }
     
}

class PRInternalNode extends PRNode{
	/*left bound of the current internal node*/
	private double leftBd;
	/*right bound of the current internal node*/
	private double rightBd;
	/*top bound of the current internal node*/
	private double topBd;
	/*bottom bound of the current internal node*/
	private double btmBd;
	
	/*
	 * One improvement in mind is to put a hash table containing current node's four child nodes. 
	 * That way, a lot of redundant code can be removed and the hash table's iterator can be used 
	 * whenever we need to iterate through current node's children. When I have time/after I roughly 
	 * finish the Hadoop quadtree.
	 */
	
	private PRNode NW;
    private PRNode NE;
    private PRNode SW;
    private PRNode SE;
    
    protected PRInternalNode(){
    	this(0.00,100.00,0.00,100.00,null);
    }
    
    protected PRInternalNode(double leftBd,double rightBd,double topBd,double btmBd,PRInternalNode parent){
    	super(parent);
    	nodeType=NodeType.INTERNAL;
    	
    	this.leftBd=leftBd;
    	this.rightBd=rightBd;
    	this.topBd=topBd;
    	this.btmBd=btmBd;
    }
    
    
    public double getLeftBd(){
   	 return leftBd;
    }
    
    public double getRightBd(){
   	 return rightBd;
    }
    
    public double getBtmBd(){
    	return btmBd;
    }
    
    public double getTopBd(){
        return topBd;
    }
    
	public PRNode getNW(){
   	 return NW;
    }
    
    public void setNW(PRNode NW){
   	 this.NW=NW;
    }
    
    public PRNode getNE(){
      return NE;
    }
    
    public void setNE(PRNode NE){
      this.NE=NE;
    }
    
    public PRNode getSW(){
   	 return SW;
    }
    
    public void setSW(PRNode SW){
   	 this.SW=SW;
    }
    
    public PRNode getSE(){
   	 return SE;
    }
    
    public void setSE(PRNode SE){
   	 this.SE=SE;
    }
}

class PRLeaf extends PRNode{
	
	private double X;
    private double Y;
	
	protected PRLeaf(){
		nodeType=NodeType.LEAF;
	}
	
	protected PRLeaf(double X,double Y,PRInternalNode parent){
		super(parent);
		
		nodeType=NodeType.LEAF;
		
	    this.X=X;
	    this.Y=Y;
	}
	
	public double getX(){
   	 return X;
    }
    
    public void setX(double X){
   	 this.X=X;
    }
    
    public double getY(){
   	 return Y;
    }
    
    public void setY(double Y){
   	 this.Y=Y;
    }
}

class PRNull extends PRNode{
	protected PRNull(){
		nodeType=NodeType.NULL;
	}
}
