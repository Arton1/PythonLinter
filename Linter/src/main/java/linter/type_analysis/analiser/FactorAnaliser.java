package linter.type_analysis.analiser;

import java.util.List;

import linter.exception.SemanticsException;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.FactorProduction;
import linter.syntax_tree.production.test_productions.PowerProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;

public class FactorAnaliser extends TypeAnaliser {

    protected FactorAnaliser(List<Table<Variable>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
	public boolean visit(ProductionNode node){
        if(!node.isType(FactorProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(PowerProduction.class))
                processPowerProduction(child);
            else
                if(child.isType(FactorProduction.class))
                    processFactorProduction(child);
        }
        return true;
    }

    private void processPowerProduction(Node node){
        PowerAnaliser analiser = new PowerAnaliser(variableTables, functionTables);
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

    private void processFactorProduction(Node node){
        node.accept(this);
        if(variable != null){
            if(variable.getType() == null)
                throw new SemanticsException("Uninitialized variable", node.getParent().getSubtreeFirstToken());
            type = variable.getType();
        }
        if(type != Type.UNSPECIFIED && type != Type.FLOAT && type != Type.INT)
            throw new SemanticsException("Bad type for unary operation, " + type, node.getParent().getSubtreeFirstToken());
    }
}
