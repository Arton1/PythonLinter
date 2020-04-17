package state;

import token.type.*;

public interface State{
    public State processCharacter(char c);
    public TokenType getTokenType();
}