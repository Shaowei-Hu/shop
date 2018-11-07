package com.shaowei.shop.service.mapper;

import com.shaowei.shop.domain.*;
import com.shaowei.shop.service.dto.ToyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Toy and its DTO ToyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ToyMapper extends EntityMapper<ToyDTO, Toy> {



    default Toy fromId(String id) {
        if (id == null) {
            return null;
        }
        Toy toy = new Toy();
        toy.setId(id);
        return toy;
    }
}
