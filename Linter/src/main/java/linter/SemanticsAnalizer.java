package linter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import linter.exception.BadSyntaxException;
import linter.exception.IndentationException;
import linter.identifier_tree.IdentifierTree;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.SyntaxTree;
import linter.syntax_tree.TokenNode;
import linter.syntax_tree.production.SimpleStatementProduction;
import linter.syntax_tree.production.compound_productions.CompoundStatementProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Variable;
import linter.type_analysis.analiser.SimpleStatementAnalizer;
import linter.type_analysis.analiser.CompoundAnalizer.CompoundStatementAnalizer;

public class SemanticsAnalizer {
    Parser parser;

    List<Table<Variable>> variableTables = new LinkedList<Table<Variable>>(); //Stack
    List<Table<Function>> functionTables = new LinkedList<Table<Function>>(); //Stack

    List<Table<Variable>> retiredVariableTables = new LinkedList<Table<Variable>>();
    List<Table<Function>> retiredFunctionTables = new LinkedList<Table<Function>>();

    private int currentIndentLevel;

    public boolean enterChild = true;

    SemanticsAnalizer(Parser parser){
        this.parser = parser;
        variableTables.add(new Table<Variable>()); //Global variable table
        functionTables.add(new Table<Function>()); //Global function table
    }
    
    SyntaxTree getNextIdentifierTree(){
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
            return null;
        // syntaxTree.printNodesInformations();
        // System.out.println("!!!!!!");
        IdentifierTree identifierTree = new IdentifierTree();
        Node currentNode;
        while((currentNode = syntaxTree.getNextNode(enterChild)) != null){
            enterChild = true;
            currentIndentLevel = syntaxTree.getCurrentLevel();
            currentNode.accept(this);
        }
        // if(retiredVariableTables.size() > 0){
        //     Table<Variable> vTable = retiredVariableTables.get(0);
        //     List<String> ident = new ArrayList<String>();
        //     ident.add("x");
        //     Variable variable = vTable.getElement(ident);
        //     System.out.println("x: " + variable.getNumberOfReferences());
        // }
        // if(retiredVariableTables.size() > 0){
            // Table<Function> fTable = functionTables.get(0);
            // List<String> ident = new ArrayList<String>();
            // ident.add("fun");
            // Function function = fTable.getElement(ident);
            // List<Type> argumentTypes = function.getArguments();
            // for(Type argument: argumentTypes)
            //     System.out.println(argument);
        // }
        enterChild = true;
        return syntaxTree;
    }

    public void visit(ProductionNode node){
        if(node.isType(SimpleStatementProduction.class)){
            node.accept(new SimpleStatementAnalizer(variableTables, functionTables));
            enterChild = false;
        }
        else if(node.isType(CompoundStatementProduction.class)){
            node.accept(new CompoundStatementAnalizer(variableTables, functionTables, retiredVariableTables, retiredFunctionTables));
            enterChild = false;
        }
    }

    public void visit(TokenNode node) {
        
    }
}