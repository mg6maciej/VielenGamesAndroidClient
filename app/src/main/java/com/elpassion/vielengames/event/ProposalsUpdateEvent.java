package com.elpassion.vielengames.event;

import com.elpassion.vielengames.data.GameProposal;

import java.util.List;

import lombok.Value;

@Value
public final class ProposalsUpdateEvent {

    List<GameProposal> proposals;
}
