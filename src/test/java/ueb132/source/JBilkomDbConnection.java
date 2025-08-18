package ueb132.source;

import pl.pkpik.bilkom.pivotcsv.csv.dao.DbConnection;

public class JBilkomDbConnection {

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/jbilkom";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    private static final String PROD_URL = "jdbc:postgresql://10.32.223.145:5432/jBilkom";
    private static final String PROD_USER = "odczyt";
    private static final String PROD_PASS = "******";

    public static DbConnection create() {
        return new DbConnection(DRIVER, URL, USER, PASS);
    }

    public static DbConnection createProd() {
        return new DbConnection(DRIVER, PROD_URL, PROD_USER, PROD_PASS);   }

}
