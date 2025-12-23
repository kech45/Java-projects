package bg.sofia.uni.fmi.mjt.socialnetwork.profile;


import java.util.Comparator;

public class UserProfileFriendsCountComparator implements Comparator<UserProfile> {

    @Override
    public int compare(UserProfile userProfile1, UserProfile userProfile2) {
        int byFriendsComparison =
        Integer.compare(userProfile1.getFriends().size(), userProfile2.getFriends().size());

        if(byFriendsComparison != 0) {
            return byFriendsComparison;
        }

        //we compare lexicographically by name if both users have the same amount of friends
        return userProfile1.getUsername().compareTo(userProfile2.getUsername());
    }
}
