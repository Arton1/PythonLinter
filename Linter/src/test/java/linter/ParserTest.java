package linter;

import org.junit.jupiter.api.Test;

import linter.exception.BadSyntaxException;
import linter.syntax_tree.SyntaxTree;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    private Parser createParser(String input){
        StreamSimulator.simulateInput(input);
        Lexer lexer = new Lexer(new StreamHandler());
        return new Parser(lexer);
    }

    @Test
    public void getNextSyntaxTree_Empty_ExpectedSingleNode(){
        String input = StreamSimulator.EOL;
        Parser parser = createParser(input);
        SyntaxTree tree = parser.getNextSyntaxTree();
        assertNull(tree); //EOF, so no tree
    }

    @Test
    public void getNextSyntaxTree_Pass_ExpectedTwoNodes(){
        String input = "pass";
        Parser parser = createParser(input);
        SyntaxTree tree = parser.getNextSyntaxTree();
        assertEquals(tree.size(), 4); //All EOLs get ignored at the beginning, so tree is empty
    }

    @Test
    public void getNextSyntaxTree_SingleVariable_NoThrows(){
        String input = "x";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
    public void getNextSyntaxTree_FunctionCallWithoutArguments_NoThrows(){
        String input = "fun()";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
    }
	
	@Test
    public void getNextSyntaxTree_FunctionWithVariables_NoThrows(){
        String input = "fun(x, y, z)";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}
	
	@Test
    public void getNextSyntaxTree_FunctionWithArguments_NoThrows(){
        String input = "fun(1, hello, [1, 2, 3])";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

    @Test
    public void getNextSyntaxTree_AssignmentWithoutValue_ExpecteBadSyntaxException(){
        String input = "x = ";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}
	
	@Test
    public void getNextSyntaxTree_AssignmentWithValue_NoThrows(){
        String input = "x = 1";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
    }

	@Test
    public void getNextSyntaxTree_List_NoThrows(){
        String input = "[1, True, False, None]";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}
	
	@Test
    public void getNextSyntaxTree_Tuple_NoThrows(){
        String input = "(1, True, False, None)";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
    }

	@Test
    public void getNextSyntaxTree_AssignList_NoThrows(){
        String input = "x = [1, 2, True, x]";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}
	
	@Test
    public void getNextSyntaxTree_AssignTuple_NoThrows(){
        String input = "x = (1, True, x)";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
    }

    @Test
    public void getNextSyntaxTree_Def_ExpecteBadSyntaxException(){
        String input = "def ";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
    }

    @Test
    public void getNextSyntaxTree_FunctionWithoutTwoDotsAndBrackets_ExpecteBadSyntaxException(){
        String input = "def hello";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
    }

    @Test
    public void getNextSyntaxTree_FunctionWithoutBody_ExpecteBadSyntaxException(){
        String input = "def hello():";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}
	
	@Test
	public void getNextSyntaxTree_FunctionWithoutBrackets_ExpecteBadSyntaxException(){
        String input = "def hello: \n	pass";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
    }

    @Test
    public void getNextSyntaxTree_UnindentedFunction_ExpecteBadSyntaxException(){
        String input = "def hello(): \n pass";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
    }

    @Test
    public void getNextSyntaxTree_CorrectFunction_NoThrows(){
		String input = "def hello(): \n\t pass";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}
	
	@Test
    public void getNextSyntaxTree_Return_NoThrows(){
        String input = "return";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
    public void getNextSyntaxTree_ReturnWithValue_NoThrows(){
        String input = "return 1";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
    }

	@Test
    public void getNextSyntaxTree_Break_NoThrows(){
        String input = "break";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
    public void getNextSyntaxTree_Continue_NoThrows(){
        String input = "continue";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
    public void getNextSyntaxTree_Import_ExpecteBadSyntaxException(){
        String input = "import";
        Parser parser = createParser(input);
		assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}

	@Test
    public void getNextSyntaxTree_ImportPackage_NoThrows(){
        String input = "import pack";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
    public void getNextSyntaxTree_ImportPackageWithDot_ExpecteBadSyntaxException(){
        String input = "import pack.";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}

	@Test
    public void getNextSyntaxTree_ImportPackageInPackage_NoThrows(){
        String input = "import pack.age.hello";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
    public void getNextSyntaxTree_From_ExpecteBadSyntaxException(){
        String input = "from";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_FromWithoutPackageName_ExpecteBadSyntaxException(){
        String input = "from import package";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_FromWithoutImportKeyword_ExpecteBadSyntaxException(){
        String input = "from package";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_CorrectFromImport_NoThrows(){
        String input = "from package import pack";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_OrWithoutSecondValue_ExpecteBadSyntaxException(){
        String input = "x or";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_OrWithTwoValues_NoThrows(){
        String input = "x or y";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_DoubleOrWithThreeValues_NoThrows(){
        String input = "x or y or z";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_AndWithoutSecondValue_ExpecteBadSyntaxException(){
        String input = "x and";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_AndWithTwoValues_NoThrows(){
        String input = "x and y";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_DoubleAndWithThreeValues_NoThrows(){
        String input = "x and y and z";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_AndWithOrWithoutMiddleValue_ExpectedBadSyntaxException(){
        String input = "x or and z";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_CorrectAndWithOr_NoThrows(){
        String input = "x or y and z";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_CorrectNot_NoThrows(){
        String input = "not x";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_NotWithoutValue_ExpectedBadSyntaxException(){
        String input = "not";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_AndOrNot_NoThrows(){
        String input = "not x or not y and z";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_SimpleComparison_NoThrows(){
        String input = "x == y";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_ComparisonAndOrNot_NoThrows(){
        String input = "not x or x == y and z < t";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_NotIn_NoThrows(){
        String input = "x not in k";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_In_NoThrows(){
        String input = "x in k";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_Is_NoThrows(){
        String input = "x is k";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_IsNot_NoThrows(){
        String input = "x is not k";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_ComparisonStatement_NoThrows(){
        String input = "not x or x == y and z < t is not k in z and l not in s";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_SingleIsMissing_ExpectedBadSyntaxException(){
        String input = "not x or x == y and z < t   not k in z and l not in s";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_ExpressionWithoutOneArgument_ExpectedBadSyntaxException(){
        String input = "x +";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_Expression_NoThrows(){
        String input = "x - y + z - k";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_Term_NoThrows(){
        String input = "x / y * z // k";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_ExpressionWithMultiply_NoThrows(){
        String input = "x+2/z*5-3";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_ExpressionWithMultiplyOneArgumentMissing_ExpectedBadSyntaxException(){
        String input = "x+2 z*3";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_Power_NoThrows(){
        String input = "x**3**y";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_ExpressionWithPower_NoThrows(){
        String input = "x+2/z**f*5-3**x";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_IfStatement_NoThrows(){
        String input = "if x*2==3+2: \n\t pass \n elif False: \n\t pass \n else: \n\t pass";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_IfStatementWithoutSingleIndent_ExpectedBadSyntaxException(){
        String input = "if True: \n\t pass \n elif False: \n pass \n else: \n\t pass";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_ForStatement_NoThrows(){
        String input = "for x in y: \n\t pass";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_ForStatementWithoutIterator_ExpectedBadSyntaxException(){
        String input = "for in y: \n\t pass";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_ForStatementWithoutList_ExpectedBadSyntaxException(){
        String input = "for x in : \n\t pass";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_WhileStatement_NoThrows(){
        String input = "while x: \n\t pass \n else: \n\t pass";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_ClassStatement_NoThrows(){
		String input = "class ClassName(BaseClass, AnotherBaseClass):\n\t x = 2 \n\t def func(): \n\t\t pass";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_TypeHinting_NoThrows(){
		String input = "def funkcja(x: int, y: float) -> bool: \n\t pass";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_TypeHintingMissingType_ThrowsBadSyntaxException(){
		String input = "def funkcja(x:): \n\t pass";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_TypeHintingMissingReturnType_ThrowsBadSyntaxException(){
		String input = "def funkcja() ->: \n\t pass";
        Parser parser = createParser(input);
        assertThrows(BadSyntaxException.class, () -> parser.getNextSyntaxTree());
	}

	@Test
	public void getNextSyntaxTree_MixedTest_NoThrows(){
		String input = "from abc import ABC\n"
                    	+"import math\n"

                        +"def print(x):\n"
                        +"	pass\n"

                        +"y = 3\n"

                        +"class Klasa:\n"

						+"	def __init__(self):\n"
						+"		xIsFive = 5\n"

						+"	def funkcja(self, x: int) -> bool:\n"
						+"		if x == 1:\n"
						+"			print(x)\n"
						+"		elif x == 2:\n"
						+"			x += 2\n"
						+"		else:\n"
						+"			return x == 1\n"
						+"		return True\n"

						+"	def wywolanie0123(self) -> float:\n"
						+"		funkcja(1, 2)";
        Parser parser = createParser(input);
        assertDoesNotThrow(() -> parser.getNextSyntaxTree());
	}


	
}