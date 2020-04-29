package linter.syntax_tree;

import linter.visitor.Visitor;

public interface TreeElement {
    public void accept(Visitor visitor);
}
