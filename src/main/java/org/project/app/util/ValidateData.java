package org.project.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class ValidateData {
    private String dateFormat, sex;
    private int degree;

    public ValidateData(String dateFormat, String sex, int degree) {
        this.dateFormat = dateFormat;
        this.sex = sex;
        this.degree = degree;
    }

    private boolean isDateValid() {
        DateFormat sdf = new SimpleDateFormat(this.dateFormat);
        sdf.setLenient(false);

        try {
            sdf.parse(dateFormat);
        }catch(ParseException e) {
            return false;
        }

        return true;
    }

    public boolean validate() {

        if (!isDateValid())
            return false;

        if (!Objects.equals(this.sex, "M") && !Objects.equals(this.sex, "F"))
            return false;

        if (this.degree > 10 || this.degree < 1)
            return false;

        return true;
    }

}
