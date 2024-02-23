package org.medical.hub.validators.password;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmationValidator implements ConstraintValidator<PasswordConfirmation, Object> {

    private String firstFieldName;

    private String secondFieldName;

    private String message;

    @Override
    public void initialize(PasswordConfirmation constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    /**
     * Default message for the validation
     *
     * @return
     */
    private String getDefaultMessage(){
        return firstFieldName+" doesn't match with "+secondFieldName;
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){

        boolean valid = false;

        try{
            final Object firstObj = BeanUtils.getProperty(obj, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(obj, secondFieldName);

            valid = (firstObj == null && secondObj == null) || (firstObj != null && firstObj.equals(secondObj));
        }catch(Exception ignore){

        }

        if(!valid){
            this.message = (message == null)? getDefaultMessage():message;
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();;
        }
        return valid;
    }
}