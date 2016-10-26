/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Zero Alpha Technology Limited
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package technology.zeroalpha.zaes.aggregate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import technology.zeroalpha.zaes.event.Event;
import technology.zeroalpha.zaes.event.EventService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 * Unit tests to ensure the correct operation of {@link AggregateService}.
 */
@RunWith(MockitoJUnitRunner.class)
public class AggregateServiceTest {

    /** Event Stream Identifier to use in tests. */
    private static final String EVENT_STREAM_ID = UUID.randomUUID().toString();

    /** Mocked {@link EventService}. */
    @Mock
    private EventService mockEventService;

    /** Instance of {@link AggregateService} to test. */
    private AggregateService<EventCapturingAggregate> serviceUnderTest;

    /**
     * Configure the service being tested with its dependencies.
     */
    @Before
    public void configureService() {
        this.serviceUnderTest = new AggregateService<>(EventCapturingAggregate.class, mockEventService);
    }

    /**
     * Ensure that calls to {@link AggregateService#buildLatestAggregate(String)} constructs a new {@link Aggregate} of
     * the appropriate type, using all events in identified stream.
     */
    @Test
    public void buildLatestAggregate_retrieves_and_applies_all_events_for_aggregate() {
        // Given
        final Event mockEvent = mock(Event.class);

        when(mockEventService.retrieveAllEvents(EVENT_STREAM_ID)).thenReturn(Collections.singletonList(mockEvent));

        // When
        final EventCapturingAggregate eventCapturingAggregate = serviceUnderTest.buildLatestAggregate(EVENT_STREAM_ID);

        // Then
        final List<Event> capturedEvents = eventCapturingAggregate.getCapturedEvents();
        assertThat(capturedEvents).containsExactly(mockEvent);
    }
}
