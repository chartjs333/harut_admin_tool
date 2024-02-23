package org.medical.hub.utils;

import org.apache.commons.lang3.StringUtils;

public class StringHelper {

    private StringHelper(){}

    public static String splitColName(String columnName, String splitBy){
        if(StringUtils.isBlank(columnName))
            return columnName;

        String[] methodSplit = columnName.split(splitBy);
        String colName = "";
        for (int i = 0; i < methodSplit.length - 1; i++) {
            colName += methodSplit[i]+splitBy;
        }
        return StringUtils.stripEnd(colName, splitBy);

    }
}
