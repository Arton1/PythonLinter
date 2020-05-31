package linter.type_analysis.analiser;

import java.util.List;

import linter.exception.SemanticsException;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.ComparisonProduction;
import linter.syntax_tree.production.test_productions.ExpressionProduction;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Type;

public class ComparisonAnaliser extends TypeAnaliser {

    boolean comparisonAppeared = false;

    protected ComparisonAnaliser(List<NameSpace> nameSpaceStack) {
        super(nameSpaceStack);
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
        ExpressionAnaliser analiser = new ExpressionAnaliser(nameSpaceStack);
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
                throw new SemanticsException("Uninitialized variable", node.getParent().getSubtreeFirstToken());
            type = variable.getType();
            variable = null;
        }
        Type typeToCompare = null;
        if(analiser.getVariable() != null){
            typeToCompare = analiser.getVariable().getType();
            if(typeToCompare == null)
                throw new SemanticsException("Uninitialized variable", node.getSubtreeFirstToken());
        }
        else if(analiser.getType() != null)
            typeToCompare = analiser.getType();
        if((type != Type.UNSPECIFIED && typeToCompare != Type.UNSPECIFIED) && (type != typeToCompare || !type.getLabel().equals(typeToCompare.getLabel()))){
            if(typeToCompare == Type.FLOAT || typeToCompare == Type.INT || typeToCompare == Type.BOOL){
                if(!(type == Type.FLOAT || type == Type.INT || type == Type.BOOL))
                    throw new SemanticsException("Impossible to compare, incompatible types. " + type + ", " + typeToCompare, node.getParent().getSubtreeFirstToken());
            }
            else
                throw new SemanticsException("Impossible to compare, incompatible types. " + type + ", " + typeToCompare, node.getParent().getSubtreeFirstToken());
        }
        comparisonAppeared = true;
    }
}
