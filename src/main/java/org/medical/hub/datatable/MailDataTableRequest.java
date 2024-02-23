package org.medical.hub.datatable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailDataTableRequest {
    @NotNull
    private MailDataTableSearch search = new MailDataTableSearch();
}
