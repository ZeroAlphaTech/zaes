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
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * A service providing access to an {@link Event} Stream.
 */
public interface EventRepository {

    /**
     * Retrieve all {@link Event}s, in order, for the given Event Stream identifier.
     *
     * @param eventStreamId Identifier of stream
     * @return Ordered list of {@link Event}s associated with identifier
     */
    List<Event> retrieveEventStream(final String eventStreamId);

    /**
     * Retrieve {@link Event}s, in order, for the given Event Stream identifier, between if provided sequence numbers
     * (inclusive).
     *
     * @param eventStreamId Identifier of stream
     * @param startSequenceNumber First sequence number to retrieve
     * @param endSequenceNumber Last sequence number to retrieve
     * @return Ordered list of {@link Event}s associated with identifier
     */
    List<Event> retrieveEventStream(
            final String eventStreamId, final int startSequenceNumber, final int endSequenceNumber);

    /**
     * Retrieve {@link Event}s, in order, for the given Event Stream identifier, between if provided date/times
     * (inclusive).
     *
     * @param eventStreamId Identifier of stream
     * @param startDate Start of time period
     * @param endDate End of time period
     * @return Ordered list of {@link Event}s associated with identifier
     */
    List<Event> retrieveEventStream(
            final String eventStreamId, final ZonedDateTime startDate, final ZonedDateTime endDate);

    /**
     * Persist the provided {@link Event}s to the Event Stream identified by the given identifier.
     *
     * @param eventStreamId Identifier of stream
     * @param events {@link Event}s to append
     * @throws ConcurrentModificationException If Event Stream has been modified since client last read it
     */
    void persistEvents(final String eventStreamId, final List<Event> events) throws ConcurrentModificationException;
}
