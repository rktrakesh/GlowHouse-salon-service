package com.glowhouse.service.impl;

import com.glowhouse.dto.request.SaloonDTO;
import com.glowhouse.dto.request.UserDTO;
import com.glowhouse.entity.Saloon;
import com.glowhouse.repository.SaloonRepository;
import com.glowhouse.service.SaloonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaloonServiceImpl implements SaloonService {

    private static final Logger logger = LoggerFactory.getLogger(SaloonServiceImpl.class);

    private final SaloonRepository repository;

    @Override
    public Saloon addNewSaloonDetails(SaloonDTO saloonDto, UserDTO userDto) {
        try {
            logger.info("Adding new saloon details for: {}, {}", saloonDto,userDto);
            Saloon saloon = new Saloon();
            if (saloonDto != null) {
                saloon.setName(saloonDto.getName());
                saloon.setImages(saloonDto.getImages());
                saloon.setAddress(saloonDto.getAddress());
                saloon.setMobile(saloonDto.getMobile());
                saloon.setEmail(saloonDto.getEmail());
                saloon.setCity(saloonDto.getCity());
                saloon.setOwnerId(userDto.getId());
                saloon.setOpenTime(saloonDto.getOpenTime());
                saloon.setCloseTime(saloonDto.getCloseTime());
                return repository.save(saloon);
            }
            logger.info("Please provide valid data.");
        } catch (Exception e) {
            logger.error("Exception while adding new saloon details");
        }
        return null;
    }

    @Override
    public Saloon updateSaloonDetails(SaloonDTO saloonDto, UserDTO userDto, Long saloonId) {
        Saloon saloon = repository.findById(saloonId).orElse(null);
        if (saloon != null && saloon.getId().equals(saloonId)) {
            logger.info("Saloon details updating starts for saloonId: {}", saloonDto);
            Optional.ofNullable(saloonDto.getName()).ifPresent(saloon::setName);
            Optional.ofNullable(saloonDto.getImages()).ifPresent(saloon::setImages);
            Optional.ofNullable(saloonDto.getAddress()).ifPresent(saloon::setAddress);
            Optional.ofNullable(saloonDto.getMobile()).ifPresent(saloon::setMobile);
            Optional.ofNullable(saloonDto.getEmail()).ifPresent(saloon::setEmail);
            Optional.ofNullable(saloonDto.getCity()).ifPresent(saloon::setCity);
            Optional.ofNullable(saloonDto.getId()).ifPresent(saloon::setOwnerId);
            Optional.ofNullable(saloonDto.getOpenTime()).ifPresent(saloon::setOpenTime);
            Optional.ofNullable(saloonDto.getCloseTime()).ifPresent(saloon::setCloseTime);
            return repository.save(saloon);
        }
        logger.info("There are no saloon details found with saloonId: {}", saloonId);
        return null;
    }

    @Override
    public List<Saloon> getAllSaloonDetails() {
        return repository.findAll();
    }

    @Override
    public Saloon getSaloonDetailById(Long id) {
        return repository.findById(id).orElse(null);
    }


    @Override
    public List<Saloon> getSaloonByOwnerId(Long ownerId) {
        return repository.findByOwnerId(ownerId);
    }

    @Override
    public List<Saloon> getSaloonByCityName(String city) {
        return repository.getSalonDetailsBasedOnRequiredData(city);
    }
}
