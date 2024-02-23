package org.medical.hub.customer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CustomerRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    private List<String> phone;
}
