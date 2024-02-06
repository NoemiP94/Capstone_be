package noemipusceddu.Capstone_be.payloads.blogpost;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BlogpostDTO(
        @NotNull(message = "This field cannot be null")
        @NotEmpty(message="This field cannot be empty")
        String title,
        @NotNull(message = "This field cannot be null")
        @NotEmpty(message="This field cannot be empty")
        String content,
        @NotNull(message = "This field cannot be null")
        LocalDate date) {
}
