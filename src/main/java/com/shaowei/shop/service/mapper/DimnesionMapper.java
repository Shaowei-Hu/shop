package com.shaowei.shop.service.mapper;

import com.shaowei.shop.domain.*;
import com.shaowei.shop.service.dto.DimnesionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Dimnesion and its DTO DimnesionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DimnesionMapper extends EntityMapper<DimnesionDTO, Dimnesion> {



    default Dimnesion fromId(String id) {
        if (id == null) {
            return null;
        }
        Dimnesion dimnesion = new Dimnesion();
        dimnesion.setId(id);
        return dimnesion;
    }
}
