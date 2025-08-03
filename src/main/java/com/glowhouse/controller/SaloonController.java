package com.glowhouse.controller;

import com.glowhouse.dto.request.SaloonDTO;
import com.glowhouse.dto.request.UserDTO;
import com.glowhouse.entity.Saloon;
import com.glowhouse.mapper.SaloonMapper;
import com.glowhouse.service.SaloonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/warehouse")
@RequiredArgsConstructor
public class SaloonController {

    private static final Logger logger = LoggerFactory.getLogger(SaloonController.class);

    private final SaloonService service;

    @PostMapping("/addNewDetails")
    public ResponseEntity<?> addNewSaloonDetails (
            @RequestBody SaloonDTO saloonDto) {

        try {
            logger.info("addNewSaloonDetails: new details: {}", saloonDto);
            UserDTO userDto = new UserDTO();
            userDto.setId(1L);
            Saloon saloonDetails = service.addNewSaloonDetails(saloonDto, userDto);
            if (saloonDetails != null) {
                SaloonDTO response = SaloonMapper.mapToDto(saloonDetails);
                logger.info("addNewSaloonDetails: adding new details ends.");
                return ResponseEntity.ok(response);
            } else {
                logger.warn("addNewSaloonDetails: No saloon details returned from service.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No saloon details were created or returned.");
            }

        } catch (Exception e) {
            logger.error("Error while adding new saloon details: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to add saloon details");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/updateDetails/{id}")
    public ResponseEntity<?> updateSaloonDetails (
            @PathVariable ("id") Long saloonId,
            @RequestBody SaloonDTO saloonDto) {

        try {
            logger.info("updateSaloonDetails: update details: {}", saloonDto);
            UserDTO userDto = new UserDTO();
            userDto.setId(1L);
            Saloon saloonDetails = service.updateSaloonDetails(saloonDto, userDto, saloonId);
            if (saloonDetails != null) {
                SaloonDTO saloonDTO = SaloonMapper.mapToDto(saloonDetails);
                logger.info("updateSaloonDetails: update details ends.");
                return ResponseEntity.ok(saloonDTO);
            } else {
                logger.warn("updateSaloonDetails:: No saloon details are updated for saloonId: {}", saloonId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No saloon details updated for salonId: " + saloonId);
            }
        } catch (Exception e) {
            logger.error("Exception while updating the saloon details for saloonId: {}, {}", saloonId, e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to update saloon details for saloonId: " + saloonId);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/getAllSaloonDetails")
    public ResponseEntity<?> getAllSaloonDetails () {
        try {
            logger.info("Api call to get all the saloon details.");
            List<Saloon> allSaloonDetails = service.getAllSaloonDetails();
            logger.info("The count of saloons are: {}", (long) allSaloonDetails.size());
            if (allSaloonDetails != null) {
                List<SaloonDTO> saloonDTOList = allSaloonDetails.stream()
                        .map(SaloonMapper::mapToDto).toList();
                return ResponseEntity.ok(saloonDTOList);
            } else {
                logger.warn("getAllSaloonDetails:: No saloon details are found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No saloon details found");
            }
        } catch (Exception e) {
            logger.error("Exception while getting all saloon details: {} ", e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to get all saloon details");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/saloonDetailsById/{id}")
    public ResponseEntity<?> getSaloonDetails (
            @PathVariable ("id") Long saloonId) {

        try {
            logger.info("Getting all the details for owenrId: {}",saloonId);
            Saloon saloonDetails = service.getSaloonDetailById(saloonId);
            if (saloonDetails != null) {
                SaloonDTO saloonDTO = SaloonMapper.mapToDto(saloonDetails);
                return ResponseEntity.ok(saloonDTO);
            } else {
                logger.warn("getSaloonDetails:: No saloon details are found for saloonId: {}", saloonId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No saloon details found for salonId: " + saloonId);
            }
        } catch (Exception e) {
            logger.error("Exception while getting saloon details for the saloonId: {}, {}",saloonId, e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to get saloon details for saloonId: "+saloonId);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/searchSaloonsByCity")
    public ResponseEntity<?> searchSaloon (
            @RequestParam ("city") String city) {

        try {
            logger.info("Getting all the saloon near: {}", city);
            List<Saloon> saloonByCityNames = service.getSaloonByCityName(city);
            if (saloonByCityNames != null) {
                List<SaloonDTO> saloonDTOs = saloonByCityNames.stream()
                        .map(SaloonMapper::mapToDto)
                        .toList();
                return ResponseEntity.ok(saloonDTOs);
            } else {
                logger.warn("getSaloonDetails:: No saloon details are found for city: {}", city);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No saloon details found for city: " + city);
            }

        } catch (Exception e) {
            logger.error("Exception while getting all the saloon details on the city: {}, {}", city, e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to get saloon details for city: "+city);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/getSaloonDetailsByOwnerId")
    public ResponseEntity<?> getSaloonDetailsByOwnerId () {
        try {
            UserDTO userDto = new UserDTO();
            userDto.setId(1L);
            logger.info("getSaloonDetailsByOwnerId:: getSaloonDetailsByOwnerId method starts for: {}", userDto.getId());
            List<Saloon> saloonDetails = service.getSaloonByOwnerId(userDto.getId());
            if (saloonDetails != null) {
                List<SaloonDTO> saloonDTOS = saloonDetails.stream()
                        .map(SaloonMapper::mapToDto)
                        .toList();
                return ResponseEntity.ok(saloonDTOS);
            } else {
                logger.warn("getSaloonDetails:: No saloon details are found for ownerId: {}", userDto.getId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No saloon details found for ownerId: " + userDto.getId());
            }
        } catch (Exception e) {
            logger.error("Exception while getSaloonDetailsByOwnerId: {}", e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to get saloon details");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
