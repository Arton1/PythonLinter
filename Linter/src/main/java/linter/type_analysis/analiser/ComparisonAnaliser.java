package linter.type_analysis.analiser;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.ComparisonProduction;
import linter.syntax_tree.production.test_productions.ExpressionProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;

public class ComparisonAnaliser extends TypeAnaliser {

    boolean comparisonAppeared = false;

    protected ComparisonAnaliser(List<Table<Variable>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(ComparisonProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(ExpressionProduction.class))
                processExpressionProduction(child);
        }
        if(comparisonAppeared)
            type = Type.BOOL;
        return true;
    }

    private void processExpressionProduction(Node node){
        ExpressionAnaliser analiser = new ExpressionAnaliser(variableTables, functionTables);
        node.accept(analiser);
        if(type == null && variable == null){
            if(analiser.getVariable() != null)
                variable = analiser.getVariable();
            else if(analiser.getType() != null)
                type = analiser.getType();
            else
                throw new RuntimeException("Nothing received from analyzer");
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
        if(type != typeToCompare || !type.getLabel().equals(typeToCompare.getLabel())){
            if(typeToCompare == Type.FLOAT || typeToCompare == Type.INT || typeToCompare == Type.BOOL){
                if(!(type == Type.FLOAT || type == Type.INT || type == Type.BOOL))
                    throw new RuntimeException("Impossible to compare");
            }
            else
                throw new RuntimeException("Impossible to compare");
        }
        comparisonAppeared = true;
    }
}
