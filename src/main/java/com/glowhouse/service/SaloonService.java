package com.glowhouse.service;

import com.glowhouse.dto.request.SaloonDTO;
import com.glowhouse.dto.request.UserDTO;
import com.glowhouse.entity.Saloon;

import java.util.List;

public interface SaloonService {

    public Saloon addNewSaloonDetails (SaloonDTO saloonDTO, UserDTO userDTO);

    public Saloon updateSaloonDetails (SaloonDTO saloonDTO, UserDTO userDTO, Long saloonId);

    public List<Saloon> getAllSaloonDetails ();

    public Saloon getSaloonDetailById (Long id);

    public List<Saloon> getSaloonByOwnerId (Long ownerId);

    public List<Saloon> getSaloonByCityName (String data);

}
