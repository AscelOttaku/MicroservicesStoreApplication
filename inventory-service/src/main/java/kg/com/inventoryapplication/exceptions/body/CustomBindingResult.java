package kg.com.inventoryapplication.exceptions.body;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomBindingResult {
    private String className;
    private String fieldName;
    private Object rejectedValue;
}
