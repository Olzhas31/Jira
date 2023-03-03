package com.example.Jira.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateHubRequest {

    private String name;
    private String content;
    private Long projectId;

    @Override
    public String toString() {
        return "CreateHubRequest{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", projectId=" + projectId +
                '}';
    }
}
