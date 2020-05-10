package linter.syntax_tree.production;

import java.util.LinkedList;
import java.util.List;

import linter.exception.IndentationException;
import linter.syntax_tree.TreeElement;
import linter.token.Token;
import linter.visitor.Visitor;

public abstract class Production implements TreeElement {

    abstract public List<TreeElement> expand(Token token, Token peek, int currentIndentLevel) throws IndentationException; //LL(1), so it has to have a lookahead

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

    protected static final List<TreeElement> createExpansion(TreeElement...treeElements){
        return new LinkedList<TreeElement>(List.of(treeElements));
    }

	public List<TreeElement> expandOptional(Token token, Token peek, int currentIndentLevel) {
		return null; //by default
	}

}