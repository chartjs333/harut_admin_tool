package org.medical.hub.datatable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.medical.hub.mail.MailStatus;

import javax.validation.constraints.NotNull;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailDataTableSearch {
    @NotNull
    private String value= "";

    @NotNull
    private Boolean regex = false;

    @NotNull
    private MailStatus status;
}
