package io.cloudslang.content.jclouds.execute;

import io.cloudslang.content.jclouds.entities.inputs.ServerIdentificationInputs;
import io.cloudslang.content.jclouds.entities.outputs.Outputs;
import io.cloudslang.content.jclouds.factory.ComputeFactory;
import io.cloudslang.content.jclouds.services.ComputeService;
import org.jclouds.rest.ResourceNotFoundException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by persdana on 7/6/2015.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StopServerExecutor.class, ComputeFactory.class})
public class StopServerExecutorTest {
    private static final String INVALID_SERVER_ID_EXCEPTION_MESSAGE = "{\"itemNotFound\": {\"message\": \"Instance not found\", \"code\": 404}}";
    private static final String REGION = "us-east-1";
    private static final String SERVER_ID = "i-578dde87";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Spy
    private StopServerExecutor toTest = new StopServerExecutor();

    @Mock
    ComputeService computeServiceMock;

    /**
     * Tests the execute method. Positive scenario.
     * @throws Exception
     */
    @Test
    public void testExecute() throws Exception {
        ServerIdentificationInputs intputs = Inputs.getServerIdentificationInputsForAmazon();
        PowerMockito.mockStatic(ComputeFactory.class);
        PowerMockito.doReturn(computeServiceMock).when(ComputeFactory.class, "getComputeService", intputs);
        Mockito.doReturn("").when(computeServiceMock).stop(REGION, SERVER_ID);

        Map<String, String> result = toTest.execute(intputs);

        Assert.assertEquals(result.get(Outputs.RETURN_CODE), "0");
        Assert.assertEquals(result.get(Outputs.RETURN_RESULT), "server stopped");
        verify(computeServiceMock).stop(REGION, SERVER_ID);
        verifyNoMoreInteractions(computeServiceMock);
    }

    @Test
    public void testExecuteNegativeScenario() throws Exception {
        exception.expect(org.jclouds.rest.ResourceNotFoundException.class);
        exception.expectMessage(INVALID_SERVER_ID_EXCEPTION_MESSAGE);

        ServerIdentificationInputs intputs = Inputs.getServerIdentificationInputsForAmazon();
        PowerMockito.mockStatic(ComputeFactory.class);
        PowerMockito.doReturn(computeServiceMock).when(ComputeFactory.class, "getComputeService", intputs);
        ResourceNotFoundException toThrow = new ResourceNotFoundException(INVALID_SERVER_ID_EXCEPTION_MESSAGE);
        Mockito.doThrow(toThrow).when(computeServiceMock).stop(REGION, SERVER_ID);

        toTest.execute(intputs);
    }
}
