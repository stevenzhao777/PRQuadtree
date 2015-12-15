import java.util.Collection;
/**
 * 
 * @author xuanhaozhao
 *
 */
public class PRQuadTree {
	
	
	public static void main(String[] args){
		PRQuadTree tree=new PRQuadTree(0,0,2,2);
		Point p1=new Point(1.5,1.5);
		Point p2=new Point(1.5,0.25);
		Point p3=new Point(0.5,0.5);
		Point p4=new Point(0.25,0.25);
		tree.insert(p1);
		tree.insert(p3);
		tree.insert(p2);
		tree.insert(p4);
		tree.delete(p4);
		System.out.println(tree.root.getNodeType());
		PRLeaf leaf=(PRLeaf)((PRInternalNode)tree.root).getSW();
//		PRInternalNode sub=(PRInternalNode) ((PRInternalNode)tree.root).getSW();
//		System.out.println(((PRLeaf)tree.root).getX());
		System.out.println(((PRLeaf)((PRInternalNode)tree.root).getNE()).getY());
		System.out.println((((PRInternalNode)tree.root).getSE()).getNodeType());
//		System.out.println((((PRLeaf)sub.getSW()).getX()));
//		System.out.println((((PRLeaf)sub.getNE()).getX()));
		System.out.println(leaf.getY());
		System.out.println((((PRInternalNode)tree.root).getNW()).getNodeType());
	}
	
	
    private PRNode root;
    
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;
    
    public PRQuadTree(){
    	root=new PRNull();
    }
    
    public PRQuadTree(double minX,double minY,double maxX,double maxY){
    	this();
    	this.maxX=maxX;
    	this.minX=minX;
    	this.minY=minY;
    	this.maxY=maxY;
    	
    }
    
    public PRQuadTree(double minX,double minY,double maxX,double maxY,PRNode root){
    	this(minX,minY,maxX,maxY);
    	if(root==null){
    		throw new IllegalArgumentException();
    	}
    	this.root=root;
    }
    
    public double getMinX(){
    	return minX;
    }
    
    public void setMinX(double minX){
    	this.minX=minX;
    }
    
    public double getMaxX(){
    	return maxX;
    }
    
    public void setMaxX(double maxX){
    	this.maxX=maxX;
    }
    
    public double getMinY(){
    	return minY;
    }
    
    public void setMinY(double minY){
    	this.minY=minY;
    }
    
    public double getMaxY(){
    	return maxY;
    }
    
    public void setMaxY(double maxY){
    	this.maxY=maxY;
    }
    
    public PRNode getRoot(){
    	return root;
    }
    
    public void setRoot(PRNode root){
    	if(root==null){
    		throw new IllegalArgumentException();
    	}
    	this.root=root;
    }
    
    
    public PRNode search(Point p){
    	if(p==null||p.getX()<minX||p.getX()>maxX||p.getY()<minY||p.getY()>maxY){
    		throw new IllegalArgumentException();
    	}
    	PRNode result=findNode(p);
    	if(result.getNodeType()==NodeType.NULL){
    		System.out.println("Cannot find a node that contains input point");
    		return null;
    	}
    	return result;
    }
    
    public void delete(Point p){
    	if(p==null||p.getX()<minX||p.getX()>maxX||p.getY()<minY||p.getY()>maxY){
    		throw new IllegalArgumentException();
    	}
    	
    	PRNode node=findNode(p);
    	
    	if(node.getNodeType()==NodeType.NULL){
    		System.out.println("Given point does not exist!");
    		return;
    	}
    	else{
    		PRInternalNode parent=(PRInternalNode)node.getParent();
    		PRNull nullNode=new PRNull();
    		nullNode.setParent(parent);
    		
    		if(parent.getNE()==node){
    			parent.setNE(nullNode);
    		}
    		else if(parent.getSE()==node){
    			parent.setSE(nullNode);
    		}
    		else if(parent.getNW()==node){
    			parent.setNW(nullNode);
    		}
    		else{
    			parent.setSW(nullNode);
    		}
    		
    		PRLeaf singleLeaf=null;
    		PRInternalNode grandParent=null;
    		int count=0;
    		while(parent!=null){
    			count=0;
    			if(parent.getNE().getNodeType()==NodeType.LEAF){
    				singleLeaf=(PRLeaf)parent.getNE();
    				count++;
    			}
    			if(parent.getSE().getNodeType()==NodeType.LEAF){
    				singleLeaf=(PRLeaf)parent.getSE();
    				count++;
    			}
    			if(parent.getNW().getNodeType()==NodeType.LEAF){
    				singleLeaf=(PRLeaf)parent.getNW();
    				count++;
    			}
    			if(parent.getSW().getNodeType()==NodeType.LEAF){
    				singleLeaf=(PRLeaf)parent.getSW();
    				count++;
    			}
    			System.out.println("leaf child count "+count);
    			if(count>1){
    				break;
    			}
    			else if(count==1){
    				grandParent=(PRInternalNode)parent.getParent();
    				singleLeaf.setParent(grandParent);
    			
    				if(grandParent!=null){
    					if(grandParent.getNE()==parent){
							grandParent.setNE(singleLeaf);
						}
						else if(grandParent.getNW()==parent){
							grandParent.setNW(singleLeaf);
						}
						else if(grandParent.getSE()==parent){
							grandParent.setSE(singleLeaf);
						}
						else{
							grandParent.setSW(singleLeaf);
						}
    				}
    				else{
    					root=singleLeaf;
    				}
    				parent=grandParent;
    			}
    			else{
    				grandParent=(PRInternalNode)parent.getParent();
    				nullNode.setParent(grandParent);
    				if(grandParent!=null){
						if(grandParent.getNE()==parent){
							grandParent.setNE(nullNode);
						}
						else if(grandParent.getNW()==parent){
							grandParent.setNW(nullNode);
						}
						else if(grandParent.getSE()==parent){
							grandParent.setSE(nullNode);
						}
						else{
							grandParent.setSW(nullNode);
						}
    				}
    				else{
    					root=nullNode;
    				}
    				parent=grandParent;
    			}
    		}
    	}
    }
    
    public void insert(Point p){
    	if(p==null||p.getX()<minX||p.getX()>maxX||p.getY()<minY||p.getY()>maxY){
    		throw new IllegalArgumentException();
    	}
    	
    	PRNode node=findNode(p);
    	
    	if(node.getNodeType()==NodeType.NULL){
    		
    		PRNode newLeaf=new PRLeaf(p.getX(),p.getY(),null);
    		
    		if(node==root){
    			root=newLeaf;
    			return;
    		}
    		
    		PRInternalNode parent = (PRInternalNode)node.getParent();
    		newLeaf.setParent(parent);
    		
            if(parent.getNE()==node){
            	parent.setNE(newLeaf);
            }
            else if(parent.getSE()==node){
            	parent.setSE(newLeaf);
            }
            else if(parent.getNW()==node){
            	parent.setNW(newLeaf);
            }
            else{
            	parent.setSW(newLeaf);
            }
    	}
    	else{
    		
    		if(p.getX()==((PRLeaf)node).getX()&&p.getY()==((PRLeaf)node).getY()){
    			return;
    		}
    		
    		if(node==root){
    			
    			PRInternalNode newRoot=new PRInternalNode(minX,maxX,maxY,minY,null);
    			node.setParent(newRoot);
    			
    			addLeaf(newRoot,(PRLeaf)node);
    			
		   	    root=newRoot;
		  
    		}
    		
    		double curLeft;
    		double curRight;
    		double curTop;
    		double curBottom;
    		
    		double curMidX;
    		double curMidY;
    		
    		PRLeaf newLeaf=new PRLeaf(p.getX(),p.getY(),null);
    		
    		/*if two points are two close, there might be a possible infinite loop,
    		 * since if we keep divide a double by two, it gets to zero pretty quickly.
    		*/
    		while(true){
    			PRInternalNode parent=(PRInternalNode)node.getParent();
    			curLeft=parent.getLeftBd();
    			curRight=parent.getRightBd();
    			curTop=parent.getTopBd();
    			curBottom=parent.getBtmBd();
    			
    			curMidX=curLeft+(curRight-curLeft)/2;
       	     	curMidY=curBottom+(curTop-curBottom)/2;
       	     	
		   	     if(p.getX()>=curMidX&&p.getY()>curMidY){
		   	    	if(parent.getNE().getNodeType()==NodeType.NULL){
		   	    		newLeaf.setParent(parent);
		   	    		parent.setNE(newLeaf);
		   	    		break;
		   	    	}
		   	    	else{
		   	    		PRInternalNode curParent=new PRInternalNode(curMidX,curRight,curTop,curMidY,parent);
		   	    		curParent.setParent(parent);
		   	    		parent.setNE(curParent);
		   	    		addLeaf(curParent,(PRLeaf)node);
		   	    	}
			     }
			     else if(p.getY()<=curMidY&&p.getX()>curMidX){
			    	 if(parent.getSE().getNodeType()==NodeType.NULL){
			    		 	newLeaf.setParent(parent);
			   	    		parent.setSE(newLeaf);
			   	    		break;
			   	    	}
			   	    	else{
			   	    		PRInternalNode curParent=new PRInternalNode(curMidX,curRight,curMidY,curBottom,parent);
			   	    		curParent.setParent(parent);
			   	    		parent.setSE(curParent);
			   	    		addLeaf(curParent,(PRLeaf)node);
			   	    	}
			     }
			     else if(p.getX()<=curMidX&&p.getY()<curMidY){
			    	 if(parent.getSW().getNodeType()==NodeType.NULL){
			    		    newLeaf.setParent(parent);
			   	    		parent.setSW(newLeaf);
			   	    		break;
			   	    	}
			   	    	else{
			   	    		PRInternalNode curParent=new PRInternalNode(curLeft,curMidX,curMidY,curBottom,parent);
			   	    		curParent.setParent(parent);
			   	    		parent.setSW(curParent);
			   	    		addLeaf(curParent,(PRLeaf)node);
			   	    	}
			     }
			     else if(p.getY()>=curMidY&&p.getX()<curMidX){
			    	 if(parent.getNW().getNodeType()==NodeType.NULL){
			    		    newLeaf.setParent(parent);
			   	    		parent.setNW(newLeaf);
			   	    		break;
			   	    	}
			   	    	else{
			   	    		PRInternalNode curParent=new PRInternalNode(curLeft,curMidX,curTop,curMidY,parent);
			   	    		curParent.setParent(parent);
			   	    		parent.setNW(curParent);
			   	    		addLeaf(curParent,(PRLeaf)node);
			   	    	}
			     }
			     else{
			    	 if(parent.getNE().getNodeType()==NodeType.NULL){
			    		    newLeaf.setParent(parent);
			   	    		parent.setNE(newLeaf);
			   	    		break;
			   	    	}
			   	    	else{
			   	    		PRInternalNode curParent=new PRInternalNode(curMidX,curRight,curTop,curMidY,parent);
			   	    		curParent.setParent(parent);
			   	    		parent.setNE(curParent);
			   	    		addLeaf(curParent,(PRLeaf)node);
			   	    	}
			     }
    		}
    		
    	}
    }
    
    private void addLeaf(PRInternalNode parent,PRLeaf child){
    	if(parent==null||child==null){
    		throw new IllegalArgumentException();
    	}
    	
    	child.setParent(parent);
    	
    	double curMidX=parent.getLeftBd()+(parent.getRightBd()-parent.getLeftBd())/2;
     	double curMidY=parent.getBtmBd()+(parent.getTopBd()-parent.getBtmBd())/2;
		
     	PRNull nullNode1=new PRNull();
     	PRNull nullNode2=new PRNull();
     	PRNull nullNode3=new PRNull();
     	
     	nullNode1.setParent(parent);
     	nullNode2.setParent(parent);
        nullNode3.setParent(parent);
     	
		if(child.getX()>=curMidX&&child.getY()>curMidY){
   	    	 parent.setNE(child);
   	    	 parent.setNW(nullNode1);
   	    	 parent.setSE(nullNode2);
   	    	 parent.setSW(nullNode3);
   	     }
   	     else if(child.getY()<=curMidY&&child.getX()>curMidX){
   	    	 parent.setSE(child);
   	    	 parent.setNW(nullNode1);
   	    	 parent.setNE(nullNode2);
   	    	 parent.setSW(nullNode3);
   	     }
   	     else if(child.getX()<=curMidX&&child.getY()<curMidY){
   	    	parent.setSW(child);
    	    parent.setNW(nullNode1);
    	    parent.setNE(nullNode2);
    	    parent.setSE(nullNode3);
   	     }
   	     else if(child.getY()>=curMidY&&child.getX()<curMidX){
   	    	 parent.setNW(child);
   	    	 parent.setSE(nullNode1);
   	    	 parent.setNE(nullNode2);
   	    	 parent.setSW(nullNode3);
   	     }
   	     else{
   	    	 parent.setNE(child);
   	    	 parent.setNW(nullNode1);
   	    	 parent.setSE(nullNode2);
   	    	 parent.setSW(nullNode3);
   	     }
    }
    
    private PRNode findNode(Point p){
    	PRNode cur=root;
    	
    	double curBottom=minY;
    	double curTop=maxY;
    	double curLeft=minX;
    	double curRight=maxX;
    	
    	double curMidX;
    	double curMidY;
    	while(cur.getNodeType()==NodeType.INTERNAL){
    		
    	     curMidX=curLeft+(curRight-curLeft)/2;
    	     curMidY=curBottom+(curTop-curBottom)/2;
    	     
    	     if(p.getX()>=curMidX&&p.getY()>curMidY){
    	    	 cur=((PRInternalNode)cur).getNE();
    	     }
    	     else if(p.getY()<=curMidY&&p.getX()>curMidX){
    	    	 cur=((PRInternalNode)cur).getSE();
    	     }
    	     else if(p.getX()<=curMidX&&p.getY()<curMidY){
    	    	 cur=((PRInternalNode)cur).getSW();
    	     }
    	     else if(p.getY()>=curMidY&&p.getX()<curMidX){
    	    	 cur=((PRInternalNode)cur).getNW();
    	     }
    	     else{
    	    	 cur=((PRInternalNode)cur).getNE();
    	     }
    	     
    	     if(cur.getNodeType()==NodeType.INTERNAL){
			     curLeft=((PRInternalNode)cur).getLeftBd();
		    	 curRight=((PRInternalNode)cur).getRightBd();
		    	 curTop=((PRInternalNode)cur).getTopBd();
		    	 curBottom=((PRInternalNode)cur).getBtmBd();
    	     }
	    	 
    	}
    	return cur;
    }
    
    
}
