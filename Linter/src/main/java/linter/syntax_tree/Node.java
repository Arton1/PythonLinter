package linter.syntax_tree;

import linter.exception.BadSyntaxException;
import linter.token.Token;

public interface Node {

    boolean processToken(Token token) throws BadSyntaxException; //returns true when processed token successfully
    Node getNextNode();
    int getSubtreeSize();
	boolean shouldRevert();

}