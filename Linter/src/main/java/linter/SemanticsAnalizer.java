package linter;

import java.util.LinkedList;
import java.util.List;

import linter.exception.BadSyntaxException;
import linter.exception.IndentationException;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.SyntaxTree;
import linter.syntax_tree.TokenNode;
import linter.syntax_tree.production.SimpleStatementProduction;
import linter.syntax_tree.production.compound_productions.CompoundStatementProduction;
import linter.type_analysis.NameSpace;
import linter.type_analysis.analiser.SimpleStatementAnalizer;
import linter.type_analysis.analiser.CompoundAnalizer.CompoundStatementAnalizer;

public class SemanticsAnalizer {
    private Parser parser;

    private List<NameSpace> nameSpaceStack = new LinkedList<NameSpace>();
    private List<NameSpace> retiredNameSpaces = new LinkedList<NameSpace>();

    private boolean enterChild = true;

    SemanticsAnalizer(Parser parser){
        this.parser = parser;
        nameSpaceStack.add(new NameSpace()); //global name space
    }
    
    void processInput(){
        while(true){
            SyntaxTree syntaxTree = null;
            try{
                syntaxTree = parser.getNextSyntaxTree();
            }
            catch(BadSyntaxException e){
                ErrorHandler.handleBadSyntaxException(e);
            }
            catch(IndentationException e){
                ErrorHandler.handleIndentationException(e);
            }
            if(syntaxTree == null)
                break;
            Node currentNode;
            while((currentNode = syntaxTree.getNextNode(enterChild)) != null){
                enterChild = true;
                currentNode.accept(this);
            }
            enterChild = true;
        }
    }

    public void visit(ProductionNode node){
        if(node.isType(SimpleStatementProduction.class)){
            node.accept(new SimpleStatementAnalizer(nameSpaceStack, nameSpaceStack.get(0), null));
            enterChild = false;
        }
        else if(node.isType(CompoundStatementProduction.class)){
            node.accept(new CompoundStatementAnalizer(nameSpaceStack, retiredNameSpaces, nameSpaceStack.get(0), null));
            enterChild = false;
        }
    }

    public void visit(TokenNode node) {
        
    }

    public NameSpace getGlobalNameSpace(){
        return nameSpaceStack.get(0);
    }

    public List<NameSpace> getLocalNameSpaces(){
        return retiredNameSpaces;
    }
}