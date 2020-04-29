package linter.visitor;

import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.TokenType;

public interface Visitor {
    public void visit(TokenType tokenType);
    public void visit(Token token);
    public void visit(Production production);
}