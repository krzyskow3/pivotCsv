package pl.pkpik.bilkom.pivotcsv.functions;

import org.junit.Assert;
import org.junit.Test;
import pl.pkpik.bilkom.pivotcsv.csv.Record;

import java.time.LocalDate;

import static pl.pkpik.bilkom.pivotcsv.functions.FunctionBuilder.field;
import static pl.pkpik.bilkom.pivotcsv.functions.FunctionBuilder.fnCase;

public class FunctionTest {

    @Test
    public void testArithmeticFormula() {
        Record rec = new Record()
                .setValue("a", "2")
                .setValue("b", "3")
                .setValue("c", "4")
                .setValue("d", "5");
        Function f = field("a").multiply(field("b")).add(field("c").multiply(field("d")));
        double result = f.getFloatValue(rec);
        Assert.assertEquals(26, result, 1e-6);
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
    public void testCaseWhenThenOrElse() {
        Record rec = new Record()
                .setValue("a", "2")
                .setValue("b", "3")
                .setValue("c", "4")
                .setValue("d", "5");
        Function f1 = fnCase(field("a").eq("2"), field("c"), field("d"));
        Function f2 = fnCase(field("b").eq("2"), field("c"), field("d"));
        Assert.assertEquals(4, f1.getIntValue(rec));
        Assert.assertEquals(5, f2.getIntValue(rec));
    }


    @Test
    public void testFloatFieldIn() {
        Record rec1 = new Record().setValue("rec_type", "10");
        Record rec2 = new Record().setValue("rec_type", "10.1");
        Record rec3 = new Record().setValue("rec_type", "10.12");
        Record rec4 = new Record().setValue("rec_type", "12.1");
        Function f = field("rec_type").in(10.1, 12.1);
        Assert.assertFalse(f.getBooleanValue(rec1));
        Assert.assertTrue(f.getBooleanValue(rec2));
        Assert.assertFalse(f.getBooleanValue(rec3));
        Assert.assertTrue(f.getBooleanValue(rec4));
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