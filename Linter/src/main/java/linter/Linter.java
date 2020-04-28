package linter;

import java.io.FileNotFoundException;

import linter.exception.BadTokenException;
import linter.token.*;

class Linter {
    public static void main(String args[]){
        StreamHandler stream = null;
        if (args.length == 0)
            stream = new StreamHandler();
        else if(args.length == 1) 
            try {
                stream = new StreamHandler(args[0]);
            } 
            catch (FileNotFoundException e) {
                ErrorHandler.handleFileNotFoundException(e);
            }
        else{
            System.out.println("Usage: java linter.Linter.class [path to source file]");
            return;
        }
        Lexer lexer = new Lexer(stream);
        Token token;
        try{
            while((token = lexer.getToken()) != null){ //until EOF
                System.out.print(token.getTokenType());
                System.out.print(" " + token.getLine() + " ");
                System.out.print(token.getColumn() + " ");
                if(token instanceof IdentifierToken)
                    System.out.print(((IdentifierToken)token).getIdentifier());
                System.out.println();
            }
        }
        catch(BadTokenException e){
            ErrorHandler.handleBadTokenException(e);
        }
    }
}