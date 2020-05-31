package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.production.compound_productions.ClassStatementProduction;
import linter.syntax_tree.production.compound_productions.CompoundStatementProduction;
import linter.syntax_tree.production.compound_productions.ForStatementProduction;
import linter.syntax_tree.production.compound_productions.FunctionStatementProduction;
import linter.syntax_tree.production.compound_productions.IfStatementProduction;
import linter.syntax_tree.production.compound_productions.WhileStatementProduction;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Type;

public class CompoundStatementAnalizer extends CompoundAnalizer {

    public CompoundStatementAnalizer(List<NameSpace> nameSpaceStack, List<NameSpace> retiredNameSpaces, NameSpace currentContextNameSpace, Type functionReturnType) {
        super(nameSpaceStack, retiredNameSpaces, currentContextNameSpace, functionReturnType);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(CompoundStatementProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(IfStatementProduction.class))
                processIfStatementProduction(child);
            else if(child.isType(WhileStatementProduction.class))
                processWhileStatementProduction(child);
            else if(child.isType(ForStatementProduction.class))
                processForStatementProduction(child);
            else if(child.isType(ClassStatementProduction.class))
                 processClassStatementProduction(child);
            else if(child.isType(FunctionStatementProduction.class))
                processFunctionStatementProduction(child);
        }
        return true;
    }
    
    private void processIfStatementProduction(Node node){
        IfStatementAnalizer analizer = new IfStatementAnalizer(nameSpaceStack, retiredNameSpaces, currentContextNameSpace, functionReturnType);
        node.accept(analizer);
    }

    private void processWhileStatementProduction(Node node){
        WhileStatementAnalizer analizer = new WhileStatementAnalizer(nameSpaceStack, retiredNameSpaces, currentContextNameSpace, functionReturnType);
        node.accept(analizer);
    }

    private void processForStatementProduction(Node node){
        ForStatementAnalizer analizer = new ForStatementAnalizer(nameSpaceStack, retiredNameSpaces, currentContextNameSpace, functionReturnType);
        node.accept(analizer);
    }

    private void processClassStatementProduction(Node node){
        ClassStatementAnalizer analizer = new ClassStatementAnalizer(nameSpaceStack, retiredNameSpaces, currentContextNameSpace, functionReturnType);
        node.accept(analizer);
    }

    private void processFunctionStatementProduction(Node node){
        FunctionStatementAnalizer analizer = new FunctionStatementAnalizer(nameSpaceStack, retiredNameSpaces, currentContextNameSpace);
        node.accept(analizer);
    }


}