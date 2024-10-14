package com.kt.task.entity;

import com.kt.task.entity.base.BaseNameEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "departments")
@SequenceGenerator(name = "entity_generator", sequenceName = "department_sequence", allocationSize = 1)
public class Department extends BaseNameEntity {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "department_id")
    private Set<Team> teams;
}
