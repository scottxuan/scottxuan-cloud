package com.scottxuan.base.pair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : scottxuan
 */
@Data
@AllArgsConstructor
public class Pair<T1,T2> implements Serializable {
    private static final long serialVersionUID = -8553839377421958791L;

    private T1 code;
    private T2 value;
}
