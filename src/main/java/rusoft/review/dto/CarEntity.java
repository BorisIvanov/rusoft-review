package rusoft.review.dto;

import java.time.LocalDate;

public class CarEntity extends ClientRemoveRequest implements Car {
    private LocalDate prodDate;

    @Override
    public LocalDate getProdDate() {
        return prodDate;
    }

    public void setProdDate(LocalDate prodDate) {
        this.prodDate = prodDate;
    }
}
