package linter.syntax_tree;

import linter.exception.BadSyntaxException;
import linter.token.Token;

public abstract class Node {
    ProductionNode parent;

    abstract boolean processToken(Token token) throws BadSyntaxException; //returns true when processed token successfully
    abstract Node getNextNode();
    abstract int getSubtreeSize();
    abstract boolean shouldRevert();

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