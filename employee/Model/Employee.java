package org.example.employee.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class Employee {
    @NotNull
   @Length(min = 3,message = "The ID should be more than 3 digits ")
private String id;
    @NotNull
    @Length(min = 4, message = "Name should be more than 4 characters ")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name contain letters only ")
private String name;
    @Email
    private String email;
    @Pattern(regexp = "^05\\d{8}$"  , message = "Number should start with 05xxxxxxxx and contain 10 numbers")
    private String phoneNumber;
@NotNull
@Min(value = 26 ,message = "Age of employee must be more than 25")
@Max(value = 64 ,message = "Age of employee must be less than 65")
    private int age;
@NotNull
@Pattern(regexp = "^(supervisor|coordinator)$" , message = "Position must be supervisor OR coordinator only ")
    private String position;
@AssertFalse
    private boolean onLeave;
@NotNull
@PastOrPresent
//@Pattern(regexp =  "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$"   , message = "the year should be 1900 or later ")  //not work regex
    @JsonFormat(pattern = "yyyy-MM-dd")

    private LocalDate hireDate;
@NotNull
@PositiveOrZero
    private int annualLeave;

}
