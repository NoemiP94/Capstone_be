package noemipusceddu.Capstone_be.payloads.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserDTO(@NotNull(message = "This field cannot be null")
                      @NotEmpty(message= "This field cannot be empty")
                      String name,
                      @NotNull(message = "This field cannot be null")
                      @NotEmpty(message= "This field cannot be empty")
                      String surname,
                      @NotNull(message = "This field cannot be null")
                      @NotEmpty(message= "This field cannot be empty")
                      @Email
                      String email,
                      @NotNull(message = "This field cannot be null")
                      @NotEmpty(message= "This field cannot be empty")
                      @Size(min = 4, max = 15, message = "Password must be between 4 and 15 chars")
                      String password) {
}
