package scottxuan.cloud.web.validate;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author : scottxuan
 */
@Component
public class MobileValidate implements ConstraintValidator<Mobile, String> {

    private Pattern pattern = Pattern.compile("^1(3|4|5|6|7|8|9)\\d{9}$");

    @Override
    public void initialize(Mobile mobile) {
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return pattern.matcher(value).matches();
    }
}
