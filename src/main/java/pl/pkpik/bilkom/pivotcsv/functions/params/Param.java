package pl.pkpik.bilkom.pivotcsv.functions.params;

import lombok.Data;

import static pl.pkpik.bilkom.pivotcsv.functions.params.ParamEnum.DECIMALS;

@Data
public class Param {

    public final ParamEnum type;
    public final String value;

}
