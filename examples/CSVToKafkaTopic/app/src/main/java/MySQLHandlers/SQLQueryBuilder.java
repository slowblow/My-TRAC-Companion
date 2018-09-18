package MySQLHandlers;

public class SQLQueryBuilder {

    public static String createTable(String name)
    {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS "+name+"(");
        sb.append("id serial NOT NULL PRIMARY KEY,\n");
        sb.append("modified timestamp default CURRENT_TIMESTAMP NOT NULL,");
        sb.append("user_id INTEGER,");
        sb.append("activity_id INTEGER,");
        sb.append("rating DOUBLE");
        sb.append(")");

        return sb.toString();
    }

    public static String insert(String tableName,String header, String[] record)
    {
        System.out.printf("Creating insert for "+ record);


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
