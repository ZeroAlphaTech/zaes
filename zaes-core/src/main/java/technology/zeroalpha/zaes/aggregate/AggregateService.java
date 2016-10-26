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

import technology.zeroalpha.zaes.event.Event;
import technology.zeroalpha.zaes.event.EventService;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service used to construct {@link Aggregate} objects, either from scratch, or from their constituent events. When
 * working with Aggregates, objects should always be created by using methods in this service.
 */
public class AggregateService<A extends Aggregate> {

    /** The {@link Class) of the {@link Aggregate}s that will be managed by this instance of the service. */
    private final Class<A> aggregateClass;

    /** Instance of an {@link EventService}, used to retrieve {@link Event}s associated with the given aggregate. */
    private final EventService eventService;

    /**
     * @param aggregateClass Class of {@link Aggregate}s to manage
     * @param eventService Service for retrieving {@link Event}s
     */
    public AggregateService(final Class<A> aggregateClass, final EventService eventService) {
        this.aggregateClass = aggregateClass;
        this.eventService = eventService;
    }

    /**
     * Build an {@link Aggregate} of the appropriate type with its current state (i.e. using all events in the specified
     * stream).
     *
     * @param eventStreamId Identifier of event stream
     * @return {@link Aggregate} with current state
     */
    public A buildLatestAggregate(final String eventStreamId) {
        return buildAggregate(eventService.retrieveAllEvents(eventStreamId));
    }

    /**
     * Build an {@link Aggregate} of the appropriate type up to the given sequence number in the event stream, ignoring
     * any events after that. Sequence number is inclusive.
     *
     * @param eventStreamId Identifier of event stream
     * @param sequenceNumber Sequence number within stream to build up to (inclusive)
     * @return {@link Aggregate} with state as at given sequence number
     */
    public A buildAggregateToSequenceNumber(final String eventStreamId, final int sequenceNumber) {
        return buildAggregate(eventService.retrieveEventsToSequenceNumber(eventStreamId, sequenceNumber));
    }

    /**
     * Build an {@link Aggregate} of the appropriate type up to the given date/time in the event stream, ignoring any
     * events after that. Date/time is inclusive.
     *
     * @param eventStreamId Identifier of event stream
     * @param dateTime Point in time to build up to
     * @return {@link Aggregate} with state as at given date/time
     */
    public A buildAggregateToDate(final String eventStreamId, final ZonedDateTime dateTime) {
        return buildAggregate(eventService.retrieveEventsToDate(eventStreamId, dateTime));
    }

    /**
     * Build an {@link Aggregate} of the managed type, using the provided Event Stream.
     *
     * @param eventStream {@link Event}s to apply to build {@link Aggregate}
     * @return {@link Aggregate} based on {@link Event}s provided
     */
    private A buildAggregate(final List<Event> eventStream) {
        final A aggregate = createInstance();

        eventStream.forEach(aggregate::applyEvent);

        return aggregate;
    }

    /**
     * Create a new, default, instance of the managed {@link Aggregate} type.
     *
     * @return Empty {@link Aggregate} of type {@link A}
     */
    private A createInstance() {
        try {
            return aggregateClass.newInstance();
        } catch (final InstantiationException ie) {
            ie.printStackTrace();
        } catch (final IllegalAccessException iae) {
            iae.printStackTrace();
        }

        return null;
    }
}
