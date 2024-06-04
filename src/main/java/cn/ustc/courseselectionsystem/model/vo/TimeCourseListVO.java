package cn.ustc.courseselectionsystem.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TimeCourseListVO {
    private List<CourseVO> timeCourseList;
}
