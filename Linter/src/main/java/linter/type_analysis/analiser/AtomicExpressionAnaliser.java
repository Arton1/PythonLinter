package linter.type_analysis.analiser;

import java.util.ArrayList;
import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.test_productions.AtomicExpressionProduction;
import linter.syntax_tree.production.test_productions.AtomicProduction;
import linter.syntax_tree.production.test_productions.OptionalTrailerProduction;
import linter.type_analysis.Function;
import linter.type_analysis.Table;
import linter.type_analysis.Type;

public class AtomicExpressionAnaliser extends TypeAnaliser {
    boolean trailerForbidden = false;

    protected AtomicExpressionAnaliser(List<Table<Type>> variableTables, List<Table<Function>> functionTables) {
        super(variableTables, functionTables);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(AtomicExpressionProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(AtomicProduction.class)){
                AtomicAnaliser analiser = new AtomicAnaliser(variableTables, functionTables);
                child.accept(analiser);
                if(analiser.getType() != null){
                    trailerForbidden = true;
                    type = analiser.getType();
                }
                else if(analiser.getIdentifier() != null)
                    addIdentifier(analiser.getIdentifier());
            }
            else
                if(child.isType(OptionalTrailerProduction.class)){
                    if(trailerForbidden)
                        throw new RuntimeException("Trailer forbidden"); //TODO: Should be thrown by error handler
                    TrailerAnaliser analiser = new TrailerAnaliser(variableTables, functionTables);
                    child.accept(analiser);
                    if(analiser.getIdentifier() != null)
                        addIdentifier(analiser.getIdentifier());
                    else
                        if(analiser.hasFunction()){
                            List<Type> argumentNames = analiser.getArguments();
                            //TODO: CHECK FUNCTION CALL AND CLEAR IDENTIFIER LIST, ANALISER RETURNS TYPE
                        }
                }
        }
        //TODO: CHECK IF IDENTIFIER LIST IS NULL IF THERE IS A TYPE
        return true;
    }

}
