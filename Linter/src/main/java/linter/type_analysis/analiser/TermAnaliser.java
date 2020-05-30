package linter.type_analysis.analiser;

import java.util.List;

import linter.exception.SemanticsException;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.FactorProduction;
import linter.syntax_tree.production.test_productions.TermProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;

public class TermAnaliser extends TypeAnaliser {

    protected TermAnaliser(List<Table<Variable>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(TermProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(FactorProduction.class))
                processFactorProduction(child);
        }
        return true;
    }

    private void processFactorProduction(Node node){
        FactorAnaliser analiser = new FactorAnaliser(variableTables, functionTables);
        node.accept(analiser);
        if(type == null && variable == null){
            if(analiser.getVariable() != null)
                variable = analiser.getVariable();
            else if(analiser.getType() != null)
                type = analiser.getType();
            else
                throw new RuntimeException("Nothing received from analyzer"); //Programmer side error
            return;
        }
        else if(variable != null){
            if(variable.getType() == null)
                throw new SemanticsException("Uninitialized variable", node.getParent().getSubtreeFirstToken());
            type = variable.getType();
            variable = null;
        }
        Type typeToCompare = null;
        if(analiser.getVariable() != null){
            typeToCompare = analiser.getVariable().getType();
            if(typeToCompare == null)
                throw new SemanticsException("Uninitialized variable", node.getParent().getSubtreeFirstToken());
        }
        else if(analiser.getType() != null)
            typeToCompare = analiser.getType();
        else
            throw new RuntimeException("Nothing received from analyzer");
        if(type == Type.CLASS_OBJECT || typeToCompare == Type.CLASS_OBJECT)
            throw new SemanticsException("Impossible to operate on class object. " + type + ", " + typeToCompare, node.getParent().getSubtreeFirstToken());
        if(type != Type.UNSPECIFIED && typeToCompare != Type.UNSPECIFIED && type != typeToCompare)
            throw new SemanticsException("Cannot operate on incompatible types. " + type + ", " + typeToCompare, node.getParent().getSubtreeFirstToken());
    }

}
