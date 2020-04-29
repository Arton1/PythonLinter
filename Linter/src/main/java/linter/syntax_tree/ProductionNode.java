package linter.syntax_tree;

import java.util.List;
import java.util.ListIterator;

import linter.exception.BadSyntaxException;
import linter.syntax_tree.production.Production;
import linter.syntax_tree.production.SingleInputProduction;
import linter.token.Token;
import linter.visitor.NodeCreatorVisitor;
import linter.visitor.Visitor;

public class ProductionNode implements Node {
    Node parent;
    List<Node> nodes;
    ListIterator<Node> nodesIterator;
    Production production;

    protected ProductionNode(Node parent) {
        this.parent = parent;
        production = new SingleInputProduction();
    }

    @Override
    public boolean processToken(Token token) throws BadSyntaxException {
        List<TreeElement> children;
        children = production.expand(token);
        Visitor visitor = new NodeCreatorVisitor(this);
        if(children == null)
            throw new BadSyntaxException(); // couldn't match token
        for (TreeElement child : children){
            child.accept(visitor); //create node and add to list
        }
        return true; //continue processing
    }

    @Override
    public Node getNextNode() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getSubtreeSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean shouldRevert() {
        // TODO Auto-generated method stub
        return false;
    }

}