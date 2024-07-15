package org.weber.planner.participant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.weber.planner.trip.Trip;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    @Autowired
    private particpantRepository repository;

    public void registerParticipantsToEvent(List<String> participantToInvite, Trip trip){
        List<Participant> participants = participantToInvite.stream().map(email -> new Participant(email, trip)).toList();

        this.repository.saveAll(participants);
        System.out.println(participants.get(0).getId());
    }

    public ParticipantCreateResponse registerParticipantToInvent(String email, Trip trip){
        Participant newParticipant = new Participant(email, trip);
        this.repository.save(newParticipant);

        return new ParticipantCreateResponse(newParticipant.getId());
    }

    public void triggerConfirmationEmailToParticipants(UUID tripId){}

    public void triggerConfirmationEmailToParticipant(String email){}

    public List<ParticipantData> getAllParticipantsFromEvent(UUID tripId) {
        return this.repository.findByTripId(tripId).stream().map(participant -> new ParticipantData(participant.getId(), participant.getName(), participant.getEmail(), participant.getIsConfirmed())).toList();
    }
}
