package linter.syntax_tree;

import java.util.LinkedList;
import java.util.List;

import linter.exception.BadSyntaxException;
import linter.syntax_tree.production.Production;
import linter.syntax_tree.production.SingleInputProduction;
import linter.syntax_tree.production.compound_productions.SuiteProduction;
import linter.token.Token;
import linter.visitor.NodeCreatorVisitor;
import linter.visitor.Visitor;

public class ProductionNode extends Node {
    List<Node> nodes = new LinkedList<Node>();
    int currentPosition = -1; //doesnt point to anything
    Production production = new SingleInputProduction();

    public ProductionNode(ProductionNode parent, Production production) {
        super(parent);
        this.production = production;
    }

    public ProductionNode(ProductionNode parent){
        super(parent);
    }

    @Override
    public boolean processToken(Token token, Token peek, int currentIndentLevel) throws BadSyntaxException {
        List<TreeElement> children;
        children = production.expand(token, peek, currentIndentLevel);
        addNodes(children);
        return false; //didnt consume token
    }

    private void addNodes(List<TreeElement> children) throws BadSyntaxException{
        if(children == null)
            throw new BadSyntaxException(); // couldn't match token
        Visitor visitor = new NodeCreatorVisitor(this);
        for (TreeElement child : children)
            child.accept(visitor); //creates nodes and adds to list using addNodes
    }

    @Override
    public void processTokenWhenBacking(Token token, Token peek, int currentIndentLevel) throws BadSyntaxException {
        List<TreeElement> children = production.expandOptional(token, peek, currentIndentLevel);
        if(children != null)
            addNodes(children);
    }

    /**
     * Removes node that's currently being worked on
     */
    public void removeProcessedNode() {
        nodes.remove(currentPosition);
        currentPosition--;
    }

    public void exchange(Node removed, Node added) {
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
    public Node getNextChildNode() {
        if(hasNextChildNode())
            return nodes.get(++currentPosition);
        return null;
    }

    public boolean hasNextChildNode(){
        return currentPosition+1 < nodes.size();
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

    @Override
    public boolean isEpsilon() {
        return nodes.size() == 0;
    }

    @Override
    public void reset() {
        currentPosition = -1;
        for(Node node : nodes)
            node.reset();
    }

    @Override
    public Token getToken() {
        return null;
    }

    @Override
    public boolean isSuiteProduction(){
        if(production instanceof SuiteProduction)
            return true;
        return false;
    }

    @Override
    public int getLevel(){
        return production.getLevel();
    }
}