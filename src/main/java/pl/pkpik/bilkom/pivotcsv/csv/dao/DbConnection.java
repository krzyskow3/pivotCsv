package pl.pkpik.bilkom.pivotcsv.csv.dao;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DbConnection {

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

}
