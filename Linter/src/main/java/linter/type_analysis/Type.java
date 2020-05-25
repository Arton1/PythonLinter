package linter.type_analysis;

import java.util.List;

public enum Type implements TableElement<Type> {
    INT("int"), FLOAT("float"), STR("str"), BYTES("bytes"), BOOL("bool"), CLASS_OBJECT(null);
    
    private String typeLabel;
    
    private Type(String type){
        this.typeLabel = type;
    }

    public static Type fromString(String typeToCreate){
        for(Type type : values()){
            if(type != CLASS_OBJECT)
                if(type.typeLabel.equals(typeToCreate))
                    return type;
        }
        Type type = CLASS_OBJECT;
        type.typeLabel = typeToCreate;
        return type;
    }

    public String getLabel(){
        return typeLabel;
    }

    @Override
    public Type getElement(List<String> identifier) throws RuntimeException {
        if(identifier.size() == 0)
            return this;
        throw new RuntimeException("Bad identifier"); //Programmer side error
    }

    @Override
    public void addElement(List<String> subList, Type element) {
        throw new RuntimeException("Cannot add variable to variable"); //Programmer side error
    }
}