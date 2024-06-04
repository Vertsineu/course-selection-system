package cn.ustc.courseselectionsystem.model.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 课程（包含课堂）的列表类
 */
@Data
@AllArgsConstructor
public class CourseWithClassListVO {
    /**
     * 课程（包含课堂）的列表
     */
    private List<CourseVO> courseList;
}
