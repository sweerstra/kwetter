import { EDIT_USER, NOT_FOUND, SET_FOLLOW_STATE, SET_FOLLOWING, SET_USER } from '../constants/ActionTypes';

const user = (state = { selectedUser: {}, userNotFound: false, following: [], isFollowing: false }, action) => {
    switch (action.type) {
        case SET_USER:
            return { ...state, selectedUser: action.user };

        case EDIT_USER:
            return { ...state, selectedUser: { ...state.selectedUser, ...action.user } };

        case SET_FOLLOWING:
            return { ...state, following: action.following };

        case SET_FOLLOW_STATE:
            return { ...state, isFollowing: action.isFollowing };

        case NOT_FOUND:
            return { ...state, userNotFound: true };

        default:
            return state;
    }
};

export default user;