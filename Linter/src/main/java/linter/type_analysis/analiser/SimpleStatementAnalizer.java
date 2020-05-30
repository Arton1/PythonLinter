package linter.type_analysis.analiser;

import java.util.List;

import linter.exception.SemanticsException;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;
import linter.syntax_tree.production.OptionalAssignmentStatementProduction;
import linter.syntax_tree.production.SimpleStatementProduction;
import linter.syntax_tree.production.test_productions.TestProduction;

public class SimpleStatementAnalizer extends TypeAnaliser {

    public SimpleStatementAnalizer(List<Table<Variable>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(SimpleStatementProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(TestProduction.class))
                procesTestProduction(child);
            else if(child.isType(OptionalAssignmentStatementProduction.class))
                processAssignmentProduction(child);
        }
        return true;
    }

    private void procesTestProduction(Node node){
        TestAnaliser analiser = new TestAnaliser(variableTables, functionTables);
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

    private void processAssignmentProduction(Node node){
        OptionalAssignmentAnaliser analiser = new OptionalAssignmentAnaliser(variableTables, functionTables);
        node.accept(analiser);
        if(variable == null)
            throw new SemanticsException("No variable to assign to", node.getParent().getSubtreeFirstToken());
        Type type = analiser.getType();
        if(analiser.shouldCheckVariableType()){ 
            if(variable.getType() == null)
                throw new SemanticsException("Uninitialized variable", node.getParent().getSubtreeFirstToken());
            if(variable.getType() != Type.UNSPECIFIED &&
                variable.getType() != Type.FLOAT && 
                variable.getType() != Type.INT && 
                variable.getType() != Type.STR && 
                variable.getType() != Type.BOOL )
                throw new SemanticsException("Bad variable type to assign to, " + variable.getType(), node.getParent().getSubtreeFirstToken());
            if(variable.getType() != analiser.getType()){
                if(variable.getType() == Type.UNSPECIFIED || getType() == Type.FLOAT || variable.getType() == Type.INT || variable.getType() == Type.BOOL){
                    if(type == Type.UNSPECIFIED || type == Type.FLOAT || type == Type.INT || type == Type.BOOL){
                        if(variable.getType() == Type.UNSPECIFIED || type == Type.UNSPECIFIED)
                            variable.setType(Type.UNSPECIFIED);
                        else if(variable.getType() == Type.FLOAT || type == Type.FLOAT)
                            variable.setType(Type.FLOAT);
                        else 
                            variable.setType(Type.INT);
                        saveVariable(variable);
                        return;
                    }
                }
                throw new SemanticsException("Incompatible variable types", node.getParent().getSubtreeFirstToken());
            }
        }
        else {
            variable.setType(type);
            saveVariable(variable);
        }
    }
}