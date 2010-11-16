package org.mpain.counting.filter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CountingResponseWrapper extends HttpServletResponseWrapper {

    private final long startTime;
    private final CountingServletOutputStream output;
    private final PrintWriter writer;

    public CountingResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        startTime = System.nanoTime();
        output = new CountingServletOutputStream(response.getOutputStream());
        
        OutputStreamWriter streamWriter = new OutputStreamWriter(output, "UTF-8");
        writer = new PrintWriter(streamWriter);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return output;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        writer.flush();
    }

    public long getElapsedTime() {
        return System.nanoTime() - startTime;
    }

    public long getByteCount() throws IOException {
        flushBuffer();
        return output.getByteCount();
    }

}
