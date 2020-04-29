package linter.syntax_tree;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import linter.exception.BadSyntaxException;
import linter.syntax_tree.production.Production;
import linter.syntax_tree.production.SingleInputProduction;
import linter.token.Token;
import linter.visitor.NodeCreatorVisitor;
import linter.visitor.Visitor;

public class ProductionNode extends Node {
    List<Node> nodes;
    ListIterator<Node> nodesIterator;
    Production production;

    public ProductionNode(ProductionNode parent, Production production) {
        super(parent);
        this.production = production;
    }

    public ProductionNode(ProductionNode parent){
        super(parent);
        production = new SingleInputProduction();
    }

    @Override
    public boolean processToken(Token token) throws BadSyntaxException {
        List<TreeElement> children;
        children = production.expand(token);
        if(children == null)
            throw new BadSyntaxException(); // couldn't match token
        nodes = new ArrayList<Node>();
        Visitor visitor = new NodeCreatorVisitor(this);
        for (TreeElement child : children){
            child.accept(visitor); //create node and add to list
        }
        nodesIterator = nodes.listIterator();
        return true; //continue processing
    }

    public void exchange(Node removed, Node added){
        int removedIndex = nodes.indexOf(removed);
        if(removedIndex != -1)
            nodes.set(removedIndex, added);
        else
            throw new RuntimeException("Parent doesnt contain node to remove");
    }

    public void addNode(Node node){
        nodes.add(node);
    }

    @Override
    public Node getNextNode() {
        if(nodesIterator.hasNext())
            return nodesIterator.next();
        return null;
    }

    @Override
    public int getSubtreeSize() {
        int size = 0;
        if(nodes != null)
            for (Node node : nodes)
                size += node.getSubtreeSize();
        return size+1;
    }

    @Override
    public String getInformation() {
        return "Production node: " + production.toString();
    }

    @Override
    public void printInformations() {
        System.out.println(getInformation());
        for (Node node: nodes){
            node.printInformations();
        }
    }

}