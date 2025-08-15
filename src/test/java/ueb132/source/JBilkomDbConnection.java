package ueb132.source;

import pl.pkpik.bilkom.pivotcsv.csv.dao.DbConnection;

public class JBilkomDbConnection {

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/jbilkom";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    public static DbConnection create() {
        return new DbConnection(DRIVER, URL, USER, PASS);
    }
}
