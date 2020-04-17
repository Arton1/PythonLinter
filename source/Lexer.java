import state.*;
import token.Token;
import token.type.TokenType;

public class Lexer {
    private StreamHandler stream;

    private State state;

    Lexer(StreamHandler stream){
        this.stream = stream;
        this.state = BeginState.firstState;
    }

    public Token getToken(){
        TokenType tokenType;
        char c;
        int tokenLinePosition = stream.getCurrentLinePosition();
        int tokenColumnPosition = stream.getCurrentColumnPosition();
        do {
            c = stream.readCharacter();
            state = state.processCharacter(c);
            if(state == null) //state doesnt exist, bad token
                ErrorHandler.handleBadTokenError();
            tokenType = state.getTokenType(); //get token if avaliable for collecting
        } while (tokenType == null);
        Token token = new Token(tokenType, tokenLinePosition, tokenColumnPosition);
        state = BeginState.firstState; //reset state machine
        return token;
    }
}