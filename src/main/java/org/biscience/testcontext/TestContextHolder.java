package org.biscience.testcontext;

public class TestContextHolder {

    private static final ThreadLocal<TestContext> holder = new ThreadLocal<>();

    public static void init() {
        holder.set(new TestContext());
    }

    public static TestContext get() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }
}
