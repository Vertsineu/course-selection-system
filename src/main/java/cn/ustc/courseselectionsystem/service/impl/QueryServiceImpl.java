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

@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {

    private final QueryClassMapper queryClassMapper;
    private final QueryStudentMapper queryStudentMapper;

    private final DynamicMapUtil dynamicMapUtil;

    @Transactional(readOnly = true)
    @Override
    public CourseWithClassListVO forClass(QueryClassParam queryClassParam, String username) {
        QueryClassMapperParam param = MapUtil.mapToQueryClassMapperParam(queryClassParam);
        return dynamicMapUtil.mapToCourseWithClassListVO(queryClassMapper.queryClassByParam(param), username);
    }

    @Transactional(readOnly = true)
    @Override
    public StudentInfoVO forStudentInfo(String username) {
        StudentInfoPO studentInfoPO = queryStudentMapper.queryStudentInfoByNumber(username);
        return MapUtil.mapToStudentInfoVO(studentInfoPO);
    }
}
