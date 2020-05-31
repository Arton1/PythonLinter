package linter.type_analysis;

public class Variable extends LanguageObject {
    Type type = null;
    Class possessingClass;
    Class classObject = null; 

    public Variable(String identifier, Class possessingClass, int line, int column){
        super(identifier, line, column);
        this.possessingClass = possessingClass;
    }

    public Type getType(){
        return type;
    }

    public void setType(Type type){
        this.type = type;
    }

    public boolean compareIdentifier(Variable variable){
        if(!getIdentifier().equals(variable.getIdentifier()))
            return false;
        return true;
    }

    public Class getPossessingClass(){
        return possessingClass;
    }
}