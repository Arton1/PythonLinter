import state.StateSet;
import state.BeginStateSet;
import token.Token;

public class Lexer {
    private StreamHandler stream;

    private StateSet stateSet;

    Lexer(StreamHandler stream){
        this.stream = stream;
        this.stateSet = new BeginStateSet();
    }

    public Token getToken(){
        Token token;
        char c;
        do {
            c = stream.readCharacter();
            stateSet = stateSet.processCharacter(c);
            if(stateSet == null) //state doesnt exist, bad token
                ErrorHandler.handleBadTokenError();
            token = stateSet.getToken(); //get token if avaliable for collecting
        } while(token == null);
        stateSet = new BeginStateSet(); //reset of state machine
        return token;
    }
}