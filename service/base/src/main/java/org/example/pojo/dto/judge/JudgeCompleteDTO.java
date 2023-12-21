package org.example.pojo.dto.judge;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.Set;

/**
 * @author violet
 */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public final class JudgeCompleteDTO {

    @NonNull
    final String id;
    final Set<String> name;
    final Set<String> sourceIps;
    final Set<String> destinationIps;
    final boolean createTask;
    final int result;

    final String suggestion;

    final List<String> influence;
    final List<String> monitorIpList;


    @JsonCreator
    public JudgeCompleteDTO(@JsonProperty("id") @NonNull String id,
                            @JsonProperty("name") Set<String> name,
                            @JsonProperty("sourceIps") Set<String> sourceIps,
                            @JsonProperty("destinationIps") Set<String> destinationIps,
                            @JsonProperty("createTask") boolean createTask,
                            @JsonProperty("result") int result,
                            @JsonProperty("suggestion") String suggestion,
                            @JsonProperty("influence") List<String> influence,
                            @JsonProperty("monitorIp") List<String> monitorIpList) {
        this.id = id;
        this.name = name;
        this.sourceIps = sourceIps;
        this.destinationIps = destinationIps;
        this.createTask = createTask;
        this.result = result;
        this.suggestion = suggestion;
        this.influence = influence;
        this.monitorIpList = monitorIpList;
    }
}
