package com.glowhouse.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data@NoArgsConstructor@AllArgsConstructor
public class ExceptionResponse {
    private String message;
    private String error;
    private LocalDateTime time;
}
