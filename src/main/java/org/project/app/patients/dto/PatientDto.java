package org.project.app.patients.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.project.app.modules.HealthProblem;

interface PatientDtoInterface {
    String name = " ";
    String dob = " ";
    // M (male) or F (female)
    String sex = " ";
    HealthProblem hp = null;

}

@Builder
@Value
public class PatientDto implements PatientDtoInterface{

    String name;
    String dob;
    String sex;
    HealthProblem hp;
}
