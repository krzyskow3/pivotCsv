package pl.pkpik.bilkom.pivotcsv.functions.params;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.pkpik.bilkom.pivotcsv.functions.params.ParamEnum.DECIMALS;

public class Params {

    public static final Param DECIMALS_0 = new Param(DECIMALS, "0");
    public static final Param DECIMALS_1 = new Param(DECIMALS, "1");
    public static final Param DECIMALS_2 = new Param(DECIMALS, "2");
    public static final Param DECIMALS_3 = new Param(DECIMALS, "3");
    public static final Param DECIMALS_4 = new Param(DECIMALS, "4");
    public static final Param DECIMALS_6 = new Param(DECIMALS, "6");
    public static final Param DECIMALS_DEFAULT = DECIMALS_4;

    public static Map<ParamEnum, Param> toMap(Param[] params) {
        return Arrays.stream(params)
                .collect(Collectors.toMap(p -> p.type, p -> p));
    }

    public static int getDecimals(Param[] params) {
        Param param = Optional.ofNullable(toMap(params).get(DECIMALS))
                .orElse(DECIMALS_DEFAULT);
        return Math.max(NumberUtils.toInt(param.value), 0);
    }


}
