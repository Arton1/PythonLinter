package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.ArrayList;
import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.compound_productions.ClassArgumentsProduction;
import linter.syntax_tree.production.compound_productions.OptionalClassParametersProduction;
import linter.type_analysis.NameSpace;
import linter.type_analysis.analiser.TypeAnaliser;
import linter.type_analysis.Class;

public class ClassParametersAnalizer extends TypeAnaliser {

    protected ClassParametersAnalizer(List<NameSpace> nameSpaceStack) {
        super(nameSpaceStack);
    }

    private List<Class> baseClasses = new ArrayList<Class>();

    @Override
    public boolean visit(ProductionNode node) {
        if (!node.isType(OptionalClassParametersProduction.class))
            return true;
        int position = 0;
        Node child;
        while ((child = node.getChildAtPosition(position++)) != null) {
            if (child.isType(ClassArgumentsProduction.class))
                processFunctionArguments(child);
        }
        return true;
    }

    private void processFunctionArguments(Node child) {
        ClassArgumentsAnalizer analiser = new ClassArgumentsAnalizer(nameSpaceStack);
        child.accept(analiser);
        baseClasses = analiser.getBaseClasses();
    }

    public List<Class> getBaseClasses(){
        return baseClasses;
    }
    
}