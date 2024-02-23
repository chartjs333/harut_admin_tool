package org.medical.hub.datatable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataTableColumnSpecs {

    @NotBlank
    private String data;

    private String name;

    @NotNull
    private boolean searchable = false;

    @NotNull
    private boolean orderable =false ;

    @NotNull
    private DataTableSearch search = new DataTableSearch();

    public void setSearchValue(String searchValue) {
        this.search.setValue(searchValue);
    }
}
