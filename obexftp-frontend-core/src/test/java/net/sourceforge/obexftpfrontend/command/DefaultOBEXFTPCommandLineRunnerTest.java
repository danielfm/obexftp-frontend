package net.sourceforge.obexftpfrontend.command;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import net.sourceforge.obexftpfrontend.obexftp.OBEXFTPException;
import net.sourceforge.obexftpfrontend.obexftp.OBEXFTPNotFoundException;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * DefaultOBEXFTPCommandLineRunner test cases.
 * @author Daniel F. Martins
 */
public class DefaultOBEXFTPCommandLineRunnerTest {

    private DefaultOBEXFTPCommandLineRunner runner;
    private Mockery context = new Mockery() {

        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    @Before
    public void setUp() {
        runner = new DefaultOBEXFTPCommandLineRunner();
    }

    @Test
    public void testRunWithoutWorkingFolder() throws Exception {
        List<String> params = new LinkedList<String>();
        params.add("java");
        params.add("-version");

        Process p = runner.run(null, params, 5);
        assertNotNull(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRunWithInvalidNonExistentFolder() throws Exception {
        List<String> params = new LinkedList<String>();
        params.add("java");
        params.add("-version");

        runner.run(new File("something.strange.here"), params, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRunWithInvalidFolder() throws Exception {
        List<String> params = new LinkedList<String>();
        params.add("java");
        params.add("-version");

        runner.run(File.createTempFile("some.file", ".temp"), params, 5);
    }

    @Test(expected = OBEXFTPNotFoundException.class)
    public void testRunInvalidApp() throws Exception {
        List<String> params = new LinkedList<String>();
        params.add("123zx5c4vasdfa3sdf4e8");

        runner.run(null, params, 2);
    }

    @Test(expected = OBEXFTPException.class)
    public void testRunWithInvalidAppArguments() throws Exception {
        List<String> params = new LinkedList<String>();
        params.add("java");
        params.add("-2134kjf");

        runner.run(null, params, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRunWithNullArguments() throws Exception {
        runner.run(new File("nonsense.here"), null, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRunWithEmptyArguments() throws Exception {
        runner.run(new File("nonsense.here"), new LinkedList<String>(), 5);
    }

    @Test
    public void testRunWithoutTimeout() throws Exception {
        List<String> params = new LinkedList<String>();
        params.add("java");
        params.add("-version");

        Process p = runner.run(new File("."), params, -1);
        assertNotNull(p);
    }

    @Test(expected = OBEXFTPException.class)
    public void testRunWithTimeout() throws Exception {
        List<String> params = new LinkedList<String>();
        params.add("groovy");
        params.add("-e");
        params.add("Thread.sleep 10000");

        runner.run(null, params, 2);
    }

    @Test
    public void testDestroyProcess() {
        final Process process = context.mock(Process.class);

        context.checking(new Expectations() {

            {
                one(process).destroy();
            }
        });

        new DefaultOBEXFTPCommandLineRunner().destroyProcess(process);
        context.assertIsSatisfied();
    }
}