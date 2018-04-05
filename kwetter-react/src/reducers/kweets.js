import { LIKE_KWEET, POST_KWEET, SET_KWEETS, SET_KWEETS_FOUND } from '../constants/ActionTypes';

const kweets = (state = { kweets: [], kweetsFound: [] }, action) => {
    switch (action.type) {
        case SET_KWEETS:
            return { ...state, kweets: action.kweets };

        case POST_KWEET:
            action.kweet.user = action.user;
            return { ...state, kweets: [action.kweet, ...state.kweets] };

        case LIKE_KWEET:
            const kweets = state.kweets.map(kweet => {
                    if (kweet.id === action.id) {
                        return { ...kweet, likes: 1 /* kweet.likes + 1 */, liked: true };
                    }
                    return kweet;
                }
            );

            return { ...state, kweets };

        case SET_KWEETS_FOUND:
            return { ...state, kweetsFound: action.kweetsFound };

        default:
            return state;
    }
};

export default kweets;