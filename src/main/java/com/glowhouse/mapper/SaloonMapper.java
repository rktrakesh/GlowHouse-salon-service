package com.glowhouse.mapper;

import com.glowhouse.dto.request.SaloonDTO;
import com.glowhouse.entity.Saloon;

public class SaloonMapper {

    public static SaloonDTO mapToDto (Saloon saloon) {
        SaloonDTO saloonDto = new SaloonDTO();
        saloonDto.setId(saloon.getId());
        saloonDto.setName(saloon.getName());
        saloonDto.setCity(saloon.getCity());
        saloonDto.setEmail(saloon.getEmail());
        saloonDto.setAddress(saloon.getAddress());
        saloonDto.setMobile(saloon.getMobile());
        saloonDto.setImages(saloon.getImages());
        saloonDto.setOpenTime(saloon.getOpenTime());
        saloonDto.setCloseTime(saloon.getCloseTime());
        saloonDto.setOwnerId(saloon.getOwnerId());
        return saloonDto;
    }

}
