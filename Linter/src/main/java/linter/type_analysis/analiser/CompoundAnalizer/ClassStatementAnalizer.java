package linter.type_analysis.analiser.CompoundAnalizer;

import java.util.ArrayList;
import java.util.List;

import linter.syntax_tree.Node;
import linter.syntax_tree.ProductionNode;
import linter.syntax_tree.TokenNode;
import linter.syntax_tree.production.compound_productions.ClassStatementProduction;
import linter.syntax_tree.production.compound_productions.OptionalClassParametersProduction;
import linter.syntax_tree.production.compound_productions.SuiteProduction;
import linter.token.IdentifierToken;
import linter.token.Token;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Type;
import linter.type_analysis.Class;

public class ClassStatementAnalizer extends CompoundAnalizer {

    private String classIdentifier;
    private List<Class> baseClasses = new ArrayList<Class>();
    private Class _class;
    private int line;
    private int column;

    public ClassStatementAnalizer(List<NameSpace> nameSpaces, List<NameSpace> retiredNameSpaces, NameSpace currentContextNameSpace, Type functionReturnType) {
        super(nameSpaces, retiredNameSpaces, currentContextNameSpace, functionReturnType);
    }

    @Override
    public boolean visit(ProductionNode node) {
        if(!node.isType(ClassStatementProduction.class))
            return true;
        int position = 0;
        Node child;
        while((child = node.getChildAtPosition(position++)) != null){
            if(child.isType(OptionalClassParametersProduction.class))
                processClassParameters(child);
            else if (child.isType(SuiteProduction.class))
                processSuiteProduction(child);
            else if (child instanceof TokenNode)
                child.accept(this);
        }
        return true;
    }

     @Override
    public void visit(TokenNode node){
        if(node.getToken() instanceof IdentifierToken){
            classIdentifier = ((IdentifierToken)node.getToken()).getIdentifier();
            Token firstToken = node.getSubtreeFirstToken();
            line = firstToken.getLine();
            column = firstToken.getColumn();
        }
    }

    private void processClassParameters(Node node){
        ClassParametersAnalizer analizer = new ClassParametersAnalizer(nameSpaceStack);
        node.accept(analizer);
        baseClasses = analizer.getBaseClasses();
    }

    private void createClassObject(){
        _class = new Class(classIdentifier, baseClasses, line, column);
        saveClass(_class);
    }

    private void processSuiteProduction(Node node) {
        createClassObject();
        addNewNameSpace();
        SuiteAnalizer analizer = new SuiteAnalizer(nameSpaceStack, retiredNameSpaces, currentContextNameSpace, functionReturnType);
        node.accept(analizer);
        removeCurrentNameSpace();
    }

    @Override
    protected void addNewNameSpace(){
        nameSpaceStack.add(_class.getNameSpace());
        currentContextNameSpace = _class.getNameSpace();
    }

    @Override
    protected void removeCurrentNameSpace(){
        nameSpaceStack.remove(nameSpaceStack.size()-1);
    }    

}