package noemipusceddu.Capstone_be.payloads.visit;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record VisitDTO(@NotEmpty(message= "This field cannot be empty")
                       @NotNull(message = "This field cannot be null")
                       String description,
                       @NotNull(message = "This field cannot be null")
                       LocalDateTime date,
                       @NotNull(message = "This field cannot be null")
                       int maxPeople) {
}
