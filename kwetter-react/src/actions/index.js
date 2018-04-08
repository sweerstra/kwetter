import * as types from '../constants/ActionTypes';
import Api from '../api';

export const setSelectedUser = (username) => dispatch => {
    return Api.user.getUser(username)
        .then(user => dispatch({ type: types.SET_USER, user }))
        .catch(() => dispatch({ type: types.NOT_FOUND }));
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

export const followUser = (followState, userId, followId) => dispatch => {
    return Api.user[followState](userId, followId)
        .then(({ response }) => {
            response = followState === 'follow' ? response : !response;
            dispatch({ type: types.SET_FOLLOW_STATE, isFollowing: response });
        });
};

export const setKweetsOfUser = (username, kweetsType) => dispatch => {
    (kweetsType === 'kweets'
        ? Api.kweet.getKweets(username)
        : Api.kweet.getTimeline(username))
        .then(kweets => dispatch({ type: types.SET_KWEETS, kweets }));
};

export const postKweet = (text, { id, ...user }) => dispatch => {
    return Api.kweet.postKweet(text, id)
        .then(kweet => dispatch({ type: types.POST_KWEET, kweet, user }));
};

export const likeKweet = (kweetId, userId) => dispatch => {
    console.log('action', { kweetId, userId });
    /*return Api.kweet.likeKweet(userId, kweetId)
        .then(resp => dispatch({ type: types.LIKE_KWEET, resp }))
        .catch(err => err);*/

    dispatch({ type: types.LIKE_KWEET, id: kweetId });
};

export const searchKweets = (text) => dispatch => {
    return Api.kweet.searchKweets(text)
        .then(kweetsFound => dispatch({ type: types.SET_KWEETS_FOUND, kweetsFound }));
};

export const emptyFoundKweets = () => ({
    type: types.SET_KWEETS_FOUND,
    kweetsFound: []
});

export const setTrends = (trends) => dispatch => {
    Api.kweet.getCurrentTrends()
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

export const logout = () => dispatch => {
    dispatch({ type: types.LOGOUT });
    localStorage.clear();
};