package com.pollspolling.backend.service;

import com.pollspolling.backend.delivery.payload.request.PollRequest;
import com.pollspolling.backend.delivery.payload.request.VoteRequest;
import com.pollspolling.backend.delivery.payload.response.PagedResponse;
import com.pollspolling.backend.delivery.payload.response.PollResponse;
import com.pollspolling.backend.domain.entity.Poll;
import com.pollspolling.backend.security.UserPrincipal;

public interface PollService {
    PagedResponse<PollResponse> getAllPolls(UserPrincipal currentUser, int page, int size);
    PagedResponse<PollResponse> getPollsCreatedBy(String username, UserPrincipal currentUser, int page, int size);
    PagedResponse<PollResponse> getPollsVotedBy(String username, UserPrincipal currentUser, int page, int size);
    Poll createPoll(PollRequest pollRequest);
    PollResponse getPollById(Long pollId, UserPrincipal currentUser);
    PollResponse castVoteAndGetUpdatedPoll(Long pollId, VoteRequest voteRequest, UserPrincipal currentUser);
}
