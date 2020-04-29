package linter.visitor;

import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.TokenType;

public class NodeCreatorVisitor implements Visitor {
    ProductionNode parent;

    public NodeCreatorVisitor(ProductionNode parent){
        this.parent = parent;
    }

	public void visit(TokenType tokenType){
        
    }

    public void visit(Token token){

    }

    public void visit(Production production){
        
    }
}