package com.vielengames.event;

import com.vielengames.data.GameProposal;

import lombok.Value;

@Value
public final class JoinGameProposalClickEvent {

    GameProposal proposal;
}
