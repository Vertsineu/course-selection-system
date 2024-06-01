package cn.ustc.courseselectionsystem.controller;

import cn.ustc.courseselectionsystem.model.param.QueryClassParam;
import cn.ustc.courseselectionsystem.service.QueryClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import cn.ustc.courseselectionsystem.model.vo.CourseWithClassListVO;
import cn.ustc.courseselectionsystem.rsp.R;
import cn.ustc.courseselectionsystem.rsp.RUtils;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/query")
public class QueryClassController {

    private final QueryClassService queryClassService;

    @PostMapping("/forClass")
    public R<CourseWithClassListVO> forClass(@RequestBody QueryClassParam queryClassParam) {
        return RUtils.success(queryClassService.forClass(queryClassParam));
    }

}
