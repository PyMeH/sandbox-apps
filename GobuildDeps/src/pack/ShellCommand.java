package pack;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ShellCommand {

	   public static final Charset ENCODING = StandardCharsets.UTF_8;
	   public static final String FILE_SEP = File.separator;

	   /**
	    * @param cmd
	    *           What you want executed
	    * @param stdout
	    *           You'll get the stdout here.
	    * @param stderr
	    *           You'll get the stderr here.
	    * @return exit code
	    * @throws IOException
	    */

	   public static int execute(String[] cmd, StringBuilder stdout, StringBuilder stderr) throws IOException {

	      ByteArrayOutputStream out = new ByteArrayOutputStream();
	      ByteArrayOutputStream err = new ByteArrayOutputStream();

	      int result = execute(new File("."), cmd, out, err, null);

	      stdout.append(new String(out.toByteArray(), ENCODING));
	      stderr.append(new String(err.toByteArray(), ENCODING));

	      return result;
	   }

	   /**
	    * Executes a command, capturing its stdout and stderr streams.
	    *
	    * @param cmd
	    *           What you want executed
	    * @param stdout
	    *           You'll get the stdout here.
	    * @param stderr
	    *           You'll get the stderr here.
	    * @return exit code
	    * @throws IOException
	    */
	   public static int execute(File curDir, String[] cmd, OutputStream stdout, OutputStream stderr, String[] environment)
	         throws IOException {
	      Integer exitCode = null;
	      Runtime r = Runtime.getRuntime();
	      Process p = r.exec(cmd, environment, curDir);
	      InputStream in = null;
	      InputStream er = null;
	      try {
	         in = p.getInputStream();
	         er = p.getErrorStream();
	         boolean finished = false; // Set to true when p is finished
	         while (!finished) {
	            try {
	               copyStreams(stdout, stderr, in, er);
	               // Ask the process for its exitValue. If the process
	               // is not finished, an IllegalThreadStateException
	               // is thrown. If it is finished, we fall through and
	               // the variable finished is set to true.
	               exitCode = p.exitValue();
	               //copy the remainder
	               copyStreams(stdout, stderr, in, er);
	               finished = true;
	            } catch (IllegalThreadStateException e) {
	               // Sleep a little to save on CPU cycles
	               try {
	                  Thread.sleep(300);
	               } catch (InterruptedException e1) {
	                  // AOK
	               }
	            }
	         }
	      } finally {
	         if (null != in) {
	            in.close();
	         }
	         if (null != er) {
	            er.close();
	         }
	      }
	      return exitCode;
	   }

	   private static void copyStreams(OutputStream stdout, OutputStream stderr, InputStream in, InputStream er)
	         throws IOException {
	      boolean copied;
	      //please do not optimize unless you REALLY know what you are doing
	      do {
	         copied = false;
	         while (in.available() > 0) {
	            int read = in.read();
	            stdout.write(read);
	            System.out.print((char) read);
	            copied=true;
	         }
	         while (er.available() > 0) {
	            int read = er.read();
	            stderr.write(read);
	            System.err.print((char) read);
	            copied=true;
	         }
	      } while (copied);
	   }
	}