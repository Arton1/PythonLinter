import token.Token;

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
        Token token = lexer.getToken();
        System.out.println(token.getTokenType());
    }
}