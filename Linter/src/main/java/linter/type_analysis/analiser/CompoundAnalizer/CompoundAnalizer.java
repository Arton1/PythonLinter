package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.List;

import linter.type_analysis.NameSpace;
import linter.type_analysis.Type;
import linter.type_analysis.analiser.ObjectCreatorAnalizer;

public abstract class CompoundAnalizer extends ObjectCreatorAnalizer {

    protected List<NameSpace> retiredNameSpaces;
    Type functionReturnType;

    protected CompoundAnalizer(List<NameSpace> nameSpaces, List<NameSpace> retiredNameSpaces, NameSpace currentContextNameSpace, Type functionReturnType){
        super(nameSpaces, currentContextNameSpace);
        this.retiredNameSpaces = retiredNameSpaces;
        this.functionReturnType = functionReturnType;
    }

    protected void addNewNameSpace(){
        NameSpace newNameSpace = new NameSpace();
        nameSpaceStack.add(newNameSpace);
        currentContextNameSpace = newNameSpace;
    }

    protected void removeCurrentNameSpace(){
        retiredNameSpaces.add(nameSpaceStack.remove(nameSpaceStack.size()-1));
    }
}