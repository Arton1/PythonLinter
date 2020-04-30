package linter.syntax_tree.production;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.visitor.Visitor;

public abstract class Production implements TreeElement {
    abstract public List<TreeElement> expand(Token token, Token peek); //LL(1), so it has to have a lookahead

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}