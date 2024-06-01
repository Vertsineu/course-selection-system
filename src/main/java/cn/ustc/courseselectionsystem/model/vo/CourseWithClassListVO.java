package cn.ustc.courseselectionsystem.model.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseWithClassListVO {
    private List<CourseVO> courseList;
}
