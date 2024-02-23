package org.medical.hub.models;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Table(name = "rules")
@Entity
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private FileType fileType;

    @NotNull
    @Convert(converter = RuleTypeConverter.class)
    private RuleType ruleType ;

    @Column(name = "compare_between")
    @Convert(converter = CompareBetweenConverter.class)
    private CompareBetween compareWith;

    private String firstColumn ;
    private String secondColumn;
    private String errorMessage;

    @Convert(converter = ExpressionConverter.class)
    private Expression expression;

    private String parameter ;
    private String variableName;
    private Integer negativeValue;
    private Integer positiveValue;
    private Integer undefinedValue;

    private String negativeMessage;
    private String undefinedMessage;
    private String methodName;
    private String parameters;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User user;
}
