package noemipusceddu.Capstone_be.payloads.exception;

import java.time.LocalDateTime;

public record ErrorDTO(String message, LocalDateTime timestamp) {
}
