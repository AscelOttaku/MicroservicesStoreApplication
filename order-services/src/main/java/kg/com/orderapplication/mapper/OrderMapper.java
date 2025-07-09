package kg.com.orderapplication.mapper;

import kg.com.orderapplication.dto.OrderDto;
import kg.com.orderapplication.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderLineMapper.class})
public interface OrderMapper {
    OrderDto mapToDto(Order order);
    Order mapToModel(OrderDto orderDto);
}
