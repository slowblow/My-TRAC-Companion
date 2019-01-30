package MySQLHandlers;

import Objects.Schema;

public class SQLQueryBuilder {


    public static String createTable(Schema schema) {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS "+schema.getName()+"(");

        boolean first = true;


        for(Schema.Field field : schema.getFields())
        {
            if (!first) sb.append(",\n");

            String sql_sentence = buildFieldDescriptor(field);
            sb.append(sql_sentence);
            first=false;
        }
        sb.append(")");



        return sb.toString();
    }

    private static String buildFieldDescriptor(Schema.Field field) {
        String field_name = field.getName();
        if(field_name.equals("id")) return "id serial NOT NULL PRIMARY KEY";
        String type = field.getType();

        String mysqlType = convertToMysqlType(type);

        String query =  field_name+" "+mysqlType;

        return query;
    }

    private static String convertToMysqlType(String type) {
        switch (type.toLowerCase()) {
            case "int":
                return "INTEGER";
            case "float":
                return "FLOAT";
            case "long":
                return "BIGINT";
            case "double":
                return "DOUBLE";
            case "boolean":
                return "BIT(1)";
            default:
                System.out.println("[ERROR] It doesn't exist a mapping type for "+type);
        }
        return null;
    }


    public static String insert(String tableName,String header, String[] record)
    {
        StringBuilder sb = new StringBuilder("INSERT INTO "+tableName+header);
        sb.append("VALUES (");
        boolean first=true;
        for(String value : record)
        {
            if(!first) sb.append(", ");
            sb.append("'"+value+"'");
            first=false;
        }
        sb.append(")");
        return sb.toString();
    }


}
