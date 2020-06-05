package linter;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

import linter.exception.LocalizableException;

public class LinterTest {

    static final String relativePathToTestFiles = System.getProperty("user.dir") + "\\src\\test\\java\\linter\\functionalTestFiles\\";

    private UsagePrinter createUsagePrinter(String inputFileName) {
        Lexer lexer = null;
        try {
            lexer = new Lexer(new StreamHandler(relativePathToTestFiles + inputFileName));
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + inputFileName);
            System.exit(1);
        }
        Parser parser = new Parser(lexer);
        SemanticsAnalizer analizer = new SemanticsAnalizer(parser);
        return new UsagePrinter(analizer);
    }

    private ByteArrayOutputStream setOutputStreamToByteArray(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        return outputStream;
    }

    private String getOutputStreamText(ByteArrayOutputStream outputStream){
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out))); //set output stream to stdout
        String outputText = outputStream.toString(Charset.defaultCharset());
        return outputText.replaceAll("\\r", ""); //remove carriage return
    }

    @Test
    public void printUsage_FunctionalTest1_ReceivedExpectedOutput(){
        String inputFileName = "functionalTest1.py";
        String expectedOutputText = "Unused variable : x : 4;11\n"
                                  + "Unused variable : y : 7;1\n"     
                                  + "Unused function : __init__ : 11;6\n"
                                  + "Unused variable : self : 11;15\n"   
                                  + "Unused variable : xIsFive : 12;3\n"
                                  + "Unused function : funkcja : 14;6\n"
                                  + "Unused variable : self : 14;14\n"
                                  + "Unused variable : z : 26;1\n";
        UsagePrinter usagePrinter = createUsagePrinter(inputFileName);
        ByteArrayOutputStream outputStream = setOutputStreamToByteArray();
        usagePrinter.printUsage();
        String outputText = getOutputStreamText(outputStream);
        assertEquals(expectedOutputText, outputText);
    }
    
    @Test
    public void printUsage_FunctionalTest2_ReceivedExpectedOutput(){
        String inputFileName = "functionalTest2.py";
        String expectedOutputText = "Error : SemanticsAnalizer : Cannot operate on incompatible types. LIST, FLOAT : 9;11\n";
        UsagePrinter usagePrinter = createUsagePrinter(inputFileName);
        ByteArrayOutputStream outputStream = setOutputStreamToByteArray();
        try{ usagePrinter.printUsage(); } catch(LocalizableException e) {};
        String outputText = getOutputStreamText(outputStream);
        assertEquals(expectedOutputText, outputText);
    }

    @Test
    public void printUsage_FunctionalTest3_ReceivedExpectedOutput(){
        String inputFileName = "functionalTest3.py";
        String expectedOutputText = "Error : Parser : Bad syntax : 3;10\n";
        UsagePrinter usagePrinter = createUsagePrinter(inputFileName);
        ByteArrayOutputStream outputStream = setOutputStreamToByteArray();
        try{ usagePrinter.printUsage(); } catch(LocalizableException e) {};
        String outputText = getOutputStreamText(outputStream);
        assertEquals(expectedOutputText, outputText);
    }

    @Test
    public void printUsage_FunctionalTest4_ReceivedExpectedOutput(){
        String inputFileName = "functionalTest4.py";
        String expectedOutputText = "Unused function : fun : 2;5\n"
                                  + "Unused variable : x : 6;3\n";
        UsagePrinter usagePrinter = createUsagePrinter(inputFileName);
        ByteArrayOutputStream outputStream = setOutputStreamToByteArray();
        try{ usagePrinter.printUsage(); } catch(LocalizableException e) {};
        String outputText = getOutputStreamText(outputStream);
        assertEquals(expectedOutputText, outputText);
    }

    
    @Test
    public void printUsage_FunctionalTest5_ReceivedExpectedOutput(){
        String inputFileName = "functionalTest5.py";
        String expectedOutputText = "Error : SemanticsAnalizer : Incompatible variable types : 2;2\n";
        UsagePrinter usagePrinter = createUsagePrinter(inputFileName);
        ByteArrayOutputStream outputStream = setOutputStreamToByteArray();
        try{ usagePrinter.printUsage(); } catch(LocalizableException e) {};
        String outputText = getOutputStreamText(outputStream);
        assertEquals(expectedOutputText, outputText);
    }

    @Test
    public void printUsage_FunctionalTest6_ReceivedExpectedOutput(){
        String inputFileName = "functionalTest6.py";
        String expectedOutputText = "Unused variable : x : 3;1\n";
        UsagePrinter usagePrinter = createUsagePrinter(inputFileName);
        ByteArrayOutputStream outputStream = setOutputStreamToByteArray();
        try{ usagePrinter.printUsage(); } catch(LocalizableException e) {};
        String outputText = getOutputStreamText(outputStream);
        assertEquals(expectedOutputText, outputText);
    }
    
    @Test
    public void printUsage_FunctionalTest7_ReceivedExpectedOutput(){
        String inputFileName = "functionalTest7.py";
        String expectedOutputText = "Error : Lexer : Bad token : 1;6\n";
        UsagePrinter usagePrinter = createUsagePrinter(inputFileName);
        ByteArrayOutputStream outputStream = setOutputStreamToByteArray();
        try{ usagePrinter.printUsage(); } catch(LocalizableException e) {};
        String outputText = getOutputStreamText(outputStream);
        assertEquals(expectedOutputText, outputText);
    }

    @Test
    public void printUsage_FunctionalTest8_ReceivedExpectedOutput(){
        String inputFileName = "functionalTest8.py";
        String expectedOutputText = "Unused function : _fun : 3;6\n";
        UsagePrinter usagePrinter = createUsagePrinter(inputFileName);
        ByteArrayOutputStream outputStream = setOutputStreamToByteArray();
        try{ usagePrinter.printUsage(); } catch(LocalizableException e) {};
        String outputText = getOutputStreamText(outputStream);
        assertEquals(expectedOutputText, outputText);
    }
}