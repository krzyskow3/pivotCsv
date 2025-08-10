package pl.pkpik.bilkom.pivotcsv.functions;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.impl.FAdd;
import pl.pkpik.bilkom.pivotcsv.functions.impl.FField;
import pl.pkpik.bilkom.pivotcsv.functions.impl.FMultiply;

import java.util.UUID;

@Data
public abstract class BaseFunction implements Function {

    private final UUID id = UUID.randomUUID();
    protected BaseFunction parent;
    protected BaseFunction arg;
    protected BaseFunction next;

    public static BaseFunction field(String field) {
        return new FField(field);
    }

    public BaseFunction root() {
        return (parent == null) ? this : parent.root();
    }

    public BaseFunction multiply(BaseFunction arg) {
        BaseFunction argRoot = arg.root();
        next = new FMultiply(argRoot);
        argRoot.parent = next;
        next.parent = this;
        return next;
    }

    public BaseFunction add(BaseFunction arg) {
        BaseFunction argRoot = arg.root();
        next = new FAdd(argRoot);
        argRoot.parent = next;
        next.parent = this;
        return next;
    }

    @Override
    public String getValue(Record record) {
        System.out.println("=> getValue: " + getClassName());
        if (parent == null) {
            FResult result = new FResult();
            calculate(record, result);
            System.out.println("=> result");
            return result.pop();
        } else {
            return root().getValue(record);
        }
    }

    private void calculate(Record record, FResult result) {
        if (arg != null) {
            arg.calculate(record, result);
        }
        apply(record, result);
        if (next != null) {
            next.calculate(record, result);
        }
    }

    public abstract void apply(Record record, FResult result);

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

    private String getClassName() {
        String[] split = StringUtils.split(getClass().getName(), '.');
        return split[split.length - 1];
    }
}
