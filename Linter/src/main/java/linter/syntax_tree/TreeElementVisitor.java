package linter.syntax_tree;

import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.TokenType;

public class TreeElementVisitor {
    ProductionNode parent;

    public TreeElementVisitor(ProductionNode parent){
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