import token.*;

class Linter {
    public static void main(String args[]){
        StreamHandler stream;
        if (args.length == 0)
            stream = new StreamHandler();
        else if(args.length == 1) 
            stream = new StreamHandler(args[0]);
        else{
            System.out.println("Usage: java Linter.class [path to source file]");
            return;
        }
        Lexer lexer = new Lexer(stream);
        Token token;
        while(!lexer.isEOF()){
            token = lexer.getToken();
            System.out.print(token.getTokenType());
            System.out.print(" " + token.getLine() + " ");
            System.out.print(token.getColumn() + " ");
            if(token instanceof IdentifierToken)
                System.out.print(((IdentifierToken)token).getIdentifier());
        }
    }
}