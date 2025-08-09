package pl.pkpik.bilkom.pivotcsv.pivottable.columns;

import lombok.Data;
import pl.pkpik.bilkom.pivotcsv.pivottable.aggregators.Aggregator;

@Data
public class DataColumnDto {

    public final Aggregator aggregator;
    public final ColumnKey columnKey;
    public final String fieldName;

    public DataColumnDto(Aggregator aggregator, ColumnKey columnKey) {
        this.aggregator = aggregator;
        this.columnKey = columnKey;
        this.fieldName = aggregator.getName() + "_" + aggregator.getField() + columnKey.fieldNameSuffix();
    }
}
