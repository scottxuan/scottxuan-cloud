package com.scottxuan.base.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author : zhaoxuan
 */
@JsonIgnoreProperties({"isDeleted", "password", "lastUpdateTime","salt"})
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 5811553969966107290L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
