package subota.max.verification.application;

import subota.max.core.confirmation.ConfirmationType;
import subota.max.verification.api.dto.Subject;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

import static subota.max.core.confirmation.ConfirmationType.EMAIL_CONFIRMATION;
import static subota.max.core.confirmation.ConfirmationType.MOBILE_CONFIRMATION;

public class SubjectValidator implements ConstraintValidator<SubjectValidation, Subject> {

    private static final Pattern emailPattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    private static final Pattern phoneNumber = Pattern.compile("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$");

    @Override
    public boolean isValid(Subject value, ConstraintValidatorContext context) {
        ConfirmationType confirmationType;
        try {
            confirmationType = ConfirmationType.valueOfIgnoreCase(value.type());
        } catch (IllegalArgumentException e) {
            return false;
        }

        if (confirmationType == EMAIL_CONFIRMATION) {
            return emailPattern.matcher(value.identity())
                .matches();
        }

        if (confirmationType == MOBILE_CONFIRMATION) {
            return phoneNumber.matcher(value.identity())
                .matches();
        }

        return true;
    }
}
