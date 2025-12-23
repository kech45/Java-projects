import bg.sofia.uni.fmi.mjt.socialnetwork.SocialNetworkImpl;
import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.Post;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.ReactionType;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.SocialFeedPost;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.DefaultUserProfile;
import bg.sofia.uni.fmi.mjt.socialnetwork.SocialNetwork;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.Interest;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


public class Main {
    public static void main(String[] args) throws UserRegistrationException {
        UserProfile user1 = new DefaultUserProfile("Johnny");
        user1.addInterest(Interest.SPORTS);
        user1.addInterest(Interest.BOOKS);

        UserProfile user2 = new DefaultUserProfile("Jimmy");
        user2.addInterest(Interest.GAMES);
        user2.addInterest(Interest.BOOKS);

        UserProfile user3 = new DefaultUserProfile("Jake");
        user3.addInterest(Interest.SPORTS);
        user3.addInterest(Interest.MOVIES);

        UserProfile user4 = new DefaultUserProfile("Arnold");
        user4.addInterest(Interest.MOVIES);
        user4.addInterest(Interest.GAMES);

        UserProfile user5 = new DefaultUserProfile("Bob");
        user5.addInterest(Interest.FOOD);
        user5.addInterest(Interest.BOOKS);

        UserProfile user6 = new DefaultUserProfile("Alice");
        user6.addInterest(Interest.MUSIC);

        user1.addFriend(user4);
        user1.addFriend(user3);
        user2.addFriend(user4);
        user3.addFriend(user5);
        user5.addFriend(user6);
        user6.addFriend(user2);
        user3.addFriend(user5);

        Collection<UserProfile> res = user6.getFriends();

        for(UserProfile el : res) {
            System.out.println(el.getUsername());
        }

        System.out.println();
        Post post1 = new SocialFeedPost(user1, "The weather is great today!");
        Post post2 = new SocialFeedPost(user4, "Very cool car this one huh!");


        post1.addReaction(user3, ReactionType.LIKE);
        post1.addReaction(user2, ReactionType.ANGRY);
        post1.addReaction(user5, ReactionType.LOVE);
        post1.addReaction(user4, ReactionType.LOVE);
        post1.addReaction(user6, ReactionType.LOVE);

        post2.addReaction(user1, ReactionType.ANGRY);
        post2.addReaction(user5, ReactionType.LAUGH);

        Map<ReactionType, Set<UserProfile>> res1 = post1.getAllReactions();

        for(Map.Entry<ReactionType, Set<UserProfile>> el : res1.entrySet()) {
            System.out.println(el.getKey());
            System.out.println(post1.getReactionCount(el.getKey()));
        }

        System.out.printf("Post 1's total reactions %d \n", post1.totalReactionsCount());
        System.out.printf("Post 2's total reactions %d \n", post2.totalReactionsCount());

        SocialNetworkImpl network1  = new SocialNetwork();

        network1.registerUser(user1); //Johnny
        network1.registerUser(user2); //Jimmy
        network1.registerUser(user4); //Arnold
        network1.registerUser(user5); //Bob
        network1.registerUser(user3); //Jake

        Set<UserProfile> res2 = network1.getReachedUsers(post1);

        for(UserProfile user: res2) {
            System.out.println(user.getUsername());
        }
    }
}