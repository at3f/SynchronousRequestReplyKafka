package com.example.SynchronousRequestReplyKafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({
        "name",
        "age"
})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reply {

    @JsonProperty("Message")
    private String message;
}
