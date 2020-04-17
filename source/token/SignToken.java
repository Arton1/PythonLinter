package token;

public class SignToken implements Token{
    enum SignType {
        PLUS, MINUS, NEGATION
    }

    SignType type;

    SignToken(SignType type){
        this.type = type;
    }
}