package linter.syntax_tree;

import linter.exception.BadSyntaxException;
import linter.exception.IndentationException;
import linter.token.Token;
import linter.token.type.BlockTokenType;
import linter.token.type.CompoundStatementTokenType;

import java.util.ArrayList;
import java.util.List;

import linter.ErrorHandler;

public class SyntaxTree {
    Node root = new ProductionNode(null);
    Node currentNode = root;
    Node nextNodeToReturn = root;
    int currentIndentLevel = 0;
    boolean beginingOfStatement = false;
    boolean finished = false;
    boolean isCompoundTree = false;
    boolean shouldCheckOptional;

    public void improve(final Token token, final Token peek) throws BadSyntaxException, IndentationException {
        if(beginingOfStatement)
            if(token.getTokenType() == BlockTokenType.INDENT){
                currentIndentLevel++;
                return; // consume indent
            }
            else 
                beginingOfStatement = false;
        checkIfCompoundTree(token);
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
            checkIfFinished(token, peek);
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

    private void checkIfCompoundTree(Token token){
        if(token.getTokenType() == BlockTokenType.TWO_DOTS)
            isCompoundTree = true;
    }

    public void checkIfFinished(Token token, Token peek){
        if(!isCompoundTree){
            if(token.getTokenType() == BlockTokenType.NEWLINE)
                finished = true;
        }
        else {
            if(token.getTokenType() == BlockTokenType.NEWLINE){
                if(peek.getTokenType() == BlockTokenType.INDENT || 
                    peek.getTokenType() == CompoundStatementTokenType.ELSE ||
                    peek.getTokenType() == CompoundStatementTokenType.ELIF)
                {
                    currentIndentLevel = 0;
                    beginingOfStatement = true;
                }
                else {
                    finished = true;
                }
            }
        }
    }

    public void processEpsilon(){
        ProductionNode parent = currentNode.getParent();
        currentNode.detachFromParent();
        currentNode = parent;
    }

    public List<Token> getNextInstruction(){
        List<Token> tokens = new ArrayList<Token>();
        while(currentNode != null && !currentNode.isSuiteProduction()){
            Node nextNode = currentNode.getNextChildNode();
            if(nextNode != null){
                currentNode = nextNode;
                Token token = currentNode.getToken();
                if(token != null)
                    tokens.add(token);
            }
            else
                currentNode = currentNode.getParent();
        }
        return tokens;
    }

    public int getCurrentIndentLevel(){
        if(currentNode.isSuiteProduction())
            currentIndentLevel = currentNode.getLevel();
        return currentIndentLevel;
    }

    public int size(){
        if(root == null)
            return 0;
        return root.getSubtreeSize();
    }

    public boolean finished(){
        return finished;
    }

    public void resetTravel(){
        root.reset();
        currentNode = root;
    }

    public void printNodesInformations(){
        root.printInformations();
    }
}
