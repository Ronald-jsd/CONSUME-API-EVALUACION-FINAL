package com.cibertec.evaluacionFinal.cibertec.dtos;

import lombok.Data;

@Data
public class StudentDTO {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String number;
    private String dni;
    private String birthdate;
    private String schoolGrade;
    private Boolean state ;

}
