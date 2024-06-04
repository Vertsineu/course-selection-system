package cn.ustc.courseselectionsystem.model.tuple;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 选中情况类
 */
@Data
@AllArgsConstructor
public class SelectStateTuple {
    /**
     * 选中该课的人数
     */
    private Integer selectedCount;
    /**
     * 是否选中该课
     */
    private Boolean selected;
}
