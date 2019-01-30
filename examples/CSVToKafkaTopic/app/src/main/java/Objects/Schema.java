package Objects;

//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown=true)
public class Schema {
    String type;
    String name;
//    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    List<Field> fields;

    public static Schema getSchema(org.apache.avro.Schema parse) {

        Schema schema = new Schema();
        schema.name=parse.getName();
        schema.type=parse.getType().getName();

        schema.fields=new ArrayList<>();

        for(org.apache.avro.Schema.Field f: parse.getFields())
        {
            String name = f.name();
            System.out.println(name);
            org.apache.avro.Schema avro_schema = f.schema();
            String type="";
            if(avro_schema.getType()==org.apache.avro.Schema.Type.UNION) {
                for (org.apache.avro.Schema subschema : avro_schema.getTypes()) {
                    type = subschema.getName();
                    if (!type.equals("null")) {
                        System.out.println("    " + type);
                    }
                }
            }
            else
            {
                type= avro_schema.getType().getName();
            }
            Field field = new Field(name, type);
            schema.fields.add(field);
        }


        return schema;
    }

/*    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setType(String type)
    {
        this.type = type;
    }
*/

    public List<Field> getFields() {
        return fields;
    }

    public String getName() {
        return name;
    }

//@JsonIgnoreProperties(ignoreUnknown=true)
public static class Field{

    String name;
    String type;

    public Field(String name, String type) {
        this.name = name;
        this.type = type;
    }


    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

/*
    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    */

}
}
