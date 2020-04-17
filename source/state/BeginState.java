package state;

import token.type.*;

public enum BeginState implements State {
    firstState {
        @Override
        public State processCharacter(char c){
            token = CompareTokenType.LESS;
            return this;
        }
    };

    TokenType token;

    public TokenType getTokenType(){
        return token;
    }

}