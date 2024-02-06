package noemipusceddu.Capstone_be.payloads.reservation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.aspectj.bridge.IMessage;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationDTO(
        @NotEmpty(message = "This field cannot be empty")
        @NotNull(message = "This field cannot be null")
        String email,
        @NotEmpty(message = "This field cannot be empty")
        @NotNull(message = "This field cannot be null")
        String name,
        @NotEmpty(message = "This field cannot be empty")
        @NotNull(message = "This field cannot be null")
        String surname,
        String text,
        @NotNull(message = "This field cannot be null")
        Long phoneNumber,
        @NotNull(message = "This field cannot be null")
        int people,
        @NotNull(message= "This field cannot be null")
        UUID visit_id
        ) {
}
