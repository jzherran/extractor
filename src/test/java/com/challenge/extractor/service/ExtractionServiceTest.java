package com.challenge.extractor.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.challenge.extractor.crosscutting.concurrency.ExceptionAwareSupplier;
import com.challenge.extractor.service.concurrency.ProcessParallelManager;
import com.challenge.extractor.service.generator.FileGenerator;
import com.challenge.extractor.utility.LogCaptorUtil;
import java.util.concurrent.ExecutorService;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-07
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProcessParallelManager.class, SystemUtils.class})
public class ExtractionServiceTest {

  private static final String FILE_OK = "src/test/resources/list_site_test.txt";
  private static final String FILE_FAIL = "src/test/resources/list_site_test_bad.txt";

  @Mock private ProcessParallelManager processParallelManager;
  @Mock private ExecutorService executorService;
  @Mock private FileGenerator fileGenerator;
  @InjectMocks private ExtractionService extractionService;

  private LogCaptorUtil captor = new LogCaptorUtil();

  @Before
  public void setup() {
    captor.setup();
  }

  @After
  public void clean() {
    captor.clean();
  }

  @Test
  @SuppressWarnings("unchecked")
  public final void testBasicExtractionSuccess() {
    try {
      PowerMockito.mockStatic(ProcessParallelManager.class);
      Whitebox.setInternalState(SystemUtils.class, "IS_OS_WINDOWS", false);

      PowerMockito.when(ProcessParallelManager.newInstance(any()))
          .thenReturn(processParallelManager);
      Mockito.doNothing()
          .when(processParallelManager)
          .addJob(anyString(), any(ExceptionAwareSupplier.class));
      Mockito.when(processParallelManager.process()).thenReturn(new int[] {29, 0});
      Mockito.when(fileGenerator.generate(anyString(), anyString(), anyList())).thenReturn(true);

      final Pair<String, int[]> result = extractionService.basicExtraction(FILE_OK);

      verify(processParallelManager, times(29))
          .addJob(anyString(), any(ExceptionAwareSupplier.class));

      assertNotNull(result);
      assertNotNull(result.getLeft());
      assertNotNull(result.getRight());
      assertEquals(29, result.getRight()[0]);
      assertEquals(0, result.getRight()[1]);
    } catch (Exception e) {
      fail(String.format("Failed tests with this exception %s", e.getMessage()));
    }
  }

  @Test
  @SuppressWarnings("unchecked")
  public final void testBasicExtractionInvalidUrls() {
    try {
      PowerMockito.mockStatic(ProcessParallelManager.class);
      Whitebox.setInternalState(SystemUtils.class, "IS_OS_WINDOWS", false);

      PowerMockito.when(ProcessParallelManager.newInstance(any()))
          .thenReturn(processParallelManager);
      Mockito.doNothing()
          .when(processParallelManager)
          .addJob(anyString(), any(ExceptionAwareSupplier.class));
      Mockito.when(processParallelManager.process()).thenReturn(new int[] {28, 0});
      Mockito.when(fileGenerator.generate(anyString(), anyString(), anyList())).thenReturn(true);

      final Pair<String, int[]> result = extractionService.basicExtraction(FILE_FAIL);

      verify(processParallelManager, times(28))
          .addJob(anyString(), any(ExceptionAwareSupplier.class));

      assertNotNull(result);
      assertNotNull(result.getLeft());
      assertNotNull(result.getRight());
      assertEquals(28, result.getRight()[0]);
      assertEquals(1, result.getRight()[1]);
    } catch (Exception e) {
      fail(String.format("Failed tests with this exception %s", e.getMessage()));
    }
  }
}
