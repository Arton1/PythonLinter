package linter.production;

import java.util.List;

import linter.token.Token;

public abstract class Production {
    List<Production> productions;

    public abstract boolean expand(Token token);

    public List<Production> getProductions(){
        return productions;
    }
}