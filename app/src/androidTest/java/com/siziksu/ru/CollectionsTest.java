package com.siziksu.ru;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siziksu.ru.common.CollectionsManager;
import com.siziksu.ru.common.Constants;
import com.siziksu.ru.common.PreferencesManager;
import com.siziksu.ru.common.model.response.users.User;
import com.siziksu.ru.common.model.response.users.Users;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CollectionsTest {

    private PreferencesManager preferencesManager;
    private CollectionsManager collectionsManager;
    private String blockedUsers;
    private String unorderedUsers;
    private String usersWithoutBlockedUsersRemoved;
    private String usersWithBlockedUsersRemoved;
    private String duplicatedUsers;

    @Before
    public void init() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        preferencesManager = new PreferencesManager(PreferenceManager.getDefaultSharedPreferences(appContext));
        collectionsManager = new CollectionsManager(preferencesManager);
        initializeStrings();
    }

    @Test
    public void removeBlockedUsers_isCorrect() throws Exception {
        preferencesManager.deleteKey(Constants.PREFERENCES_KEY_USERS_BLOCKED);
        preferencesManager.setString(Constants.PREFERENCES_KEY_USERS_BLOCKED, blockedUsers);
        List<User> usersWithBlockedUsersRemovedObject = new Gson().fromJson(usersWithBlockedUsersRemoved, new TypeToken<List<User>>() {}.getType());
        List<User> usersToWorkWith = new Gson().fromJson(usersWithoutBlockedUsersRemoved, new TypeToken<List<User>>() {}.getType());
        collectionsManager.removeBlockedUsers(usersToWorkWith);
        String jsonUsersWithBlockedUsersRemoved = new Gson().toJson(usersWithBlockedUsersRemovedObject);
        String jsonUsersToWorkWith = new Gson().toJson(usersToWorkWith);
        assertEquals(jsonUsersWithBlockedUsersRemoved, jsonUsersToWorkWith);
    }

    @Test
    public void sortUsers_isCorrect() throws Exception {
        Users users = new Gson().fromJson(unorderedUsers, Users.class);
        collectionsManager.sortUsersByName(users.users);
        assertEquals("aurora", users.users.get(0).name.first);
    }

    @Test
    public void removeDuplicates_isCorrect() throws Exception {
        Users users = new Gson().fromJson(duplicatedUsers, Users.class);
        collectionsManager.removeDuplicates(users.users);
        assertEquals(3, users.users.size());
    }

    private void initializeStrings() {
        blockedUsers = "[" +
                       "{" +
                       "\"gender\": \"female\"," +
                       "\"name\": {" +
                       "\"title\": \"miss\"," +
                       "\"first\": \"carmen\"," +
                       "\"last\": \"serrano\"" +
                       "}," +
                       "\"location\": {" +
                       "\"street\": \"4562 calle del arenal\"," +
                       "\"city\": \"parla\"," +
                       "\"state\": \"aragón\"," +
                       "\"postcode\": 26914" +
                       "}," +
                       "\"email\": \"carmen.serrano@example.com\"," +
                       "\"login\": {" +
                       "\"username\": \"yellowpeacock112\"," +
                       "\"password\": \"ffff\"," +
                       "\"salt\": \"KLBLMKJ3\"," +
                       "\"md5\": \"a55b2f726298ead1e29cbf78f1c9877c\"," +
                       "\"sha1\": \"75a018c009ca8510b946c9fc1c2e228bf62db500\"," +
                       "\"sha256\": \"8d5dadcac6e636ce6b03120857c8b6bda0f78533ba75288ba5db0d65bcab14f3\"" +
                       "}," +
                       "\"dob\": \"1966-10-21 19:35:09\"," +
                       "\"registered\": \"2016-01-07 22:52:33\"," +
                       "\"phone\": \"915-578-484\"," +
                       "\"cell\": \"669-597-780\"," +
                       "\"id\": {" +
                       "\"name\": \"DNI\"," +
                       "\"value\": \"35415756-Z\"" +
                       "}," +
                       "\"picture\": {" +
                       "\"large\": \"https://randomuser.me/api/portraits/women/17.jpg\"," +
                       "\"medium\": \"https://randomuser.me/api/portraits/med/women/17.jpg\"," +
                       "\"thumbnail\": \"https://randomuser.me/api/portraits/thumb/women/17.jpg\"" +
                       "}," +
                       "\"nat\": \"ES\"" +
                       "}" +
                       "]";
        usersWithBlockedUsersRemoved = "[" +
                                       "{" +
                                       "\"gender\": \"male\"," +
                                       "\"name\": {" +
                                       "\"title\": \"mr\"," +
                                       "\"first\": \"juan\"," +
                                       "\"last\": \"martin\"" +
                                       "}," +
                                       "\"location\": {" +
                                       "\"street\": \"7643 calle de atocha\"," +
                                       "\"city\": \"vigo\"," +
                                       "\"state\": \"andalucía\"," +
                                       "\"postcode\": 18042" +
                                       "}," +
                                       "\"email\": \"juan.martin@example.com\"," +
                                       "\"login\": {" +
                                       "\"username\": \"lazybird844\"," +
                                       "\"password\": \"lacrosse\"," +
                                       "\"salt\": \"oFJOZNKT\"," +
                                       "\"md5\": \"594b2acb48481fc0bfa4166732ef18f7\"," +
                                       "\"sha1\": \"9a17dbadb4a3ad4d6d4f83bae8906647f3025224\"," +
                                       "\"sha256\": \"540ffe86f2cea9cf502127b7feed3ae23faf8fbb8ec3d72780d349fef1205093\"" +
                                       "}," +
                                       "\"dob\": \"1995-05-20 07:50:54\"," +
                                       "\"registered\": \"2003-05-17 21:39:36\"," +
                                       "\"phone\": \"933-263-803\"," +
                                       "\"cell\": \"694-211-086\"," +
                                       "\"id\": {" +
                                       "\"name\": \"DNI\"," +
                                       "\"value\": \"84144025-I\"" +
                                       "}," +
                                       "\"picture\": {" +
                                       "\"large\": \"https://randomuser.me/api/portraits/men/94.jpg\"," +
                                       "\"medium\": \"https://randomuser.me/api/portraits/med/men/94.jpg\"," +
                                       "\"thumbnail\": \"https://randomuser.me/api/portraits/thumb/men/94.jpg\"" +
                                       "}," +
                                       "\"nat\": \"ES\"" +
                                       "}," +
                                       "{" +
                                       "\"gender\": \"female\"," +
                                       "\"name\": {" +
                                       "\"title\": \"ms\"," +
                                       "\"first\": \"aurora\"," +
                                       "\"last\": \"vicente\"" +
                                       "}," +
                                       "\"location\": {" +
                                       "\"street\": \"4535 calle de la democracia\"," +
                                       "\"city\": \"cuenca\"," +
                                       "\"state\": \"castilla y león\"," +
                                       "\"postcode\": 98492" +
                                       "}," +
                                       "\"email\": \"aurora.vicente@example.com\"," +
                                       "\"login\": {" +
                                       "\"username\": \"whiteladybug671\"," +
                                       "\"password\": \"copper\"," +
                                       "\"salt\": \"1pgaMRHB\"," +
                                       "\"md5\": \"ba5718a626bc8835c55c14b01939c90c\"," +
                                       "\"sha1\": \"9613ef2af96f722c1ef5b976b86a549ce906e7c2\"," +
                                       "\"sha256\": \"a583f18a17d58bd42b91370ea888e40337b6284c8bde40c0b5a342a90558ba2f\"" +
                                       "}," +
                                       "\"dob\": \"1977-03-09 23:00:07\"," +
                                       "\"registered\": \"2014-03-16 21:13:03\"," +
                                       "\"phone\": \"982-933-152\"," +
                                       "\"cell\": \"682-638-588\"," +
                                       "\"id\": {" +
                                       "\"name\": \"DNI\"," +
                                       "\"value\": \"32509216-N\"" +
                                       "}," +
                                       "\"picture\": {" +
                                       "\"large\": \"https://randomuser.me/api/portraits/women/90.jpg\"," +
                                       "\"medium\": \"https://randomuser.me/api/portraits/med/women/90.jpg\"," +
                                       "\"thumbnail\": \"https://randomuser.me/api/portraits/thumb/women/90.jpg\"" +
                                       "}," +
                                       "\"nat\": \"ES\"" +
                                       "}" +
                                       "]";
        usersWithoutBlockedUsersRemoved = "[" +
                                          "{" +
                                          "\"gender\": \"female\"," +
                                          "\"name\": {" +
                                          "\"title\": \"miss\"," +
                                          "\"first\": \"carmen\"," +
                                          "\"last\": \"serrano\"" +
                                          "}," +
                                          "\"location\": {" +
                                          "\"street\": \"4562 calle del arenal\"," +
                                          "\"city\": \"parla\"," +
                                          "\"state\": \"aragón\"," +
                                          "\"postcode\": 26914" +
                                          "}," +
                                          "\"email\": \"carmen.serrano@example.com\"," +
                                          "\"login\": {" +
                                          "\"username\": \"yellowpeacock112\"," +
                                          "\"password\": \"ffff\"," +
                                          "\"salt\": \"KLBLMKJ3\"," +
                                          "\"md5\": \"a55b2f726298ead1e29cbf78f1c9877c\"," +
                                          "\"sha1\": \"75a018c009ca8510b946c9fc1c2e228bf62db500\"," +
                                          "\"sha256\": \"8d5dadcac6e636ce6b03120857c8b6bda0f78533ba75288ba5db0d65bcab14f3\"" +
                                          "}," +
                                          "\"dob\": \"1966-10-21 19:35:09\"," +
                                          "\"registered\": \"2016-01-07 22:52:33\"," +
                                          "\"phone\": \"915-578-484\"," +
                                          "\"cell\": \"669-597-780\"," +
                                          "\"id\": {" +
                                          "\"name\": \"DNI\"," +
                                          "\"value\": \"35415756-Z\"" +
                                          "}," +
                                          "\"picture\": {" +
                                          "\"large\": \"https://randomuser.me/api/portraits/women/17.jpg\"," +
                                          "\"medium\": \"https://randomuser.me/api/portraits/med/women/17.jpg\"," +
                                          "\"thumbnail\": \"https://randomuser.me/api/portraits/thumb/women/17.jpg\"" +
                                          "}," +
                                          "\"nat\": \"ES\"" +
                                          "}," +
                                          "{" +
                                          "\"gender\": \"male\"," +
                                          "\"name\": {" +
                                          "\"title\": \"mr\"," +
                                          "\"first\": \"juan\"," +
                                          "\"last\": \"martin\"" +
                                          "}," +
                                          "\"location\": {" +
                                          "\"street\": \"7643 calle de atocha\"," +
                                          "\"city\": \"vigo\"," +
                                          "\"state\": \"andalucía\"," +
                                          "\"postcode\": 18042" +
                                          "}," +
                                          "\"email\": \"juan.martin@example.com\"," +
                                          "\"login\": {" +
                                          "\"username\": \"lazybird844\"," +
                                          "\"password\": \"lacrosse\"," +
                                          "\"salt\": \"oFJOZNKT\"," +
                                          "\"md5\": \"594b2acb48481fc0bfa4166732ef18f7\"," +
                                          "\"sha1\": \"9a17dbadb4a3ad4d6d4f83bae8906647f3025224\"," +
                                          "\"sha256\": \"540ffe86f2cea9cf502127b7feed3ae23faf8fbb8ec3d72780d349fef1205093\"" +
                                          "}," +
                                          "\"dob\": \"1995-05-20 07:50:54\"," +
                                          "\"registered\": \"2003-05-17 21:39:36\"," +
                                          "\"phone\": \"933-263-803\"," +
                                          "\"cell\": \"694-211-086\"," +
                                          "\"id\": {" +
                                          "\"name\": \"DNI\"," +
                                          "\"value\": \"84144025-I\"" +
                                          "}," +
                                          "\"picture\": {" +
                                          "\"large\": \"https://randomuser.me/api/portraits/men/94.jpg\"," +
                                          "\"medium\": \"https://randomuser.me/api/portraits/med/men/94.jpg\"," +
                                          "\"thumbnail\": \"https://randomuser.me/api/portraits/thumb/men/94.jpg\"" +
                                          "}," +
                                          "\"nat\": \"ES\"" +
                                          "}," +
                                          "{" +
                                          "\"gender\": \"female\"," +
                                          "\"name\": {" +
                                          "\"title\": \"ms\"," +
                                          "\"first\": \"aurora\"," +
                                          "\"last\": \"vicente\"" +
                                          "}," +
                                          "\"location\": {" +
                                          "\"street\": \"4535 calle de la democracia\"," +
                                          "\"city\": \"cuenca\"," +
                                          "\"state\": \"castilla y león\"," +
                                          "\"postcode\": 98492" +
                                          "}," +
                                          "\"email\": \"aurora.vicente@example.com\"," +
                                          "\"login\": {" +
                                          "\"username\": \"whiteladybug671\"," +
                                          "\"password\": \"copper\"," +
                                          "\"salt\": \"1pgaMRHB\"," +
                                          "\"md5\": \"ba5718a626bc8835c55c14b01939c90c\"," +
                                          "\"sha1\": \"9613ef2af96f722c1ef5b976b86a549ce906e7c2\"," +
                                          "\"sha256\": \"a583f18a17d58bd42b91370ea888e40337b6284c8bde40c0b5a342a90558ba2f\"" +
                                          "}," +
                                          "\"dob\": \"1977-03-09 23:00:07\"," +
                                          "\"registered\": \"2014-03-16 21:13:03\"," +
                                          "\"phone\": \"982-933-152\"," +
                                          "\"cell\": \"682-638-588\"," +
                                          "\"id\": {" +
                                          "\"name\": \"DNI\"," +
                                          "\"value\": \"32509216-N\"" +
                                          "}," +
                                          "\"picture\": {" +
                                          "\"large\": \"https://randomuser.me/api/portraits/women/90.jpg\"," +
                                          "\"medium\": \"https://randomuser.me/api/portraits/med/women/90.jpg\"," +
                                          "\"thumbnail\": \"https://randomuser.me/api/portraits/thumb/women/90.jpg\"" +
                                          "}," +
                                          "\"nat\": \"ES\"" +
                                          "}" +
                                          "]";
        unorderedUsers = "{" +
                         "\"results\": [" +
                         "{" +
                         "\"gender\": \"female\"," +
                         "\"name\": {" +
                         "\"title\": \"miss\"," +
                         "\"first\": \"carmen\"," +
                         "\"last\": \"serrano\"" +
                         "}," +
                         "\"location\": {" +
                         "\"street\": \"4562 calle del arenal\"," +
                         "\"city\": \"parla\"," +
                         "\"state\": \"aragón\"," +
                         "\"postcode\": 26914" +
                         "}," +
                         "\"email\": \"carmen.serrano@example.com\"," +
                         "\"login\": {" +
                         "\"username\": \"yellowpeacock112\"," +
                         "\"password\": \"ffff\"," +
                         "\"salt\": \"KLBLMKJ3\"," +
                         "\"md5\": \"a55b2f726298ead1e29cbf78f1c9877c\"," +
                         "\"sha1\": \"75a018c009ca8510b946c9fc1c2e228bf62db500\"," +
                         "\"sha256\": \"8d5dadcac6e636ce6b03120857c8b6bda0f78533ba75288ba5db0d65bcab14f3\"" +
                         "}," +
                         "\"dob\": \"1966-10-21 19:35:09\"," +
                         "\"registered\": \"2016-01-07 22:52:33\"," +
                         "\"phone\": \"915-578-484\"," +
                         "\"cell\": \"669-597-780\"," +
                         "\"id\": {" +
                         "\"name\": \"DNI\"," +
                         "\"value\": \"35415756-Z\"" +
                         "}," +
                         "\"picture\": {" +
                         "\"large\": \"https://randomuser.me/api/portraits/women/17.jpg\"," +
                         "\"medium\": \"https://randomuser.me/api/portraits/med/women/17.jpg\"," +
                         "\"thumbnail\": \"https://randomuser.me/api/portraits/thumb/women/17.jpg\"" +
                         "}," +
                         "\"nat\": \"ES\"" +
                         "}," +
                         "{" +
                         "\"gender\": \"male\"," +
                         "\"name\": {" +
                         "\"title\": \"mr\"," +
                         "\"first\": \"juan\"," +
                         "\"last\": \"martin\"" +
                         "}," +
                         "\"location\": {" +
                         "\"street\": \"7643 calle de atocha\"," +
                         "\"city\": \"vigo\"," +
                         "\"state\": \"andalucía\"," +
                         "\"postcode\": 18042" +
                         "}," +
                         "\"email\": \"juan.martin@example.com\"," +
                         "\"login\": {" +
                         "\"username\": \"lazybird844\"," +
                         "\"password\": \"lacrosse\"," +
                         "\"salt\": \"oFJOZNKT\"," +
                         "\"md5\": \"594b2acb48481fc0bfa4166732ef18f7\"," +
                         "\"sha1\": \"9a17dbadb4a3ad4d6d4f83bae8906647f3025224\"," +
                         "\"sha256\": \"540ffe86f2cea9cf502127b7feed3ae23faf8fbb8ec3d72780d349fef1205093\"" +
                         "}," +
                         "\"dob\": \"1995-05-20 07:50:54\"," +
                         "\"registered\": \"2003-05-17 21:39:36\"," +
                         "\"phone\": \"933-263-803\"," +
                         "\"cell\": \"694-211-086\"," +
                         "\"id\": {" +
                         "\"name\": \"DNI\"," +
                         "\"value\": \"84144025-I\"" +
                         "}," +
                         "\"picture\": {" +
                         "\"large\": \"https://randomuser.me/api/portraits/men/94.jpg\"," +
                         "\"medium\": \"https://randomuser.me/api/portraits/med/men/94.jpg\"," +
                         "\"thumbnail\": \"https://randomuser.me/api/portraits/thumb/men/94.jpg\"" +
                         "}," +
                         "\"nat\": \"ES\"" +
                         "}," +
                         "{" +
                         "\"gender\": \"female\"," +
                         "\"name\": {" +
                         "\"title\": \"ms\"," +
                         "\"first\": \"aurora\"," +
                         "\"last\": \"vicente\"" +
                         "}," +
                         "\"location\": {" +
                         "\"street\": \"4535 calle de la democracia\"," +
                         "\"city\": \"cuenca\"," +
                         "\"state\": \"castilla y león\"," +
                         "\"postcode\": 98492" +
                         "}," +
                         "\"email\": \"aurora.vicente@example.com\"," +
                         "\"login\": {" +
                         "\"username\": \"whiteladybug671\"," +
                         "\"password\": \"copper\"," +
                         "\"salt\": \"1pgaMRHB\"," +
                         "\"md5\": \"ba5718a626bc8835c55c14b01939c90c\"," +
                         "\"sha1\": \"9613ef2af96f722c1ef5b976b86a549ce906e7c2\"," +
                         "\"sha256\": \"a583f18a17d58bd42b91370ea888e40337b6284c8bde40c0b5a342a90558ba2f\"" +
                         "}," +
                         "\"dob\": \"1977-03-09 23:00:07\"," +
                         "\"registered\": \"2014-03-16 21:13:03\"," +
                         "\"phone\": \"982-933-152\"," +
                         "\"cell\": \"682-638-588\"," +
                         "\"id\": {" +
                         "\"name\": \"DNI\"," +
                         "\"value\": \"32509216-N\"" +
                         "}," +
                         "\"picture\": {" +
                         "\"large\": \"https://randomuser.me/api/portraits/women/90.jpg\"," +
                         "\"medium\": \"https://randomuser.me/api/portraits/med/women/90.jpg\"," +
                         "\"thumbnail\": \"https://randomuser.me/api/portraits/thumb/women/90.jpg\"" +
                         "}," +
                         "\"nat\": \"ES\"" +
                         "}" +
                         "]," +
                         "\"info\": {" +
                         "\t\"seed\": \"abc\"," +
                         "\t\"results\": 20," +
                         "\t\"page\": 20," +
                         "\t\"version\": \"1.1\"" +
                         "}" +
                         "}";
        duplicatedUsers = "{" +
                          "\"results\": [" +
                          "{" +
                          "\"gender\": \"female\"," +
                          "\"name\": {" +
                          "\"title\": \"miss\"," +
                          "\"first\": \"carmen\"," +
                          "\"last\": \"serrano\"" +
                          "}," +
                          "\"location\": {" +
                          "\"street\": \"4562 calle del arenal\"," +
                          "\"city\": \"parla\"," +
                          "\"state\": \"aragón\"," +
                          "\"postcode\": 26914" +
                          "}," +
                          "\"email\": \"carmen.serrano@example.com\"," +
                          "\"login\": {" +
                          "\"username\": \"yellowpeacock112\"," +
                          "\"password\": \"ffff\"," +
                          "\"salt\": \"KLBLMKJ3\"," +
                          "\"md5\": \"a55b2f726298ead1e29cbf78f1c9877c\"," +
                          "\"sha1\": \"75a018c009ca8510b946c9fc1c2e228bf62db500\"," +
                          "\"sha256\": \"8d5dadcac6e636ce6b03120857c8b6bda0f78533ba75288ba5db0d65bcab14f3\"" +
                          "}," +
                          "\"dob\": \"1966-10-21 19:35:09\"," +
                          "\"registered\": \"2016-01-07 22:52:33\"," +
                          "\"phone\": \"915-578-484\"," +
                          "\"cell\": \"669-597-780\"," +
                          "\"id\": {" +
                          "\"name\": \"DNI\"," +
                          "\"value\": \"35415756-Z\"" +
                          "}," +
                          "\"picture\": {" +
                          "\"large\": \"https://randomuser.me/api/portraits/women/17.jpg\"," +
                          "\"medium\": \"https://randomuser.me/api/portraits/med/women/17.jpg\"," +
                          "\"thumbnail\": \"https://randomuser.me/api/portraits/thumb/women/17.jpg\"" +
                          "}," +
                          "\"nat\": \"ES\"" +
                          "}," +
                          "{" +
                          "\"gender\": \"male\"," +
                          "\"name\": {" +
                          "\"title\": \"mr\"," +
                          "\"first\": \"juan\"," +
                          "\"last\": \"martin\"" +
                          "}," +
                          "\"location\": {" +
                          "\"street\": \"7643 calle de atocha\"," +
                          "\"city\": \"vigo\"," +
                          "\"state\": \"andalucía\"," +
                          "\"postcode\": 18042" +
                          "}," +
                          "\"email\": \"juan.martin@example.com\"," +
                          "\"login\": {" +
                          "\"username\": \"lazybird844\"," +
                          "\"password\": \"lacrosse\"," +
                          "\"salt\": \"oFJOZNKT\"," +
                          "\"md5\": \"594b2acb48481fc0bfa4166732ef18f7\"," +
                          "\"sha1\": \"9a17dbadb4a3ad4d6d4f83bae8906647f3025224\"," +
                          "\"sha256\": \"540ffe86f2cea9cf502127b7feed3ae23faf8fbb8ec3d72780d349fef1205093\"" +
                          "}," +
                          "\"dob\": \"1995-05-20 07:50:54\"," +
                          "\"registered\": \"2003-05-17 21:39:36\"," +
                          "\"phone\": \"933-263-803\"," +
                          "\"cell\": \"694-211-086\"," +
                          "\"id\": {" +
                          "\"name\": \"DNI\"," +
                          "\"value\": \"84144025-I\"" +
                          "}," +
                          "\"picture\": {" +
                          "\"large\": \"https://randomuser.me/api/portraits/men/94.jpg\"," +
                          "\"medium\": \"https://randomuser.me/api/portraits/med/men/94.jpg\"," +
                          "\"thumbnail\": \"https://randomuser.me/api/portraits/thumb/men/94.jpg\"" +
                          "}," +
                          "\"nat\": \"ES\"" +
                          "}," +
                          "{" +
                          "\"gender\": \"female\"," +
                          "\"name\": {" +
                          "\"title\": \"ms\"," +
                          "\"first\": \"aurora\"," +
                          "\"last\": \"vicente\"" +
                          "}," +
                          "\"location\": {" +
                          "\"street\": \"4535 calle de la democracia\"," +
                          "\"city\": \"cuenca\"," +
                          "\"state\": \"castilla y león\"," +
                          "\"postcode\": 98492" +
                          "}," +
                          "\"email\": \"aurora.vicente@example.com\"," +
                          "\"login\": {" +
                          "\"username\": \"whiteladybug671\"," +
                          "\"password\": \"copper\"," +
                          "\"salt\": \"1pgaMRHB\"," +
                          "\"md5\": \"ba5718a626bc8835c55c14b01939c90c\"," +
                          "\"sha1\": \"9613ef2af96f722c1ef5b976b86a549ce906e7c2\"," +
                          "\"sha256\": \"a583f18a17d58bd42b91370ea888e40337b6284c8bde40c0b5a342a90558ba2f\"" +
                          "}," +
                          "\"dob\": \"1977-03-09 23:00:07\"," +
                          "\"registered\": \"2014-03-16 21:13:03\"," +
                          "\"phone\": \"982-933-152\"," +
                          "\"cell\": \"682-638-588\"," +
                          "\"id\": {" +
                          "\"name\": \"DNI\"," +
                          "\"value\": \"32509216-N\"" +
                          "}," +
                          "\"picture\": {" +
                          "\"large\": \"https://randomuser.me/api/portraits/women/90.jpg\"," +
                          "\"medium\": \"https://randomuser.me/api/portraits/med/women/90.jpg\"," +
                          "\"thumbnail\": \"https://randomuser.me/api/portraits/thumb/women/90.jpg\"" +
                          "}," +
                          "\"nat\": \"ES\"" +
                          "}," +
                          "\t{" +
                          "\"gender\": \"female\"," +
                          "\"name\": {" +
                          "\"title\": \"miss\"," +
                          "\"first\": \"carmen\"," +
                          "\"last\": \"serrano\"" +
                          "}," +
                          "\"location\": {" +
                          "\"street\": \"4562 calle del arenal\"," +
                          "\"city\": \"parla\"," +
                          "\"state\": \"aragón\"," +
                          "\"postcode\": 26914" +
                          "}," +
                          "\"email\": \"carmen.serrano@example.com\"," +
                          "\"login\": {" +
                          "\"username\": \"yellowpeacock112\"," +
                          "\"password\": \"ffff\"," +
                          "\"salt\": \"KLBLMKJ3\"," +
                          "\"md5\": \"a55b2f726298ead1e29cbf78f1c9877c\"," +
                          "\"sha1\": \"75a018c009ca8510b946c9fc1c2e228bf62db500\"," +
                          "\"sha256\": \"8d5dadcac6e636ce6b03120857c8b6bda0f78533ba75288ba5db0d65bcab14f3\"" +
                          "}," +
                          "\"dob\": \"1966-10-21 19:35:09\"," +
                          "\"registered\": \"2016-01-07 22:52:33\"," +
                          "\"phone\": \"915-578-484\"," +
                          "\"cell\": \"669-597-780\"," +
                          "\"id\": {" +
                          "\"name\": \"DNI\"," +
                          "\"value\": \"35415756-Z\"" +
                          "}," +
                          "\"picture\": {" +
                          "\"large\": \"https://randomuser.me/api/portraits/women/17.jpg\"," +
                          "\"medium\": \"https://randomuser.me/api/portraits/med/women/17.jpg\"," +
                          "\"thumbnail\": \"https://randomuser.me/api/portraits/thumb/women/17.jpg\"" +
                          "}," +
                          "\"nat\": \"ES\"" +
                          "}," +
                          "\t{" +
                          "\"gender\": \"female\"," +
                          "\"name\": {" +
                          "\"title\": \"miss\"," +
                          "\"first\": \"carmen\"," +
                          "\"last\": \"serrano\"" +
                          "}," +
                          "\"location\": {" +
                          "\"street\": \"4562 calle del arenal\"," +
                          "\"city\": \"parla\"," +
                          "\"state\": \"aragón\"," +
                          "\"postcode\": 26914" +
                          "}," +
                          "\"email\": \"carmen.serrano@example.com\"," +
                          "\"login\": {" +
                          "\"username\": \"yellowpeacock112\"," +
                          "\"password\": \"ffff\"," +
                          "\"salt\": \"KLBLMKJ3\"," +
                          "\"md5\": \"a55b2f726298ead1e29cbf78f1c9877c\"," +
                          "\"sha1\": \"75a018c009ca8510b946c9fc1c2e228bf62db500\"," +
                          "\"sha256\": \"8d5dadcac6e636ce6b03120857c8b6bda0f78533ba75288ba5db0d65bcab14f3\"" +
                          "}," +
                          "\"dob\": \"1966-10-21 19:35:09\"," +
                          "\"registered\": \"2016-01-07 22:52:33\"," +
                          "\"phone\": \"915-578-484\"," +
                          "\"cell\": \"669-597-780\"," +
                          "\"id\": {" +
                          "\"name\": \"DNI\"," +
                          "\"value\": \"35415756-Z\"" +
                          "}," +
                          "\"picture\": {" +
                          "\"large\": \"https://randomuser.me/api/portraits/women/17.jpg\"," +
                          "\"medium\": \"https://randomuser.me/api/portraits/med/women/17.jpg\"," +
                          "\"thumbnail\": \"https://randomuser.me/api/portraits/thumb/women/17.jpg\"" +
                          "}," +
                          "\"nat\": \"ES\"" +
                          "}" +
                          "]," +
                          "\"info\": {" +
                          "\t\"seed\": \"abc\"," +
                          "\t\"results\": 20," +
                          "\t\"page\": 20," +
                          "\t\"version\": \"1.1\"" +
                          "}" +
                          "}";
    }
}
