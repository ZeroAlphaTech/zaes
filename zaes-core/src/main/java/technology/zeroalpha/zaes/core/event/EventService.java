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
package technology.zeroalpha.zaes.core.event;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;

/**
 * Service used to retrieve {@link Event} Streams from the underlying {@link EventRepository}.
 */
public class EventService {

    /** {@link EventRepository} implementation for retrieving {@link Event}s from the underlying store. */
    private final EventRepository eventRepository;

    /**
     * @param eventRepository Service offering access to underlying {@link Event} data store
     */
    public EventService(final EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Retrieve all {@link Event}s, in order, associated with the provided Event Stream identifier.
     *
     * @param eventStreamId Identifier of Event Stream
     * @return Ordered list of {@link Event}s
     */
    public List<Event> retrieveAllEvents(final String eventStreamId) {
        if(Optional.ofNullable(eventStreamId).isPresent()) {
            return Optional
                    .ofNullable(eventRepository.retrieveEventStream(eventStreamId))
                    .orElseGet(Collections::emptyList);
        }

        return Collections.emptyList();
    }

    /**
     * Retrieve all {@link Event}s, in order, associated with the provided Event Stream identifier, up to the given
     * sequence number (inclusive).
     *
     * @param eventStreamId Identifier of Event Stream
     * @param sequenceNumber Sequence number to retrieve events to
     * @return Ordered list of {@link Event}s
     */
    public List<Event> retrieveEventsToSequenceNumber(final String eventStreamId, final int sequenceNumber) {
        return retrieveEventsBetweenSequenceNumbers(eventStreamId, Integer.MIN_VALUE, sequenceNumber);
    }

    /**
     * Retrieve all {@link Event}s, in order, associated with the provided Event Stream identifier, starting at the
     * given sequence number (inclusive).
     *
     * @param eventStreamId Identifier of Event Stream
     * @param sequenceNumber Sequence number to retrieve events from
     * @return Ordered list of {@link Event}s
     */
    public List<Event> retrieveEventsFromSequenceNumber(final String eventStreamId, final int sequenceNumber) {
        return retrieveEventsBetweenSequenceNumbers(eventStreamId, sequenceNumber, Integer.MAX_VALUE);
    }

    /**
     * Retrieve all {@link Event}s, in order, associated with the provided Event Stream identifier, between the two
     * given sequence numbers (inclusive).
     *
     * @param eventStreamId Identifier of Event Stream
     * @param startSequenceNumber Sequence number to retrieve events from
     * @param endSequenceNumber Sequence number to retrieve events to
     * @return Ordered list of {@link Event}s
     */
    public List<Event> retrieveEventsBetweenSequenceNumbers(
            final String eventStreamId, final int startSequenceNumber, final int endSequenceNumber) {
        if(Optional.ofNullable(eventStreamId).isPresent()) {
            return Optional
                    .ofNullable(
                            eventRepository.retrieveEventStream(eventStreamId, startSequenceNumber, endSequenceNumber))
                    .orElseGet(Collections::emptyList);
        }

        return Collections.emptyList();
    }

    /**
     * Retrieve all {@link Event}s, in order, associated with the provided Event Stream identifier, up to the given
     * date/time (inclusive).
     *
     * @param eventStreamId Identifier of Event Stream
     * @param dateTime Date/time to retrieve {@link Event}s up to
     * @return Ordered list of {@link Event}s
     */
    public List<Event> retrieveEventsToDate(final String eventStreamId, final ZonedDateTime dateTime) {
        return Collections.emptyList();
    }

    /**
     * Retrieve all {@link Event}s, in order, associated with the provided Event Stream identifier, from the given
     * date/time onwards (inclusive).
     *
     * @param eventStreamId Identifier of Event Stream
     * @param dateTime Date/time to retrieve {@link Event}s from
     * @return Ordered list of {@link Event}s
     */
    public List<Event> retrieveEventsFromDate(final String eventStreamId, final ZonedDateTime dateTime) {
        return Collections.emptyList();
    }

    /**
     * Retrieve all {@link Event}s, in order, associated with the provided Event Stream identifier, between the two
     * given date/times (inclusive).
     *
     * @param eventStreamId Identifier of Event Stream
     * @param startDateTime Date/time to retrieve {@link Event}s from
     * @param endDateTime Date/time to retrieve {@link Event}s to
     * @return Ordered list of {@link Event}s
     */
    public List<Event> retrieveEventsBetweenDates(
            final String eventStreamId, final ZonedDateTime startDateTime, final ZonedDateTime endDateTime) {
        return Collections.emptyList();
    }

    /**
     * Publish the given List of {@link Event}s to the Event Stream.
     *
     * @param eventStreamId Identifier of Event Stream
     * @param lastSeenSequenceNumber Last sequence number that was observed for this Stream
     * @param unpublishedEvents {@link Event}s to publish
     * @throws ConcurrentModificationException If Event Stream has been modified since we last read it
     */
    public void publishEvents(
            final String eventStreamId, final int lastSeenSequenceNumber, final List<Event> unpublishedEvents)
            throws ConcurrentModificationException {

    }
}
