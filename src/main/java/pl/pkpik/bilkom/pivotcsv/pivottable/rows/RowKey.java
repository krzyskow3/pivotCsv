package pl.pkpik.bilkom.pivotcsv.pivottable.rows;

import org.jetbrains.annotations.NotNull;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.pivottable.base.BaseFieldsKey;

import java.util.List;

public class RowKey extends BaseFieldsKey {

    public RowKey(@NotNull Record record, @NotNull List<String> fields) {
        super(record, fields);
    }
}
