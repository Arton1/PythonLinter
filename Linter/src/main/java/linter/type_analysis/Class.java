package linter.type_analysis;

import java.util.List;

public class Class extends LanguageObject {
    private NameSpace nameSpace = new NameSpace();
    List<Class> baseClasses;

    public Class(String identifier, List<Class> baseClasses, int line, int column){
        super(identifier, line, column);
        this.baseClasses = baseClasses;
    }
    
    public NameSpace getNameSpace(){
        return nameSpace;
    }
}