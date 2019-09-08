package com.challenge.extractor.cosscutting.concurrency;

import java.util.function.Supplier;

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
