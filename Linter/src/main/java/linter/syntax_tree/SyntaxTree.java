package linter.syntax_tree;

public class SyntaxTree {
    Node root;
    Node currentNode;
    int currentIndentLevel;

    public SyntaxTree(SyntaxTreeBuilder syntaxTreeBuilder){
        this(syntaxTreeBuilder.root);
    }

    protected SyntaxTree(Node root){
        this.root = root;
        this.currentNode = root;
    }

    public int size(){
        if(root == null)
            return 0;
        return root.getSubtreeSize();
    }

    public Node getNextNode(boolean enterChild){
        while(true){ //Pass all productions that were already processed
            Node nextNode = currentNode.getNextChildNode();
            if(nextNode == null || !enterChild){
                enterChild = true;
                currentNode = currentNode.getParent();
                if(currentNode == null)
                    return null;
                else
                    if(currentNode.isSuiteProduction())
                        currentIndentLevel = currentNode.getLevel()-1; //Tree is quitting compound statement
            }
            else {
                if(currentNode.isSuiteProduction())
                    currentIndentLevel = currentNode.getLevel(); //Tree is entering compound statement
                return currentNode = nextNode;
            }
        }
    }

    public void resetTravel(){
        root.reset();
        currentNode = root;
        currentIndentLevel = 0;
    }

    public int getCurrentLevel(){
        return currentIndentLevel;
    }

    public void printNodesInformations(){
        root.printInformations();
    }

}