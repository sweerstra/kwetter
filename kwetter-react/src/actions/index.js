import * as types from '../constants/ActionTypes';
import Api from '../api';

export const setSelectedUser = (username) => dispatch => {
    return new Promise((resolve, reject) => {
        return Api.user.getUser(username)
            .then(user => {
                dispatch({ type: types.SET_USER, user });
                resolve(user);
            })
            .catch(err => {
                dispatch({ type: types.NOT_FOUND });
                reject(err);
            });
    });
};

export const editSelectedUser = (username, user) => dispatch => {
    return Api.user.editUser({ username, ...user })
        .then(() => dispatch({ type: types.EDIT_USER, user }));
};

export const setFollowing = (id, selectedUserId) => dispatch => {
    return Api.user.getFollowing(id)
        .then(following => {
            dispatch({ type: types.SET_FOLLOWING, following });

            if (selectedUserId) {
                const isFollowing = following.some(user => user.id === selectedUserId);
                dispatch({ type: types.SET_FOLLOW_STATE, isFollowing });
            }
        });
};

export const setFollowers = (id) => dispatch => {
    return Api.user.getFollowers(id)
        .then(followers => dispatch({ type: types.SET_FOLLOWERS, followers }));
};

export const followUser = (followState, userId, followId) => dispatch => {
    return Api.user[followState](userId, followId)
        .then(({ response }) => {
            response = followState === 'follow' ? response : !response;
            dispatch({ type: types.SET_FOLLOW_STATE, isFollowing: response });
        });
};

export const setKweetsOfUser = (username, kweetsType) => dispatch => {
    const kweetsMethods = new Map([
        ['kweets', 'getKweets'],
        ['timeline', 'getTimeline'],
        ['mentions', 'getKweetsByMention']
    ]);

    return Api.kweet[kweetsMethods.get(kweetsType)](username)
        .then(kweets => dispatch({ type: types.SET_KWEETS, kweets }));
};

export const getUser = (username) => {
    return Api.user.getUser(username);
};

export const postKweet = (text, { id, ...user }) => dispatch => {
    return Api.kweet.postKweet(text, id)
        .then(kweet => dispatch({ type: types.POST_KWEET, kweet, user }));
};

export const likeKweet = (kweetId, userId) => dispatch => {
    return Api.kweet.likeKweet(userId, { id: kweetId })
        .then(({ response }) => {
            if (response) {
                dispatch({ type: types.LIKE_KWEET, kweetId });
            }
        });
};

export const checkUserLikes = (userId) => dispatch => {
    Api.kweet.getUserLikes(userId)
        .then(likes => dispatch({ type: types.SET_LIKED_KWEETS, likes }));
};

export const searchKweets = (text) => {
    return Api.kweet.searchKweets(text);
};

export const searchAndSetKweets = (text) => dispatch => {
    return Api.kweet.searchKweets(text)
        .then(kweetsFound => dispatch({ type: types.SET_KWEETS_FOUND, kweetsFound }));
};

export const setTrends = (trends) => dispatch => {
    return Api.kweet.getCurrentTrends()
        .then(trends => dispatch({ type: types.SET_TRENDS, trends }));
};

export const authenticate = (username, password) => dispatch => {
    return Api.user.authenticate(username, password)
        .then(user => {
            dispatch({ type: types.LOGIN, user });
            localStorage.setItem('access_token', 'token');
            localStorage.setItem('logged_in', JSON.stringify(user));
            return true;
        })
        .catch(err => {
            console.log(err);
            return false;
        });
};

export const register = (username, password, shouldLogin) => dispatch => {
    return Api.user.register(username, password)
        .then(() => {
            if (shouldLogin) {
                dispatch(authenticate(username, password));
            }
        });
};

export const logout = () => dispatch => {
    dispatch({ type: types.LOGOUT });
    localStorage.clear();
};