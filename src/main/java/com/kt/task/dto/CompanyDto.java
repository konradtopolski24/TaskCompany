package com.kt.task.dto;

import com.kt.task.dto.base.BaseNameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class CompanyDto extends BaseNameDto {

    private Set<DepartmentDto> departments;
}
