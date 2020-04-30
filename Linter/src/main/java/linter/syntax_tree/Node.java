package linter.syntax_tree;

import linter.exception.BadSyntaxException;
import linter.token.Token;

public abstract class Node {
    ProductionNode parent;

    abstract public boolean processToken(Token token, Token peek) throws BadSyntaxException; //returns true when processed token successfully
    abstract public Node getNextNode();
    abstract public int getSubtreeSize();
    abstract public String getInformation();
    abstract public void printInformations();

    /**
     * 
     * @param parent
     * It is ProductionNode, because all other types are leafs.
     */
    protected Node(ProductionNode parent){
        this.parent = parent;
    }

	Node getParent() {
		return parent;
    }
    
}