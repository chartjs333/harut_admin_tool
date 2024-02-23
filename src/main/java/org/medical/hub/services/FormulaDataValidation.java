package org.medical.hub.services;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.util.CellRangeAddress;

@Setter
@Getter
public class FormulaDataValidation {

    private String formula;
    private String sqref;

    private String[] values;
    private CellRangeAddress cellAddresses;
}
