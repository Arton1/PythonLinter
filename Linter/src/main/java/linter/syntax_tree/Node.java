package linter.syntax_tree;

import linter.exception.BadSyntaxException;
import linter.token.Token;

public abstract class Node {
    protected ProductionNode parent;

    abstract public boolean processToken(Token token, Token peek) throws BadSyntaxException; //returns true when consumed token
    abstract public Node getNextNode();
    abstract public int getSubtreeSize();
    abstract public String getInformation();
    abstract public void printInformations();
    abstract public boolean isEpsilon();

    /**
     * 
     * @param parent
     * It is ProductionNode, because all other types are leafs.
     */
    protected Node(ProductionNode parent){
        this.parent = parent;
    }

	public ProductionNode getParent() {
		return parent;
    }

    /**
     * Works only, when used on node that's been currently processed
     */
    public void detachFromParent(){
        parent.removeProcessedNode();
    }
    
}