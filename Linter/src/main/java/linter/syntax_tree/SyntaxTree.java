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

    public void improve(final Token token) throws BadSyntaxException {
        try {
            while (true) {
                while (currentNode.processToken(token))
                    currentNode = currentNode.getNextNode();
                if(token.getTokenType() == BlockTokenType.NEWLINE)
                    finished = true;
                else
                    setNextProcessedNode();
                break;
            }
        }
        catch(BadSyntaxException e){
            e.setLine(token.getLine());
            e.setColumn(token.getColumn());
            throw e;
        }
    }

    private void setNextProcessedNode() throws BadSyntaxException {
        Node nextNode;
        while((nextNode = currentNode.getParent().getNextNode()) == null){
            currentNode = currentNode.getParent();
            if(currentNode == null)
                throw new BadSyntaxException();
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
