/*
 ****************************************************************
 *
 * EQ RAISE - Commercial Underwriting Portal.
 *
 * © 2018, Equitable Bank All rights reserved.
 *
 ****************************************************************
 */

package com.challenge.extractor.utility;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.slf4j.Logger.ROOT_LOGGER_NAME;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import lombok.Getter;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;

/**
 * Junit util component for captors.
 *
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-07
 */
@Getter
@SuppressWarnings({"rawtypes", "unchecked"})
public class LogCaptorUtil {

  @Mock private Appender mockAppender;

  @Captor private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

  public LogCaptorUtil() {
    super();
    MockitoAnnotations.initMocks(this);
  }

  public void setup() {

    Logger logger = (Logger) LoggerFactory.getLogger(ROOT_LOGGER_NAME);
    logger.addAppender(mockAppender);
  }

  public void clean() {

    Logger logger = (Logger) LoggerFactory.getLogger(ROOT_LOGGER_NAME);
    logger.detachAppender(mockAppender);
  }

  public void assertForContent(
      final int times, final int logToAssert, final Level level, final String message) {

    verify(getMockAppender(), times(times)).doAppend(getCaptorLoggingEvent().capture());

    LoggingEvent le = getCaptorLoggingEvent().getAllValues().get(logToAssert);

    assertThat(le.getLevel(), equalTo(level));
    assertThat(le.getFormattedMessage(), equalTo(message));
  }

  public void assertForContent(final Level level, final String message) {
    for (LoggingEvent event : getCaptorLoggingEvent().getAllValues()) {
      if ((event.getLevel() == level) && (event.getFormattedMessage().equals(message))) {
        assertThat(event.getLevel(), equalTo(level));
        assertThat(event.getFormattedMessage(), equalTo(message));

        return;
      }
    }
    fail("expected log message was not found: " + message);
  }

  public void assertTotalExpectedLogs(final int times) {

    verify(getMockAppender(), times(times)).doAppend(getCaptorLoggingEvent().capture());
  }

  public void assertForContent(final int logToAssert, final Level level, final String message) {

    LoggingEvent le = getCaptorLoggingEvent().getAllValues().get(logToAssert);

    assertThat(le.getLevel(), equalTo(level));
    assertThat(le.getFormattedMessage(), equalTo(message));
  }
}
