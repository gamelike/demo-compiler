package org.example.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * description:
 *
 * @author zhao_yifan
 * @since 2022/5/7
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@SuperBuilder
public class EvidenceDTO {

    private String evidenceId;

    private Integer evidenceType;

    private String evidenceDesc;

    private String eventType;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonAlias("TIME")
    private String eventTime;
    private String keyFeature;
    private boolean selected;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvidenceDTO that = (EvidenceDTO) o;
        return Objects.equal(getEvidenceId(), that.getEvidenceId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEvidenceId());
    }
}
