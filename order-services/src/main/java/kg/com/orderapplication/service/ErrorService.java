package kg.com.orderapplication.service;

import jakarta.servlet.http.HttpServletRequest;
import kg.com.orderapplication.exceptions.body.ValidationExceptionBody;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.format.DateTimeParseException;
import java.util.Map;

public interface ErrorService {
    ValidationExceptionBody handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest httpServletRequest);

    Map<String, Object> handleMethodValidationException(HandlerMethodValidationException ex, HttpServletRequest request);

    Map<String, Object> handleDateTimeParserException(DateTimeParseException ex);
}
