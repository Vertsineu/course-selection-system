package cn.ustc.courseselectionsystem.model.po;

import lombok.Data;

@Data
public class TpcPO {
    private Integer classId;

    private String timeWeek;
    private String timeDay;
    private String timePeriod;
    private String place;
    private Integer teacherId;
}
