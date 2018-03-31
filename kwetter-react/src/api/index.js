import Request from './Request';
import { KWEET_API_URL, USER_API_URL } from '../constants';

export default {
    user: {
        authenticate: (username, password) => Request.post(`${USER_API_URL}/auth`, { username, password }),
        getUser: (username) => Request.get(`${USER_API_URL}/${username}`),
        editUser: (id, user) => Request.put(`${USER_API_URL}/${id}`, user),
        getFollowing: (id) => Request.get(`${USER_API_URL}/following`),
        getFollowers: (id) => Request.get(`${USER_API_URL}/followers`),
        follow: (id, followId) => Request.post(`${USER_API_URL}/${id}/follow/${followId}`),
        unfollow: (id, unfollowId) => Request.post(`${USER_API_URL}/${id}/unfollow/${unfollowId}`)
    },
    kweet: {
        getKweet: (id) => Request.get(`${KWEET_API_URL}/${id}`),
        postKweet: (text, id) => Request.post(KWEET_API_URL, { text, user: { id } }),
        editKweet: (id, kweet) => Request.put(`${KWEET_API_URL}/${id}`, kweet),
        searchKweets: (text) => Request.get(`${KWEET_API_URL}/search/${text}`),
        getKweets: (userId) => Request.get(`${KWEET_API_URL}/user/${userId}`),
        getTimeline: (userId) => Request.get(`${KWEET_API_URL}/timeline/${userId}`),
        getKweetsByTrend: (trend) => Request.get(`${KWEET_API_URL}/trend/${trend}`),
        getKweetsByMention: (mention) => Request.get(`${KWEET_API_URL}/mention/${mention}`),
        likeKweet: (userId, kweet) => Request.post(`${KWEET_API_URL}/like/${userId}`, kweet)
    }
};