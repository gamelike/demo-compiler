package org.example.pojo.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author violet
 */
@Getter
@Setter
@Accessors(chain = true)
public class GuideVo {

    private String message;

    private String tabName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        GuideVo guideVo = (GuideVo) o;

        return new EqualsBuilder().append(tabName, guideVo.tabName).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(tabName).toHashCode();
    }
}
