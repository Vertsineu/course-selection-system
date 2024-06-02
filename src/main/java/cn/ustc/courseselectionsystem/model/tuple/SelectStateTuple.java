package cn.ustc.courseselectionsystem.model.tuple;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SelectStateTuple {
    private Integer selectedCount;
    private Boolean selected;
}
