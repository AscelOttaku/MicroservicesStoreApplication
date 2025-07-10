package kg.com.orderapplication.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderOperationResultDto {
    private String message;
    private boolean operationSucceeded;
}
