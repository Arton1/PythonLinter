package linter;

import java.util.Deque;
import java.util.ListIterator;

import linter.exception.BadSyntaxException;
import linter.production.Production;
import linter.token.Token;

public class SyntaxTree {
    Production root;
    Production currentNode;
    Deque<ListIterator<Production>> edgesStack;

    public SyntaxTree(){
        currentNode = root;
    }

    void improve(Token token) throws BadSyntaxException {
        boolean success = currentNode.expand(token);
        if(success){
            ListIterator<Production> currentNodeIterator = currentNode.getProductions().listIterator();
            currentNode = currentNodeIterator.next();
            edgesStack.add(currentNodeIterator);
        }
        else{
            ListIterator<Production> parentIterator = edgesStack.pop();
            currentNode = parentIterator.next();
        }
    }

    void reduce(){

    }
}