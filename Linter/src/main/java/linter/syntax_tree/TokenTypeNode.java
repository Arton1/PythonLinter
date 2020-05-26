package linter.syntax_tree;

import linter.SemanticsAnalizer;
import linter.exception.BadSyntaxException;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.TokenType;

public class TokenTypeNode extends Node {
    TokenType tokenType;

    public TokenTypeNode(ProductionNode parent, TokenType tokenType) {
        super(parent);
        this.tokenType = tokenType;
    }

    @Override
    public boolean processToken(Token token, Token peek, int currentIndentLevel) throws BadSyntaxException {
        if (token.getTokenType() != tokenType)
            throw new BadSyntaxException();
        TokenNode node = new TokenNode(parent, token);
        parent.exchange(this, node);
        return true; // token consumed
    }

    @Override
    public Node getNextChildNode() {
        return null; // no children in here
    }

    @Override
    public int getSubtreeSize() {
        return 1;
    }

    @Override
    public String getInformation() {
        return "Token type node : " + tokenType;
    }

    @Override
    public void printInformations() {
        System.out.println(getInformation());
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
    public void accept(SemanticsAnalizer analizer) {
        // TODO: Throw bad syntax tree error
    }

    @Override
    public Token getToken() {
        // TODO: Throw bad syntax tree error
        return null;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean isType(Class<? extends Production> className) {
        // TODO Auto-generated method stub
        return false;
    }
}