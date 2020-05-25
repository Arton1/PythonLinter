package linter.syntax_tree;

import linter.SemanticsAnalizer;
import linter.exception.BadSyntaxException;
import linter.token.Token;

public abstract class Node {
    protected ProductionNode parent;

    abstract public boolean processToken(Token token, Token peek, int currentIndentLevel) throws BadSyntaxException; //returns true when consumed token
    abstract public void processTokenWhenBacking(Token token, Token peek, int currentIndentLevel) throws BadSyntaxException; //returns true when consumed token
    abstract public Node getNextChildNode();
    abstract public int getSubtreeSize();
    abstract public String getInformation();
    abstract public void printInformations();
    abstract public boolean isEpsilon();
    abstract public Token getToken();
    abstract public void accept(SemanticsAnalizer analizer);
    abstract public void accept(NodeVisitor visitor);
    
    public void reset(){ }

    /**
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
    
	public boolean isSuiteProduction() {
		return false;
    }
    
	public int getLevel() {
		return 0; //TODO: Should throw cannot get level error
	}
    
}