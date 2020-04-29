package linter.visitor;

import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.TokenNode;
import linter.syntax_tree.TokenTypeNode;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.TokenType;

public class NodeCreatorVisitor implements Visitor {
    ProductionNode parent;

    public NodeCreatorVisitor(ProductionNode parent){
        this.parent = parent;
    }

	public void visit(TokenType tokenType){
        TokenTypeNode node = new TokenTypeNode(parent, tokenType);
        parent.addNode(node);
    }

    public void visit(Token token){
        TokenNode node = new TokenNode(parent, token);
        parent.addNode(node);
    }

    public void visit(Production production){
        ProductionNode node = new ProductionNode(parent, production);
        parent.addNode(node);
    }
}