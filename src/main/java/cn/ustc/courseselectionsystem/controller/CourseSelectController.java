package cn.ustc.courseselectionsystem.controller;

import cn.ustc.courseselectionsystem.model.vo.ClassVO;
import cn.ustc.courseselectionsystem.model.vo.CourseWithClassListVO;
import cn.ustc.courseselectionsystem.model.vo.TimeCourseMapVO;
import cn.ustc.courseselectionsystem.rsp.None;
import cn.ustc.courseselectionsystem.rsp.R;
import cn.ustc.courseselectionsystem.rsp.RUtils;
import cn.ustc.courseselectionsystem.service.CourseSelectService;
import cn.ustc.courseselectionsystem.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/course")
public class CourseSelectController {

    private final CourseSelectService courseSelectService;
    private final TokenUtil tokenUtil;

    @GetMapping("/timeSet")
    public R<TimeCourseMapVO> timeSet(@CookieValue("token") String token) {
        return RUtils.success(courseSelectService.timeSelectedCourseMap(tokenUtil.parseToken(token).get("username")));
    }

    /**
     * 获取所有已选课程
     * @return 所有已选课程
     */
    @GetMapping("/checked")
    public R<CourseWithClassListVO> checked(@CookieValue("token") String token) {
        return RUtils.success(courseSelectService.checkedClasses(tokenUtil.parseToken(token).get("username")));
    }

    /**
     * 选择课程
     * @param classVO 要选择的课程的id
     * @return 是否选课成功
     */
    @PostMapping("/select")
    public R<None> select(@RequestBody ClassVO classVO, @CookieValue("token") String token) {
        courseSelectService.selectClass(classVO.getId(), tokenUtil.parseToken(token).get("username"));
        return RUtils.success("选课成功");
    }

    /**
     * 删除课程
     * @param classVO 要删除的课程的id
     * @return 是否删课成功
     */
    @DeleteMapping("/delete")
    public R<None> delete(@RequestBody ClassVO classVO, @CookieValue("token") String token) {
        courseSelectService.deleteClass(classVO.getId(), tokenUtil.parseToken(token).get("username"));
        return RUtils.success("删课成功");
    }

}
