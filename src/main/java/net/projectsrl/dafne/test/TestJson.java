
package net.projectsrl.dafne.test;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;


public class TestJson {

    public static void main(String[] args) throws IOException {

        Employee emp = JacksonObjectMapperExample.createEmployee();
        
        OutputStream baos=new ByteArrayOutputStream();

        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(baos);
        // for pretty printing
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());

        jsonGenerator.writeStartObject(); // start root object
        jsonGenerator.writeNumberField("id", emp.getId());
        jsonGenerator.writeStringField("name", emp.getName());
        jsonGenerator.writeBooleanField("permanent", emp.isPermanent());

        jsonGenerator.writeObjectFieldStart("address"); // start address object
        jsonGenerator.writeStringField("street", emp.getAddress().getStreet());
        jsonGenerator.writeStringField("city", emp.getAddress().getCity());
        jsonGenerator.writeNumberField("zipcode", emp.getAddress().getZipcode());
        jsonGenerator.writeEndObject(); // end address object

        jsonGenerator.writeArrayFieldStart("phoneNumbers");
        for (long num : emp.getPhoneNumbers())
            jsonGenerator.writeNumber(num);
        jsonGenerator.writeEndArray();

        jsonGenerator.writeStringField("role", emp.getRole());

        jsonGenerator.writeArrayFieldStart("cities"); // start cities array
        for (String city : emp.getCities())
            jsonGenerator.writeString(city);
        jsonGenerator.writeEndArray(); // closing cities array

        jsonGenerator.writeObjectFieldStart("properties");
        Set<String> keySet = emp.getProperties().keySet();
        for (String key : keySet) {
            String value = emp.getProperties().get(key);
            jsonGenerator.writeStringField(key, value);
        }
        jsonGenerator.writeEndObject(); // closing properties
        jsonGenerator.writeEndObject(); // closing root object

        jsonGenerator.flush();
        jsonGenerator.close();
        
        System.out.println(baos.toString());
    }

}