package net.projectsrl.mail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.DataSource;

public class ByteArrayDataSource implements DataSource {

    private byte[] data; // Il contenuto del file in byte
    private String type; // Il tipo MIME (es. "application/pdf")
    private String name; // Il nome del file

    public ByteArrayDataSource(byte[] data, String type, String name) {
        this.data = data;
        this.type = type;
        this.name = name;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (data == null) {
            throw new IOException("Data is null.");
        }
        return new ByteArrayInputStream(data);
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new IOException("Non è possibile scrivere su un ByteArrayDataSource.");
    }

    @Override
    public String getContentType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }
}