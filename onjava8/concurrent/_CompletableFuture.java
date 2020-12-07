package concurrent;


import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.concurrent.locks.LockSupport;

/**
 * A {@link Future} that may be explicitly completed (setting its
 * value and status), and may be used as a {@link CompletionStage},
 * supporting dependent functions and actions that trigger upon its
 * completion.
 * @author Doug Lea
 * @since 1.8
 */
/*public class _CompletableFuture<T> implements Future<T>, CompletionStage<T> {

    *//*
     * Overview:
     *
     * A _CompletableFuture may have dependent completion actions,
     * collected in a linked stack. It atomically completes by CASing
     * a result field, and then pops off and runs those actions. This
     * applies across normal vs exceptional outcomes, sync vs async
     * actions, binary triggers, and various forms of completions.
     *
     * Non-nullness of field result (set via CAS) indicates done.  An
     * AltResult is used to box null as a result, as well as to hold
     * exceptions.  Using a single field makes completion simple to
     * detect and trigger.  Encoding and decoding is straightforward
     * but adds to the sprawl of trapping and associating exceptions
     * with targets.  Minor simplifications rely on (static) NIL (to
     * box null results) being the only AltResult with a null
     * exception field, so we don't usually need explicit comparisons.
     * Even though some of the generics casts are unchecked (see
     * SuppressWarnings annotations), they are placed to be
     * appropriate even if checked.
     *
     * Dependent actions are represented by Completion objects linked
     * as Treiber stacks headed by field "stack". There are Completion
     * classes for each kind of action, grouped into single-input
     * (UniCompletion), two-input (BiCompletion), projected
     * (BiCompletions using either (not both) of two inputs), shared
     * (CoCompletion, used by the second of two sources), zero-input
     * source actions, and Signallers that unblock waiters. Class
     * Completion extends ForkJoinTask to enable async execution
     * (adding no space overhead because we exploit its "tag" methods
     * to maintain claims). It is also declared as Runnable to allow
     * usage with arbitrary executors.
     *
     * Support for each kind of _CompletionStage relies on a separate
     * class, along with two _CompletableFuture methods:
     *
     * * A Completion class with name X corresponding to function,
     *   prefaced with "Uni", "Bi", or "Or". Each class contains
     *   fields for source(s), actions, and dependent. They are
     *   boringly similar, differing from others only with respect to
     *   underlying functional forms. We do this so that users don't
     *   encounter layers of adaptors in common usages. We also
     *   include "Relay" classes/methods that don't correspond to user
     *   methods; they copy results from one stage to another.
     *
     * * Boolean _CompletableFuture method x(...) (for example
     *   uniApply) takes all of the arguments needed to check that an
     *   action is triggerable, and then either runs the action or
     *   arranges its async execution by executing its Completion
     *   argument, if present. The method returns true if known to be
     *   complete.
     *
     * * Completion method tryFire(int mode) invokes the associated x
     *   method with its held arguments, and on success cleans up.
     *   The mode argument allows tryFire to be called twice (SYNC,
     *   then ASYNC); the first to screen and trap exceptions while
     *   arranging to execute, and the second when called from a
     *   task. (A few classes are not used async so take slightly
     *   different forms.)  The claim() callback suppresses function
     *   invocation if already claimed by another thread.
     *
     * * _CompletableFuture method xStage(...) is called from a public
     *   stage method of _CompletableFuture x. It screens user
     *   arguments and invokes and/or creates the stage object.  If
     *   not async and x is already complete, the action is run
     *   immediately.  Otherwise a Completion c is created, pushed to
     *   x's stack (unless done), and started or triggered via
     *   c.tryFire.  This also covers races possible if x completes
     *   while pushing.  Classes with two inputs (for example BiApply)
     *   deal with races across both while pushing actions.  The
     *   second completion is a CoCompletion pointing to the first,
     *   shared so that at most one performs the action.  The
     *   multiple-arity methods allOf and anyOf do this pairwise to
     *   form trees of completions.
     *
     * Note that the generic type parameters of methods vary according
     * to whether "this" is a source, dependent, or completion.
     *
     * Method postComplete is called upon completion unless the target
     * is guaranteed not to be observable (i.e., not yet returned or
     * linked). Multiple threads can call postComplete, which
     * atomically pops each dependent action, and tries to trigger it
     * via method tryFire, in NESTED mode.  Triggering can propagate
     * recursively, so NESTED mode returns its completed dependent (if
     * one exists) for further processing by its caller (see method
     * postFire).
     *
     * Blocking methods get() and join() rely on Signaller Completions
     * that wake up waiting threads.  The mechanics are similar to
     * Treiber stack wait-nodes used in FutureTask, Phaser, and
     * SynchronousQueue. See their internal documentation for
     * algorithmic details.
     *
     * Without precautions, CompletableFutures would be prone to
     * garbage accumulation as chains of Completions build up, each
     * pointing back to its sources. So we null out fields as soon as
     * possible (see especially method Completion.detach). The
     * screening checks needed anyway harmlessly ignore null arguments
     * that may have been obtained during races with threads nulling
     * out fields.  We also try to unlink fired Completions from
     * stacks that might never be popped (see method postFire).
     * Completion fields need not be declared as final or volatile
     * because they are only visible to other threads upon safe
     * publication.
     *//*

    volatile Object result;       // Either the result or boxed AltResult
    volatile java.util.concurrent.CompletableFuture.Completion stack;    // Top of Treiber stack of dependent actions

    final boolean internalComplete(Object r) { // CAS from null to r
        return UNSAFE.compareAndSwapObject(this, RESULT, null, r);
    }

    final boolean casStack(java.util.concurrent.CompletableFuture.Completion cmp, java.util.concurrent.CompletableFuture.Completion val) {
        return UNSAFE.compareAndSwapObject(this, STACK, cmp, val);
    }

    *//** Returns true if successfully pushed c onto stack. *//*
    final boolean tryPushStack(java.util.concurrent.CompletableFuture.Completion c) {
        java.util.concurrent.CompletableFuture.Completion h = stack;
        lazySetNext(c, h);
        return UNSAFE.compareAndSwapObject(this, STACK, h, c);
    }

    *//** Unconditionally pushes c onto stack, retrying if necessary. *//*
    final void pushStack(java.util.concurrent.CompletableFuture.Completion c) {
        do {} while (!tryPushStack(c));
    }

    *//* ------------- Encoding and decoding outcomes -------------- *//*

    static final class AltResult { // See above
        final Throwable ex;        // null only for NIL
        AltResult(Throwable x) { this.ex = x; }
    }

    *//** The encoding of the null value. *//*
    static final java.util.concurrent.CompletableFuture.AltResult NIL = new java.util.concurrent.CompletableFuture.AltResult(null);

    *//** Completes with the null value, unless already completed. *//*
    final boolean completeNull() {
        return UNSAFE.compareAndSwapObject(this, RESULT, null,
                NIL);
    }

    *//** Returns the encoding of the given non-exceptional value. *//*
    final Object encodeValue(T t) {
        return (t == null) ? NIL : t;
    }

    *//** Completes with a non-exceptional result, unless already completed. *//*
    final boolean completeValue(T t) {
        return UNSAFE.compareAndSwapObject(this, RESULT, null,
                (t == null) ? NIL : t);
    }

    *//**
     * Returns the encoding of the given (non-null) exception as a
     * wrapped CompletionException unless it is one already.
     *//*
    static java.util.concurrent.CompletableFuture.AltResult encodeThrowable(Throwable x) {
        return new java.util.concurrent.CompletableFuture.AltResult((x instanceof CompletionException) ? x :
                new CompletionException(x));
    }

    *//** Completes with an exceptional result, unless already completed. *//*
    final boolean completeThrowable(Throwable x) {
        return UNSAFE.compareAndSwapObject(this, RESULT, null,
                encodeThrowable(x));
    }

    *//**
     * Returns the encoding of the given (non-null) exception as a
     * wrapped CompletionException unless it is one already.  May
     * return the given Object r (which must have been the result of a
     * source future) if it is equivalent, i.e. if this is a simple
     * relay of an existing CompletionException.
     *//*
    static Object encodeThrowable(Throwable x, Object r) {
        if (!(x instanceof CompletionException))
            x = new CompletionException(x);
        else if (r instanceof java.util.concurrent.CompletableFuture.AltResult && x == ((java.util.concurrent.CompletableFuture.AltResult)r).ex)
            return r;
        return new java.util.concurrent.CompletableFuture.AltResult(x);
    }

    *//**
     * Completes with the given (non-null) exceptional result as a
     * wrapped CompletionException unless it is one already, unless
     * already completed.  May complete with the given Object r
     * (which must have been the result of a source future) if it is
     * equivalent, i.e. if this is a simple propagation of an
     * existing CompletionException.
     *//*
    final boolean completeThrowable(Throwable x, Object r) {
        return UNSAFE.compareAndSwapObject(this, RESULT, null,
                encodeThrowable(x, r));
    }

    *//**
     * Returns the encoding of the given arguments: if the exception
     * is non-null, encodes as AltResult.  Otherwise uses the given
     * value, boxed as NIL if null.
     *//*
    Object encodeOutcome(T t, Throwable x) {
        return (x == null) ? (t == null) ? NIL : t : encodeThrowable(x);
    }

    *//**
     * Returns the encoding of a copied outcome; if exceptional,
     * rewraps as a CompletionException, else returns argument.
     *//*
    static Object encodeRelay(Object r) {
        Throwable x;
        return (((r instanceof java.util.concurrent.CompletableFuture.AltResult) &&
                (x = ((java.util.concurrent.CompletableFuture.AltResult)r).ex) != null &&
                !(x instanceof CompletionException)) ?
                new java.util.concurrent.CompletableFuture.AltResult(new CompletionException(x)) : r);
    }

    *//**
     * Completes with r or a copy of r, unless already completed.
     * If exceptional, r is first coerced to a CompletionException.
     *//*
    final boolean completeRelay(Object r) {
        return UNSAFE.compareAndSwapObject(this, RESULT, null,
                encodeRelay(r));
    }

    *//**
     * Reports result using Future.get conventions.
     *//*
    private static <T> T reportGet(Object r)
            throws InterruptedException, ExecutionException {
        if (r == null) // by convention below, null means interrupted
            throw new InterruptedException();
        if (r instanceof java.util.concurrent.CompletableFuture.AltResult) {
            Throwable x, cause;
            if ((x = ((java.util.concurrent.CompletableFuture.AltResult)r).ex) == null)
                return null;
            if (x instanceof CancellationException)
                throw (CancellationException)x;
            if ((x instanceof CompletionException) &&
                    (cause = x.getCause()) != null)
                x = cause;
            throw new ExecutionException(x);
        }
        @SuppressWarnings("unchecked") T t = (T) r;
        return t;
    }

    *//**
     * Decodes outcome to return result or throw unchecked exception.
     *//*
    private static <T> T reportJoin(Object r) {
        if (r instanceof java.util.concurrent.CompletableFuture.AltResult) {
            Throwable x;
            if ((x = ((java.util.concurrent.CompletableFuture.AltResult)r).ex) == null)
                return null;
            if (x instanceof CancellationException)
                throw (CancellationException)x;
            if (x instanceof CompletionException)
                throw (CompletionException)x;
            throw new CompletionException(x);
        }
        @SuppressWarnings("unchecked") T t = (T) r;
        return t;
    }

    *//* ------------- Async task preliminaries -------------- *//*

    *//**
     * A marker interface identifying asynchronous tasks produced by
     * {@code async} methods. This may be useful for monitoring,
     * debugging, and tracking asynchronous activities.
     *
     * @since 1.8
     *//*
    public static interface AsynchronousCompletionTask {
    }

    private static final boolean useCommonPool =
            (ForkJoinPool.getCommonPoolParallelism() > 1);

    *//**
     * Default executor -- ForkJoinPool.commonPool() unless it cannot
     * support parallelism.
     *//*
    private static final Executor asyncPool = useCommonPool ?
            ForkJoinPool.commonPool() : new java.util.concurrent.CompletableFuture.ThreadPerTaskExecutor();

    *//** Fallback if ForkJoinPool.commonPool() cannot support parallelism *//*
    static final class ThreadPerTaskExecutor implements Executor {
        public void execute(Runnable r) { new Thread(r).start(); }
    }

    *//**
     * Null-checks user executor argument, and translates uses of
     * commonPool to asyncPool in case parallelism disabled.
     *//*
    static Executor screenExecutor(Executor e) {
        if (!useCommonPool && e == ForkJoinPool.commonPool())
            return asyncPool;
        if (e == null) throw new NullPointerException();
        return e;
    }

    // Modes for Completion.tryFire. Signedness matters.
    static final int SYNC   =  0;
    static final int ASYNC  =  1;
    static final int NESTED = -1;

    *//* ------------- Base Completion classes and operations -------------- *//*

    @SuppressWarnings("serial")
    abstract static class Completion extends ForkJoinTask<Void>
            implements Runnable, java.util.concurrent.CompletableFuture.AsynchronousCompletionTask {
        volatile java.util.concurrent.CompletableFuture.Completion next;      // Treiber stack link

        *//**
         * Performs completion action if triggered, returning a
         * dependent that may need propagation, if one exists.
         *
         * @param mode SYNC, ASYNC, or NESTED
         *//*
        abstract java.util.concurrent.CompletableFuture<?> tryFire(int mode);

        *//** Returns true if possibly still triggerable. Used by cleanStack. *//*
        abstract boolean isLive();

        public final void run()                { tryFire(ASYNC); }
        public final boolean exec()            { tryFire(ASYNC); return true; }
        public final Void getRawResult()       { return null; }
        public final void setRawResult(Void v) {}
    }

    static void lazySetNext(java.util.concurrent.CompletableFuture.Completion c, java.util.concurrent.CompletableFuture.Completion next) {
        UNSAFE.putOrderedObject(c, NEXT, next);
    }

    *//**
     * Pops and tries to trigger all reachable dependents.  Call only
     * when known to be done.
     *//*
    final void postComplete() {
        *//*
         * On each step, variable f holds current dependents to pop
         * and run.  It is extended along only one path at a time,
         * pushing others to avoid unbounded recursion.
         *//*
        java.util.concurrent.CompletableFuture<?> f = this; java.util.concurrent.CompletableFuture.Completion h;
        while ((h = f.stack) != null ||
                (f != this && (h = (f = this).stack) != null)) {
            java.util.concurrent.CompletableFuture<?> d; java.util.concurrent.CompletableFuture.Completion t;
            if (f.casStack(h, t = h.next)) {
                if (t != null) {
                    if (f != this) {
                        pushStack(h);
                        continue;
                    }
                    h.next = null;    // detach
                }
                f = (d = h.tryFire(NESTED)) == null ? this : d;
            }
        }
    }

    *//** Traverses stack and unlinks dead Completions. *//*
    final void cleanStack() {
        for (java.util.concurrent.CompletableFuture.Completion p = null, q = stack; q != null;) {
            java.util.concurrent.CompletableFuture.Completion s = q.next;
            if (q.isLive()) {
                p = q;
                q = s;
            }
            else if (p == null) {
                casStack(q, s);
                q = stack;
            }
            else {
                p.next = s;
                if (p.isLive())
                    q = s;
                else {
                    p = null;  // restart
                    q = stack;
                }
            }
        }
    }

    *//* ------------- One-input Completions -------------- *//*

    *//** A Completion with a source, dependent, and executor. *//*
    @SuppressWarnings("serial")
    abstract static class UniCompletion<T,V> extends java.util.concurrent.CompletableFuture.Completion {
        Executor executor;                 // executor to use (null if none)
        java.util.concurrent.CompletableFuture<V> dep;          // the dependent to complete
        java.util.concurrent.CompletableFuture<T> src;          // source for action

        UniCompletion(Executor executor, java.util.concurrent.CompletableFuture<V> dep,
                      java.util.concurrent.CompletableFuture<T> src) {
            this.executor = executor; this.dep = dep; this.src = src;
        }

        *//**
         * Returns true if action can be run. Call only when known to
         * be triggerable. Uses FJ tag bit to ensure that only one
         * thread claims ownership.  If async, starts as task -- a
         * later call to tryFire will run action.
         *//*
        final boolean claim() {
            Executor e = executor;
            if (compareAndSetForkJoinTaskTag((short)0, (short)1)) {
                if (e == null)
                    return true;
                executor = null; // disable
                e.execute(this);
            }
            return false;
        }

        final boolean isLive() { return dep != null; }
    }

    *//** Pushes the given completion (if it exists) unless done. *//*
    final void push(java.util.concurrent.CompletableFuture.UniCompletion<?,?> c) {
        if (c != null) {
            while (result == null && !tryPushStack(c))
                lazySetNext(c, null); // clear on failure
        }
    }

    *//**
     * Post-processing by dependent after successful UniCompletion
     * tryFire.  Tries to clean stack of source a, and then either runs
     * postComplete or returns this to caller, depending on mode.
     *//*
    final java.util.concurrent.CompletableFuture<T> postFire(java.util.concurrent.CompletableFuture<?> a, int mode) {
        if (a != null && a.stack != null) {
            if (mode < 0 || a.result == null)
                a.cleanStack();
            else
                a.postComplete();
        }
        if (result != null && stack != null) {
            if (mode < 0)
                return this;
            else
                postComplete();
        }
        return null;
    }

    @SuppressWarnings("serial")
    static final class UniApply<T,V> extends java.util.concurrent.CompletableFuture.UniCompletion<T,V> {
        Function<? super T,? extends V> fn;
        UniApply(Executor executor, java.util.concurrent.CompletableFuture<V> dep,
                 java.util.concurrent.CompletableFuture<T> src,
                 Function<? super T,? extends V> fn) {
            super(executor, dep, src); this.fn = fn;
        }
        final java.util.concurrent.CompletableFuture<V> tryFire(int mode) {
            java.util.concurrent.CompletableFuture<V> d; java.util.concurrent.CompletableFuture<T> a;
            if ((d = dep) == null ||
                    !d.uniApply(a = src, fn, mode > 0 ? null : this))
                return null;
            dep = null; src = null; fn = null;
            return d.postFire(a, mode);
        }
    }

    final <S> boolean uniApply(java.util.concurrent.CompletableFuture<S> a,
                               Function<? super S,? extends T> f,
                               java.util.concurrent.CompletableFuture.UniApply<S,T> c) {
        Object r; Throwable x;
        if (a == null || (r = a.result) == null || f == null)
            return false;
        tryComplete: if (result == null) {
            if (r instanceof java.util.concurrent.CompletableFuture.AltResult) {
                if ((x = ((java.util.concurrent.CompletableFuture.AltResult)r).ex) != null) {
                    completeThrowable(x, r);
                    break tryComplete;
                }
                r = null;
            }
            try {
                if (c != null && !c.claim())
                    return false;
                @SuppressWarnings("unchecked") S s = (S) r;
                completeValue(f.apply(s));
            } catch (Throwable ex) {
                completeThrowable(ex);
            }
        }
        return true;
    }

    private <V> java.util.concurrent.CompletableFuture<V> uniApplyStage(
            Executor e, Function<? super T,? extends V> f) {
        if (f == null) throw new NullPointerException();
        java.util.concurrent.CompletableFuture<V> d =  new java.util.concurrent.CompletableFuture<V>();
        if (e != null || !d.uniApply(this, f, null)) {
            java.util.concurrent.CompletableFuture.UniApply<T,V> c = new java.util.concurrent.CompletableFuture.UniApply<T,V>(e, d, this, f);
            push(c);
            c.tryFire(SYNC);
        }
        return d;
    }

    @SuppressWarnings("serial")
    static final class UniAccept<T> extends java.util.concurrent.CompletableFuture.UniCompletion<T,Void> {
        Consumer<? super T> fn;
        UniAccept(Executor executor, java.util.concurrent.CompletableFuture<Void> dep,
                  java.util.concurrent.CompletableFuture<T> src, Consumer<? super T> fn) {
            super(executor, dep, src); this.fn = fn;
        }
        final java.util.concurrent.CompletableFuture<Void> tryFire(int mode) {
            java.util.concurrent.CompletableFuture<Void> d; java.util.concurrent.CompletableFuture<T> a;
            if ((d = dep) == null ||
                    !d.uniAccept(a = src, fn, mode > 0 ? null : this))
                return null;
            dep = null; src = null; fn = null;
            return d.postFire(a, mode);
        }
    }

    final <S> boolean uniAccept(java.util.concurrent.CompletableFuture<S> a,
                                Consumer<? super S> f, java.util.concurrent.CompletableFuture.UniAccept<S> c) {
        Object r; Throwable x;
        if (a == null || (r = a.result) == null || f == null)
            return false;
        tryComplete: if (result == null) {
            if (r instanceof java.util.concurrent.CompletableFuture.AltResult) {
                if ((x = ((java.util.concurrent.CompletableFuture.AltResult)r).ex) != null) {
                    completeThrowable(x, r);
                    break tryComplete;
                }
                r = null;
            }
            try {
                if (c != null && !c.claim())
                    return false;
                @SuppressWarnings("unchecked") S s = (S) r;
                f.accept(s);
                completeNull();
            } catch (Throwable ex) {
                completeThrowable(ex);
            }
        }
        return true;
    }

    private java.util.concurrent.CompletableFuture<Void> uniAcceptStage(Executor e,
                                                                        Consumer<? super T> f) {
        if (f == null) throw new NullPointerException();
        java.util.concurrent.CompletableFuture<Void> d = new java.util.concurrent.CompletableFuture<Void>();
        if (e != null || !d.uniAccept(this, f, null)) {
            java.util.concurrent.CompletableFuture.UniAccept<T> c = new java.util.concurrent.CompletableFuture.UniAccept<T>(e, d, this, f);
            push(c);
            c.tryFire(SYNC);
        }
        return d;
    }

    @SuppressWarnings("serial")
    static final class UniRun<T> extends java.util.concurrent.CompletableFuture.UniCompletion<T,Void> {
        Runnable fn;
        UniRun(Executor executor, java.util.concurrent.CompletableFuture<Void> dep,
               java.util.concurrent.CompletableFuture<T> src, Runnable fn) {
            super(executor, dep, src); this.fn = fn;
        }
        final java.util.concurrent.CompletableFuture<Void> tryFire(int mode) {
            java.util.concurrent.CompletableFuture<Void> d; java.util.concurrent.CompletableFuture<T> a;
            if ((d = dep) == null ||
                    !d.uniRun(a = src, fn, mode > 0 ? null : this))
                return null;
            dep = null; src = null; fn = null;
            return d.postFire(a, mode);
        }
    }

    final boolean uniRun(java.util.concurrent.CompletableFuture<?> a, Runnable f, java.util.concurrent.CompletableFuture.UniRun<?> c) {
        Object r; Throwable x;
        if (a == null || (r = a.result) == null || f == null)
            return false;
        if (result == null) {
            if (r instanceof java.util.concurrent.CompletableFuture.AltResult && (x = ((java.util.concurrent.CompletableFuture.AltResult)r).ex) != null)
                completeThrowable(x, r);
            else
                try {
                    if (c != null && !c.claim())
                        return false;
                    f.run();
                    completeNull();
                } catch (Throwable ex) {
                    completeThrowable(ex);
                }
        }
        return true;
    }

    private java.util.concurrent.CompletableFuture<Void> uniRunStage(Executor e, Runnable f) {
        if (f == null) throw new NullPointerException();
        java.util.concurrent.CompletableFuture<Void> d = new java.util.concurrent.CompletableFuture<Void>();
        if (e != null || !d.uniRun(this, f, null)) {
            java.util.concurrent.CompletableFuture.UniRun<T> c = new java.util.concurrent.CompletableFuture.UniRun<T>(e, d, this, f);
            push(c);
            c.tryFire(SYNC);
        }
        return d;
    }

    @SuppressWarnings("serial")
    static final class UniWhenComplete<T> extends java.util.concurrent.CompletableFuture.UniCompletion<T,T> {
        BiConsumer<? super T, ? super Throwable> fn;
        UniWhenComplete(Executor executor, java.util.concurrent.CompletableFuture<T> dep,
                        java.util.concurrent.CompletableFuture<T> src,
                        BiConsumer<? super T, ? super Throwable> fn) {
            super(executor, dep, src); this.fn = fn;
        }
        final java.util.concurrent.CompletableFuture<T> tryFire(int mode) {
            java.util.concurrent.CompletableFuture<T> d; java.util.concurrent.CompletableFuture<T> a;
            if ((d = dep) == null ||
                    !d.uniWhenComplete(a = src, fn, mode > 0 ? null : this))
                return null;
            dep = null; src = null; fn = null;
            return d.postFire(a, mode);
        }
    }

    final boolean uniWhenComplete(java.util.concurrent.CompletableFuture<T> a,
                                  BiConsumer<? super T,? super Throwable> f,
                                  java.util.concurrent.CompletableFuture.UniWhenComplete<T> c) {
        Object r; T t; Throwable x = null;
        if (a == null || (r = a.result) == null || f == null)
            return false;
        if (result == null) {
            try {
                if (c != null && !c.claim())
                    return false;
                if (r instanceof java.util.concurrent.CompletableFuture.AltResult) {
                    x = ((java.util.concurrent.CompletableFuture.AltResult)r).ex;
                    t = null;
                } else {
                    @SuppressWarnings("unchecked") T tr = (T) r;
                    t = tr;
                }
                f.accept(t, x);
                if (x == null) {
                    internalComplete(r);
                    return true;
                }
            } catch (Throwable ex) {
                if (x == null)
                    x = ex;
            }
            completeThrowable(x, r);
        }
        return true;
    }

    private java.util.concurrent.CompletableFuture<T> uniWhenCompleteStage(
            Executor e, BiConsumer<? super T, ? super Throwable> f) {
        if (f == null) throw new NullPointerException();
        java.util.concurrent.CompletableFuture<T> d = new java.util.concurrent.CompletableFuture<T>();
        if (e != null || !d.uniWhenComplete(this, f, null)) {
            java.util.concurrent.CompletableFuture.UniWhenComplete<T> c = new java.util.concurrent.CompletableFuture.UniWhenComplete<T>(e, d, this, f);
            push(c);
            c.tryFire(SYNC);
        }
        return d;
    }

    @SuppressWarnings("serial")
    static final class UniHandle<T,V> extends java.util.concurrent.CompletableFuture.UniCompletion<T,V> {
        BiFunction<? super T, Throwable, ? extends V> fn;
        UniHandle(Executor executor, java.util.concurrent.CompletableFuture<V> dep,
                  java.util.concurrent.CompletableFuture<T> src,
                  BiFunction<? super T, Throwable, ? extends V> fn) {
            super(executor, dep, src); this.fn = fn;
        }
        final java.util.concurrent.CompletableFuture<V> tryFire(int mode) {
            java.util.concurrent.CompletableFuture<V> d; java.util.concurrent.CompletableFuture<T> a;
            if ((d = dep) == null ||
                    !d.uniHandle(a = src, fn, mode > 0 ? null : this))
                return null;
            dep = null; src = null; fn = null;
            return d.postFire(a, mode);
        }
    }

    final <S> boolean uniHandle(java.util.concurrent.CompletableFuture<S> a,
                                BiFunction<? super S, Throwable, ? extends T> f,
                                java.util.concurrent.CompletableFuture.UniHandle<S,T> c) {
        Object r; S s; Throwable x;
        if (a == null || (r = a.result) == null || f == null)
            return false;
        if (result == null) {
            try {
                if (c != null && !c.claim())
                    return false;
                if (r instanceof java.util.concurrent.CompletableFuture.AltResult) {
                    x = ((java.util.concurrent.CompletableFuture.AltResult)r).ex;
                    s = null;
                } else {
                    x = null;
                    @SuppressWarnings("unchecked") S ss = (S) r;
                    s = ss;
                }
                completeValue(f.apply(s, x));
            } catch (Throwable ex) {
                completeThrowable(ex);
            }
        }
        return true;
    }

    private <V> java.util.concurrent.CompletableFuture<V> uniHandleStage(
            Executor e, BiFunction<? super T, Throwable, ? extends V> f) {
        if (f == null) throw new NullPointerException();
        java.util.concurrent.CompletableFuture<V> d = new java.util.concurrent.CompletableFuture<V>();
        if (e != null || !d.uniHandle(this, f, null)) {
            java.util.concurrent.CompletableFuture.UniHandle<T,V> c = new java.util.concurrent.CompletableFuture.UniHandle<T,V>(e, d, this, f);
            push(c);
            c.tryFire(SYNC);
        }
        return d;
    }

    @SuppressWarnings("serial")
    static final class UniExceptionally<T> extends java.util.concurrent.CompletableFuture.UniCompletion<T,T> {
        Function<? super Throwable, ? extends T> fn;
        UniExceptionally(java.util.concurrent.CompletableFuture<T> dep, java.util.concurrent.CompletableFuture<T> src,
                         Function<? super Throwable, ? extends T> fn) {
            super(null, dep, src); this.fn = fn;
        }
        final java.util.concurrent.CompletableFuture<T> tryFire(int mode) { // never ASYNC
            // assert mode != ASYNC;
            java.util.concurrent.CompletableFuture<T> d; java.util.concurrent.CompletableFuture<T> a;
            if ((d = dep) == null || !d.uniExceptionally(a = src, fn, this))
                return null;
            dep = null; src = null; fn = null;
            return d.postFire(a, mode);
        }
    }

    final boolean uniExceptionally(java.util.concurrent.CompletableFuture<T> a,
                                   Function<? super Throwable, ? extends T> f,
                                   java.util.concurrent.CompletableFuture.UniExceptionally<T> c) {
        Object r; Throwable x;
        if (a == null || (r = a.result) == null || f == null)
            return false;
        if (result == null) {
            try {
                if (r instanceof java.util.concurrent.CompletableFuture.AltResult && (x = ((java.util.concurrent.CompletableFuture.AltResult)r).ex) != null) {
                    if (c != null && !c.claim())
                        return false;
                    completeValue(f.apply(x));
                } else
                    internalComplete(r);
            } catch (Throwable ex) {
                completeThrowable(ex);
            }
        }
        return true;
    }

    private java.util.concurrent.CompletableFuture<T> uniExceptionallyStage(
            Function<Throwable, ? extends T> f) {
        if (f == null) throw new NullPointerException();
        java.util.concurrent.CompletableFuture<T> d = new java.util.concurrent.CompletableFuture<T>();
        if (!d.uniExceptionally(this, f, null)) {
            java.util.concurrent.CompletableFuture.UniExceptionally<T> c = new java.util.concurrent.CompletableFuture.UniExceptionally<T>(d, this, f);
            push(c);
            c.tryFire(SYNC);
        }
        return d;
    }

    @SuppressWarnings("serial")
    static final class UniRelay<T> extends java.util.concurrent.CompletableFuture.UniCompletion<T,T> { // for Compose
        UniRelay(java.util.concurrent.CompletableFuture<T> dep, java.util.concurrent.CompletableFuture<T> src) {
            super(null, dep, src);
        }
        final java.util.concurrent.CompletableFuture<T> tryFire(int mode) {
            java.util.concurrent.CompletableFuture<T> d; java.util.concurrent.CompletableFuture<T> a;
            if ((d = dep) == null || !d.uniRelay(a = src))
                return null;
            src = null; dep = null;
            return d.postFire(a, mode);
        }
    }

    final boolean uniRelay(java.util.concurrent.CompletableFuture<T> a) {
        Object r;
        if (a == null || (r = a.result) == null)
            return false;
        if (result == null) // no need to claim
            completeRelay(r);
        return true;
    }

    @SuppressWarnings("serial")
    static final class UniCompose<T,V> extends java.util.concurrent.CompletableFuture.UniCompletion<T,V> {
        Function<? super T, ? extends CompletionStage<V>> fn;
        UniCompose(Executor executor, java.util.concurrent.CompletableFuture<V> dep,
                   java.util.concurrent.CompletableFuture<T> src,
                   Function<? super T, ? extends CompletionStage<V>> fn) {
            super(executor, dep, src); this.fn = fn;
        }
        final java.util.concurrent.CompletableFuture<V> tryFire(int mode) {
            java.util.concurrent.CompletableFuture<V> d; java.util.concurrent.CompletableFuture<T> a;
            if ((d = dep) == null ||
                    !d.uniCompose(a = src, fn, mode > 0 ? null : this))
                return null;
            dep = null; src = null; fn = null;
            return d.postFire(a, mode);
        }
    }

    final <S> boolean uniCompose(
            java.util.concurrent.CompletableFuture<S> a,
            Function<? super S, ? extends CompletionStage<T>> f,
            java.util.concurrent.CompletableFuture.UniCompose<S,T> c) {
        Object r; Throwable x;
        if (a == null || (r = a.result) == null || f == null)
            return false;
        tryComplete: if (result == null) {
            if (r instanceof java.util.concurrent.CompletableFuture.AltResult) {
                if ((x = ((java.util.concurrent.CompletableFuture.AltResult)r).ex) != null) {
                    completeThrowable(x, r);
                    break tryComplete;
                }
                r = null;
            }
            try {
                if (c != null && !c.claim())
                    return false;
                @SuppressWarnings("unchecked") S s = (S) r;
                java.util.concurrent.CompletableFuture<T> g = f.apply(s).toCompletableFuture();
                if (g.result == null || !uniRelay(g)) {
                    java.util.concurrent.CompletableFuture.UniRelay<T> copy = new java.util.concurrent.CompletableFuture.UniRelay<T>(this, g);
                    g.push(copy);
                    copy.tryFire(SYNC);
                    if (result == null)
                        return false;
                }
            } catch (Throwable ex) {
                completeThrowable(ex);
            }
        }
        return true;
    }

    private <V> java.util.concurrent.CompletableFuture<V> uniComposeStage(
            Executor e, Function<? super T, ? extends CompletionStage<V>> f) {
        if (f == null) throw new NullPointerException();
        Object r; Throwable x;
        if (e == null && (r = result) != null) {
            // try to return function result directly
            if (r instanceof java.util.concurrent.CompletableFuture.AltResult) {
                if ((x = ((java.util.concurrent.CompletableFuture.AltResult)r).ex) != null) {
                    return new java.util.concurrent.CompletableFuture<V>(encodeThrowable(x, r));
                }
                r = null;
            }
            try {
                @SuppressWarnings("unchecked") T t = (T) r;
                java.util.concurrent.CompletableFuture<V> g = f.apply(t).toCompletableFuture();
                Object s = g.result;
                if (s != null)
                    return new java.util.concurrent.CompletableFuture<V>(encodeRelay(s));
                java.util.concurrent.CompletableFuture<V> d = new java.util.concurrent.CompletableFuture<V>();
                java.util.concurrent.CompletableFuture.UniRelay<V> copy = new java.util.concurrent.CompletableFuture.UniRelay<V>(d, g);
                g.push(copy);
                copy.tryFire(SYNC);
                return d;
            } catch (Throwable ex) {
                return new java.util.concurrent.CompletableFuture<V>(encodeThrowable(ex));
            }
        }
        java.util.concurrent.CompletableFuture<V> d = new java.util.concurrent.CompletableFuture<V>();
        java.util.concurrent.CompletableFuture.UniCompose<T,V> c = new java.util.concurrent.CompletableFuture.UniCompose<T,V>(e, d, this, f);
        push(c);
        c.tryFire(SYNC);
        return d;
    }

    *//* ------------- Two-input Completions -------------- *//*

    *//** A Completion for an action with two sources *//*
    @SuppressWarnings("serial")
    abstract static class BiCompletion<T,U,V> extends java.util.concurrent.CompletableFuture.UniCompletion<T,V> {
        java.util.concurrent.CompletableFuture<U> snd; // second source for action
        BiCompletion(Executor executor, java.util.concurrent.CompletableFuture<V> dep,
                     java.util.concurrent.CompletableFuture<T> src, java.util.concurrent.CompletableFuture<U> snd) {
            super(executor, dep, src); this.snd = snd;
        }
    }

    *//** A Completion delegating to a BiCompletion *//*
    @SuppressWarnings("serial")
    static final class CoCompletion extends java.util.concurrent.CompletableFuture.Completion {
        java.util.concurrent.CompletableFuture.BiCompletion<?,?,?> base;
        CoCompletion(java.util.concurrent.CompletableFuture.BiCompletion<?,?,?> base) { this.base = base; }
        final java.util.concurrent.CompletableFuture<?> tryFire(int mode) {
            java.util.concurrent.CompletableFuture.BiCompletion<?,?,?> c; java.util.concurrent.CompletableFuture<?> d;
            if ((c = base) == null || (d = c.tryFire(mode)) == null)
                return null;
            base = null; // detach
            return d;
        }
        final boolean isLive() {
            java.util.concurrent.CompletableFuture.BiCompletion<?,?,?> c;
            return (c = base) != null && c.dep != null;
        }
    }

    *//** Pushes completion to this and b unless both done. *//*
    final void bipush(java.util.concurrent.CompletableFuture<?> b, java.util.concurrent.CompletableFuture.BiCompletion<?,?,?> c) {
        if (c != null) {
            Object r;
            while ((r = result) == null && !tryPushStack(c))
                lazySetNext(c, null); // clear on failure
            if (b != null && b != this && b.result == null) {
                java.util.concurrent.CompletableFuture.Completion q = (r != null) ? c : new java.util.concurrent.CompletableFuture.CoCompletion(c);
                while (b.result == null && !b.tryPushStack(q))
                    lazySetNext(q, null); // clear on failure
            }
        }
    }

    *//** Post-processing after successful BiCompletion tryFire. *//*
    final java.util.concurrent.CompletableFuture<T> postFire(java.util.concurrent.CompletableFuture<?> a,
                                                             java.util.concurrent.CompletableFuture<?> b, int mode) {
        if (b != null && b.stack != null) { // clean second source
            if (mode < 0 || b.result == null)
                b.cleanStack();
            else
                b.postComplete();
        }
        return postFire(a, mode);
    }

    @SuppressWarnings("serial")
    static final class BiApply<T,U,V> extends java.util.concurrent.CompletableFuture.BiCompletion<T,U,V> {
        BiFunction<? super T,? super U,? extends V> fn;
        BiApply(Executor executor, java.util.concurrent.CompletableFuture<V> dep,
                java.util.concurrent.CompletableFuture<T> src, java.util.concurrent.CompletableFuture<U> snd,
                BiFunction<? super T,? super U,? extends V> fn) {
            super(executor, dep, src, snd); this.fn = fn;
        }
        final java.util.concurrent.CompletableFuture<V> tryFire(int mode) {
            java.util.concurrent.CompletableFuture<V> d;
            java.util.concurrent.CompletableFuture<T> a;
            java.util.concurrent.CompletableFuture<U> b;
            if ((d = dep) == null ||
                    !d.biApply(a = src, b = snd, fn, mode > 0 ? null : this))
                return null;
            dep = null; src = null; snd = null; fn = null;
            return d.postFire(a, b, mode);
        }
    }

    final <R,S> boolean biApply(java.util.concurrent.CompletableFuture<R> a,
                                java.util.concurrent.CompletableFuture<S> b,
                                BiFunction<? super R,? super S,? extends T> f,
                                java.util.concurrent.CompletableFuture.BiApply<R,S,T> c) {
        Object r, s; Throwable x;
        if (a == null || (r = a.result) == null ||
                b == null || (s = b.result) == null || f == null)
            return false;
        tryComplete: if (result == null) {
            if (r instanceof java.util.concurrent.CompletableFuture.AltResult) {
                if ((x = ((java.util.concurrent.CompletableFuture.AltResult)r).ex) != null) {
                    completeThrowable(x, r);
                    break tryComplete;
                }
                r = null;
            }
            if (s instanceof java.util.concurrent.CompletableFuture.AltResult) {
                if ((x = ((java.util.concurrent.CompletableFuture.AltResult)s).ex) != null) {
                    completeThrowable(x, s);
                    break tryComplete;
                }
                s = null;
            }
            try {
                if (c != null && !c.claim())
                    return false;
                @SuppressWarnings("unchecked") R rr = (R) r;
                @SuppressWarnings("unchecked") S ss = (S) s;
                completeValue(f.apply(rr, ss));
            } catch (Throwable ex) {
                completeThrowable(ex);
            }
        }
        return true;
    }

    private <U,V> java.util.concurrent.CompletableFuture<V> biApplyStage(
            Executor e, CompletionStage<U> o,
            BiFunction<? super T,? super U,? extends V> f) {
        java.util.concurrent.CompletableFuture<U> b;
        if (f == null || (b = o.toCompletableFuture()) == null)
            throw new NullPointerException();
        java.util.concurrent.CompletableFuture<V> d = new java.util.concurrent.CompletableFuture<V>();
        if (e != null || !d.biApply(this, b, f, null)) {
            java.util.concurrent.CompletableFuture.BiApply<T,U,V> c = new java.util.concurrent.CompletableFuture.BiApply<T,U,V>(e, d, this, b, f);
            bipush(b, c);
            c.tryFire(SYNC);
        }
        return d;
    }

    @SuppressWarnings("serial")
    static final class BiAccept<T,U> extends java.util.concurrent.CompletableFuture.BiCompletion<T,U,Void> {
        BiConsumer<? super T,? super U> fn;
        BiAccept(Executor executor, java.util.concurrent.CompletableFuture<Void> dep,
                 java.util.concurrent.CompletableFuture<T> src, java.util.concurrent.CompletableFuture<U> snd,
                 BiConsumer<? super T,? super U> fn) {
            super(executor, dep, src, snd); this.fn = fn;
        }
        final java.util.concurrent.CompletableFuture<Void> tryFire(int mode) {
            java.util.concurrent.CompletableFuture<Void> d;
            java.util.concurrent.CompletableFuture<T> a;
            java.util.concurrent.CompletableFuture<U> b;
            if ((d = dep) == null ||
                    !d.biAccept(a = src, b = snd, fn, mode > 0 ? null : this))
                return null;
            dep = null; src = null; snd = null; fn = null;
            return d.postFire(a, b, mode);
        }
    }

    final <R,S> boolean biAccept(java.util.concurrent.CompletableFuture<R> a,
                                 java.util.concurrent.CompletableFuture<S> b,
                                 BiConsumer<? super R,? super S> f,
                                 java.util.concurrent.CompletableFuture.BiAccept<R,S> c) {
        Object r, s; Throwable x;
        if (a == null || (r = a.result) == null ||
                b == null || (s = b.result) == null || f == null)
            return false;
        tryComplete: if (result == null) {
            if (r instanceof java.util.concurrent.CompletableFuture.AltResult) {
                if ((x = ((java.util.concurrent.CompletableFuture.AltResult)r).ex) != null) {
                    completeThrowable(x, r);
                    break tryComplete;
                }
                r = null;
            }
            if (s instanceof java.util.concurrent.CompletableFuture.AltResult) {
                if ((x = ((java.util.concurrent.CompletableFuture.AltResult)s).ex) != null) {
                    completeThrowable(x, s);
                    break tryComplete;
                }
                s = null;
            }
            try {
                if (c != null && !c.claim())
                    return false;
                @SuppressWarnings("unchecked") R rr = (R) r;
                @SuppressWarnings("unchecked") S ss = (S) s;
                f.accept(rr, ss);
                completeNull();
            } catch (Throwable ex) {
                completeThrowable(ex);
            }
        }
        return true;
    }

    private <U> java.util.concurrent.CompletableFuture<Void> biAcceptStage(
            Executor e, CompletionStage<U> o,
            BiConsumer<? super T,? super U> f) {
        java.util.concurrent.CompletableFuture<U> b;
        if (f == null || (b = o.toCompletableFuture()) == null)
            throw new NullPointerException();
        java.util.concurrent.CompletableFuture<Void> d = new java.util.concurrent.CompletableFuture<Void>();
        if (e != null || !d.biAccept(this, b, f, null)) {
            java.util.concurrent.CompletableFuture.BiAccept<T,U> c = new java.util.concurrent.CompletableFuture.BiAccept<T,U>(e, d, this, b, f);
            bipush(b, c);
            c.tryFire(SYNC);
        }
        return d;
    }

    @SuppressWarnings("serial")
    static final class BiRun<T,U> extends java.util.concurrent.CompletableFuture.BiCompletion<T,U,Void> {
        Runnable fn;
        BiRun(Executor executor, java.util.concurrent.CompletableFuture<Void> dep,
              java.util.concurrent.CompletableFuture<T> src,
              java.util.concurrent.CompletableFuture<U> snd,
              Runnable fn) {
            super(executor, dep, src, snd); this.fn = fn;
        }
        final java.util.concurrent.CompletableFuture<Void> tryFire(int mode) {
            java.util.concurrent.CompletableFuture<Void> d;
            java.util.concurrent.CompletableFuture<T> a;
            java.util.concurrent.CompletableFuture<U> b;
            if ((d = dep) == null ||
                    !d.biRun(a = src, b = snd, fn, mode > 0 ? null : this))
                return null;
            dep = null; src = null; snd = null; fn = null;
            return d.postFire(a, b, mode);
        }
    }

    final boolean biRun(java.util.concurrent.CompletableFuture<?> a, java.util.concurrent.CompletableFuture<?> b,
                        Runnable f, java.util.concurrent.CompletableFuture.BiRun<?,?> c) {
        Object r, s; Throwable x;
        if (a == null || (r = a.result) == null ||
                b == null || (s = b.result) == null || f == null)
            return false;
        if (result == null) {
            if (r instanceof java.util.concurrent.CompletableFuture.AltResult && (x = ((java.util.concurrent.CompletableFuture.AltResult)r).ex) != null)
                completeThrowable(x, r);
            else if (s instanceof java.util.concurrent.CompletableFuture.AltResult && (x = ((java.util.concurrent.CompletableFuture.AltResult)s).ex) != null)
                completeThrowable(x, s);
            else
                try {
                    if (c != null && !c.claim())
                        return false;
                    f.run();
                    completeNull();
                } catch (Throwable ex) {
                    completeThrowable(ex);
                }
        }
        return true;
    }

    private java.util.concurrent.CompletableFuture<Void> biRunStage(Executor e, CompletionStage<?> o,
                                                                    Runnable f) {
        java.util.concurrent.CompletableFuture<?> b;
        if (f == null || (b = o.toCompletableFuture()) == null)
            throw new NullPointerException();
        java.util.concurrent.CompletableFuture<Void> d = new java.util.concurrent.CompletableFuture<Void>();
        if (e != null || !d.biRun(this, b, f, null)) {
            java.util.concurrent.CompletableFuture.BiRun<T,?> c = new java.util.concurrent.CompletableFuture.BiRun<>(e, d, this, b, f);
            bipush(b, c);
            c.tryFire(SYNC);
        }
        return d;
    }

    @SuppressWarnings("serial")
    static final class BiRelay<T,U> extends java.util.concurrent.CompletableFuture.BiCompletion<T,U,Void> { // for And
        BiRelay(java.util.concurrent.CompletableFuture<Void> dep,
                java.util.concurrent.CompletableFuture<T> src,
                java.util.concurrent.CompletableFuture<U> snd) {
            super(null, dep, src, snd);
        }
        final java.util.concurrent.CompletableFuture<Void> tryFire(int mode) {
            java.util.concurrent.CompletableFuture<Void> d;
            java.util.concurrent.CompletableFuture<T> a;
            java.util.concurrent.CompletableFuture<U> b;
            if ((d = dep) == null || !d.biRelay(a = src, b = snd))
                return null;
            src = null; snd = null; dep = null;
            return d.postFire(a, b, mode);
        }
    }

    boolean biRelay(java.util.concurrent.CompletableFuture<?> a, java.util.concurrent.CompletableFuture<?> b) {
        Object r, s; Throwable x;
        if (a == null || (r = a.result) == null ||
                b == null || (s = b.result) == null)
            return false;
        if (result == null) {
            if (r instanceof java.util.concurrent.CompletableFuture.AltResult && (x = ((java.util.concurrent.CompletableFuture.AltResult)r).ex) != null)
                completeThrowable(x, r);
            else if (s instanceof java.util.concurrent.CompletableFuture.AltResult && (x = ((java.util.concurrent.CompletableFuture.AltResult)s).ex) != null)
                completeThrowable(x, s);
            else
                completeNull();
        }
        return true;
    }

    *//** Recursively constructs a tree of completions. *//*
    static java.util.concurrent.CompletableFuture<Void> andTree(java.util.concurrent.CompletableFuture<?>[] cfs,
                                                                int lo, int hi) {
        java.util.concurrent.CompletableFuture<Void> d = new java.util.concurrent.CompletableFuture<Void>();
        if (lo > hi) // empty
            d.result = NIL;
        else {
            java.util.concurrent.CompletableFuture<?> a, b;
            int mid = (lo + hi) >>> 1;
            if ((a = (lo == mid ? cfs[lo] :
                    andTree(cfs, lo, mid))) == null ||
                    (b = (lo == hi ? a : (hi == mid+1) ? cfs[hi] :
                            andTree(cfs, mid+1, hi)))  == null)
                throw new NullPointerException();
            if (!d.biRelay(a, b)) {
                java.util.concurrent.CompletableFuture.BiRelay<?,?> c = new java.util.concurrent.CompletableFuture.BiRelay<>(d, a, b);
                a.bipush(b, c);
                c.tryFire(SYNC);
            }
        }
        return d;
    }

    *//* ------------- Projected (Ored) BiCompletions -------------- *//*

    *//** Pushes completion to this and b unless either done. *//*
    final void orpush(java.util.concurrent.CompletableFuture<?> b, java.util.concurrent.CompletableFuture.BiCompletion<?,?,?> c) {
        if (c != null) {
            while ((b == null || b.result == null) && result == null) {
                if (tryPushStack(c)) {
                    if (b != null && b != this && b.result == null) {
                        java.util.concurrent.CompletableFuture.Completion q = new java.util.concurrent.CompletableFuture.CoCompletion(c);
                        while (result == null && b.result == null &&
                                !b.tryPushStack(q))
                            lazySetNext(q, null); // clear on failure
                    }
                    break;
                }
                lazySetNext(c, null); // clear on failure
            }
        }
    }

    @SuppressWarnings("serial")
    static final class OrApply<T,U extends T,V> extends java.util.concurrent.CompletableFuture.BiCompletion<T,U,V> {
        Function<? super T,? extends V> fn;
        OrApply(Executor executor, java.util.concurrent.CompletableFuture<V> dep,
                java.util.concurrent.CompletableFuture<T> src,
                java.util.concurrent.CompletableFuture<U> snd,
                Function<? super T,? extends V> fn) {
            super(executor, dep, src, snd); this.fn = fn;
        }
        final java.util.concurrent.CompletableFuture<V> tryFire(int mode) {
            java.util.concurrent.CompletableFuture<V> d;
            java.util.concurrent.CompletableFuture<T> a;
            java.util.concurrent.CompletableFuture<U> b;
            if ((d = dep) == null ||
                    !d.orApply(a = src, b = snd, fn, mode > 0 ? null : this))
                return null;
            dep = null; src = null; snd = null; fn = null;
            return d.postFire(a, b, mode);
        }
    }

    final <R,S extends R> boolean orApply(java.util.concurrent.CompletableFuture<R> a,
                                          java.util.concurrent.CompletableFuture<S> b,
                                          Function<? super R, ? extends T> f,
                                          java.util.concurrent.CompletableFuture.OrApply<R,S,T> c) {
        Object r; Throwable x;
        if (a == null || b == null ||
                ((r = a.result) == null && (r = b.result) == null) || f == null)
            return false;
        tryComplete: if (result == null) {
            try {
                if (c != null && !c.claim())
                    return false;
                if (r instanceof java.util.concurrent.CompletableFuture.AltResult) {
                    if ((x = ((java.util.concurrent.CompletableFuture.AltResult)r).ex) != null) {
                        completeThrowable(x, r);
                        break tryComplete;
                    }
                    r = null;
                }
                @SuppressWarnings("unchecked") R rr = (R) r;
                completeValue(f.apply(rr));
            } catch (Throwable ex) {
                completeThrowable(ex);
            }
        }
        return true;
    }

    private <U extends T,V> java.util.concurrent.CompletableFuture<V> orApplyStage(
            Executor e, CompletionStage<U> o,
            Function<? super T, ? extends V> f) {
        java.util.concurrent.CompletableFuture<U> b;
        if (f == null || (b = o.toCompletableFuture()) == null)
            throw new NullPointerException();
        java.util.concurrent.CompletableFuture<V> d = new java.util.concurrent.CompletableFuture<V>();
        if (e != null || !d.orApply(this, b, f, null)) {
            java.util.concurrent.CompletableFuture.OrApply<T,U,V> c = new java.util.concurrent.CompletableFuture.OrApply<T,U,V>(e, d, this, b, f);
            orpush(b, c);
            c.tryFire(SYNC);
        }
        return d;
    }

    @SuppressWarnings("serial")
    static final class OrAccept<T,U extends T> extends java.util.concurrent.CompletableFuture.BiCompletion<T,U,Void> {
        Consumer<? super T> fn;
        OrAccept(Executor executor, java.util.concurrent.CompletableFuture<Void> dep,
                 java.util.concurrent.CompletableFuture<T> src,
                 java.util.concurrent.CompletableFuture<U> snd,
                 Consumer<? super T> fn) {
            super(executor, dep, src, snd); this.fn = fn;
        }
        final java.util.concurrent.CompletableFuture<Void> tryFire(int mode) {
            java.util.concurrent.CompletableFuture<Void> d;
            java.util.concurrent.CompletableFuture<T> a;
            java.util.concurrent.CompletableFuture<U> b;
            if ((d = dep) == null ||
                    !d.orAccept(a = src, b = snd, fn, mode > 0 ? null : this))
                return null;
            dep = null; src = null; snd = null; fn = null;
            return d.postFire(a, b, mode);
        }
    }

    final <R,S extends R> boolean orAccept(java.util.concurrent.CompletableFuture<R> a,
                                           java.util.concurrent.CompletableFuture<S> b,
                                           Consumer<? super R> f,
                                           java.util.concurrent.CompletableFuture.OrAccept<R,S> c) {
        Object r; Throwable x;
        if (a == null || b == null ||
                ((r = a.result) == null && (r = b.result) == null) || f == null)
            return false;
        tryComplete: if (result == null) {
            try {
                if (c != null && !c.claim())
                    return false;
                if (r instanceof java.util.concurrent.CompletableFuture.AltResult) {
                    if ((x = ((java.util.concurrent.CompletableFuture.AltResult)r).ex) != null) {
                        completeThrowable(x, r);
                        break tryComplete;
                    }
                    r = null;
                }
                @SuppressWarnings("unchecked") R rr = (R) r;
                f.accept(rr);
                completeNull();
            } catch (Throwable ex) {
                completeThrowable(ex);
            }
        }
        return true;
    }

    private <U extends T> java.util.concurrent.CompletableFuture<Void> orAcceptStage(
            Executor e, CompletionStage<U> o, Consumer<? super T> f) {
        java.util.concurrent.CompletableFuture<U> b;
        if (f == null || (b = o.toCompletableFuture()) == null)
            throw new NullPointerException();
        java.util.concurrent.CompletableFuture<Void> d = new java.util.concurrent.CompletableFuture<Void>();
        if (e != null || !d.orAccept(this, b, f, null)) {
            java.util.concurrent.CompletableFuture.OrAccept<T,U> c = new java.util.concurrent.CompletableFuture.OrAccept<T,U>(e, d, this, b, f);
            orpush(b, c);
            c.tryFire(SYNC);
        }
        return d;
    }

    @SuppressWarnings("serial")
    static final class OrRun<T,U> extends java.util.concurrent.CompletableFuture.BiCompletion<T,U,Void> {
        Runnable fn;
        OrRun(Executor executor, java.util.concurrent.CompletableFuture<Void> dep,
              java.util.concurrent.CompletableFuture<T> src,
              java.util.concurrent.CompletableFuture<U> snd,
              Runnable fn) {
            super(executor, dep, src, snd); this.fn = fn;
        }
        final java.util.concurrent.CompletableFuture<Void> tryFire(int mode) {
            java.util.concurrent.CompletableFuture<Void> d;
            java.util.concurrent.CompletableFuture<T> a;
            java.util.concurrent.CompletableFuture<U> b;
            if ((d = dep) == null ||
                    !d.orRun(a = src, b = snd, fn, mode > 0 ? null : this))
                return null;
            dep = null; src = null; snd = null; fn = null;
            return d.postFire(a, b, mode);
        }
    }

    final boolean orRun(java.util.concurrent.CompletableFuture<?> a, java.util.concurrent.CompletableFuture<?> b,
                        Runnable f, java.util.concurrent.CompletableFuture.OrRun<?,?> c) {
        Object r; Throwable x;
        if (a == null || b == null ||
                ((r = a.result) == null && (r = b.result) == null) || f == null)
            return false;
        if (result == null) {
            try {
                if (c != null && !c.claim())
                    return false;
                if (r instanceof java.util.concurrent.CompletableFuture.AltResult && (x = ((java.util.concurrent.CompletableFuture.AltResult)r).ex) != null)
                    completeThrowable(x, r);
                else {
                    f.run();
                    completeNull();
                }
            } catch (Throwable ex) {
                completeThrowable(ex);
            }
        }
        return true;
    }

    private java.util.concurrent.CompletableFuture<Void> orRunStage(Executor e, CompletionStage<?> o,
                                                                    Runnable f) {
        java.util.concurrent.CompletableFuture<?> b;
        if (f == null || (b = o.toCompletableFuture()) == null)
            throw new NullPointerException();
        java.util.concurrent.CompletableFuture<Void> d = new java.util.concurrent.CompletableFuture<Void>();
        if (e != null || !d.orRun(this, b, f, null)) {
            java.util.concurrent.CompletableFuture.OrRun<T,?> c = new java.util.concurrent.CompletableFuture.OrRun<>(e, d, this, b, f);
            orpush(b, c);
            c.tryFire(SYNC);
        }
        return d;
    }

    @SuppressWarnings("serial")
    static final class OrRelay<T,U> extends java.util.concurrent.CompletableFuture.BiCompletion<T,U,Object> { // for Or
        OrRelay(java.util.concurrent.CompletableFuture<Object> dep, java.util.concurrent.CompletableFuture<T> src,
                java.util.concurrent.CompletableFuture<U> snd) {
            super(null, dep, src, snd);
        }
        final java.util.concurrent.CompletableFuture<Object> tryFire(int mode) {
            java.util.concurrent.CompletableFuture<Object> d;
            java.util.concurrent.CompletableFuture<T> a;
            java.util.concurrent.CompletableFuture<U> b;
            if ((d = dep) == null || !d.orRelay(a = src, b = snd))
                return null;
            src = null; snd = null; dep = null;
            return d.postFire(a, b, mode);
        }
    }

    final boolean orRelay(java.util.concurrent.CompletableFuture<?> a, java.util.concurrent.CompletableFuture<?> b) {
        Object r;
        if (a == null || b == null ||
                ((r = a.result) == null && (r = b.result) == null))
            return false;
        if (result == null)
            completeRelay(r);
        return true;
    }

    *//** Recursively constructs a tree of completions. *//*
    static java.util.concurrent.CompletableFuture<Object> orTree(java.util.concurrent.CompletableFuture<?>[] cfs,
                                                                 int lo, int hi) {
        java.util.concurrent.CompletableFuture<Object> d = new java.util.concurrent.CompletableFuture<Object>();
        if (lo <= hi) {
            java.util.concurrent.CompletableFuture<?> a, b;
            int mid = (lo + hi) >>> 1;
            if ((a = (lo == mid ? cfs[lo] :
                    orTree(cfs, lo, mid))) == null ||
                    (b = (lo == hi ? a : (hi == mid+1) ? cfs[hi] :
                            orTree(cfs, mid+1, hi)))  == null)
                throw new NullPointerException();
            if (!d.orRelay(a, b)) {
                java.util.concurrent.CompletableFuture.OrRelay<?,?> c = new java.util.concurrent.CompletableFuture.OrRelay<>(d, a, b);
                a.orpush(b, c);
                c.tryFire(SYNC);
            }
        }
        return d;
    }

    *//* ------------- Zero-input Async forms -------------- *//*

    @SuppressWarnings("serial")
    static final class AsyncSupply<T> extends ForkJoinTask<Void>
            implements Runnable, java.util.concurrent.CompletableFuture.AsynchronousCompletionTask {
        java.util.concurrent.CompletableFuture<T> dep; Supplier<T> fn;
        AsyncSupply(java.util.concurrent.CompletableFuture<T> dep, Supplier<T> fn) {
            this.dep = dep; this.fn = fn;
        }

        public final Void getRawResult() { return null; }
        public final void setRawResult(Void v) {}
        public final boolean exec() { run(); return true; }

        public void run() {
            java.util.concurrent.CompletableFuture<T> d; Supplier<T> f;
            if ((d = dep) != null && (f = fn) != null) {
                dep = null; fn = null;
                if (d.result == null) {
                    try {
                        d.completeValue(f.get());
                    } catch (Throwable ex) {
                        d.completeThrowable(ex);
                    }
                }
                d.postComplete();
            }
        }
    }

    static <U> java.util.concurrent.CompletableFuture<U> asyncSupplyStage(Executor e,
                                                                          Supplier<U> f) {
        if (f == null) throw new NullPointerException();
        java.util.concurrent.CompletableFuture<U> d = new java.util.concurrent.CompletableFuture<U>();
        e.execute(new java.util.concurrent.CompletableFuture.AsyncSupply<U>(d, f));
        return d;
    }

    @SuppressWarnings("serial")
    static final class AsyncRun extends ForkJoinTask<Void>
            implements Runnable, java.util.concurrent.CompletableFuture.AsynchronousCompletionTask {
        java.util.concurrent.CompletableFuture<Void> dep; Runnable fn;
        AsyncRun(java.util.concurrent.CompletableFuture<Void> dep, Runnable fn) {
            this.dep = dep; this.fn = fn;
        }

        public final Void getRawResult() { return null; }
        public final void setRawResult(Void v) {}
        public final boolean exec() { run(); return true; }

        public void run() {
            java.util.concurrent.CompletableFuture<Void> d; Runnable f;
            if ((d = dep) != null && (f = fn) != null) {
                dep = null; fn = null;
                if (d.result == null) {
                    try {
                        f.run();
                        d.completeNull();
                    } catch (Throwable ex) {
                        d.completeThrowable(ex);
                    }
                }
                d.postComplete();
            }
        }
    }

    static java.util.concurrent.CompletableFuture<Void> asyncRunStage(Executor e, Runnable f) {
        if (f == null) throw new NullPointerException();
        java.util.concurrent.CompletableFuture<Void> d = new java.util.concurrent.CompletableFuture<Void>();
        e.execute(new java.util.concurrent.CompletableFuture.AsyncRun(d, f));
        return d;
    }

    *//* ------------- Signallers -------------- *//*

    *//**
     * Completion for recording and releasing a waiting thread.  This
     * class implements ManagedBlocker to avoid starvation when
     * blocking actions pile up in ForkJoinPools.
     *//*
    @SuppressWarnings("serial")
    static final class Signaller extends java.util.concurrent.CompletableFuture.Completion
            implements ForkJoinPool.ManagedBlocker {
        long nanos;                    // wait time if timed
        final long deadline;           // non-zero if timed
        volatile int interruptControl; // > 0: interruptible, < 0: interrupted
        volatile Thread thread;

        Signaller(boolean interruptible, long nanos, long deadline) {
            this.thread = Thread.currentThread();
            this.interruptControl = interruptible ? 1 : 0;
            this.nanos = nanos;
            this.deadline = deadline;
        }
        final java.util.concurrent.CompletableFuture<?> tryFire(int ignore) {
            Thread w; // no need to atomically claim
            if ((w = thread) != null) {
                thread = null;
                LockSupport.unpark(w);
            }
            return null;
        }
        public boolean isReleasable() {
            if (thread == null)
                return true;
            if (Thread.interrupted()) {
                int i = interruptControl;
                interruptControl = -1;
                if (i > 0)
                    return true;
            }
            if (deadline != 0L &&
                    (nanos <= 0L || (nanos = deadline - System.nanoTime()) <= 0L)) {
                thread = null;
                return true;
            }
            return false;
        }
        public boolean block() {
            if (isReleasable())
                return true;
            else if (deadline == 0L)
                LockSupport.park(this);
            else if (nanos > 0L)
                LockSupport.parkNanos(this, nanos);
            return isReleasable();
        }
        final boolean isLive() { return thread != null; }
    }

    *//**
     * Returns raw result after waiting, or null if interruptible and
     * interrupted.
     *//*
    private Object waitingGet(boolean interruptible) {
        java.util.concurrent.CompletableFuture.Signaller q = null;
        boolean queued = false;
        int spins = -1;
        Object r;
        while ((r = result) == null) {
            if (spins < 0)
                spins = (Runtime.getRuntime().availableProcessors() > 1) ?
                        1 << 8 : 0; // Use brief spin-wait on multiprocessors
            else if (spins > 0) {
                if (ThreadLocalRandom.nextSecondarySeed() >= 0)
                    --spins;
            }
            else if (q == null)
                q = new java.util.concurrent.CompletableFuture.Signaller(interruptible, 0L, 0L);
            else if (!queued)
                queued = tryPushStack(q);
            else if (interruptible && q.interruptControl < 0) {
                q.thread = null;
                cleanStack();
                return null;
            }
            else if (q.thread != null && result == null) {
                try {
                    ForkJoinPool.managedBlock(q);
                } catch (InterruptedException ie) {
                    q.interruptControl = -1;
                }
            }
        }
        if (q != null) {
            q.thread = null;
            if (q.interruptControl < 0) {
                if (interruptible)
                    r = null; // report interruption
                else
                    Thread.currentThread().interrupt();
            }
        }
        postComplete();
        return r;
    }

    *//**
     * Returns raw result after waiting, or null if interrupted, or
     * throws TimeoutException on timeout.
     *//*
    private Object timedGet(long nanos) throws TimeoutException {
        if (Thread.interrupted())
            return null;
        if (nanos <= 0L)
            throw new TimeoutException();
        long d = System.nanoTime() + nanos;
        java.util.concurrent.CompletableFuture.Signaller q = new java.util.concurrent.CompletableFuture.Signaller(true, nanos, d == 0L ? 1L : d); // avoid 0
        boolean queued = false;
        Object r;
        // We intentionally don't spin here (as waitingGet does) because
        // the call to nanoTime() above acts much like a spin.
        while ((r = result) == null) {
            if (!queued)
                queued = tryPushStack(q);
            else if (q.interruptControl < 0 || q.nanos <= 0L) {
                q.thread = null;
                cleanStack();
                if (q.interruptControl < 0)
                    return null;
                throw new TimeoutException();
            }
            else if (q.thread != null && result == null) {
                try {
                    ForkJoinPool.managedBlock(q);
                } catch (InterruptedException ie) {
                    q.interruptControl = -1;
                }
            }
        }
        if (q.interruptControl < 0)
            r = null;
        q.thread = null;
        postComplete();
        return r;
    }

    *//* ------------- public methods -------------- *//*

    *//**
     * Creates a new incomplete _CompletableFuture.
     *//*
    public _CompletableFuture() {
    }

    *//**
     * Creates a new complete _CompletableFuture with given encoded result.
     *//*
    private _CompletableFuture(Object r) {
        this.result = r;
    }

    *//**
     * Returns a new _CompletableFuture that is asynchronously completed
     * by a task running in the {@link ForkJoinPool#commonPool()} with
     * the value obtained by calling the given Supplier.
     *
     * @param supplier a function returning the value to be used
     * to complete the returned _CompletableFuture
     * @param <U> the function's return type
     * @return the new _CompletableFuture
     *//*
    public static <U> java.util.concurrent.CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
        return asyncSupplyStage(asyncPool, supplier);
    }

    *//**
     * Returns a new _CompletableFuture that is asynchronously completed
     * by a task running in the given executor with the value obtained
     * by calling the given Supplier.
     *
     * @param supplier a function returning the value to be used
     * to complete the returned _CompletableFuture
     * @param executor the executor to use for asynchronous execution
     * @param <U> the function's return type
     * @return the new _CompletableFuture
     *//*
    public static <U> java.util.concurrent.CompletableFuture<U> supplyAsync(Supplier<U> supplier,
                                                                            Executor executor) {
        return asyncSupplyStage(screenExecutor(executor), supplier);
    }

    *//**
     * Returns a new _CompletableFuture that is asynchronously completed
     * by a task running in the {@link ForkJoinPool#commonPool()} after
     * it runs the given action.
     *
     * @param runnable the action to run before completing the
     * returned _CompletableFuture
     * @return the new _CompletableFuture
     *//*
    public static java.util.concurrent.CompletableFuture<Void> runAsync(Runnable runnable) {
        return asyncRunStage(asyncPool, runnable);
    }

    *//**
     * Returns a new _CompletableFuture that is asynchronously completed
     * by a task running in the given executor after it runs the given
     * action.
     *
     * @param runnable the action to run before completing the
     * returned _CompletableFuture
     * @param executor the executor to use for asynchronous execution
     * @return the new _CompletableFuture
     *//*
    public static java.util.concurrent.CompletableFuture<Void> runAsync(Runnable runnable,
                                                                        Executor executor) {
        return asyncRunStage(screenExecutor(executor), runnable);
    }

    *//**
     * Returns a new _CompletableFuture that is already completed with
     * the given value.
     *
     * @param value the value
     * @param <U> the type of the value
     * @return the completed _CompletableFuture
     *//*
    public static <U> java.util.concurrent.CompletableFuture<U> completedFuture(U value) {
        return new java.util.concurrent.CompletableFuture<U>((value == null) ? NIL : value);
    }

    *//**
     * Returns {@code true} if completed in any fashion: normally,
     * exceptionally, or via cancellation.
     *
     * @return {@code true} if completed
     *//*
    public boolean isDone() {
        return result != null;
    }

    *//**
     * Waits if necessary for this future to complete, and then
     * returns its result.
     *
     * @return the result value
     * @throws CancellationException if this future was cancelled
     * @throws ExecutionException if this future completed exceptionally
     * @throws InterruptedException if the current thread was interrupted
     * while waiting
     *//*
    public T get() throws InterruptedException, ExecutionException {
        Object r;
        return reportGet((r = result) == null ? waitingGet(true) : r);
    }

    *//**
     * Waits if necessary for at most the given time for this future
     * to complete, and then returns its result, if available.
     *
     * @param timeout the maximum time to wait
     * @param unit the time unit of the timeout argument
     * @return the result value
     * @throws CancellationException if this future was cancelled
     * @throws ExecutionException if this future completed exceptionally
     * @throws InterruptedException if the current thread was interrupted
     * while waiting
     * @throws TimeoutException if the wait timed out
     *//*
    public T get(long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        Object r;
        long nanos = unit.toNanos(timeout);
        return reportGet((r = result) == null ? timedGet(nanos) : r);
    }

    *//**
     * Returns the result value when complete, or throws an
     * (unchecked) exception if completed exceptionally. To better
     * conform with the use of common functional forms, if a
     * computation involved in the completion of this
     * _CompletableFuture threw an exception, this method throws an
     * (unchecked) {@link CompletionException} with the underlying
     * exception as its cause.
     *
     * @return the result value
     * @throws CancellationException if the computation was cancelled
     * @throws CompletionException if this future completed
     * exceptionally or a completion computation threw an exception
     *//*
    public T join() {
        Object r;
        return reportJoin((r = result) == null ? waitingGet(false) : r);
    }

    *//**
     * Returns the result value (or throws any encountered exception)
     * if completed, else returns the given valueIfAbsent.
     *
     * @param valueIfAbsent the value to return if not completed
     * @return the result value, if completed, else the given valueIfAbsent
     * @throws CancellationException if the computation was cancelled
     * @throws CompletionException if this future completed
     * exceptionally or a completion computation threw an exception
     *//*
    public T getNow(T valueIfAbsent) {
        Object r;
        return ((r = result) == null) ? valueIfAbsent : reportJoin(r);
    }

    *//**
     * If not already completed, sets the value returned by {@link
     * #get()} and related methods to the given value.
     *
     * @param value the result value
     * @return {@code true} if this invocation caused this _CompletableFuture
     * to transition to a completed state, else {@code false}
     *//*
    public boolean complete(T value) {
        boolean triggered = completeValue(value);
        postComplete();
        return triggered;
    }

    *//**
     * If not already completed, causes invocations of {@link #get()}
     * and related methods to throw the given exception.
     *
     * @param ex the exception
     * @return {@code true} if this invocation caused this _CompletableFuture
     * to transition to a completed state, else {@code false}
     *//*
    public boolean completeExceptionally(Throwable ex) {
        if (ex == null) throw new NullPointerException();
        boolean triggered = internalComplete(new java.util.concurrent.CompletableFuture.AltResult(ex));
        postComplete();
        return triggered;
    }

    public <U> java.util.concurrent.CompletableFuture<U> thenApply(
            Function<? super T,? extends U> fn) {
        return uniApplyStage(null, fn);
    }

    public <U> java.util.concurrent.CompletableFuture<U> thenApplyAsync(
            Function<? super T,? extends U> fn) {
        return uniApplyStage(asyncPool, fn);
    }

    public <U> java.util.concurrent.CompletableFuture<U> thenApplyAsync(
            Function<? super T,? extends U> fn, Executor executor) {
        return uniApplyStage(screenExecutor(executor), fn);
    }

    public java.util.concurrent.CompletableFuture<Void> thenAccept(Consumer<? super T> action) {
        return uniAcceptStage(null, action);
    }

    public java.util.concurrent.CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> action) {
        return uniAcceptStage(asyncPool, action);
    }

    public java.util.concurrent.CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> action,
                                                                        Executor executor) {
        return uniAcceptStage(screenExecutor(executor), action);
    }

    public java.util.concurrent.CompletableFuture<Void> thenRun(Runnable action) {
        return uniRunStage(null, action);
    }

    public java.util.concurrent.CompletableFuture<Void> thenRunAsync(Runnable action) {
        return uniRunStage(asyncPool, action);
    }

    public java.util.concurrent.CompletableFuture<Void> thenRunAsync(Runnable action,
                                                                     Executor executor) {
        return uniRunStage(screenExecutor(executor), action);
    }

    public <U,V> java.util.concurrent.CompletableFuture<V> thenCombine(
            CompletionStage<? extends U> other,
            BiFunction<? super T,? super U,? extends V> fn) {
        return biApplyStage(null, other, fn);
    }

    public <U,V> java.util.concurrent.CompletableFuture<V> thenCombineAsync(
            CompletionStage<? extends U> other,
            BiFunction<? super T,? super U,? extends V> fn) {
        return biApplyStage(asyncPool, other, fn);
    }

    public <U,V> java.util.concurrent.CompletableFuture<V> thenCombineAsync(
            CompletionStage<? extends U> other,
            BiFunction<? super T,? super U,? extends V> fn, Executor executor) {
        return biApplyStage(screenExecutor(executor), other, fn);
    }

    public <U> java.util.concurrent.CompletableFuture<Void> thenAcceptBoth(
            CompletionStage<? extends U> other,
            BiConsumer<? super T, ? super U> action) {
        return biAcceptStage(null, other, action);
    }

    public <U> java.util.concurrent.CompletableFuture<Void> thenAcceptBothAsync(
            CompletionStage<? extends U> other,
            BiConsumer<? super T, ? super U> action) {
        return biAcceptStage(asyncPool, other, action);
    }

    public <U> java.util.concurrent.CompletableFuture<Void> thenAcceptBothAsync(
            CompletionStage<? extends U> other,
            BiConsumer<? super T, ? super U> action, Executor executor) {
        return biAcceptStage(screenExecutor(executor), other, action);
    }

    public java.util.concurrent.CompletableFuture<Void> runAfterBoth(CompletionStage<?> other,
                                                                     Runnable action) {
        return biRunStage(null, other, action);
    }

    public java.util.concurrent.CompletableFuture<Void> runAfterBothAsync(CompletionStage<?> other,
                                                                          Runnable action) {
        return biRunStage(asyncPool, other, action);
    }

    public java.util.concurrent.CompletableFuture<Void> runAfterBothAsync(CompletionStage<?> other,
                                                                          Runnable action,
                                                                          Executor executor) {
        return biRunStage(screenExecutor(executor), other, action);
    }

    public <U> java.util.concurrent.CompletableFuture<U> applyToEither(
            CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return orApplyStage(null, other, fn);
    }

    public <U> java.util.concurrent.CompletableFuture<U> applyToEitherAsync(
            CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return orApplyStage(asyncPool, other, fn);
    }

    public <U> java.util.concurrent.CompletableFuture<U> applyToEitherAsync(
            CompletionStage<? extends T> other, Function<? super T, U> fn,
            Executor executor) {
        return orApplyStage(screenExecutor(executor), other, fn);
    }

    public java.util.concurrent.CompletableFuture<Void> acceptEither(
            CompletionStage<? extends T> other, Consumer<? super T> action) {
        return orAcceptStage(null, other, action);
    }

    public java.util.concurrent.CompletableFuture<Void> acceptEitherAsync(
            CompletionStage<? extends T> other, Consumer<? super T> action) {
        return orAcceptStage(asyncPool, other, action);
    }

    public java.util.concurrent.CompletableFuture<Void> acceptEitherAsync(
            CompletionStage<? extends T> other, Consumer<? super T> action,
            Executor executor) {
        return orAcceptStage(screenExecutor(executor), other, action);
    }

    public java.util.concurrent.CompletableFuture<Void> runAfterEither(CompletionStage<?> other,
                                                                       Runnable action) {
        return orRunStage(null, other, action);
    }

    public java.util.concurrent.CompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> other,
                                                                            Runnable action) {
        return orRunStage(asyncPool, other, action);
    }

    public java.util.concurrent.CompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> other,
                                                                            Runnable action,
                                                                            Executor executor) {
        return orRunStage(screenExecutor(executor), other, action);
    }

    public <U> java.util.concurrent.CompletableFuture<U> thenCompose(
            Function<? super T, ? extends CompletionStage<U>> fn) {
        return uniComposeStage(null, fn);
    }

    public <U> java.util.concurrent.CompletableFuture<U> thenComposeAsync(
            Function<? super T, ? extends CompletionStage<U>> fn) {
        return uniComposeStage(asyncPool, fn);
    }

    public <U> java.util.concurrent.CompletableFuture<U> thenComposeAsync(
            Function<? super T, ? extends CompletionStage<U>> fn,
            Executor executor) {
        return uniComposeStage(screenExecutor(executor), fn);
    }

    public java.util.concurrent.CompletableFuture<T> whenComplete(
            BiConsumer<? super T, ? super Throwable> action) {
        return uniWhenCompleteStage(null, action);
    }

    public java.util.concurrent.CompletableFuture<T> whenCompleteAsync(
            BiConsumer<? super T, ? super Throwable> action) {
        return uniWhenCompleteStage(asyncPool, action);
    }

    public java.util.concurrent.CompletableFuture<T> whenCompleteAsync(
            BiConsumer<? super T, ? super Throwable> action, Executor executor) {
        return uniWhenCompleteStage(screenExecutor(executor), action);
    }

    public <U> java.util.concurrent.CompletableFuture<U> handle(
            BiFunction<? super T, Throwable, ? extends U> fn) {
        return uniHandleStage(null, fn);
    }

    public <U> java.util.concurrent.CompletableFuture<U> handleAsync(
            BiFunction<? super T, Throwable, ? extends U> fn) {
        return uniHandleStage(asyncPool, fn);
    }

    public <U> java.util.concurrent.CompletableFuture<U> handleAsync(
            BiFunction<? super T, Throwable, ? extends U> fn, Executor executor) {
        return uniHandleStage(screenExecutor(executor), fn);
    }

    *//**
     * Returns this _CompletableFuture.
     *
     * @return this _CompletableFuture
     *//*
    public java.util.concurrent.CompletableFuture<T> toCompletableFuture() {
        return this;
    }

    // not in interface _CompletionStage

    *//**
     * Returns a new _CompletableFuture that is completed when this
     * _CompletableFuture completes, with the result of the given
     * function of the exception triggering this _CompletableFuture's
     * completion when it completes exceptionally; otherwise, if this
     * _CompletableFuture completes normally, then the returned
     * _CompletableFuture also completes normally with the same value.
     * Note: More flexible versions of this functionality are
     * available using methods {@code whenComplete} and {@code handle}.
     *
     * @param fn the function to use to compute the value of the
     * returned _CompletableFuture if this _CompletableFuture completed
     * exceptionally
     * @return the new _CompletableFuture
     *//*
    public java.util.concurrent.CompletableFuture<T> exceptionally(
            Function<Throwable, ? extends T> fn) {
        return uniExceptionallyStage(fn);
    }

    *//* ------------- Arbitrary-arity constructions -------------- *//*

    *//**
     * Returns a new _CompletableFuture that is completed when all of
     * the given CompletableFutures complete.  If any of the given
     * CompletableFutures complete exceptionally, then the returned
     * _CompletableFuture also does so, with a CompletionException
     * holding this exception as its cause.  Otherwise, the results,
     * if any, of the given CompletableFutures are not reflected in
     * the returned _CompletableFuture, but may be obtained by
     * inspecting them individually. If no CompletableFutures are
     * provided, returns a _CompletableFuture completed with the value
     * {@code null}.
     *
     * <p>Among the applications of this method is to await completion
     * of a set of independent CompletableFutures before continuing a
     * program, as in: {@code _CompletableFuture.allOf(c1, c2,
     * c3).join();}.
     *
     * @param cfs the CompletableFutures
     * @return a new _CompletableFuture that is completed when all of the
     * given CompletableFutures complete
     * @throws NullPointerException if the array or any of its elements are
     * {@code null}
     *//*
    public static java.util.concurrent.CompletableFuture<Void> allOf(java.util.concurrent.CompletableFuture<?>... cfs) {
        return andTree(cfs, 0, cfs.length - 1);
    }

    *//**
     * Returns a new _CompletableFuture that is completed when any of
     * the given CompletableFutures complete, with the same result.
     * Otherwise, if it completed exceptionally, the returned
     * _CompletableFuture also does so, with a CompletionException
     * holding this exception as its cause.  If no CompletableFutures
     * are provided, returns an incomplete _CompletableFuture.
     *
     * @param cfs the CompletableFutures
     * @return a new _CompletableFuture that is completed with the
     * result or exception of any of the given CompletableFutures when
     * one completes
     * @throws NullPointerException if the array or any of its elements are
     * {@code null}
     *//*
    public static java.util.concurrent.CompletableFuture<Object> anyOf(java.util.concurrent.CompletableFuture<?>... cfs) {
        return orTree(cfs, 0, cfs.length - 1);
    }

    *//* ------------- Control and status methods -------------- *//*

    *//**
     * If not already completed, completes this _CompletableFuture with
     * a {@link CancellationException}. Dependent CompletableFutures
     * that have not already completed will also complete
     * exceptionally, with a {@link CompletionException} caused by
     * this {@code CancellationException}.
     *
     * @param mayInterruptIfRunning this value has no effect in this
     * implementation because interrupts are not used to control
     * processing.
     *
     * @return {@code true} if this task is now cancelled
     *//*
    public boolean cancel(boolean mayInterruptIfRunning) {
        boolean cancelled = (result == null) &&
                internalComplete(new java.util.concurrent.CompletableFuture.AltResult(new CancellationException()));
        postComplete();
        return cancelled || isCancelled();
    }

    *//**
     * Returns {@code true} if this _CompletableFuture was cancelled
     * before it completed normally.
     *
     * @return {@code true} if this _CompletableFuture was cancelled
     * before it completed normally
     *//*
    public boolean isCancelled() {
        Object r;
        return ((r = result) instanceof java.util.concurrent.CompletableFuture.AltResult) &&
                (((java.util.concurrent.CompletableFuture.AltResult)r).ex instanceof CancellationException);
    }

    *//**
     * Returns {@code true} if this _CompletableFuture completed
     * exceptionally, in any way. Possible causes include
     * cancellation, explicit invocation of {@code
     * completeExceptionally}, and abrupt termination of a
     * _CompletionStage action.
     *
     * @return {@code true} if this _CompletableFuture completed
     * exceptionally
     *//*
    public boolean isCompletedExceptionally() {
        Object r;
        return ((r = result) instanceof java.util.concurrent.CompletableFuture.AltResult) && r != NIL;
    }

    *//**
     * Forcibly sets or resets the value subsequently returned by
     * method {@link #get()} and related methods, whether or not
     * already completed. This method is designed for use only in
     * error recovery actions, and even in such situations may result
     * in ongoing dependent completions using established versus
     * overwritten outcomes.
     *
     * @param value the completion value
     *//*
    public void obtrudeValue(T value) {
        result = (value == null) ? NIL : value;
        postComplete();
    }

    *//**
     * Forcibly causes subsequent invocations of method {@link #get()}
     * and related methods to throw the given exception, whether or
     * not already completed. This method is designed for use only in
     * error recovery actions, and even in such situations may result
     * in ongoing dependent completions using established versus
     * overwritten outcomes.
     *
     * @param ex the exception
     * @throws NullPointerException if the exception is null
     *//*
    public void obtrudeException(Throwable ex) {
        if (ex == null) throw new NullPointerException();
        result = new java.util.concurrent.CompletableFuture.AltResult(ex);
        postComplete();
    }

    *//**
     * Returns the estimated number of CompletableFutures whose
     * completions are awaiting completion of this _CompletableFuture.
     * This method is designed for use in monitoring system state, not
     * for synchronization control.
     *
     * @return the number of dependent CompletableFutures
     *//*
    public int getNumberOfDependents() {
        int count = 0;
        for (java.util.concurrent.CompletableFuture.Completion p = stack; p != null; p = p.next)
            ++count;
        return count;
    }

    *//**
     * Returns a string identifying this _CompletableFuture, as well as
     * its completion state.  The state, in brackets, contains the
     * String {@code "Completed Normally"} or the String {@code
     * "Completed Exceptionally"}, or the String {@code "Not
     * completed"} followed by the number of CompletableFutures
     * dependent upon its completion, if any.
     *
     * @return a string identifying this _CompletableFuture, as well as its state
     *//*
    public String toString() {
        Object r = result;
        int count;
        return super.toString() +
                ((r == null) ?
                        (((count = getNumberOfDependents()) == 0) ?
                                "[Not completed]" :
                                "[Not completed, " + count + " dependents]") :
                        (((r instanceof java.util.concurrent.CompletableFuture.AltResult) && ((java.util.concurrent.CompletableFuture.AltResult)r).ex != null) ?
                                "[Completed exceptionally]" :
                                "[Completed normally]"));
    }

    // Unsafe mechanics
    private static final sun.misc.Unsafe UNSAFE;
    private static final long RESULT;
    private static final long STACK;
    private static final long NEXT;
    static {
        try {
            final sun.misc.Unsafe u;
            UNSAFE = u = sun.misc.Unsafe.getUnsafe();
            Class<?> k = java.util.concurrent.CompletableFuture.class;
            RESULT = u.objectFieldOffset(k.getDeclaredField("result"));
            STACK = u.objectFieldOffset(k.getDeclaredField("stack"));
            NEXT = u.objectFieldOffset
                    (java.util.concurrent.CompletableFuture.Completion.class.getDeclaredField("next"));
        } catch (Exception x) {
            throw new Error(x);
        }
    }
}*/

