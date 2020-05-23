package linter;

import java.util.List;

import linter.identifier_tree.IdentifierTree;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.SyntaxTree;
import linter.syntax_tree.TokenNode;
import linter.syntax_tree.production.AnnualAssignmentStatementProduction;
import linter.syntax_tree.production.Production;
import linter.syntax_tree.production.SimpleStatementProduction;
import linter.token.Token;

public class SemanticsAnalizer {
    Parser parser;

    SemanticsAnalizer(Parser parser){
        this.parser = parser;
    }
    
    IdentifierTree getNextIdentifierTree(){
        SyntaxTree syntaxTree = parser.getNextSyntaxTree();
        IdentifierTree identifierTree = new IdentifierTree();
        Node currentNode;
        while((currentNode = syntaxTree.getNextNode()) != null)
            currentNode.accept(this);
        return null;
    }

    public void visit(ProductionNode node){
        Production production = node.getProduction();
        if(production instanceof SimpleStatementProduction)
            checkForbiddenAssignment(node);
    }

    public void visit(TokenNode node){

    }

    private void checkForbiddenAssignment(ProductionNode node){

    }
}