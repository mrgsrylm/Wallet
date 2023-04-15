package com.pollspolling.backend.delivery.controller;

import com.pollspolling.backend.delivery.payload.UserIdentityAvailability;
import com.pollspolling.backend.delivery.payload.UserProfile;
import com.pollspolling.backend.delivery.payload.UserSummary;
import com.pollspolling.backend.delivery.payload.response.PagedResponse;
import com.pollspolling.backend.delivery.payload.response.PollResponse;
import com.pollspolling.backend.domain.constant.ApplicationConstants;
import com.pollspolling.backend.domain.entity.User;
import com.pollspolling.backend.exception.ResourceNotFoundException;
import com.pollspolling.backend.repository.PollRepository;
import com.pollspolling.backend.repository.UserRepository;
import com.pollspolling.backend.repository.VoteRepository;
import com.pollspolling.backend.security.CurrentUser;
import com.pollspolling.backend.security.UserPrincipal;
import com.pollspolling.backend.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private PollService pollService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/me")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal user) {
        return new UserSummary(
                user.getId(),
                user.getUsername(),
                user.getName()
        );
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        long pollCount = pollRepository.countByCreatedBy(user.getId());
        long voteCount = voteRepository.countByUserId(user.getId());

        return new UserProfile(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getCreatedAt(),
                pollCount,
                voteCount
        );
    }

    @GetMapping("/users/{username}/polls")
    public PagedResponse<PollResponse> getPollsCreatedBy(@PathVariable(value = "username") String username,
                                                         @CurrentUser UserPrincipal currentUser,
                                                         @RequestParam(value = "page", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER) int page,
                                                         @RequestParam(value = "size", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsCreatedBy(username, currentUser, page, size);
    }

    @GetMapping("/users/{username}/votes")
    public PagedResponse<PollResponse> getPollsVotedBy(@PathVariable(value = "username") String username,
                                                       @CurrentUser UserPrincipal currentUser,
                                                       @RequestParam(value = "page", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsVotedBy(username, currentUser, page, size);
    }
}
