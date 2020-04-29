package linter.syntax_tree;

import java.util.List;
import java.util.ListIterator;

import linter.token.Token;

abstract class Node {
    List<Node> nodes;
    ListIterator<Node> nodesIterator;
    Node parent;

    abstract boolean processToken(Token token); //returns true when processed token successfully
    abstract boolean changeState(); //returns true when changed state succesfully

    protected Node(Node parent){
        this.parent = parent;
    }

	boolean shouldRevert() {
		return false;
	}

	Node getParent() {
		return parent;
	}

	Node getNextNode() {
        if(nodesIterator.hasNext())
            return nodesIterator.next();
        else
            return null;
	}
	public int getSubtreeSize() {
        int size = 0;
        for(Node node : nodes)
            size += node.getSubtreeSize();
        return size+1;
	}
}