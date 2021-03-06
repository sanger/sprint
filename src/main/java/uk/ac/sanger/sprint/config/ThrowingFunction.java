package uk.ac.sanger.sprint.config;

/**
 * A function that declares a thrown exception
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 * @param <E> the type of exception that may be thrown
 */
public interface ThrowingFunction<T, R, E extends Throwable> {
    /**
     * Applies this function to the given argument
     * @param t the function argument
     * @return the function result
     * @exception E an exception thrown by the function
     */
    R apply(T t) throws E;
}
