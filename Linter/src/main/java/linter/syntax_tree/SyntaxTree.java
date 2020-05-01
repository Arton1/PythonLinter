package linter.syntax_tree;

import linter.exception.BadSyntaxException;
import linter.token.Token;
import linter.token.type.BlockTokenType;

public class SyntaxTree {
    Node root;
    Node currentNode;
    boolean finished;

    public SyntaxTree(){
        root = new ProductionNode(null);
        currentNode = root;
        finished = false;
    }

    public void improve(final Token token, final Token peek) throws BadSyntaxException {
        try {
            while (!currentNode.processToken(token, peek)){ //process token until consumed
                while(currentNode.isEpsilon()){
                    ProductionNode parent = currentNode.getParent();
                    currentNode.detachFromParent();
                    currentNode = parent;
                }
                setNextProcessedNode();
            }
            if(token.getTokenType() == BlockTokenType.NEWLINE)
                finished = true;
            else
                setNextProcessedNode();
        }
        catch(BadSyntaxException e){
            e.setLine(token.getLine());
            e.setColumn(token.getColumn());
            throw e;
        }
    }

    private void setNextProcessedNode() throws BadSyntaxException {
        Node nextNode = currentNode.getNextNode();
        if(nextNode == null){
            while((nextNode = currentNode.getParent().getNextNode()) == null){
                currentNode = currentNode.getParent();
                if(currentNode == null)
                    throw new BadSyntaxException();
            }   
        }
        currentNode = nextNode;
    }

    public void reduce(){

    }

    public int size(){
        if(root == null)
            return 0;
        return root.getSubtreeSize();
    }

    public boolean finished(){
        return finished;
    }

    public void printNodesInformations(){
        root.printInformations();
    }
}
