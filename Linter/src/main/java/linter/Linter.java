package linter;

import java.io.FileNotFoundException;

class Linter {
    public static void main(String args[]){
        StreamHandler stream = null;
        if (args.length == 0)
            stream = new StreamHandler();
        else 
            if(args.length == 1) 
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
        Parser parser = new Parser(lexer);
        SemanticsAnalizer semanticsAnalizer = new SemanticsAnalizer(parser);
        UsagePrinter usagePrinter = new UsagePrinter(semanticsAnalizer);
        usagePrinter.printUsage();
    }
}