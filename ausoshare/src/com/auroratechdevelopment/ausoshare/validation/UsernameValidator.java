package com.auroratechdevelopment.ausoshare.validation;

import android.text.TextUtils;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.common.validation.ValidatorBase;

/**
 * Created by happy pan on 2015/11/6.
 */
public class UsernameValidator extends ValidatorBase {
    private static final String ValidationPattern = "^[A-Za-z0-9.-]+";

    @Override
    public ValidatorBase.ValidationResult ValidateValue(String value, String... args) {
        if (TextUtils.isEmpty(value)) {
            return createValidationResult(false, R.string.validate_empty_username);
        } else if (!doesMatchPattern(ValidationPattern, value)) {
            return createValidationResult(false, R.string.validate_username_failure);
        } else if (isInRange(value, CustomApplication.getContext().getResources()
                        .getInteger(R.integer.min_username_length),
                CustomApplication.getContext().getResources()
                        .getInteger(R.integer.max_username_length)
        ) != 0) {
            return createValidationResult(
                    false,
                    R.string.validate_outofrange_username,
                    CustomApplication.getContext().getResources()
                            .getInteger(R.integer.min_username_length),
                    CustomApplication.getContext().getResources()
                            .getInteger(R.integer.max_username_length)
            );
        } else {
            return createValidationResult(true, -1);
        }
    }
}
