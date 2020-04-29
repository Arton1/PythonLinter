package linter.production;

import linter.token.Token;

public interface Production {
    Production expand(Token token);
}