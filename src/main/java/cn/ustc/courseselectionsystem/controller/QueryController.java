package cn.ustc.courseselectionsystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ustc.courseselectionsystem.model.vo.CourseWithClassListVO;
import cn.ustc.courseselectionsystem.rsp.R;
import cn.ustc.courseselectionsystem.rsp.RUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/query")
public class QueryController {
    @GetMapping("/forClass")
    public R<CourseWithClassListVO> forClass(@RequestParam String param) {
        try {
            return RUtils.success(new CourseWithClassListVO());
        } catch (Exception e) {

        }
        return null;
    }

}
