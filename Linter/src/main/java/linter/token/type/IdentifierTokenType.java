package linter.token.type;

import linter.syntax_tree.TreeElementVisitor;

public enum IdentifierTokenType implements TokenType {
    NAME;

    @Override
    public void accept(TreeElementVisitor visitor){
        visitor.visit(this);
    }
}