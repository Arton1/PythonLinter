package linter.syntax_tree.production.import_productions;

import java.util.Arrays;
import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.SimpleStatementTokenType;

public class RecursiveCommaModulesProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek) {
        if(token.getTokenType() == SimpleStatementTokenType.COMMA)
            return Arrays.asList(token, new ModuleProduction(), new OptionalAsNameProduction(), this);
        else
            return Arrays.asList();
    }

}
