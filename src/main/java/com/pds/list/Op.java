package com.pds.list;

/**
 * Created by alexander on 13.12.14.
 */
final class Op {
    private final OpType type;
    private final OpFiller filler;
    private final Object params;

    Op(OpType type, OpFiller filler, Object params) {
        this.type = type;
        this.filler = filler;
        this.params = params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Op op = (Op) o;

        if (type != op.type) return false;
        if (!filler.isEqual(params, op.params)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + filler.hash(params);
        return result;
    }
}
