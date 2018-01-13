/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Zero Alpha Technology Limited
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
package technology.zeroalpha.zaes.cqrs.command;

import technology.zeroalpha.zaes.core.aggregate.Aggregate;
import technology.zeroalpha.zaes.core.aggregate.AggregateService;
import technology.zeroalpha.zaes.core.event.Event;

import java.util.List;

public abstract class CommandProcessor<A extends Aggregate, C extends Command<A>> {

    private final AggregateService<A> aggregateService;

    public CommandProcessor(final AggregateService<A> aggregateService) {
        this.aggregateService = aggregateService;
    }

    public void process(final C command) {
        final A aggregate = aggregateService.createNewAggregate();
        processCommand(aggregate, command);
    }

    public void process(final String aggregateIdentifier, final C command) {
        final A aggregate = aggregateService.buildLatestAggregate(aggregateIdentifier);
        processCommand(aggregate, command);
    }

    protected abstract List<Event> processCommand(final A aggregate, final C command);
}
