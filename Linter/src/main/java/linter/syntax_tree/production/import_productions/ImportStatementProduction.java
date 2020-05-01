package linter.syntax_tree.production.import_productions;

import java.util.Arrays;
import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.IdentifierTokenType;
import linter.token.type.SimpleStatementTokenType;

public class ImportStatementProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek) {
        if(token.getTokenType() == SimpleStatementTokenType.IMPORT)
            return Arrays.asList(token, 
                                 new ModuleProduction(), 
                                 new OptionalAsNameProduction(), 
                                 new RecursiveCommaModulesProduction());
        else if(token.getTokenType() == SimpleStatementTokenType.FROM) 
            return Arrays.asList(token, 
                                 new ModuleProduction(), 
                                 SimpleStatementTokenType.RETURN, 
                                 IdentifierTokenType.NAME, 
                                 new OptionalAsNameProduction(), 
                                 new RecursiveCommaNamesProduction());
        return null;
    }
}