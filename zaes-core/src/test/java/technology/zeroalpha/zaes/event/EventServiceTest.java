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
package technology.zeroalpha.zaes.event;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
/**
 * Unit tests to ensure the correct operation of {@link EventService}.
 */
@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    /** Identifier of event stream to use in tests. */
    private static final String EVENT_STREAM_ID = UUID.randomUUID().toString();

    /** Mocked {@link EventRepository}. */
    @Mock
    private EventRepository mockEventRepository;

    /** Instance of {@link EventService} to test. */
    private EventService serviceUnderTest;

    /**
     * Populate service being tested with its dependencies.
     */
    @Before
    public void configureServiceBeingTested() {
        this.serviceUnderTest = new EventService(mockEventRepository);
    }

    /**
     * Ensure that calls to {@link EventService#retrieveAllEvents(String)} delegate correctly to
     * {@link EventRepository}, and return the received Event Stream.
     */
    @Test
    public void retrieveAllEvents_correctly_delegates_to_repository() {
        // Given
        final Event mockEvent = mock(Event.class);
        final List<Event> mockEventStream = Collections.singletonList(mockEvent);

        when(mockEventRepository.retrieveEventStream(EVENT_STREAM_ID)).thenReturn(mockEventStream);

        // When
        final List<Event> eventStream = serviceUnderTest.retrieveAllEvents(EVENT_STREAM_ID);

        // Then
        assertThat(eventStream).isSameAs(mockEventStream);
    }

    /**
     * Ensure that calls to {@link EventService#retrieveAllEvents(String)} return an empty collection if provided stream
     * id is null.
     */
    @Test
    public void retrieveAllEvents_returns_empty_collection_if_stream_id_is_null() {
        // When
        final List<Event> eventStream = serviceUnderTest.retrieveAllEvents(null);

        // Then
        verifyZeroInteractions(mockEventRepository);
        assertThat(eventStream).isNotNull().isEmpty();
    }

    /**
     * Ensure that calls to {@link EventService#retrieveAllEvents(String)} return an empty collection if delegated call
     * to {@link EventRepository} returns null.
     */
    @Test
    public void retrieveAllEvents_returns_empty_collection_if_repository_returns_null() {
        // Given
        when(mockEventRepository.retrieveEventStream(EVENT_STREAM_ID)).thenReturn(null);

        // When
        final List<Event> eventStream = serviceUnderTest.retrieveAllEvents(EVENT_STREAM_ID);

        // Then
        assertThat(eventStream).isNotNull().isEmpty();
    }

    /**
     * Ensure that calls to {@link EventService#retrieveEventsToSequenceNumber(String, int)} delegate correctly to
     * {@link EventRepository}, and return the received Event Stream.
     */
    @Test
    public void retrieveEventsToSequenceNumber_correctly_delegates_to_repository() {
        // Given
        final int sequenceNumber = 5;

        final Event mockEvent = mock(Event.class);
        final List<Event> mockEventStream = Collections.singletonList(mockEvent);

        when(mockEventRepository.retrieveEventStream(EVENT_STREAM_ID, Integer.MIN_VALUE, sequenceNumber))
                .thenReturn(mockEventStream);

        // When
        final List<Event> eventStream =
                serviceUnderTest.retrieveEventsToSequenceNumber(EVENT_STREAM_ID, sequenceNumber);

        // Then
        assertThat(eventStream).isSameAs(mockEventStream);
    }

    /**
     * Ensure that calls to {@link EventService#retrieveEventsToSequenceNumber(String, int)} return an empty collection
     * if provided stream id is null.
     */
    @Test
    public void retrieveEventsToSequenceNumber_returns_empty_collection_if_stream_id_is_null() {
        // Given
        final int sequenceNumber = 5;

        // When
        final List<Event> eventStream = serviceUnderTest.retrieveEventsToSequenceNumber(null, sequenceNumber);

        // Then
        assertThat(eventStream).isNotNull().isEmpty();
    }

    /**
     * Ensure that calls to {@link EventService#retrieveEventsToSequenceNumber(String, int)} return an empty collection
     * if delegated call to {@link EventRepository} returns null.
     */
    @Test
    public void retrieveEventsToSequenceNumber_returns_empty_collection_if_repository_returns_null() {
        // Given
        final int sequenceNumber = 5;

        when(mockEventRepository.retrieveEventStream(EVENT_STREAM_ID, 0, sequenceNumber)).thenReturn(null);

        // When
        final List<Event> eventStream =
                serviceUnderTest.retrieveEventsToSequenceNumber(EVENT_STREAM_ID, sequenceNumber);

        // Then
        assertThat(eventStream).isNotNull().isEmpty();
    }

    /**
     * Ensure that calls to {@link EventService#retrieveEventsFromSequenceNumber(String, int)} delegate correctly to
     * {@link EventRepository}, and return the received Event Stream.
     */
    @Test
    public void retrieveEventsFromSequenceNumber_correctly_delegates_to_repository() {
        // Given
        final int sequenceNumber = 5;

        final Event mockEvent = mock(Event.class);
        final List<Event> mockEventStream = Collections.singletonList(mockEvent);

        when(mockEventRepository.retrieveEventStream(EVENT_STREAM_ID, sequenceNumber, Integer.MAX_VALUE))
                .thenReturn(mockEventStream);

        // When
        final List<Event> eventStream =
                serviceUnderTest.retrieveEventsFromSequenceNumber(EVENT_STREAM_ID, sequenceNumber);

        // Then
        assertThat(eventStream).isSameAs(mockEventStream);
    }

    /**
     * Ensure that calls to {@link EventService#retrieveEventsFromSequenceNumber(String, int)} return an empty
     * collection if provided stream id is null.
     */
    @Test
    public void retrieveEventsFromSequenceNumber_returns_empty_collection_if_stream_id_is_null() {
        // Given
        final int sequenceNumber = 5;

        // When
        final List<Event> eventStream = serviceUnderTest.retrieveEventsFromSequenceNumber(null, sequenceNumber);

        // Then
        assertThat(eventStream).isNotNull().isEmpty();
    }

    /**
     * Ensure that calls to {@link EventService#retrieveEventsFromSequenceNumber(String, int)} return an empty
     * collection if delegated call to {@link EventRepository} returns null.
     */
    @Test
    public void retrieveEventsFromSequenceNumber_returns_empty_collection_if_repository_returns_null() {
        // Given
        final int sequenceNumber = 5;

        when(mockEventRepository.retrieveEventStream(EVENT_STREAM_ID, sequenceNumber, Integer.MAX_VALUE))
                .thenReturn(null);

        // When
        final List<Event> eventStream =
                serviceUnderTest.retrieveEventsFromSequenceNumber(EVENT_STREAM_ID, sequenceNumber);

        // Then
        assertThat(eventStream).isNotNull().isEmpty();
    }
}
