package linter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import linter.exception.SemanticsException;
import linter.type_analysis.NameSpace;
import linter.type_analysis.Type;
import linter.type_analysis.Variable;

public class SemanticsAnalizerTest {
    public static final String EOL = StreamSimulator.EOL;

    private SemanticsAnalizer createSemanticsAnalizer(String input) {
        StreamSimulator.simulateInput(input);
        Lexer lexer = new Lexer(new StreamHandler());
        Parser parser = new Parser(lexer);
        return new SemanticsAnalizer(parser);
    }

    @Test
    public void processInput_Assignment_NoThrows(){
        String input = "x = 1";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertDoesNotThrow(() -> analizer.processInput());
    }
    
    @Test
    public void getNextSyntaxTree_AssignmentToNumber_ExpectedSemanticsException(){
        String input = "123 = 1";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertThrows(SemanticsException.class, () -> analizer.processInput());
    }
    
    @Test
    public void getNextSyntaxTree_AssignmentVariableSavedInGlobalNameSpace_NotNull(){
        String input = "x = 1";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        analizer.processInput();
        NameSpace space = analizer.getGlobalNameSpace();
        List<String> identifier = new ArrayList<String>();
        identifier.add("x");
        Variable variable = space.getVariable(identifier);
        assertNotNull(variable);
    }
    
    @Test
    public void getNextSyntaxTree_AssignmentVariableSavedInGlobalNameSpace_TypeInt(){
        String input = "x = 1";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        analizer.processInput();
        NameSpace space = analizer.getGlobalNameSpace();
        List<String> identifier = new ArrayList<String>();
        identifier.add("x");
        Variable variable = space.getVariable(identifier);
        assertEquals(Type.INT, variable.getType());
    }
    
    @Test
    public void getNextSyntaxTree_AugmentedAssignment_ExpectedSemanticsException(){
        String input = "x += 2";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertThrows(SemanticsException.class, () -> analizer.processInput());
    }

    @Test
    public void getNextSyntaxTree_FunctionWithSingleArgument_ArgumentSavedInLocalSpace(){
        String input = "def fun(x):\n\t pass";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        analizer.processInput();
        List<NameSpace> localNameSpaces = analizer.getLocalNameSpaces();
        List<String> identifier = new ArrayList<String>();
        identifier.add("x");
        Variable variable = localNameSpaces.get(0).getVariable(identifier);
        assertNotNull(variable);
    }

    @Test
    public void getNextSyntaxTree_FunctionWithReturn_ExpectedSemanticsException(){
        String input = "def fun() -> bool:\n\t return 100";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertThrows(SemanticsException.class, () -> analizer.processInput());
    }

    @Test
    public void getNextSyntaxTree_FunctionWithReturnWithoutReturnHint_ExpectedSemanticsException(){
        String input = "def fun():\n\t return 100";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertThrows(SemanticsException.class, () -> analizer.processInput());
    }

    @Test
    public void getNextSyntaxTree_ReturnInGlobalScope_ExpectedSemanticsException(){
        String input = "return";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertThrows(SemanticsException.class, () -> analizer.processInput());
    }

    @Test
    public void getNextSyntaxTree_ReturnInGlobalScopeIf_ExpectedSemanticsException(){
        String input = "if True: \n\t return";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertThrows(SemanticsException.class, () -> analizer.processInput());
    }

    @Test
    public void getNextSyntaxTree_FunctionWithIntReturnFloatReturnHintInt_ExpectedSemanticsException(){
        String input = "def fun() -> int:\n\t return 100.0";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertDoesNotThrow(() -> analizer.processInput());
    }

    @Test
    public void getNextSyntaxTree_FunctionWithIntReturnIntReturnHintFloat_ExpectedSemanticsException(){
        String input = "def fun() -> float:\n\t return 100";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertDoesNotThrow(() -> analizer.processInput());
    }
    
    @Test
    public void getNextSyntaxTree_ForStatement_IteratorSavedInLocalSpace(){
        String input = "y = [1,2,3]\nfor x in y:\n\t pass";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        analizer.processInput();
        List<NameSpace> localNameSpaces = analizer.getLocalNameSpaces();
        List<String> identifier = new ArrayList<String>();
        identifier.add("x");
        Variable variable = localNameSpaces.get(0).getVariable(identifier);
        assertNotNull(variable);
    }
    
    @Test
    public void getNextSyntaxTree_ForStatementWithIteratedInt_ExpectedSemanticsException(){
        String input = "y = 1\nfor x in y:\n\t pass";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertThrows(SemanticsException.class, () -> analizer.processInput());
    }
    
    @Test
    public void getNextSyntaxTree_ForStatementWithTuple_NoThrows(){
        String input = "y = (1, 2, True)\nfor x in y:\n\t pass";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertDoesNotThrow(() -> analizer.processInput());
    }
    
    @Test
    public void getNextSyntaxTree_ComparisonWithIncompatibleTypes_ExpectedSemanticsException(){
        String input = "1 == None";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertThrows(SemanticsException.class, () -> analizer.processInput());
    }
    
    @Test
    public void getNextSyntaxTree_FunctionCallWithoutFunctionDefined_ExpectedSemanticsException(){
        String input = "fun(1, 2, 3)";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertThrows(SemanticsException.class, () -> analizer.processInput());
    }
    
    @Test
    public void getNextSyntaxTree_FunctionCall_NoThrows(){
        String input = "def fun(x: int):\n\t pass \n fun(1)";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertDoesNotThrow(() -> analizer.processInput());
    }
    
    @Test
    public void getNextSyntaxTree_NotMatchingFunctionCall_ExpectedSemanticsException(){
        String input = "def fun(x: int, y: float):\n\t pass \n fun(1)";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertThrows(SemanticsException.class, () -> analizer.processInput());
    }
    
    @Test
    public void getNextSyntaxTree_NotMatchingFunctionCallTypes_ExpectedSemanticsException(){
        String input = "def fun(x: int, y: float):\n\t pass \n fun(1, None)";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertThrows(SemanticsException.class, () -> analizer.processInput());
    }

    @Test
    public void getNextSyntaxTree_AssignedVariableSavedInClassScope_ExpectedTypeInt(){
        String input = "x = None\n class Klasa:\n\t x = 2";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        analizer.processInput();
        NameSpace space = analizer.getGlobalNameSpace();
        List<String> identifier = new ArrayList<String>();
        identifier.add("Klasa");
        identifier.add("x");
        Variable variable = space.getVariable(identifier);
        assertEquals(Type.INT, variable.getType());
    }

    @Test
    public void getNextSyntaxTree_AssignedVariableSavedInClassScope_ExpectedTypeNoneOfVariableSavedInGlobalScope(){
        String input = "x = None\n class Klasa:\n\t x = 2";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        analizer.processInput();
        NameSpace space = analizer.getGlobalNameSpace();
        List<String> identifier = new ArrayList<String>();
        identifier.add("x");
        Variable variable = space.getVariable(identifier);
        assertEquals(Type.NONE, variable.getType());
    }

    @Test
    public void getNextSyntaxTree_AssignedVariableSavedInFunction_ExpectedTypeNoneOfVariableSavedInGlobalScope(){
        String input = "def fun():\n\t x=2 \n";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        analizer.processInput();
        NameSpace space = analizer.getGlobalNameSpace();
        List<String> identifier = new ArrayList<String>();
        identifier.add("x");
        Variable variable = space.getVariable(identifier);
        assertNull(variable);
    }

    @Test
    public void getNextSyntaxTree_ClassWithNonExistentBaseClass_ExpectedTypeNoneOfVariableSavedInGlobalScope(){
        String input = "class Klasa(Base):\n\t x = 2";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertThrows(SemanticsException.class, () -> analizer.processInput());
    }

    @Test
    public void getNextSyntaxTree_ClassWithoutExpectedClass_ExpectedSemanticsException(){
        String input = "class Klasa:\n\t pass \n Klasa.Klasa2.x = 1";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertThrows(SemanticsException.class, () -> analizer.processInput());
    }

    @Test
    public void getNextSyntaxTree_ClassInsideClass_NoThrow(){
        String input = "class Klasa:\n\t class Klasa2: \n\t\t x=2 \n Klasa.Klasa2.x += 1";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertDoesNotThrow(() -> analizer.processInput());
    }

    @Test
    public void getNextSyntaxTree_ClassInsideClass_ExpectedSemanticsException(){
        String input = "class Klasa:\n\t pass \n Klasa.Klasa2.x += 1";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertThrows(SemanticsException.class, () -> analizer.processInput());
    }

    @Test
    public void getNextSyntaxTree_FunctionInsideIfBlock_ExpectedSemanticsException(){
        String input = "if True: \n\t def func(): \n\t\t pass \n func()";
        SemanticsAnalizer analizer = createSemanticsAnalizer(input);
        assertThrows(SemanticsException.class, () -> analizer.processInput());
    }
}