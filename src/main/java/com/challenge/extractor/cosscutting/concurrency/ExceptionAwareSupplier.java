package com.challenge.extractor.cosscutting.concurrency;

import java.util.function.Supplier;

/**
 * Supplier to manage errors in threads.
 *
 * @param <T> generic type of exception
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-07
 */
public interface ExceptionAwareSupplier<T> extends Supplier<T> {

  @Override
  @SuppressWarnings("unchecked")
  default T get() {
    try {
      return process();
    } catch (Exception e) {
      return (T) e;
    }
  }

  T process() throws Exception;
}
