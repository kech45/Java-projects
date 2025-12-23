package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

import java.util.Collection;

public interface UserProfile {

    String getUsername();

    Collection<Interest> getInterests();

    boolean addInterest(Interest interest);

    boolean removeInterest(Interest interest);

    Collection<UserProfile> getFriends();

    boolean addFriend(UserProfile userProfile);

    boolean unFriend(UserProfile userProfile);

    boolean isFriend(UserProfile userProfile);
}
