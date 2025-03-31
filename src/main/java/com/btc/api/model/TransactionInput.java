package com.btc.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionInput {

    @JsonProperty("script")
    String script;
    @JsonProperty("sequence")
    long sequence;
    @JsonProperty("prev_out")
    PreviousOutput previousOutput;

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public PreviousOutput getPreviousOutput() {
        return previousOutput;
    }

    public void setPreviousOutput(PreviousOutput previousOutput) {
        this.previousOutput = previousOutput;
    }
}
