package scottxuan.cloud.base.result;

import scottxuan.cloud.base.error.ErrorCodes;
import scottxuan.cloud.base.error.IError;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author : scottxuan
 */
public final class ResultBo<T> implements IResult<T> {

    private static final ResultBo<?> EMPTY = new ResultBo<>();
    private final T value;
    private IError error;
    private Object[] args;

    private ResultBo() {
        this.error = ErrorCodes.OPERATE_SUCCESS;
        this.value = null;
    }

    private ResultBo(T value) {
        this.error = ErrorCodes.OPERATE_SUCCESS;
        this.value = value;
    }

    private ResultBo(IError error, Object... args) {
        this.value = null;
        this.error = error;
        this.args = args;
    }

    public static <T> ResultBo<T> empty() {
        @SuppressWarnings("unchecked")
        ResultBo<T> t = (ResultBo<T>) EMPTY;
        return t;
    }

    public static <T> ResultBo<T> of(T value) {
        return new ResultBo<>(value);
    }

    public static <T> ResultBo<T> of(IError error) {
        return new ResultBo<T>(error);
    }

    public static <T> ResultBo<T> of(IError error, Object... args) {
        return new ResultBo<>(error, args);
    }

    public static <T> ResultBo<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    public boolean isPresent() {
        return value != null;
    }

    public boolean isNotPresent() {
        return !isPresent();
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

    public ResultBo<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return this;
        } else {
            return predicate.test(value) ? this : empty();
        }
    }

    public <U> ResultBo<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty();
        } else {
            return ResultBo.ofNullable(mapper.apply(value));
        }
    }

    public <U> ResultBo<U> flatMap(Function<? super T, ResultBo<U>> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty();
        } else {
            return Objects.requireNonNull(mapper.apply(value));
        }
    }

    public T orElse(T other) {
        return value != null ? value : other;
    }

    public T orElseGet(Supplier<? extends T> other) {
        return value != null ? value : other.get();
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ResultBo)) {
            return false;
        }

        ResultBo<?> other = (ResultBo<?>) obj;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value != null
                ? String.format("ResultBo[%s]", value)
                : "ResultBo.empty";
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public IError getError() {
        return error;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    @Override
    public boolean isSuccess() {
        if (this.error == null) {
            return Boolean.TRUE;
        } else {
            return this.error.getCode() == 200;
        }
    }
}
