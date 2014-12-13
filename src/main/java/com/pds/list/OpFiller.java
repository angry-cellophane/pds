package com.pds.list;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by alexander on 13.12.14.
 */
enum OpFiller {

    SINGLE {
        @Override
        int hash(Object params) {
            if (params == null) return 0;
            return params.hashCode();
        }

        @Override
        boolean isEqual(Object myParams, Object thatParams) {
            if (myParams == null ^ thatParams == null) return false;
            return myParams == thatParams || myParams.equals(thatParams);
        }
    },
    ARRAY {
        @Override
        int hash(Object params) {
            return Arrays.hashCode((Object[]) params);
        }

        @Override
        boolean isEqual(Object myParams, Object thatParams) {
            return Arrays.equals((Object[])myParams, (Object[]) thatParams);
        }
    },
    NON {
        @Override
        int hash(Object params) {
            return 0;
        }

        @Override
        boolean isEqual(Object myParams, Object thatParams) {
            return myParams == null && myParams == thatParams;
        }
    };

    abstract int hash(Object params);
    abstract boolean isEqual(Object myParams, Object thatParams);
}
