package com.example.user.validation;

import com.example.user.db.UserUtils;

//thực thi câu lệnh kiểm tra
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.SQLException;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmailValidation, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        try {
            return !UserUtils.checkExistByEmail(email);
        } catch (SQLException e) {
            return false;
        }
    }
}
