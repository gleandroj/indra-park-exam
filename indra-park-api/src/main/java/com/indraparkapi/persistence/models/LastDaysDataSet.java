package com.indraparkapi.persistence.models;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

public interface LastDaysDataSet extends Serializable {

    public String getEnteredAt();

    @JsonRawValue()
    public JsonNode getTypes();
}
