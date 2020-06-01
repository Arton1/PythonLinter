package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.ArrayList;
import java.util.List;

import linter.exception.SemanticsException;
import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.TokenNode;
import linter.syntax_tree.production.compound_productions.ClassArgumentsProduction;
import linter.token.IdentifierToken;
import linter.type_analysis.NameSpace;
import linter.type_analysis.analiser.TypeAnaliser;
import linter.type_analysis.Class;

public class ClassArgumentsAnalizer extends TypeAnaliser {
    private List<Class> baseClasses = new ArrayList<Class>();

    protected ClassArgumentsAnalizer(List<NameSpace> nameSpaceStack) {
        super(nameSpaceStack);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if (!node.isType(ClassArgumentsProduction.class))
            return true;
        int position = 0;
        Node child;
        while ((child = node.getChildAtPosition(position++)) != null) {
            if (child instanceof TokenNode)
                child.accept(this);
        }
        return true;
    }

    @Override
    public void visit(TokenNode node){
        if(node.getToken() instanceof IdentifierToken){
            String string = ((IdentifierToken)node.getToken()).getIdentifier();
            Class baseClass = findClass(string);
            if(baseClass == null)
                throw new SemanticsException("Base class doesn't exist", node.getSubtreeFirstToken());
            baseClasses.add(baseClass);
        }
    }

    public List<Class> getBaseClasses(){
        return baseClasses;
    }
    
}