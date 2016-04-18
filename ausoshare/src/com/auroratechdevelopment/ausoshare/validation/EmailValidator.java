package com.auroratechdevelopment.ausoshare.validation;

import android.text.TextUtils;

import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.common.validation.ValidatorBase;

/**
 * Created by happy pan on 2015/11/6.
 */
public class EmailValidator extends ValidatorBase {
    private static final String ValidationPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$";

    @Override
    public ValidatorBase.ValidationResult ValidateValue(String value, String... args) {
        String confirmEmail = args.length > 0 ? args[0] : null;
        if (TextUtils.isEmpty(value)) {
            return createValidationResult(false, R.string.validate_empty_email);
        } else if (!doesMatchPattern(ValidationPattern, value)) {
            return createValidationResult(false, R.string.validate_email_failure);
        } else if (confirmEmail != null && value.compareToIgnoreCase(confirmEmail) != 0) {
            return createValidationResult(false, R.string.validate_email_nomatch);
        } else {
            return createValidationResult(true, -1);
        }
    }
}