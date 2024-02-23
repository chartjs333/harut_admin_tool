package org.medical.hub.datatable;

import lombok.Data;
import org.medical.hub.mail.MailStatus;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class DataTableRequest {

    @NotNull
    @Min(0)
    private Integer draw = 1;

    @NotNull
    @Min(0)
    private Integer start = 0;

    @NotNull
    @Min(-1)
    private Integer length = 10;

    private MailStatus selectStatus; // новое поле

    @NotEmpty
    private List<DataTableColumnSpecs> columns = new ArrayList<>();

    @NotNull
    private DataTableSearch search = new DataTableSearch();

    private List<DataTableOrder> order = new ArrayList<>();
}
