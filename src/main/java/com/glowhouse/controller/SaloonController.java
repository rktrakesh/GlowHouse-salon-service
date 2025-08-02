package com.glowhouse.controller;

import com.glowhouse.dto.request.SaloonDTO;
import com.glowhouse.dto.request.UserDTO;
import com.glowhouse.entity.Saloon;
import com.glowhouse.mapper.SaloonMapper;
import com.glowhouse.service.SaloonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
@RequiredArgsConstructor
public class SaloonController {

    private static final Logger logger = LoggerFactory.getLogger(SaloonController.class);

    private final SaloonService service;

    @PostMapping("/addNewDetails")
    public ResponseEntity<SaloonDTO> addNewSaloonDetails (
            @RequestBody SaloonDTO saloonDto) {

        try {
            logger.info("addNewSaloonDetails: new details: {}", saloonDto);
            UserDTO userDto = new UserDTO();
            userDto.setId(1L);
            Saloon saloonDetails = service.addNewSaloonDetails(saloonDto, userDto);
            SaloonDTO response = new SaloonDTO();
            if (saloonDetails != null) {
                response = SaloonMapper.mapToDto(saloonDetails);
            }
            logger.info("addNewSaloonDetails: adding new details ends.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error while adding new saloon details: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updateDetails/{id}")
    public ResponseEntity<SaloonDTO> updateSaloonDetails (
            @PathVariable ("id") Long saloonId,
            @RequestBody SaloonDTO saloonDto) {

        try {
            logger.info("updateSaloonDetails: update details: {}", saloonDto);
            UserDTO userDto = new UserDTO();
            userDto.setId(1L);
            Saloon saloonDetails = service.updateSaloonDetails(saloonDto, userDto, saloonId);
            SaloonDTO saloonDTO = new SaloonDTO();
            if (saloonDetails != null) {
                saloonDTO = SaloonMapper.mapToDto(saloonDetails);
            }
            logger.info("updateSaloonDetails: update details ends.");
            return ResponseEntity.ok(saloonDTO);
        } catch (Exception e) {
            logger.error("Exception while updating the saloon details for saloonId: {}, {}", saloonId, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getAllSaloonDetails")
    public ResponseEntity<List<SaloonDTO>> getAllSaloonDetails () {
        try {
            logger.info("Api call to get all the saloon details.");
            List<Saloon> allSaloonDetails = service.getAllSaloonDetails();
            logger.info("The count of saloons are: {}", (long) allSaloonDetails.size());
            List<SaloonDTO> saloonDTOList = new ArrayList<>();
            if (allSaloonDetails != null) {
                saloonDTOList = allSaloonDetails.stream()
                        .map(SaloonMapper::mapToDto).toList();
            }
            return ResponseEntity.ok(saloonDTOList);
        } catch (Exception e) {
            logger.error("Exception while getting all saloon details: ", e.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/saloonDetailsById/{id}")
    public ResponseEntity<SaloonDTO> getSaloonDetails (
            @PathVariable ("id") Long saloonId) {

        try {
            logger.info("Getting all the details for owenrId: {}",saloonId);
            Saloon saloonDetails = service.getSaloonDetailById(saloonId);
            SaloonDTO saloonDTO = new SaloonDTO();
            if (saloonDetails != null) {
                saloonDTO = SaloonMapper.mapToDto(saloonDetails);
            }
            return ResponseEntity.ok(saloonDTO);
        } catch (Exception e) {
            logger.error("Exception while getting saloon details for the saloonId: {}, {}",saloonId, e.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/searchSaloonsByCity")
    public ResponseEntity<List<SaloonDTO>> searchSaloon (
            @RequestParam ("city") String city) {

        try {
            logger.info("Getting all the saloon near: {}", city);
            List<Saloon> saloonByCityNames = service.getSaloonByCityName(city);
            List<SaloonDTO> saloonDTOs = new ArrayList<>();
            if (saloonByCityNames != null) {
                saloonDTOs = saloonByCityNames.stream()
                        .map(SaloonMapper::mapToDto)
                        .toList();
            }
            return ResponseEntity.ok(saloonDTOs);
        } catch (Exception e) {
            logger.error("Exception while getting all the saloon details on the city: {}, {}", city, e.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getSaloonDetailsByOwnerId")
    public ResponseEntity<List<SaloonDTO>> getSaloonDetailsByOwnerId () {
        try {
            UserDTO userDto = new UserDTO();
            userDto.setId(1L);
            logger.info("getSaloonDetailsByOwnerId:: getSaloonDetailsByOwnerId method starts for: {}", userDto.getId());
            List<Saloon> saloonDetails = service.getSaloonByOwnerId(userDto.getId());
            List<SaloonDTO> saloonDTOS = new ArrayList<>();
            if (saloonDetails != null) {
                saloonDTOS = saloonDetails.stream()
                        .map(SaloonMapper::mapToDto)
                        .toList();
            }
            return ResponseEntity.ok(saloonDTOS);
        } catch (Exception e) {
            logger.error("Exception while getSaloonDetailsByOwnerId: {}", e.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

}
