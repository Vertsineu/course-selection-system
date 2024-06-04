package cn.ustc.courseselectionsystem.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class TimeSetVO {
    private Set<Integer> timeSet;
}
