package com.vielengames.event;

import com.vielengames.data.GameProposal;

import java.util.List;

import lombok.Value;

@Value
public final class ProposalsUpdateEvent {

    List<GameProposal> proposals;
}
