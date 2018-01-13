package technology.zeroalpha.zaes.core.event

import spock.lang.Specification

class EventServiceSpecification extends Specification {

    def eventRepository = Mock EventRepository

    def eventService = new EventService(eventRepository)

    def 'Passing null to retrieveAllEvents() throws NullPointerException'() {
        when:
            eventService.retrieveAllEvents(null)

        then:
            thrown(NullPointerException)
    }

    def 'If EventRepository returns null on calls to retrieveAllEvents() then return an empty List'() {
        given:
            def aggregateIdentifier = 'aggregate-identifier'
            eventRepository.retrieveEventStream(aggregateIdentifier) >> null

        when:
            def events = eventService.retrieveAllEvents(aggregateIdentifier)

        then:
            events.isEmpty()
    }

    def 'Calls to retrieveAllEvents return all Events from EventRepository'() {
        given:
            def aggregateIdentifier = 'aggregate-identifier'
            def firstEvent = Mock Event
            def secondEvent = Mock Event
            eventRepository.retrieveEventStream(aggregateIdentifier) >> [firstEvent, secondEvent]

        when:
            def events = eventService.retrieveAllEvents(aggregateIdentifier)

        then:
            events == [firstEvent, secondEvent]
    }
}
