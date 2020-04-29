package linter.production;

import linter.token.Token;

public enum SingleInputProduction implements Production {
    first{
        public Production expand(Token token){
            return null;
        }
    }


}