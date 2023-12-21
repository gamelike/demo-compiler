package org.example.model.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.Objects;
import lombok.Builder;
import lombok.Data;

/**
 * redis storage tool box title.
 *
 * @author violet
 * @since 2022/12/27
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
@Data
@Builder
public class ToolBoxStatisticVo {
    private String name;
    @JsonAlias("mark_total")
    private long markTotal;
    private long total;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToolBoxStatisticVo that = (ToolBoxStatisticVo) o;
        return Objects.equal(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }
}
