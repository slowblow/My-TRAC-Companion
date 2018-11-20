package Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Schema {
    String type;
    String name;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    List<Field> fields;

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
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


    public List<Field> getFields() {
        return fields;
    }

    public String getName() {
        return name;
    }

@JsonIgnoreProperties(ignoreUnknown=true)
public static class Field{
    String name;

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    String type;

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
}
