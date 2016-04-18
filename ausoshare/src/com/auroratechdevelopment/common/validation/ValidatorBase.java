package com.auroratechdevelopment.common.validation;

import android.content.Context;

/**
 *
 * @author Tim.Wang
 */
abstract public class ValidatorBase {
    public class ValidationResult
    {
        private boolean m_Valid;
        private String m_Error;
        
        protected ValidationResult(boolean isValid, String error){
            m_Valid = isValid;
            m_Error = error;
        }
        public boolean isValid() {
            return m_Valid;
        }
        public String getError() {
            return m_Error;
        }
    };
    
    protected Context m_Context;
    
    public void setContext(Context context)
    {
        m_Context = context;
    }
    
    protected boolean numericOnly(String val)
    {
        if(val == null)
            return false;
        
        int len = val.length();
        for(int index = 0; index < len; index++){
            char c = val.charAt(index);
            if(c < '0' || c > '9')
                return false;
        }
        return true;
    }
    protected boolean doesMatchPattern(String pattern, String value)
    {
        if(value == null)
            return false;
        
        return value.matches(pattern);
    }
    protected byte isInRange(String val, int low, int high)
    {
        if(val == null){
            if(low != 0){
                return -1;
            }
            else{
                return 0;
            }
        }
        int len = val.length();
        if(len >= low && len <= high)
            return 0;
        else if(len < low)
            return -1;
        else
            return 1;
    }
    protected boolean isComboboxValueSelected(int valueResId, String val)
    {
    	String[] vals = m_Context.getResources().getStringArray(valueResId);
    	if(vals == null || vals.length <= 1){
    		return false;
    	}
    	return vals[0].compareTo(val) != 0;
    }
    protected ValidationResult createValidationResult(boolean success, int resId, Object... args)
    {
        if(success)
            return new ValidationResult(true, "");
        else{
            String msg = m_Context.getResources().getString(resId);
            if(args != null)
                msg = String.format(msg, args);
            return new ValidationResult(false, msg);
        }
    }
    abstract public ValidationResult ValidateValue(String value, String...args);
}
