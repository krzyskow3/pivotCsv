package pl.pkpik.bilkom.pivotcsv.csv.db;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DbConnection {

    public static final DbConnection BILKOM = new DbConnection(
            "org.postgresql.Driver",
            "jdbc:postgresql://localhost:5432/jbilkom",
            "postgres",
            "postgres");

    private final String driver;
    private final String url;
    private final String username;
    private final String password;

    @SneakyThrows
    public Csv executeQuery(String selectSql) {
        Class.forName(driver);
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            try (Statement stmt = con.createStatement()) {
                try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
                    List<Map<String, Object>> records = new ArrayList<>();
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    List<String> fields = new ArrayList<>();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        String columnName = metaData.getColumnName(i);
                        fields.add(columnName);
                    }
                    while (resultSet.next()) {
                        Map<String, Object> record = fields.stream()
                                .collect(Collectors.toMap(field -> field, field ->  getValue(resultSet, field)));
                        records.add(record);
                    }
                    return Csv.create(records);
                }
            }
        }
    }

    @SneakyThrows
    private Object getValue(ResultSet rs, String field) {
        String value = rs.getString(field);
        boolean isNull = (value == null) || "null".equals(value);
        return isNull ? "<NULL>" : value;
    }

    private static final File OUT_FOLDER = new File("target/out");

    @SneakyThrows
    public static void main(String[] args) {
        Csv csv = BILKOM.executeQuery("SELECT * FROM sale_temporary");
        csv.save(new File(OUT_FOLDER,"db_sale_temporary.csv"));
    }

}
