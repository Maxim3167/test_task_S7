package app.util;

import lombok.experimental.UtilityClass;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
@UtilityClass
public class StringErrorBuilder {
    public static String buildErrorString(BindingResult bindingResult){
        StringBuilder stringBuilder = new StringBuilder();
        bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}
