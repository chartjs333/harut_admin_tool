package org.medical.hub.validators;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.hibernate.mapping.Formula;
import org.medical.hub.services.FormulaDataValidation;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTExtension;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTExtensionList;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExcelFormulaDataValidation {

    public String[] getValues(Workbook wb, String formula){
        AreaReference formulaReference = new AreaReference(formula, SpreadsheetVersion.EXCEL2007);
        CellReference[] allReferencedCells = formulaReference.getAllReferencedCells();
        FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
        String[] values = new String[allReferencedCells.length];

        for (int j = 0; j < allReferencedCells.length; j++) {
            CellReference cellReference = allReferencedCells[j];
            Sheet valueSheet = wb.getSheet(cellReference.getSheetName());
            Cell cell = valueSheet.getRow(cellReference.getRow()).getCell(cellReference.getCol());
            CellValue evaluate = formulaEvaluator.evaluate(cell);
            values[j] = StringUtils.trimToEmpty(StringUtils.removeStart(StringUtils.removeEnd(evaluate.formatAsString(), "\""), "\""));
        }

        return values;
    }

    public List<FormulaDataValidation> getFormulaDataValidation(Workbook wb, XSSFCell xcell) {
        List<FormulaDataValidation> formulaValidation = getFormulaDataValidation(xcell);
        List<FormulaDataValidation> dataValidations = new ArrayList<>();

        for (FormulaDataValidation validation : formulaValidation) {
            String[] values =  getValues(wb, validation.getFormula());
            String stRef = validation.getSqref();
            String[] regions = stRef.split(" ");
            for (String region : regions) {
                String[] parts = region.split(":");
                CellReference begin = new CellReference(parts[0]);
                CellReference end = parts.length > 1 ? new CellReference(parts[1]) : begin;
                CellRangeAddress cellRangeAddress = new CellRangeAddress(begin.getRow(), end.getRow(), begin.getCol(), end.getCol());

                FormulaDataValidation fbValidation = new FormulaDataValidation();
                fbValidation.setValues(values);
                fbValidation.setCellAddresses(cellRangeAddress);
                fbValidation.setSqref(stRef);
                fbValidation.setFormula(validation.getFormula());

                dataValidations.add(fbValidation);
            }

        }

        return dataValidations;
    }

    public List<FormulaDataValidation> getFormulaDataValidation(XSSFCell cell) {
        List<FormulaDataValidation> formulaDataValidations = new ArrayList<>();

        CTExtensionList extLst = cell.getSheet().getCTWorksheet().getExtLst();
        if (extLst == null) {
            return formulaDataValidations;
        }

        CTExtension[] extArray = extLst.getExtArray();
        List<Node> dataValidationNodes = new ArrayList<>();
        for (CTExtension ext : extArray) {
            searchForDataValidation(ext.getDomNode(), dataValidationNodes);
        }

        for (Node dataValidationNode : dataValidationNodes) {
            FormulaDataValidation dataValidations = new FormulaDataValidation();
            getDataValidationInfo(dataValidationNode, dataValidations);
            formulaDataValidations.add(dataValidations);
        }

        return formulaDataValidations;
    }

    private void searchForDataValidation(Node node, List<Node> nodesInQuestion) {
        if (StringUtils.equalsIgnoreCase("x14:dataValidation", node.getNodeName())) {
            nodesInQuestion.add(node);
            return;
        }

        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            searchForDataValidation(node.getChildNodes().item(i), nodesInQuestion);
        }
    }

    private static void getDataValidationInfo(Node node, FormulaDataValidation dataValidations) {
        if (StringUtils.equalsIgnoreCase("#text", node.getNodeName())) {
            if (StringUtils.equalsIgnoreCase("xm:sqref", node.getParentNode().getNodeName())) {
                dataValidations.setSqref(node.getNodeValue());
            } else if (StringUtils.equalsIgnoreCase("xm:f", node.getParentNode().getNodeName())) {
                dataValidations.setFormula(node.getNodeValue());
            }
            return;
        }

        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            getDataValidationInfo(node.getChildNodes().item(i), dataValidations);
        }
    }
}
