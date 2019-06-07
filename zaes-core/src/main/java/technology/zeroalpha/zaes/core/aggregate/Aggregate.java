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
package technology.zeroalpha.zaes.core.aggregate;

import technology.zeroalpha.zaes.core.event.Event;

import java.time.ZonedDateTime;

/**
 * Representation of a single domain object. Provides all business logic required to modify its state and perform
 * invariant checking, and raises resulting {@link Event}s. Can be recreated to any previous state by re-applying
 * {@link Event}s in order.
 */
public abstract class Aggregate {

    private String aggregateIdentifier;

    /** Sequence number of last {@link Event} applied. */
    private int sequenceNumber;

    /** Timestamp of last {@link Event} applied. */
    private ZonedDateTime lastModificationDate;

    public final void setAggregateIdentifier(final String aggregateIdentifier) {
        this.aggregateIdentifier = aggregateIdentifier;
    }

    /**
     * Update the {@link Aggregate}'s state based on the given {@link Event}.
     *
     * @param event {@link Event} to apply
     */
    public void applyEvent(final Event event) {
        this.sequenceNumber = event.getSequenceNumber();
        processEvent(event);
    }

    protected abstract void processEvent(final Event event);
}
