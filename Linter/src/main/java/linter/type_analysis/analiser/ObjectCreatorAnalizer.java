package linter.type_analysis.analiser;

import java.util.List;

import linter.type_analysis.NameSpace;
import linter.type_analysis.Variable;
import linter.type_analysis.Class;
import linter.type_analysis.Function;

public abstract class ObjectCreatorAnalizer extends TypeAnaliser {

    protected NameSpace currentContextNameSpace;

    protected ObjectCreatorAnalizer(List<NameSpace> nameSpaceStack, NameSpace currentContextNameSpace) {
        super(nameSpaceStack);
        this.currentContextNameSpace = currentContextNameSpace;
    }

    protected void saveVariable(Variable variable){
        currentContextNameSpace.addVariable(variable);
    }

    protected void saveFunction(Function function){
        currentContextNameSpace.addFunction(function);
    }

    protected void saveClass(Class _class){
        currentContextNameSpace.addClass(_class);
    }
}