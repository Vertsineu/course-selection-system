package cn.ustc.courseselectionsystem.model.po;

import lombok.Data;

/**
 * 时间地点人物类
 */
@Data
public class TpcPO {
    /**
     * 课堂id
     */
    private Integer classId;

    /**
     * 哪一周
     */
    private String timeWeek;
    /**
     * 哪一天
     */
    private String timeDay;
    /**
     * 哪一个时间段
     */
    private String timePeriod;
    /**
     * 地点
     */
    private String place;
    /**
     * 教师id
     */
    private Integer teacherId;
}
