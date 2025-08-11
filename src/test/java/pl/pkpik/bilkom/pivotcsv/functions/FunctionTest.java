package pl.pkpik.bilkom.pivotcsv.functions;

import org.junit.Assert;
import org.junit.Test;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.params.Params;

import java.time.LocalDate;

import static pl.pkpik.bilkom.pivotcsv.functions.FunctionBuilder.field;

public class FunctionTest {

    @Test
    public void testArithmeticFormula() {
        Record rec = new Record()
                .setValue("a", "2")
                .setValue("b", "3")
                .setValue("c", "4")
                .setValue("d", "5");
        Function f = field("a").multiply(field("b")).add(field("c").multiply(field("d")));
        String result = f.getValue(rec, Params.DECIMALS_0);
        Assert.assertEquals("26", result);
    }

    @Test
    public void testFieldIn() {
        Record rec1 = new Record().setValue("rec_type", "abc");
        Record rec2 = new Record().setValue("rec_type", "def");
        Function f = field("rec_type").in("abc", "xyz");
        Assert.assertEquals("true", f.getValue(rec1));
        Assert.assertEquals("false", f.getValue(rec2));
    }

    @Test
    public void testFieldDayBetween() {
        Record rec1 = new Record().setValue("op_day", "2025-06-06");
        Record rec2 = new Record().setValue("op_day", "2025-08-08");
        Record rec3 = new Record().setValue("op_day", "2025-08-28");
        LocalDate fromDay = LocalDate.of(2025, 8, 1);
        LocalDate toDay = LocalDate.of(2025, 8, 15);
        Function f = field("op_day").between(fromDay, toDay);
        Assert.assertEquals("false", f.getValue(rec1));
        Assert.assertEquals("true", f.getValue(rec2));
        Assert.assertEquals("false", f.getValue(rec3));
    }

}