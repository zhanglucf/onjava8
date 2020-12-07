package com.example.onjava8.concurrent;/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.concurrent.Executor;

/*
 * CompletionStage定义了一组接口用于在一个阶段执行结束之后，
 * 要么继续执行下一个阶段，要么对结果进行转换产生新的结果等等，
 * 一般来说要执行下一个阶段都需要上一个阶段正常完成，
 * 当然这个类也提供了对异常结果的处理接口。CompletionStage只定义
 * 了一组基本的接口，其实现类还可据此扩展出更丰富的方法。
 *
 *
方法概述
CompletionStage的接口方法可以从多种角度进行分类，从最宏观的横向划分，CompletionStage的接口主要分三类：

一、产出型：
    就是用上一个阶段的结果作为指定函数的参数执行函数产生新的结果。
    这一类接口方法名中基本都有apply字样，接口的参数是(Bi)Function类型。

二、消费型：
    就是用上一个阶段的结果,作为指定操作的参数,执行指定的操作，
    但不对阶段结果产生影响。这一类接口方法名中基本都有accept字样，接口的参数是(Bi)Consumer类型。

三、不消费也不产出型：
    就是不依据上一个阶段的执行结果，只要上一个阶段完成（但一般要求正常完成），
    就执行指定的操作，且不对阶段的结果产生影响。这一类接口方法名中基本都有run字样，接口的参数是Runnable类型。

还有一组特别的方法带有compose字样，它以依赖阶段本身作为参数而不是阶段产生的结果进行产出型（或函数型）操作。

 * @author Doug Lea
 * @since 1.8
 */
public interface _CompletionStage<T> {

    /**
     * 依赖单个阶段
     * 此阶段正常完成，其结果作为参数应用到function，生成新的CompletionStage
     */

    public <U> _CompletionStage<U> thenApply(Function<? super T, ? extends U> fn);

    /**
     *  依赖单个阶段
     * 使用该阶段异步执行工具执行此阶段，正常完成后，其结果作为参数应用到function，生成新的CompletionStage
     */
    public <U> _CompletionStage<U> thenApplyAsync (Function<? super T, ? extends U> fn);

    /**
     * 依赖单个阶段
     * 使用指定的Executor执行此阶段，正常完成后，其结果作为参数应用到function，生成新的CompletionStage
     */
    public <U> _CompletionStage<U> thenApplyAsync (Function<? super T, ? extends U> fn,Executor executor);

    /**
     * 依赖单个阶段
     * 此阶段正常完成，其结果作为参数应用到function
     * 最后生成新的返回值类型为void的CompletionStage
     */
    public _CompletionStage<Void> thenAccept(Consumer<? super T> action);

    /**
     *  依赖单个阶段
     * 使用该阶段异步执行工具执行此阶段，正常完成后，其结果作为参数应用到function，
     * 最后生成新的返回值类型为void的CompletionStage
     */
    public _CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action);

    /**
     * 依赖单个阶段
     * 使用指定的Executor执行此阶段，正常完成后，其结果作为参数应用到function，
     * 最后生成新的返回值类型为void的CompletionStage
     */
    public _CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action,
                                                  Executor executor);

    /**
     * 依赖单个阶段
     * 此阶段正常完成，其结果作为参数应用到action
     * 最后生成新的返回值类型为void的CompletionStage
     */
    public _CompletionStage<Void> thenRun(Runnable action);

    /**
     *  依赖单个阶段
     * 使用该阶段异步执行工具执行此阶段，正常完成后，其结果作为参数应用到action
     * 最后生成新的返回值类型为void的CompletionStage
     */
    public _CompletionStage<Void> thenRunAsync(Runnable action);

    /**
     * 依赖单个阶段
     * 使用指定的Executor执行此阶段，正常完成后，其结果作为参数应用到action，
     * 最后生成新的返回值类型为void的CompletionStage
     */
    public _CompletionStage<Void> thenRunAsync(Runnable action,
                                               Executor executor);

    /**
     * 依赖两个阶段都正常完成
     * 其结束作为参数应用于function
     * 最后生成新的CompletionStage
     */
    public <U, V> _CompletionStage<V> thenCombine (_CompletionStage<? extends U> other,
     BiFunction<? super T, ? super U, ? extends V> fn);

    /**
     * 依赖两个阶段都正常完成
     * 使用该阶段默认异步执行工具执行当前和另一阶段，
     * 正常完成后，其结果作为参数应用到function，生成新的CompletionStage
     */
    public <U, V> _CompletionStage<V> thenCombineAsync (_CompletionStage<? extends U> other,
     BiFunction<? super T, ? super U, ? extends V> fn);


    /**
     * 依赖两个阶段都正常完成
     * 使用指定的Executor执行执行当前和另一阶段，
     * 正常完成后，其结果作为参数应用到function，生成新的CompletionStage
     */
    public <U, V> _CompletionStage<V> thenCombineAsync (_CompletionStage<? extends U> other,
     BiFunction<? super T, ? super U, ? extends V> fn,
     Executor executor);

    /**
     * 依赖两个阶段都完成
     * 其结束作为参数应用于function
     * 最后生成新的CompletionStage<void>
     */
    public <U> _CompletionStage<Void> thenAcceptBoth (_CompletionStage<? extends U> other,
     BiConsumer<? super T, ? super U> action);

    /**
     * 依赖两个阶段都完成
     * 使用该阶段默认异步执行工具执行当前和另一阶段
     * 其结束作为参数应用于function
     * 最后生成新的CompletionStage<void>
     */
    public <U> _CompletionStage<Void> thenAcceptBothAsync (_CompletionStage<? extends U> other,
     BiConsumer<? super T, ? super U> action);

    /**
     * 依赖两个阶段都完成
     * 使用指定的Executor执行执行当前和另一阶段，     * 其结束作为参数应用于function
     * 最后生成新的CompletionStage<void>
     */
    public <U> _CompletionStage<Void> thenAcceptBothAsync (_CompletionStage<? extends U> other,
     BiConsumer<? super T, ? super U> action,
     Executor executor);

    /**
     * Returns a new _CompletionStage that, when this and the other
     * given stage both complete normally, executes the given action.
     * <p>
     * See the {@link _CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * @param other  the other _CompletionStage
     * @param action the action to perform before completing the
     *               returned _CompletionStage
     * @return the new _CompletionStage
     */
    public _CompletionStage<Void> runAfterBoth(_CompletionStage<?> other,
                                               Runnable action);

    /**
     * Returns a new _CompletionStage that, when this and the other
     * given stage complete normally, executes the given action using
     * this stage's default asynchronous execution facility.
     * <p>
     * See the {@link _CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * @param other  the other _CompletionStage
     * @param action the action to perform before completing the
     *               returned _CompletionStage
     * @return the new _CompletionStage
     */
    public _CompletionStage<Void> runAfterBothAsync(_CompletionStage<?> other,
                                                    Runnable action);

    /**
     * Returns a new _CompletionStage that, when this and the other
     * given stage complete normally, executes the given action using
     * the supplied executor.
     * <p>
     * See the {@link _CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * @param other    the other _CompletionStage
     * @param action   the action to perform before completing the
     *                 returned _CompletionStage
     * @param executor the executor to use for asynchronous execution
     * @return the new _CompletionStage
     */
    public _CompletionStage<Void> runAfterBothAsync(_CompletionStage<?> other,
                                                    Runnable action,
                                                    Executor executor);

    /**
     * 依赖两个阶段中的任何一个完成
     * 其结束作为参数应用于function
     * 最后生成新的CompletionStage
     */
    public <U> _CompletionStage<U> applyToEither (_CompletionStage<? extends T> other,
     Function<? super T, U> fn);

    /**
     * 依赖两个阶段中的任何一个完成（默认异步执行工具）
     * 其结束作为参数应用于function
     * 最后生成新的CompletionStage
     */
    public <U> _CompletionStage<U> applyToEitherAsync
    (_CompletionStage<? extends T> other,
     Function<? super T, U> fn);

    /**
     * 依赖两个阶段中的任何一个完成（使用指定的Executor执行）
     * 其结束作为参数应用于function
     * 最后生成新的CompletionStage
     */
    public <U> _CompletionStage<U> applyToEitherAsync
    (_CompletionStage<? extends T> other,
     Function<? super T, U> fn,
     Executor executor);

    /**
     * 依赖两个阶段中的任何一个完成
     * 其结束作为参数应用于function
     * 最后生成新的CompletionStage<Void>
     */
    public _CompletionStage<Void> acceptEither (_CompletionStage<? extends T> other,
     Consumer<? super T> action);

    /**
     * 依赖两个阶段中的任何一个完成（默认异步执行工具）
     * 其结束作为参数应用于function
     * 最后生成新的CompletionStage<Void>
     */
    public _CompletionStage<Void> acceptEitherAsync (_CompletionStage<? extends T> other,
     Consumer<? super T> action);

    /**
     * 依赖两个阶段中的任何一个完成（使用指定的Executor执行）
     * 其结束作为参数应用于function
     * 最后生成新的CompletionStage<Void>
     */
    public _CompletionStage<Void> acceptEitherAsync
    (_CompletionStage<? extends T> other,
     Consumer<? super T> action,
     Executor executor);

    /**
     * Returns a new _CompletionStage that, when either this or the
     * other given stage complete normally, executes the given action.
     * <p>
     * See the {@link _CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * @param other  the other _CompletionStage
     * @param action the action to perform before completing the
     *               returned _CompletionStage
     * @return the new _CompletionStage
     */
    public _CompletionStage<Void> runAfterEither(_CompletionStage<?> other,
                                                 Runnable action);

    /**
     * Returns a new _CompletionStage that, when either this or the
     * other given stage complete normally, executes the given action
     * using this stage's default asynchronous execution facility.
     * <p>
     * See the {@link _CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * @param other  the other _CompletionStage
     * @param action the action to perform before completing the
     *               returned _CompletionStage
     * @return the new _CompletionStage
     */
    public _CompletionStage<Void> runAfterEitherAsync
    (_CompletionStage<?> other,
     Runnable action);

    /**
     * Returns a new _CompletionStage that, when either this or the
     * other given stage complete normally, executes the given action
     * using the supplied executor.
     * <p>
     * See the {@link _CompletionStage} documentation for rules
     * covering exceptional completion.
     *
     * @param other    the other _CompletionStage
     * @param action   the action to perform before completing the
     *                 returned _CompletionStage
     * @param executor the executor to use for asynchronous execution
     * @return the new _CompletionStage
     */
    public _CompletionStage<Void> runAfterEitherAsync
    (_CompletionStage<?> other,
     Runnable action,
     Executor executor);

    /**
     * 根据正常完成的阶段本身而不是其结果的产出型
     */
    public <U> _CompletionStage<U> thenCompose (Function<? super T, ? extends _CompletionStage<U>> fn);

    /**
     * 根据正常完成的阶段本身而不是其结果的产出型
     */
    public <U> _CompletionStage<U> thenComposeAsync (Function<? super T, ? extends _CompletionStage<U>> fn);

    /**
     * 根据正常完成的阶段本身而不是其结果的产出型
     */
    public <U> _CompletionStage<U> thenComposeAsync (Function<? super T, ? extends _CompletionStage<U>> fn, Executor executor);

    /**
     * 异常完成的产出型
     */
    public _CompletionStage<T> exceptionally (Function<Throwable, ? extends T> fn);

    /**
     * 不论阶段正常还是异常完成的消耗型
     */
    public _CompletionStage<T> whenComplete (BiConsumer<? super T, ? super Throwable> action);

    /**
     * 不论阶段正常还是异常完成的消耗型
     */
    public _CompletionStage<T> whenCompleteAsync (BiConsumer<? super T, ? super Throwable> action);

    /**
     * 不论阶段正常还是异常完成的消耗型
     */
    public _CompletionStage<T> whenCompleteAsync (BiConsumer<? super T, ? super Throwable> action, Executor executor);

    /**
     * 无论正常或异常，均可执行到指定BiFunction
     */
    public <U> _CompletionStage<U> handle (BiFunction<? super T, Throwable, ? extends U> fn);

    /**
     * 无论正常或异常，均可执行到指定BiFunction
     */
    public <U> _CompletionStage<U> handleAsync (BiFunction<? super T, Throwable, ? extends U> fn);

    /**
     * 无论正常或异常，均可执行到指定BiFunction
     */

    public <U> _CompletionStage<U> handleAsync (BiFunction<? super T, Throwable, ? extends U> fn, Executor executor);

    /**
     * Returns a {@link CompletableFuture} maintaining the same
     * completion properties as this stage. If this stage is already a
     * _CompletableFuture, this method may return this stage itself.
     * Otherwise, invocation of this method may be equivalent in
     * effect to {@code thenApply(x -> x)}, but returning an instance
     * of type {@code _CompletableFuture}. A _CompletionStage
     * implementation that does not choose to interoperate with others
     * may throw {@code UnsupportedOperationException}.
     *
     * @return the _CompletableFuture
     * @throws UnsupportedOperationException if this implementation
     *                                       does not interoperate with _CompletableFuture
     */
    public CompletableFuture<T> toCompletableFuture();

}
