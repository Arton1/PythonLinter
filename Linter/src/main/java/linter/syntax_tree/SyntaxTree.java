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
                    currentNode = getNextProcessedNode();
                break;
            }
        }
        catch(BadSyntaxException e){
            e.setLine(token.getLine());
            e.setColumn(token.getColumn());
            throw e;
        }
    }

    private Node getNextProcessedNode() throws BadSyntaxException {
        Node node;
        while((node = currentNode.getParent().getNextNode()) == null){
            node = currentNode.getParent();
            if(currentNode == null)
                throw new BadSyntaxException();
        }
        return node;
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
}
