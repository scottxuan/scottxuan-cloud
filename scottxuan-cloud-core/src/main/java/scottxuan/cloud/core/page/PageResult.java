package scottxuan.cloud.core.page;

import com.google.common.collect.Lists;
import scottxuan.cloud.base.error.ErrorCodes;
import scottxuan.cloud.base.error.IError;
import scottxuan.cloud.base.page.Page;
import scottxuan.cloud.base.result.IPageResult;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author : zhaoxuan
 */
public class PageResult<T> implements IPageResult {
    private static final PageResult<?> EMPTY = new PageResult<>();

    private final List<T> value;
    private Page page;
    private IError error;
    private Object[] args;

    private PageResult() {
        this.value = Lists.newArrayList();
        this.page = new Page();
        this.error = ErrorCodes.OPERATE_SUCCESS;
    }

    private PageResult(List<T> value) {
        this.value = value;
        this.page = new Page();
        this.error = ErrorCodes.OPERATE_SUCCESS;
    }

    private PageResult(List<T> value, Page page) {
        this.value = value;
        this.page = page;
        this.error = ErrorCodes.OPERATE_SUCCESS;
    }

    private PageResult(IError error) {
        this.value = Lists.newArrayList();
        this.page = new Page();
        this.error = error;
    }

    private PageResult(IError error, Object... args) {
        this.value = Lists.newArrayList();
        this.page = new Page();
        this.error = error;
        this.args = args;
    }

    public static <T> PageResult<T> empty() {
        @SuppressWarnings("unchecked")
        PageResult<T> t = (PageResult<T>) EMPTY;
        return t;
    }

    public static <T> PageResult<T> ofNullable(List<T> value) {
        return value == null ? empty() : of(value);
    }

    public static <T> PageResult<T> of(List<T> value) {
        if (value instanceof com.github.pagehelper.Page) {
            com.github.pagehelper.Page p = (com.github.pagehelper.Page) value;
            Page page = new Page(p.getPageNum(), p.getPageSize(), p.getPages(), p.getTotal());
            return new PageResult<>(value, page);
        }
        return new PageResult<>(value);
    }

    public static <T> PageResult<T> of(List<T> value, Page page) {
        return new PageResult<>(value, page);
    }

    public static <T> PageResult<T> of(IError error) {
        return new PageResult<T>(error);
    }

    public static <T> PageResult<T> of(IError error, Object... args) {
        return new PageResult<T>(error, args);
    }


    public List<T> get() {
        if (this.value == null) {
            throw new NoSuchElementException("No value present");
        } else {
            return this.value;
        }
    }

    public boolean isPresent() {
        return this.value != null && this.value.size() > 0;
    }

    public void ifPresent(Consumer<? super List<T>> consumer) {
        if (value != null)
            consumer.accept(value);
    }

    public PageResult<T> filter(Predicate<? super List<T>> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent())
            return this;
        else
            return predicate.test(value) ? this : empty();
    }

    public <U> PageResult<U> flatMap(Function<? super List<T>, PageResult<U>> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent())
            return empty();
        else {
            return Objects.requireNonNull(mapper.apply(value));
        }
    }

    public List<T> orElse(List<T> other) {
        return value != null ? value : other;
    }

    public List<T> orElseGet(Supplier<? extends List<T>> other) {
        return value != null ? value : other.get();
    }

    public <X extends Throwable> List<T> orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
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

        if (!(obj instanceof PageResult)) {
            return false;
        }

        PageResult<?> other = (PageResult<?>) obj;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value != null
                ? String.format("PageResult[%s]", value)
                : "PageResult.empty";
    }


    @Override
    public List<T> getValue() {
        return value;
    }

    @Override
    public Page getPage() {
        return page;
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
