package kg.com.orderapplication.mapper;

import kg.com.orderapplication.dto.OrderLineItemsDto;
import kg.com.orderapplication.model.OrderLineItems;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderLineMapper {
    OrderLineItemsDto mapToDto(OrderLineItems orderLineItems);
    OrderLineItems mapToModel(OrderLineItemsDto orderLineItemsDto);
}
