import { EDIT_USER, NOT_FOUND, SET_USER } from '../constants/ActionTypes';

const user = (state = { selectedUser: {}, userNotFound: false }, action) => {
    switch (action.type) {
        case SET_USER:
            return { selectedUser: action.user };

        case EDIT_USER:
            return { selectedUser: { ...state.selectedUser, ...action.user } };

        case NOT_FOUND:
            return { userNotFound: true };

        default:
            return state;
    }
};

export default user;