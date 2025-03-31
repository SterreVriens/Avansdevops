package project.domain.sprint;

import java.util.Date;

public class Sprint {
    private String name;
    private Date startDate;
    private Date endDate;

    public Sprint(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
