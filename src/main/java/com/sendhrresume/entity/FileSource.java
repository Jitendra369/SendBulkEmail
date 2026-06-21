package com.sendhrresume.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "file_source", uniqueConstraints = {
        @UniqueConstraint(
                name = "unique_file_name",
                columnNames = "fileName"
        )
})
@Getter
@Setter
public class FileSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fileName;
    private String fileType;
    private String fileLocation;
}
