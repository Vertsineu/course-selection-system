package cn.ustc.courseselectionsystem.service.impl;

import cn.ustc.courseselectionsystem.mapper.QueryClassMapper;
import cn.ustc.courseselectionsystem.mapper.QueryStudentMapper;
import cn.ustc.courseselectionsystem.model.param.QueryClassMapperParam;
import cn.ustc.courseselectionsystem.model.param.QueryClassParam;
import cn.ustc.courseselectionsystem.model.po.*;
import cn.ustc.courseselectionsystem.model.vo.CourseWithClassListVO;
import cn.ustc.courseselectionsystem.model.vo.StudentInfoVO;
import cn.ustc.courseselectionsystem.service.QueryService;
import cn.ustc.courseselectionsystem.util.DynamicMapUtil;
import cn.ustc.courseselectionsystem.util.MapUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 查询服务实现类
 */
@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {

    /**
     * 查询课程数据库相关操作类
     */
    private final QueryClassMapper queryClassMapper;
    /**
     * 查询学生数据库相关操作类
     */
    private final QueryStudentMapper queryStudentMapper;

    /**
     * 动态工具类
     */
    private final DynamicMapUtil dynamicMapUtil;

    /**
     * 查询课程
     * @param queryClassParam 查询课程参数
     * @param username 学生学号
     * @return 课程列表
     */
    @Transactional(readOnly = true)
    @Override
    public CourseWithClassListVO forClass(QueryClassParam queryClassParam, String username) {
        QueryClassMapperParam param = MapUtil.mapToQueryClassMapperParam(queryClassParam);
        return dynamicMapUtil.mapToCourseWithClassListVO(queryClassMapper.queryClassByParam(param), username);
    }

    /**
     * 查询学生信息
     * @param username 学生学号
     * @return 学生信息
     */
    @Transactional(readOnly = true)
    @Override
    public StudentInfoVO forStudentInfo(String username) {
        StudentInfoPO studentInfoPO = queryStudentMapper.queryStudentInfoByNumber(username);
        return MapUtil.mapToStudentInfoVO(studentInfoPO);
    }
}
