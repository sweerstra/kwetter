import { POST_KWEET, SET_KWEETS } from '../constants/ActionTypes';

const kweets = (state = { kweets: [] }, action) => {
    switch (action.type) {
        case SET_KWEETS:
            return { kweets: action.kweets };

        case POST_KWEET:
            action.kweet.user = action.user;
            return { kweets: [action.kweet, ...state.kweets] };

        default:
            return state;
    }
};

export default kweets;