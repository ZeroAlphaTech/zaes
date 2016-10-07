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

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;

/**
 * Created by chris on 05/10/16.
 */
public class EventService {

    private final EventRepository eventRepository;

    public EventService(final EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> retrieveAllEvents(final String streamId) {
        if(Optional.ofNullable(streamId).isPresent()) {
            final List<Event> eventStream = eventRepository.retrieveEventStream(streamId);

            if (eventStream != null) {
                return eventStream;
            }
        }

        return Collections.emptyList();
    }

    public List<Event> retrieveEventsToSequenceNumber(final String streamId, final int sequenceNumber) {
        return retrieveEventsBetweenSequenceNumbers(streamId, Integer.MIN_VALUE, sequenceNumber);
    }

    public List<Event> retrieveEventsFromSequenceNumber(final String streamId, final int sequenceNumber) {
        return retrieveEventsBetweenSequenceNumbers(streamId, sequenceNumber, Integer.MAX_VALUE);
    }

    public List<Event> retrieveEventsBetweenSequenceNumbers(
            final String streamId, final int startSequenceNumber, final int endSequenceNumber) {
        if(Optional.ofNullable(streamId).isPresent()) {
            final List<Event> eventStream =
                    eventRepository.retrieveEventStream(streamId, startSequenceNumber, endSequenceNumber);

            if (eventStream != null) {
                return eventStream;
            }
        }

        return Collections.emptyList();
    }

    public List<Event> retrieveEventsToDate(final String streamId, final ZonedDateTime dateTime) {
        return Collections.emptyList();
    }

    public List<Event> retrieveEventsAfterDate(final String streamId, final ZonedDateTime dateTime) {
        return Collections.emptyList();
    }

    public List<Event> retrieveEventsBetweenDates(
            final String streamId, final ZonedDateTime startDateTime, final ZonedDateTime endDateTime) {
        return Collections.emptyList();
    }

    public void publishEvents(final String streamId, final int lastSeenSequenceNumber, List<Event> events)
            throws ConcurrentModificationException {

    }
}
