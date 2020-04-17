package state;

import token.Token;

public interface StateSet {
    public StateSet processCharacter(char c);
    public Token getToken();
}