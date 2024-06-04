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

/**
 * 查询控制层
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/query")
public class QueryController {

    /**
     * 查询服务层对象
     */
    private final QueryService queryService;
    /**
     * Token 工具类
     */
    private final TokenUtil tokenUtil;

    /**
     * 查询课程
     * @param queryClassParam 查询参数
     * @param token 用户token
     * @return 课程列表
     */
    @PostMapping("/forClass")
    public R<CourseWithClassListVO> forClass(@RequestBody QueryClassParam queryClassParam, @CookieValue("token") String token) {
        return RUtils.success(queryService.forClass(queryClassParam, tokenUtil.parseToken(token).get("username")));
    }

    /**
     * 查询学生信息
     * @param token 用户token
     * @return 学生信息
     */
    @GetMapping("/forStudentInfo")
    public R<StudentInfoVO> forStudentInfo(@CookieValue("token") String token) {
        return RUtils.success(queryService.forStudentInfo(tokenUtil.parseToken(token).get("username")));
    }

}
