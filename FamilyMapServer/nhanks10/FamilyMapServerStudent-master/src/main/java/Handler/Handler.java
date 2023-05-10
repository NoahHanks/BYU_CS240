package Handler;

import java.io.*;

/**
 * The basic handler class which is able to read and write strings when given the proper
 * input or output stream. All the other handlers will inherit these basic functions.
 */
public class Handler {
    /**
     * Reads a String from an InputStream.
     *
     * @param is InputStream when input is read.
     * @return String of what is read.
     * @throws IOException Error with input from the user.
     */
    protected String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) sb.append(buf, 0, len);
        return sb.toString();
    }

    /**
     * Writes a string to an OutputStream
     *
     * @param str The string to be written.
     * @param os  OutputStream where the string is written.
     * @throws IOException Error with input or output.
     */
    protected void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
