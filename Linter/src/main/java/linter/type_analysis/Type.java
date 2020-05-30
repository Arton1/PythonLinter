package linter.type_analysis;


public enum Type {
    INT("int"), FLOAT("float"), STR("str"), BOOL("bool"), NONE("None"), CLASS_OBJECT(null), LIST("list"), TUPLE("tuple"), UNSPECIFIED(null);
    
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
}