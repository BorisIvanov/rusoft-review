package rusoft.review.dto;

import java.time.LocalDate;

public class ClientEntity extends ClientRemoveRequest {
    private LocalDate birthDate;

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
