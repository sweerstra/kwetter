import { NOT_FOUND, SET_USER } from '../constants/ActionTypes';

const user = (state = { currentUser: {}, userNotFound: false }, action) => {
    switch (action.type) {
        case SET_USER:
            return { currentUser: action.user };

        case NOT_FOUND:
            console.log('from reducer, not found');
            return { userNotFound: true };

        default:
            return state;
    }
};

export default user;