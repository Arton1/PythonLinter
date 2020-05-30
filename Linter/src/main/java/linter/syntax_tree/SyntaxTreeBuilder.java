package linter.syntax_tree;

import linter.exception.BadSyntaxException;
import linter.exception.IndentationException;
import linter.token.Token;
import linter.token.type.BlockTokenType;
import linter.token.type.CompoundStatementTokenType;
import linter.token.type.IdentifierTokenType;
import linter.token.type.TokenType;

import java.util.ArrayList;
import java.util.List;

public class SyntaxTreeBuilder extends SyntaxTree {
    int currentIndentLevel = 0;
    boolean beginingOfStatement = false;
    boolean finished = false;
    boolean shouldCheckOptional;

    public SyntaxTreeBuilder() {
        super(new ProductionNode(null));
    }

    public SyntaxTreeBuilder(SyntaxTreeBuilder syntaxTreeBuilder) {
        super(syntaxTreeBuilder);
    }

    public void improve(final Token token, final Token peek) throws BadSyntaxException, IndentationException {
        if(beginingOfStatement)
            if(token.getTokenType() == BlockTokenType.INDENT){
                currentIndentLevel++;
                return; // consume indent
            }
            else
                beginingOfStatement = false;
        try {
            if(shouldCheckOptional){
                currentNode.processTokenWhenBacking(token, peek, currentIndentLevel); //add optional nodes
                setPositionToNextProcessedNodeOrGoBack(token, peek); // go to next node
                shouldCheckOptional = false;
                if(finished){
                    checkTreeIntegrity();
                    return;
                }
            }
            while (!currentNode.processToken(token, peek, currentIndentLevel)){ //process token until consumed
                while(currentNode.isEpsilon()) //while, because parent could be empty after detaching
                    processEpsilon();
                setPositionToNextProcessedNodeOrGoBack(token, peek);
                if(finished){
                    checkTreeIntegrity();
                    return;
                }
            }
            setPositionForNewToken();
            if(token.getTokenType() == BlockTokenType.NEWLINE && peek.getTokenType() != BlockTokenType.INDENT)
                finished = true;
            if(finished){
                checkTreeIntegrity();
                return;
            }
            checkBeginningOfStatement(token, peek);
        }
        catch(BadSyntaxException e){
            e.setLine(token.getLine());
            e.setColumn(token.getColumn());
            throw e;
            
        }
        catch(IndentationException e){
            e.setLine(token.getLine());
            e.setColumn(0);
            throw e;
        }
    }

    private void setPositionForNewToken() {
        Node nextNode = currentNode.getNextChildNode();
        while(nextNode == null){
            nextNode = currentNode.getParent();
            if(nextNode == null){
                finished = true; //Went back to root
                return; //stay on the current node
            }
            currentNode = nextNode;
            nextNode = currentNode.getNextChildNode();
            if(nextNode == null){
                if(currentNode.isRoot())
                    finished = true;
                else
                    shouldCheckOptional = true; //check some optionals in productions at next improve call
                return;
            }
        }
        currentNode = nextNode;
    }

    private void setPositionToNextProcessedNodeOrGoBack(Token token, Token peek) {
        Node nextNode = currentNode.getNextChildNode();
        while(nextNode == null){
            nextNode = currentNode.getParent();
            if(nextNode == null){
                //HERE
                finished = true; //Went back to root
                return; //stay on the current node
            }
            currentNode = nextNode;
            nextNode = currentNode.getNextChildNode();
            if(nextNode == null){
                currentNode.processTokenWhenBacking(token, peek, currentIndentLevel); //check some optional productions
                nextNode = currentNode.getNextChildNode(); //try again, maybe some nodes added
            }
        }
        currentNode = nextNode;
    }

    private void checkBeginningOfStatement(Token token, Token peek){
        if(token.getTokenType() == BlockTokenType.NEWLINE ||
            peek.getTokenType() == BlockTokenType.INDENT || 
            peek.getTokenType() == CompoundStatementTokenType.ELSE ||
            peek.getTokenType() == CompoundStatementTokenType.ELIF){
                currentIndentLevel = 0;
                beginingOfStatement = true;
            }
    }

    private void checkTreeIntegrity(){
        currentNode.checkSubtreeViability(); //Current node is SingleInputProduction
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

    public boolean finished(){
        return finished;
    }
}
