package org.medical.hub.customer;

import lombok.Getter;
import lombok.Setter;
import org.medical.hub.models.StringListConverter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Convert(converter = StringListConverter.class)
    private List<String> phone;

    @Column(name = "email", unique = true)
    private String email;


    private Long createdAt;
    private Long updatedAt;
}
