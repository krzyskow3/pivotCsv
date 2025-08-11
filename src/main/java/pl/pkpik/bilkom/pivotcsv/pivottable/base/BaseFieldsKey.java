package pl.pkpik.bilkom.pivotcsv.pivottable.base;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import pl.pkpik.bilkom.pivotcsv.csv.Record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class BaseFieldsKey implements Comparable<BaseFieldsKey> {

    private final List<@NotNull String> values;

    public BaseFieldsKey(@NotNull Record record, @NotNull List<String> fields) {
        this(fields.stream()
                .map(record::getValue)
                .collect(Collectors.toList()));
    }

    private BaseFieldsKey(List<@NotNull String> values) {
        this.values = values;
    }

    public int size() {
        return values.size();
    }

    public List<String> getValues() {
        return new ArrayList<>(values);
    }

    public String fieldNameSuffix() {
        return (values.isEmpty()) ? "" : ("_" + StringUtils.join(values, '_'));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof BaseFieldsKey) {
            BaseFieldsKey other = (BaseFieldsKey) obj;
            return (this.size() == other.size()) && IntStream.range(0, values.size())
                    .allMatch(i -> Objects.equals(this.values.get(i), other.values.get(i)));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values.toArray());
    }

    @Override
    public int compareTo(@NotNull BaseFieldsKey other) {
        for (int i = 0; i < size(); i++) {
            if (i < other.size()) {
                int diff = this.values.get(i).compareTo(other.values.get(i));
                if (diff != 0) {
                    return diff;
                }
            } else {
                return 1;
            }
        }
        if (size() < other.size()) {
            return -1;
        }
        return 0;
    }

    public String getValue(int idx) {
        return (idx < values.size()) ? values.get(idx) : "";
    }
}
