package linter.syntax_tree.production.import_productions;

import java.util.Arrays;
import java.util.List;

import linter.syntax_tree.TreeElement;
import linter.syntax_tree.production.Production;
import linter.token.Token;
import linter.token.type.IdentifierTokenType;
import linter.token.type.SimpleStatementTokenType;

public class OptionalAsNameProduction extends Production {

    @Override
    public List<TreeElement> expand(Token token, Token peek) {
        if(token.getTokenType() == SimpleStatementTokenType.AS)
            return Arrays.asList(token, IdentifierTokenType.NAME);
        else
            return Arrays.asList(); //Epsilon
    }

}
