package linter.syntax_tree;

import linter.SemanticsAnalizer;
import linter.exception.BadSyntaxException;
import linter.syntax_tree.production.Production;
import linter.token.Token;

public class TokenNode extends Node {

    Token token;

    public TokenNode(ProductionNode parent, Token token) {
        super(parent);
        this.token = token;
    }

    @Override
    public boolean processToken(Token token, Token peek, int currentIndentLevel) throws BadSyntaxException {
        if (this.token.getTokenType() != token.getTokenType())
            throw new BadSyntaxException();
        return true; // token already consumed
    }

    @Override
    public int getSubtreeSize() {
        return 1;
    }

    @Override
    public String getInformation() {
        return "Token node : " + token.getTokenType();
    }

    @Override
    public void printInformations() {
        System.out.println(getInformation());
    }

    @Override
    public Node getNextChildNode() {
        return null; // no children in here
    }

    @Override
    public boolean isEpsilon() {
        return false;
    }

    @Override
    public void processTokenWhenBacking(Token token, Token peek, int currentIndentLevel) throws BadSyntaxException {
        // no processing
    }

    @Override
    public Token getToken() {
        return token;
    }

    @Override
    public void accept(SemanticsAnalizer analizer) {
        analizer.visit(this);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isType(Class<? extends Production> className) {
        return false;
    }

    @Override
    public Token getSubtreeFirstToken() {
        return token;
    }

    @Override
    public void checkSubtreeViability() {
        return; //nothing to do, nothing to throw
    }

}