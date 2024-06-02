package cn.ustc.courseselectionsystem.controller;

import cn.ustc.courseselectionsystem.model.param.QueryClassParam;
import cn.ustc.courseselectionsystem.model.vo.StudentInfoVO;
import cn.ustc.courseselectionsystem.service.QueryService;
import cn.ustc.courseselectionsystem.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import cn.ustc.courseselectionsystem.model.vo.CourseWithClassListVO;
import cn.ustc.courseselectionsystem.rsp.R;
import cn.ustc.courseselectionsystem.rsp.RUtils;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/query")
public class QueryController {

    private final QueryService queryService;
    private final TokenUtil tokenUtil;

    @PostMapping("/forClass")
    public R<CourseWithClassListVO> forClass(@RequestBody QueryClassParam queryClassParam, @CookieValue("token") String token) {
        return RUtils.success(queryService.forClass(queryClassParam, tokenUtil.parseToken(token).get("username")));
    }

    @GetMapping("/forStudentInfo")
    public R<StudentInfoVO> forStudentInfo(@CookieValue("token") String token) {
        return RUtils.success(queryService.forStudentInfo(tokenUtil.parseToken(token).get("username")));
    }

}
