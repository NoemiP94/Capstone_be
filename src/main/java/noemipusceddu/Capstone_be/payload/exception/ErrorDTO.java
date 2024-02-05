package noemipusceddu.Capstone_be.payload.exception;

import java.time.LocalDateTime;

public record ErrorDTO(String message, LocalDateTime timestamp) {
}
