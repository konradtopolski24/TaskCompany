package com.kt.task.dto;

import com.kt.task.dto.base.BaseNameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ManagerDto extends BaseNameDto {

    private String surname;
    private String email;
    private String phoneNumber;
    private String street;
    private String postalCode;
    private String city;
}
