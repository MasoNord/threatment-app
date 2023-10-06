package org.project.app.patients.entities;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.*;
import org.project.app.modules.HealthProblem;

interface PatientInterface {
    String name = "";

    String dob = "";

    String sex = " ";
    HealthProblem hp = null;
    // data created at
    String dc = " ";
    // data updated at
    String du = " ";
}

@Builder
@Value
public class Patient implements PatientInterface{
    String id;

    @JsonRawValue
    String name;

    @JsonRawValue
    String dob;

    @JsonRawValue
    String sex;

    HealthProblem hp;

    String dc;

    String du;
}

