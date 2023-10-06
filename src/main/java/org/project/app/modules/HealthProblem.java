package org.project.app.modules;


import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Builder;
import lombok.Value;
interface  HealthProblemInterface {
    String name = " ";
    // from 0 to 1;
    int degree = 0;
}

@Value
@Builder
public class HealthProblem implements HealthProblemInterface {
    @JsonRawValue
    String name;
    int degree;
}
