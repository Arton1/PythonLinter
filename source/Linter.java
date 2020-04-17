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
        System.out.println(stream.readCharacter());
        System.out.println(stream.getCurrentLinePosition());
        System.out.println(stream.getCurrentColumnPosition());
        //Lexer lexer = new Lexer(stream);
        //Token token = lexer.getToken();
    }
}