package bg.sofia.uni.fmi.mjt.socialnetwork.post;

import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.time.LocalDateTime;
import java.util.*;

public class SocialFeedPost implements Post {
    private final String  uniqueId;
    private final UserProfile author;
    private final String content;
    private final LocalDateTime publishedOn;
    private final EnumMap<ReactionType, Set<UserProfile>> reactions;

    public SocialFeedPost(UserProfile author, String content) {
        uniqueId = UUID.randomUUID().toString();
        this.author = author;
        this.content = content;
        this.publishedOn = LocalDateTime.now();
        this.reactions = new EnumMap<>(ReactionType.class); //Empty Enum map
    }


    @Override
    public String getUniqueId() {
        return this.uniqueId;
    }

    @Override
    public UserProfile getAuthor() {
        return this.author;
    }

    @Override
    public LocalDateTime getPublishedOn() {
        return publishedOn;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public boolean addReaction(UserProfile userProfile, ReactionType reactionType) {
        if(userProfile == null) {
            throw new IllegalArgumentException("User not found!");
        }

        if(reactionType == null) {
            throw new IllegalArgumentException("Reaction doesn't exist!");
        }

        for(ReactionType reaction : reactions.keySet()) {
            //users is a reference not a copy, it changes map
            Set<UserProfile> users = reactions.get(reaction);
            if(users.contains(userProfile)) {
                users.remove(userProfile);

                if(!reactions.containsKey(reactionType))
                {
                    reactions.put(reactionType, new HashSet<>());
                }

                reactions.get(reactionType).add(userProfile);
                return false;
            }
        }

        if(!reactions.containsKey(reactionType))
        {
            reactions.put(reactionType, new HashSet<>());
        }

        reactions.get(reactionType).add(userProfile);
        return true;
    }

    @Override
    public boolean removeReaction(UserProfile userProfile) {
        if(userProfile == null ) {
            throw new IllegalArgumentException("User hasn't reacted!");
        }

        for(Set<UserProfile> users : reactions.values()) {
            if(users.remove(userProfile)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Map<ReactionType, Set<UserProfile>> getAllReactions() {
        Map<ReactionType, Set<UserProfile>> result = new EnumMap<>(ReactionType.class);

        for( ReactionType key : reactions.keySet()) {
            result.put(key, Collections.unmodifiableSet(reactions.get(key)));
        }

        return Collections.unmodifiableMap(result);
    }

    @Override
    public int getReactionCount(ReactionType reactionType) {
        if(reactionType == null) {
            throw new IllegalArgumentException("Reaction does not exist!");
        }

        if(reactions.get(reactionType) == null) {
            return 0;
        }
        return reactions.get(reactionType).size();
    }

    @Override
    public int totalReactionsCount () {
        int sum = 0;
        for(ReactionType reaction : reactions.keySet()) {
            sum += getReactionCount(reaction);
        }
        return sum;
    }
}
