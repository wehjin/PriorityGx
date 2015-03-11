package com.rubyhuntersky.gx.support;

/**
 * @author wehjin
 * @since 2/9/15.
 */

public class EditorStatus<T> {

    public final T value;
    public final boolean isNewValue;
    public final boolean isCancelled;
    public final boolean isDone;

    public EditorStatus(T value, boolean isNewValue) {
        this.value = value;
        this.isNewValue = isNewValue;
        this.isCancelled = false;
        this.isDone = false;
    }

    public EditorStatus(EditorStatus<T> editorStatus) {
        this.value = editorStatus.value;
        this.isNewValue = editorStatus.isNewValue;
        this.isCancelled = editorStatus.isCancelled;
        this.isDone = editorStatus.isDone;
    }

    private EditorStatus(T value, boolean isNewValue, boolean isCancelled, boolean isDone) {
        this.value = value;
        this.isNewValue = isNewValue;
        this.isCancelled = isCancelled;
        this.isDone = isDone;
    }

    public EditorStatus<T> withValue(T value) {
        return new EditorStatus<>(value, isNewValue, isCancelled, isDone);
    }

    public EditorStatus<T> withDone(boolean done) {
        return new EditorStatus<>(value, isNewValue, isCancelled, done);
    }

    @SuppressWarnings("unchecked")
    public <T2> EditorStatus<T2> withCast(Class<T2> castClass) {
        return (EditorStatus<T2>) this;
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EditorStatus)) return false;

        EditorStatus that = (EditorStatus) o;

        if (isCancelled != that.isCancelled) return false;
        if (isDone != that.isDone) return false;
        if (isNewValue != that.isNewValue) return false;
        if (!value.equals(that.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + (isNewValue ? 1 : 0);
        result = 31 * result + (isCancelled ? 1 : 0);
        result = 31 * result + (isDone ? 1 : 0);
        return result;
    }
}
