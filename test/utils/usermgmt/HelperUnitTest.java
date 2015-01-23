package utils.usermgmt;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import commons.AbstractUnitTest;


public class HelperUnitTest extends AbstractUnitTest {

    @Test
    public void readTextLogsErrorIfNoFile() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        try {
            System.setOut(ps);
            Helper.readText("incorrectFileName.html");
            System.out.flush();
            assertTrue(baos.toString().contains(Helper.EXCEPTION_MESSAGE));
        } finally {
        	System.setOut(old);
        }
    }
}