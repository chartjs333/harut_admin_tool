package org.medical.hub.datatable;

public class DataTableUtils {

    private DataTableUtils(){}

    public static int getPageNumber(DataTableRequest dataTable) {
        boolean flag = dataTable.getLength() <= 0 || dataTable.getStart() <= 0 || dataTable.getLength() > dataTable.getStart();
        if (flag) {
            return 0;
        }
        return (int) Math.floor((double) dataTable.getStart() / dataTable.getLength());
    }
}
