package linter.type_analysis;


public abstract class LanguageObject {
    private String identifier;
    private int numberOfReferences = 0;
    private int line;
    private int column;

    protected LanguageObject(String identifier, int line, int column){
        this.identifier = identifier;
        this.line = line;
        this.column = column;
    }

    public String getIdentifier(){
        return identifier;
    }

     public int getNumberOfReferences(){
        return numberOfReferences;
    }

    public void incrementNumberOfReferences(){
        numberOfReferences++;
    }

    public int getLine(){
        return line;
    }

    public int getColumn(){
        return column;
    }

    public void decrementNumberOfReferences(){
        numberOfReferences--;
    }
}