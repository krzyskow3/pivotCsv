package pl.pkpik.bilkom.pivotcsv.functions;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.impl.*;
import pl.pkpik.bilkom.pivotcsv.functions.params.Param;

import java.time.LocalDate;
import java.util.UUID;

@Data
public abstract class BaseFunction implements Function {

    public static final double EPSILON = 1e-6;

    private final UUID id = UUID.randomUUID();
    protected BaseFunction parent;
    protected BaseFunction arg;
    protected BaseFunction next;

    public BaseFunction root() {
        return (parent == null) ? this : parent.root();
    }

    public BaseFunction link(BaseFunction parent) {
        this.setParent(parent);
        parent.setNext(this);
        if (this.arg != null) {
            this.arg.setParent(this);
        }
        return this;
    }

    public BaseFunction multiply(BaseFunction arg) {
        return new FnMultiply(arg.root()).link(this);
    }

    public BaseFunction add(BaseFunction arg) {
        return new FnAdd(arg.root()).link(this);
    }

    public BaseFunction in(String... values) {
        return new FnValueIn(values).link(this);
    }

    public BaseFunction in(double... values) {
        return new FnFloatValueIn(values).link(this);
    }

    public Function between(LocalDate fromDay, LocalDate toDay) {
        return new FnDayBetween(fromDay, toDay).link(this);
    }

    @Override
    public String getValue(Record record, Param... params) {
        if (parent == null) {
            FnResult result = new FnResult();
            calculate(record, result, params);
            return result.pop();
        } else {
            return root().getValue(record, params);
        }
    }

    @Override
    public double getFloatValue(Record record, Param... params) {
        return NumberUtils.toDouble(getValue(record, params));
    }

    @Override
    public boolean getBooleanValue(Record record, Param... params) {
        return "true".equalsIgnoreCase(getValue(record, params));
    }

    private void calculate(Record record, FnResult result, Param[] params) {
        if (arg != null) {
            arg.calculate(record, result, params);
        }
//        System.out.println("=> apply: " + getClassName());
        apply(record, result, params);
        if (next != null) {
            next.calculate(record, result, params);
        }
    }

    public abstract void apply(Record record, FnResult result, Param[] params);

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        info.append("{ \"class\": \"").append(getClassName()).append("\"");
        if (parent != null) {
            info.append(", \"parent\": \"").append(parent.id).append("\"");
        }
        info.append(", \"id\": \"").append(id).append("\"");
        if (arg != null) {
            info.append(", \"arg\": ").append(arg);
        }
        if (next != null) {
            info.append(", \"next\": ").append(next);
        }
        info.append(" }");
        return info.toString();
    }

    public String formatValue(double value, int decimals) {
        String format = "%." + decimals + "f";
        return String.format(format, value).replace(",", ".");
    }

    private String getClassName() {
        String[] split = StringUtils.split(getClass().getName(), '.');
        return split[split.length - 1];
    }

}
