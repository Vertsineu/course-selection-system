package cn.ustc.courseselectionsystem.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class TimeCourseMapVO {
    private Map<Integer, CourseVO> timeCourseMap;
}
