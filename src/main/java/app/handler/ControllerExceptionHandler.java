package app.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    @ExceptionHandler
    public String handleException(Exception exception){
        log.error("Получена ошибка!",exception);
        return exception.getMessage();
    }
}
