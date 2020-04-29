package linter.syntax_tree;

import linter.exception.BadSyntaxException;
import linter.token.Token;

public class SyntaxTree {
    Node root;
    Node currentNode;

    public SyntaxTree(){
        currentNode = root;
    }

    public void improve(final Token token) throws BadSyntaxException {
        try {
            while (true) {
                while (currentNode.processToken(token))
                    currentNode = currentNode.getNextNode();
                if (currentNode.shouldRevert()) 
                    revert();
                else 
                    setToNextNode();
            }
        }
        catch(BadSyntaxException e){
            e.setLine(token.getLine());
            e.setColumn(token.getColumn());
            throw e;
        }
    }

    private void revert() throws BadSyntaxException{
        currentNode = currentNode.getParent();
        while(!currentNode.changeState()){
            currentNode = currentNode.getParent();
            if(currentNode == null)
                throw new BadSyntaxException();
        }
    }

    private void setToNextNode() throws BadSyntaxException {
        while((currentNode = currentNode.getParent().getNextNode()) == null){
            currentNode = currentNode.getParent();
            if(currentNode == null)
                throw new BadSyntaxException();
        }
    }

    public void reduce(){

    }

    public int size(){
        if(root == null)
            return 0;
        return root.getSubtreeSize();
    }
}
