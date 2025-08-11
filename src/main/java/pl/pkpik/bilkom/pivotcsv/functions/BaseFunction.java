package pl.pkpik.bilkom.pivotcsv.functions;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.impl.*;
import pl.pkpik.bilkom.pivotcsv.functions.params.Param;

import java.time.LocalDate;
import java.util.UUID;

import static pl.pkpik.bilkom.pivotcsv.functions.params.Params.getDecimals;

@Data
public abstract class BaseFunction implements Function {

    private final UUID id = UUID.randomUUID();
    protected BaseFunction parent;
    protected BaseFunction arg;
    protected BaseFunction next;

    public BaseFunction root() {
        return (parent == null) ? this : parent.root();
    }

    public BaseFunction multiply(BaseFunction arg) {
        BaseFunction argRoot = arg.root();
        next = new FnMultiply(argRoot);
        argRoot.parent = next;
        next.parent = this;
        return next;
    }

    public BaseFunction add(BaseFunction arg) {
        BaseFunction argRoot = arg.root();
        next = new FnAdd(argRoot);
        argRoot.parent = next;
        next.parent = this;
        return next;
    }

    public BaseFunction in(String... values) {
        next = new FnValueIn(values);
        next.parent = this;
        return next;
    }

    public Function between(LocalDate fromDay, LocalDate toDay) {
        next = new FnDayBetween(fromDay, toDay);
        next.parent = this;
        return next;
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
