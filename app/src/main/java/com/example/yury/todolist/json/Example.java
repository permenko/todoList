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
        "_links",
        "_embedded",
        "page"
})
public class Example {

    @JsonProperty("_links")
    private com.example.yury.todolist.json.Links Links;
    @JsonProperty("_embedded")
    private com.example.yury.todolist.json.Embedded Embedded;
    @JsonProperty("page")
    private Page page;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The Links
     */
    @JsonProperty("_links")
    public com.example.yury.todolist.json.Links getLinks() {
        return Links;
    }

    /**
     *
     * @param Links
     * The _links
     */
    @JsonProperty("_links")
    public void setLinks(com.example.yury.todolist.json.Links Links) {
        this.Links = Links;
    }

    /**
     *
     * @return
     * The Embedded
     */
    @JsonProperty("_embedded")
    public com.example.yury.todolist.json.Embedded getEmbedded() {
        return Embedded;
    }

    /**
     *
     * @param Embedded
     * The _embedded
     */
    @JsonProperty("_embedded")
    public void setEmbedded(com.example.yury.todolist.json.Embedded Embedded) {
        this.Embedded = Embedded;
    }

    /**
     *
     * @return
     * The page
     */
    @JsonProperty("page")
    public Page getPage() {
        return page;
    }

    /**
     *
     * @param page
     * The page
     */
    @JsonProperty("page")
    public void setPage(Page page) {
        this.page = page;
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