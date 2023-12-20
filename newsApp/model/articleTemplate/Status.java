package newsApp.model.articleTemplate;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "code",
        "message"
})
@Generated("jsonschema2pojo")
public class Status {

    @JsonProperty("code")
    private Integer code;
    @JsonProperty("message")
    private String message;

    public Status(String status) {
        // Parse the status string to initialize the code and message fields
        if (status.equalsIgnoreCase("ok")) {
            this.code = 200;
            this.message = "OK";
        } else {
            this.code = 500;
            this.message = "Error";
        }
    }

    @JsonProperty("code")
    public Integer getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(Integer code) {
        this.code = code;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Status{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}

