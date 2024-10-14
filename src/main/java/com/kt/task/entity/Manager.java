package com.kt.task.entity;

import com.kt.task.entity.base.BaseNameEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "managers")
@SequenceGenerator(name = "entity_generator", sequenceName = "manager_sequence", allocationSize = 1)
public class Manager extends BaseNameEntity {

    private String surname;

    private String email;

    private String phoneNumber;

    private String street;

    private String postalCode;

    private String city;
}
