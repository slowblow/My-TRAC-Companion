package MySQLHandlers;

public class SQLQueryBuilder {

    public static String createTable(String name, String[] header)
    {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS "+name+"(");
        sb.append("id serial NOT NULL PRIMARY KEY,\n");
        sb.append("modified timestamp default CURRENT_TIMESTAMP NOT NULL");

        for (String field : header)
        {
            sb.append(",\n"+field +" varchar(500)");
        }
        sb.append(")");
    return sb.toString();
    }

    public static String insert(String tableName,String[] header, String[] record)
    {
        StringBuilder sb = new StringBuilder("INSERT INTO "+tableName+" (");
        boolean first=true;
        for(String field : header)
        {
            if(!first) sb.append(", ");
            sb.append(field);
            first=false;
        }
        sb.append(") VALUES (");
        first=true;
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
