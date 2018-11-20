package Objects;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown=true)
public  class Version {

    String subject;
    int version;
    int id;
    String schema;

    Schema schema_object;

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVersion(int version) {
        this.version = version;
    }


   public Schema getSchema() throws IOException {


        if (this.schema_object!=null) return schema_object;

        ObjectMapper mapper = new ObjectMapper();
        this.schema_object=mapper.readValue(this.schema,Schema.class);

        return this.schema_object;
    }
}
