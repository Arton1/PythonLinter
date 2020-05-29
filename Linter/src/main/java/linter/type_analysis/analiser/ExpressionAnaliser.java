package linter.type_analysis.analiser;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.ExpressionProduction;
import linter.syntax_tree.production.test_productions.TermProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;

public class ExpressionAnaliser extends TypeAnaliser {

    protected ExpressionAnaliser(List<Table<Variable>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(ExpressionProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(TermProduction.class))
                processTermProduction(child);
        }
        return true;
    }

    private void processTermProduction(Node node){
        TermAnaliser analiser = new TermAnaliser(variableTables, functionTables);
        node.accept(analiser);
        if(type == null && variable == null){
            if(analiser.getVariable() != null)
                variable = getVariable();
            else if(analiser.getType() != null)
                type = getType();
            return;
        }
        else if(variable != null){
            if(variable.getType() == null)
                throw new RuntimeException("No variable type");
            type = variable.getType();
            variable = null;
        }
        Type typeToCompare = null;
        if(analiser.getVariable() != null)
            typeToCompare = analiser.getVariable().getType();
        else if(analiser.getType() != null)
            typeToCompare = analiser.getType();
        if(type == Type.CLASS_OBJECT || typeToCompare == Type.CLASS_OBJECT)
            throw new RuntimeException("Cannot operate on class objects");
        if(type != typeToCompare)
            throw new RuntimeException("Types dont match");
    }

}
