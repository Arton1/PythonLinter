package linter;

import java.util.LinkedList;
import java.util.List;

import linter.identifier_tree.IdentifierTree;
import linter.semantics_analysis.AssignmentSemanticsChecker;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.SyntaxTree;
import linter.syntax_tree.TokenNode;
import linter.syntax_tree.production.SimpleStatementProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;

public class SemanticsAnalizer {
    Parser parser;

    List<Table<Type>> variableTables;
    List<Table<Function>> functionTables;

    int currentIndentLevel;

    SemanticsAnalizer(Parser parser){
        this.parser = parser;
        variableTables = new LinkedList<Table<Type>>();
        variableTables.add(new Table<Type>()); //Global variable table
        functionTables = new LinkedList<Table<Function>>();
        functionTables.add(new Table<Function>()); //Global variable table
    }
    
    IdentifierTree getNextIdentifierTree(){
        SyntaxTree syntaxTree = parser.getNextSyntaxTree();
        IdentifierTree identifierTree = new IdentifierTree();
        Node currentNode;
        while((currentNode = syntaxTree.getNextNode()) != null){
            currentIndentLevel = syntaxTree.getCurrentLevel();
            currentNode.accept(this);
        }
        return null;
    }

    public void visit(ProductionNode node){
        checkSubtreeAcceptability(node);
        checkSubtreeTyping(node);
    }

    public void visit(TokenNode node) {

    }

    public void checkSubtreeAcceptability(ProductionNode node){
        node.accept(new AssignmentSemanticsChecker());
        //Call more checkers if needed
    }

    private void checkSubtreeTyping(ProductionNode node) {
        checkSubtreeAssignment(node);
    }

    private void checkSubtreeAssignment(ProductionNode node){
        if(node.getProduction() instanceof SimpleStatementProduction == false)
            return;
    }
}