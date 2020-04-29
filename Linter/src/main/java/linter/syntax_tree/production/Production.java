package linter.syntax_tree.production;

import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.visitor.Visitor;

public abstract class Production implements TreeElement {
    abstract public List<TreeElement> expand(Token token);

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}