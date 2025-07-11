package kg.com.productapplication.exceptions.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.com.inventoryapplication.exceptions.body.ValidationExceptionBody;
import kg.com.inventoryapplication.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
@RequiredArgsConstructor
@Valid
public class GlobalControllerAdvice {
    private final ErrorService errorService;

    private ValidationExceptionBody handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request
    ) {
        return errorService.handleValidationException(ex, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request, Model model
    ) {
        var exception = handleValidationException(ex, request);
        model.addAttribute("exception", exception);
        return "errors/validation_error";
    }

    @ExceptionHandler({NoSuchElementException.class, ClassCastException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementException(
            Model model, RuntimeException ex, HttpServletRequest request
    ) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleHandlerMethodValidationException(
            HandlerMethodValidationException ex, HttpServletRequest request, Model model
    ) {
        Map<String, Object> exceptionBody = errorService.handleMethodValidationException(ex, request);

        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("message", exceptionBody.get("message"));
        model.addAttribute("details", request);

        return "errors/error";
    }

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleDateTimeParseException(DateTimeParseException ex) {
        return errorService.handleDateTimeParserException(ex);
    }

    @ExceptionHandler({IllegalArgumentException.class, IOException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(
            Exception ex,
            HttpServletRequest request,
            Model model) {

        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("details", request);
        return "errors/error";
    }
}
