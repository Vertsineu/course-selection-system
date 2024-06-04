package cn.ustc.courseselectionsystem.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class TimeSetVo {
    private Set<Integer> timeSet;
}
