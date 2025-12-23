package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;

public class DefaultUserProfile implements UserProfile {
    private final String username;
    private final EnumSet<Interest> interests;
    private final HashSet<UserProfile> friends;

    public DefaultUserProfile(String username) {
        this.username = username;
        this.interests = EnumSet.noneOf(Interest.class); //Empty enum set
        this.friends = new HashSet<>();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public Collection<Interest> getInterests() {
        return Collections.unmodifiableCollection(interests);
    }

    @Override
    public boolean addInterest(Interest interest) {
        if(interest == null){
            throw new IllegalArgumentException("Interest cannot be blank!");
        }

        return this.interests.add(interest);
    }

    @Override
    public boolean removeInterest(Interest interest) {
        if(interest == null){
            throw new IllegalArgumentException("Interest cannot be blank!");
        }

        return this.interests.remove(interest);
    }

    @Override
    public Collection<UserProfile> getFriends() {
        return Collections.unmodifiableCollection(friends);
    }

    //Internal helper functions ONLY FOR CLASSES, NOT INSTANCES
    private void addFriendInternal(UserProfile userProfile) {
        this.friends.add(userProfile);
    }

    private void removeFriendInternal(UserProfile userProfile) {
        this.friends.remove(userProfile);
    }

    @Override
    public boolean addFriend(UserProfile userProfile) {
        if(userProfile == null) {
            throw new IllegalArgumentException("User doesn't exist!");
        }

        if(this.username.equals(userProfile.getUsername())) {
            throw new IllegalArgumentException("Cannot add yourself as a friend!");
        }

        boolean added = this.friends.add(userProfile);

        if(added) {
            ((DefaultUserProfile) userProfile).addFriendInternal(this);
        }

        return added;
    }

    @Override
    public boolean unFriend(UserProfile userProfile) {
        if(userProfile == null || this.username.equals(userProfile.getUsername())) {
            throw new IllegalArgumentException("User doesn't exist!");
        }

        boolean removed = this.friends.remove(userProfile);

        if(removed) {
            ((DefaultUserProfile) userProfile).removeFriendInternal(this);
        }

        return removed;
    }

    @Override
    public boolean isFriend(UserProfile userProfile) {
        if(userProfile == null) {
            throw new IllegalArgumentException("User doesn't exist!");
        }

        return this.friends.contains(userProfile);
    }
}
