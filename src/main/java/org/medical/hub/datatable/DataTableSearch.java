package org.medical.hub.datatable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataTableSearch {

    @NotNull
    private String value= "";

    @NotNull
    private Boolean regex = false;
}
