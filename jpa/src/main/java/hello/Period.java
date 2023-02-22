package hello;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable //값타입 명시
public class Period {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    //해당 값만 사용하는 매소드를 생성가능
    public boolean isWork(){
        return false;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
