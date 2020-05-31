package linter;

import java.util.ArrayList;
import java.util.List;

import linter.exception.SemanticsException;
import linter.type_analysis.Function;
import linter.type_analysis.LanguageObject;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Variable;

public class UsagePrinter {
    private SemanticsAnalizer semanticsAnalizer;

    public UsagePrinter(SemanticsAnalizer semanticsAnalizer){
        this.semanticsAnalizer = semanticsAnalizer;
    }

    public void printUsage(){
        try{
            semanticsAnalizer.processInput();
        }
        catch(SemanticsException e){
            ErrorHandler.handleSemanticsException(e);
        }
        List<LanguageObject> unusedElementsList = new ArrayList<LanguageObject>();
        NameSpace globalNameSpace = semanticsAnalizer.getGlobalNameSpace();
        addUnusedElements(unusedElementsList, globalNameSpace);
        List<NameSpace> localNameSpaces = semanticsAnalizer.getLocalNameSpaces();
        for(NameSpace localNameSpace : localNameSpaces)
            addUnusedElements(unusedElementsList, localNameSpace);
        sortUnusedElementsList(unusedElementsList);
        for(LanguageObject unusedElement : unusedElementsList)
            printUnusedElementInformations(unusedElement); 
    }

    private void sortUnusedElementsList(List<LanguageObject> unusedElementsList){
        unusedElementsList.sort((element1, element2) -> {
            int diff = element1.getLine() - element2.getLine();
            if(diff == 0)
                return element1.getColumn() - element2.getColumn();
            else
                return diff;
        });
    }

    private void addUnusedElements(List<LanguageObject> list, NameSpace nameSpace){
        list.addAll(nameSpace.getUnusedVariables());
        list.addAll(nameSpace.getUnusedFunctions());
        list.addAll(nameSpace.getUnusedClasses());
    }

    private void printUnusedElementInformations(LanguageObject object){
        String identifier;
        if(object instanceof Variable)
            identifier = "variable";
        else if(object instanceof Function)
            identifier = "function";
        else
            identifier = "class";
        System.out.println("Unused " + identifier + " : " + object.getIdentifier() + " : " + object.getLine() + ";" + object.getColumn());
    }
}