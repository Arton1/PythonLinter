package linter.type_analysis.analiser;

import java.util.List;

import linter.exception.SemanticsException;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.AtomicExpressionProduction;
import linter.syntax_tree.production.test_productions.FactorProduction;
import linter.syntax_tree.production.test_productions.PowerProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;

public class PowerAnaliser extends TypeAnaliser {

    protected PowerAnaliser(List<Table<Variable>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(PowerProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(AtomicExpressionProduction.class))
                processAtomicExpressionProduction(child);
            else
                if(child.isType(FactorProduction.class))
                    processFactorProduction(child);
        }
        return true;
    }

    public void processAtomicExpressionProduction(Node node){
        AtomicExpressionAnaliser analiser = new AtomicExpressionAnaliser(variableTables, functionTables);
        node.accept(analiser);
        if(analiser.getType() != null){
            type = analiser.getType();
            return;
        }
        if(analiser.getVariable() != null){
            variable = analiser.getVariable();
            return;
        }
        throw new RuntimeException("Nothing received from analyzer");
    }

    public void processFactorProduction(Node node){
        FactorAnaliser analiser = new FactorAnaliser(variableTables, functionTables);
        node.accept(analiser);
         if(variable != null){
            if(variable.getType() == null)
                throw new SemanticsException("Uninitialized variable", node.getSubtreeFirstToken());;
            type = variable.getType();
        }
        Type typeToCompare = null;
        if(analiser.getVariable() != null){
            typeToCompare = analiser.getVariable().getType();
            if(typeToCompare == null)
                throw new SemanticsException("Uninitialized variable", node.getSubtreeFirstToken());
        }
        else if(analiser.getType() != null)
            typeToCompare = analiser.getType();
        else
            throw new RuntimeException("Nothing received from analyzer"); //Programmer side error
        if(type != Type.UNSPECIFIED && typeToCompare != Type.UNSPECIFIED && type != Type.FLOAT && type != Type.INT && typeToCompare != Type.FLOAT && typeToCompare != Type.INT)
            throw new SemanticsException("Cannot operate on incompatible types. " + type + ", " + typeToCompare, node.getParent().getSubtreeFirstToken());;
        if(typeToCompare == Type.INT && type == Type.INT)
            type = Type.INT;
        else
            type = Type.FLOAT;
            
    }

}
