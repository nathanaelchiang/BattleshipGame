//package java.cs3500.pa03;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.PrintStream;
//import org.junit.jupiter.api.Test;
//
//class DriverTest {
//
//  @Test
//  public void fakeTest() {
//    String input = "Name\n6\n6\n1\n1\n1\n1\n1";
//    String input2 = "1\n1\n1\n1\n1";
//
//    // Redirect the standard output to capture console output
//    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//    PrintStream originalOut = System.out;
//    System.setOut(new PrintStream(outputStream));
//
//    // Simulate user input for the first part
//    InputStream input1 = new ByteArrayInputStream(input.getBytes());
//    System.setIn(input1);
//    Driver.main(new String[0]);
//
//    // Capture the console output
//    String consoleOutput1 = outputStream.toString();
//
//    // Reset the output stream and redirect standard output again
//    outputStream.reset();
//    System.setOut(new PrintStream(outputStream));
//
//    // Simulate user input for the second part
//    InputStream input2Stream = new ByteArrayInputStream(input2.getBytes());
//    System.setIn(input2Stream);
//    Driver.main(new String[0]);
//
//    // Capture the console output
//    String consoleOutput2 = outputStream.toString();
//
//    // Restore the standard output
//    System.setOut(originalOut);
//
//    // Perform assertions on the console output
//    // ...
//
//    // Example assertion: Check if the output contains a specific string in the first part
//    assertTrue(consoleOutput1.contains("Hello! Welcome to the OOD BattleSalvo Game!"));
//
//    // Example assertion: Check if the output contains a specific string in the second part
//    assertTrue(consoleOutput2.contains("Please enter your fleet in the order"));
//
//    // Example assertion: Check the number of lines in the output in the first part
//    String[] lines1 = consoleOutput1.split(System.lineSeparator());
//    assertEquals(10, lines1.length); // Adjust the expected line count accordingly
//
//    // Example assertion: Check the number of lines in the output in the second part
//    String[] lines2 = consoleOutput2.split(System.lineSeparator());
//    assertEquals(7, lines2.length); // Adjust the expected line count accordingly
//  }
//
//
//}