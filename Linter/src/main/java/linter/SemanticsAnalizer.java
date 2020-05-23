package linter;

import java.util.List;

import linter.identifier_tree.IdentifierTree;
import linter.syntax_tree.SyntaxTree;
import linter.token.Token;

public class SemanticsAnalizer {
    Parser parser;

    SemanticsAnalizer(Parser parser){
        this.parser = parser;
    }
    
    IdentifierTree getNextIdentifierTree(){
        SyntaxTree syntaxTree = parser.getNextSyntaxTree();
        while(true){
            int level = syntaxTree.getCurrentIndentLevel();
            List<Token> instruction = syntaxTree.getNextInstruction();
            if(instruction == null)
                break;
            checkValidity(instruction);
        }
        return null;
    }

    private void checkValidity(List<Token> instruction){

    }

}