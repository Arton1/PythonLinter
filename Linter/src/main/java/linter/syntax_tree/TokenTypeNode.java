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
        throw new RuntimeException("Bad syntax tree");
    }

    @Override
    public void accept(SemanticsAnalizer analizer) {
        throw new RuntimeException("Bad syntax tree");
    }

    @Override
    public Token getToken() {
        throw new RuntimeException("Bad syntax tree");
    }

    @Override
    public void accept(NodeVisitor visitor) {
        throw new RuntimeException("Bad syntax tree");
    }

    @Override
    public boolean isType(Class<? extends Production> className) {
        throw new RuntimeException("Bad syntax tree");
    }

    @Override
    public Token getSubtreeFirstToken() {
        throw new RuntimeException("Bad syntax tree");
    }

    @Override
    public void checkSubtreeViability() {
        throw new BadSyntaxException();
    }
}