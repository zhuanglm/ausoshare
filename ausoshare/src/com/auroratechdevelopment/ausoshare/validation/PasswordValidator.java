package com.auroratechdevelopment.ausoshare.validation;

/**
 * Created by happy pan on 2015/11/6.
 */

import android.text.TextUtils;

import com.auroratechdevelopment.common.validation.ValidatorBase;
import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.common.validation.ValidatorBase;

public class PasswordValidator extends ValidatorBase {

    @Override
    public ValidatorBase.ValidationResult ValidateValue(String value, String... args) {
        String confirmPwd = null;
        if (args != null && args.length > 0) {
            confirmPwd = args[0];
        }

        if (TextUtils.isEmpty(value)) {
            return createValidationResult(false, R.string.validate_empty_password);
        } else if (isInRange(value, CustomApplication.getContext().getResources()
                        .getInteger(R.integer.min_password_length),
                CustomApplication.getContext().getResources()
                        .getInteger(R.integer.max_password_length)
        ) != 0) {
            return createValidationResult(
                    false,
                    R.string.validate_outofrange,
                    CustomApplication.getContext().getResources()
                            .getInteger(R.integer.min_password_length),
                    CustomApplication.getContext().getResources()
                            .getInteger(R.integer.max_password_length)
            );
        } else if (args != null && args.length > 0 && value.compareToIgnoreCase(confirmPwd) != 0) {
            return createValidationResult(false, R.string.validate_password_nomatch);
        } else {
            return createValidationResult(true, -1);
        }

    }
}

