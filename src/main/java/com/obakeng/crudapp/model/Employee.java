package com.obakeng.crudapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Employees")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String email;
    private String fullName;
    private String jobTitle;
    private String password;
}
