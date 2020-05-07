package linter.syntax_tree;

import linter.exception.BadSyntaxException;
import linter.exception.IndentationException;
import linter.token.IdentifierToken;
import linter.token.Token;
import linter.token.type.BlockTokenType;
import linter.token.type.IdentifierTokenType;
import linter.ErrorHandler;

public class SyntaxTree {
    Node root;
    Node currentNode;
    int currentIndentLevel;
    boolean beginingOfStatement;
    boolean finished;
    boolean isCompoundTree;
    boolean shouldCheckOptional;

    public SyntaxTree(){
        root = new ProductionNode(null);
        currentNode = root;
        finished = false;
        beginingOfStatement = true;
        currentIndentLevel = 0;
        isCompoundTree = false;
    }

    public void improve(final Token token, final Token peek) throws BadSyntaxException, IndentationException {
        if(beginingOfStatement){
            if(token.getTokenType() == BlockTokenType.INDENT){
                currentIndentLevel++;
                return; // consume indent
            }
            else 
                beginingOfStatement = false;
        }
        if(token.getTokenType() == BlockTokenType.TWO_DOTS)
            isCompoundTree = true;
        try {
            if(shouldCheckOptional){
                currentNode.processTokenWhenBacking(token, peek, currentIndentLevel); //add optional nodes
                setNextProcessedNodeOrGoBack(token, peek); //go to next node
                shouldCheckOptional = false;
            }
            while (!currentNode.processToken(token, peek, currentIndentLevel)){ //process token until consumed
                while(currentNode.isEpsilon()) //while, because parent could be empty after detaching
                    processEpsilon();
                setNextProcessedNodeOrGoBack(token, peek);
            }
            if(token.getTokenType() == BlockTokenType.NEWLINE)
                processEndOfLine(peek);
            prepareForNewToken();
        }
        catch(BadSyntaxException e){
            e.setLine(token.getLine());
            e.setColumn(token.getColumn());
            ErrorHandler.handleBadSyntaxException(e);
        }
        catch(IndentationException e){
            e.setLine(token.getLine());
            e.setColumn(0);
            ErrorHandler.handleIndentationException(e);
        }
    }

    private void prepareForNewToken() {
        Node nextNode = currentNode.getNextChildNode();
        while(nextNode == null){
            currentNode = currentNode.getParent();
            if(currentNode == null)
                throw new BadSyntaxException();
            nextNode = currentNode.getNextChildNode();
            if(nextNode == null){
                shouldCheckOptional = true; //check some optionals in productions at next improve call
                return;
            }
        }
        currentNode = nextNode;
    }

    private void setNextProcessedNodeOrGoBack(Token token, Token peek) {
        Node nextNode = currentNode.getNextChildNode();
        while(nextNode == null){
            currentNode = currentNode.getParent();
            if(currentNode == null)
                throw new BadSyntaxException();
            nextNode = currentNode.getNextChildNode();
            if(nextNode == null){
                currentNode.processTokenWhenBacking(token, peek, currentIndentLevel); //check some optional productions
                nextNode = currentNode.getNextChildNode(); //try again, maybe some nodes added
            }
        }
        currentNode = nextNode;
    }

    public void reduce(){

    }

    public void processEndOfLine(Token peek){
        if (peek.getTokenType() instanceof IdentifierTokenType)
            System.out.println(((IdentifierToken)peek).getIdentifier());
        System.out.println(peek.toString()+" !!!!!!");
        if(isCompoundTree && peek.getTokenType() == BlockTokenType.INDENT){
            currentIndentLevel = 0;
            beginingOfStatement = true;
        }
        else
            finished = true;
    }

    public void processEpsilon(){
        ProductionNode parent = currentNode.getParent();
        currentNode.detachFromParent();
        currentNode = parent;
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
