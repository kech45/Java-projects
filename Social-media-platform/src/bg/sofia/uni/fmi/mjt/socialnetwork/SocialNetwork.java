package bg.sofia.uni.fmi.mjt.socialnetwork;

import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.Post;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.ReactionType;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.SocialFeedPost;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.Interest;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfileFriendsCountComparator;

import java.util.*;

public class SocialNetwork implements  SocialNetworkImpl {
    private final Set<UserProfile> registeredUsers;
    private final Collection<Post> posts;

    public SocialNetwork() {
        this.registeredUsers = new HashSet<>();
        this.posts = new ArrayList<>();
    }
    @Override
    public void registerUser(UserProfile userProfile) throws UserRegistrationException {
        if (userProfile == null) {
            throw new IllegalArgumentException("The user does not exist!");
        }

        if(registeredUsers.contains(userProfile)) {
            throw new UserRegistrationException("The user is already registered!");
        }

        registeredUsers.add(userProfile);
    }

    @Override
    public Set<UserProfile> getAllUsers() {
        return Collections.unmodifiableSet(registeredUsers);
    }

    @Override
    public Post post(UserProfile userProfile, String content) throws UserRegistrationException {
        if(userProfile == null) {
            throw new IllegalArgumentException("User does not exist!");
        }

        if(!registeredUsers.contains(userProfile)) {
            throw new UserRegistrationException("User is not registered!");
        }

        if(content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be empty!");
        }

        Post res =  new SocialFeedPost(userProfile, content);
        posts.add(res);
        return res;
    }

    @Override
    public Collection<Post> getPosts() {
        return Collections.unmodifiableCollection(posts);
    }

    @Override
    public Set<UserProfile> getReachedUsers(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post cannot be null!");
        }

        Set<UserProfile> visited = new HashSet<>();
        Set<UserProfile> reached = new HashSet<>();

        Deque<UserProfile> queue = new ArrayDeque<>();
        UserProfile root = post.getAuthor();

        queue.add(root);
        visited.add(root);

        while(!queue.isEmpty()) {
            UserProfile curr = queue.pop();

            for(UserProfile friend : curr.getFriends()) {
                if (!registeredUsers.contains(friend)) {
                    continue;
                }
                if (visited.contains(friend)) {
                    continue;
                }

                visited.add(friend);
                queue.add(friend);

                EnumSet<Interest> interests = EnumSet.copyOf(root.getInterests());
                interests.retainAll(friend.getInterests());

                if(!interests.isEmpty()) {
                    reached.add(friend);
                }
            }
        }

        return reached;
    }

    public void bfsTest(Post post) {
        Set<UserProfile> visited = new HashSet<>();
        UserProfile root = post.getAuthor();
        Deque<UserProfile> queue = new ArrayDeque<>();

        queue.add(root);

        while(!queue.isEmpty()) {
            UserProfile curr = queue.pop();
            for(UserProfile friend : curr.getFriends()) {
                if (!registeredUsers.contains(friend)) {
                    continue;
                }

                if (visited.contains(friend)) {
                    continue;
                }

                visited.add(friend);
                queue.add(friend);
                System.out.println(friend.getUsername());
            }
        }
    }

    public Set<UserProfile> getMutualFriends(UserProfile userProfile1, UserProfile userProfile2)
            throws  UserRegistrationException {
        if(userProfile1 == null || userProfile2 == null) {
            throw new IllegalArgumentException("One of the users is non existent!");
        }

        if (!registeredUsers.contains(userProfile1) || !registeredUsers.contains(userProfile2)) {
            throw new UserRegistrationException("One of the users is not registered!");
        }

        Set<UserProfile> res = new HashSet<UserProfile>(userProfile1.getFriends());
        res.retainAll(userProfile2.getFriends());

        return res;
    }

    public SortedSet<UserProfile> getAllProfilesSortedByFriendsCount() {
        SortedSet<UserProfile> result = new TreeSet<>(new UserProfileFriendsCountComparator());

        result.addAll(registeredUsers);

        return result;
    }
}
