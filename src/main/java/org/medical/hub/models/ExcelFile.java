package org.medical.hub.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "excel_files")
public class ExcelFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private FileType typeOf;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Status status;
    private String fileName;
    private String fileId;

    @Lob
    @Column(name = "content", columnDefinition = "BLOB")
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
