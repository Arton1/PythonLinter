package linter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import linter.identifier_tree.IdentifierTree;
import linter.semantics_analysis.AssignmentSemanticsChecker;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.SyntaxTree;
import linter.syntax_tree.TokenNode;
import linter.syntax_tree.production.SimpleStatementProduction;
import linter.syntax_tree.production.test_productions.TestProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Variable;
import linter.type_analysis.analiser.SimpleStatementAnalizer;
import linter.type_analysis.analiser.TestAnaliser;

public class SemanticsAnalizer {
    Parser parser;

    List<Table<Variable>> variableTables;
    List<Table<Function>> functionTables;

    int currentIndentLevel;

    boolean enterChild = true;

    SemanticsAnalizer(Parser parser){
        this.parser = parser;
        variableTables = new LinkedList<Table<Variable>>();
        variableTables.add(new Table<Variable>()); //Global variable table
        functionTables = new LinkedList<Table<Function>>();
        functionTables.add(new Table<Function>()); //Global function table
    }
    
    IdentifierTree getNextIdentifierTree(){
        SyntaxTree syntaxTree = parser.getNextSyntaxTree();
        if(syntaxTree == null)
            return null;
        IdentifierTree identifierTree = new IdentifierTree();
        Node currentNode;
        while((currentNode = syntaxTree.getNextNode(enterChild)) != null){
            enterChild = true;
            currentIndentLevel = syntaxTree.getCurrentLevel();
            currentNode.accept(this);
        }
        // Table<Variable> vTable = variableTables.get(0);
        // List<String> ident = new ArrayList<String>();
        // ident.add("x");
        // Variable variable = vTable.getElement(ident);
        // System.out.println("x: " + variable.getNumberOfReferences());
        // ident = new ArrayList<String>();
        // ident.add("y");
        // variable = vTable.getElement(ident);
        // if(variable != null)
        //     System.out.println("y: " + variable.getNumberOfReferences());
        return identifierTree;
    }

    public void visit(ProductionNode node){
        if(node.isType(SimpleStatementProduction.class)){
            node.accept(new SimpleStatementAnalizer(variableTables, functionTables));
            enterChild = false;
        }
    }

    public void visit(TokenNode node) {
        
    }
}