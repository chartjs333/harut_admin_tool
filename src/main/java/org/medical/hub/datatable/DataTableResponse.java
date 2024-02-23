package org.medical.hub.datatable;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class DataTableResponse<T> {

    @JsonView(View.class)
    private int draw;

    @JsonView(View.class)
    private long recordsTotal = 0L;

    @JsonView(View.class)
    private long recordsFiltered = 0L;

    @JsonView(View.class)
    private List<T> data = Collections.emptyList();

    @JsonView(View.class)
    private String error;

    public interface View {
    }
}
