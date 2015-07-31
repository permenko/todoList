package com.example.yury.todolist.json;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "taskName",
        "taskStatus",
        "_links"
})
public class Task {

    @JsonProperty("taskName")
    private String taskName;
    @JsonProperty("taskStatus")
    private Boolean taskStatus;
    @JsonProperty("_links")
    private Links_ Links;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The taskName
     */
    @JsonProperty("taskName")
    public String getTaskName() {
        return taskName;
    }

    /**
     *
     * @param taskName
     * The taskName
     */
    @JsonProperty("taskName")
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     *
     * @return
     * The taskStatus
     */
    @JsonProperty("taskStatus")
    public Boolean getTaskStatus() {
        return taskStatus;
    }

    /**
     *
     * @param taskStatus
     * The taskStatus
     */
    @JsonProperty("taskStatus")
    public void setTaskStatus(Boolean taskStatus) {
        this.taskStatus = taskStatus;
    }

    /**
     *
     * @return
     * The Links
     */
    @JsonProperty("_links")
    public Links_ getLinks() {
        return Links;
    }

    /**
     *
     * @param Links
     * The _links
     */
    @JsonProperty("_links")
    public void setLinks(Links_ Links) {
        this.Links = Links;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        }

}