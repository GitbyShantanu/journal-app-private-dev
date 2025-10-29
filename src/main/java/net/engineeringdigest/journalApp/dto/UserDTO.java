package net.engineeringdigest.journalApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userName;
    private String password;
    private String email;
    private boolean sentimentAnalysis;

    // roles not needed in DTO, service will hardcode
}
