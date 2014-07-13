package com.elpassion.vielengames.event;

import com.elpassion.vielengames.data.GameProposal;

import lombok.Value;

@Value
public final class LeaveGameProposalClickEvent {

    GameProposal proposal;
}
