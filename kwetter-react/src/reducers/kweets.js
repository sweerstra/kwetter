import {
    LIKE_KWEET,
    POST_KWEET,
    SET_KWEETS,
    SET_KWEETS_FOUND,
    SET_LIKED_KWEETS,
    SET_TRENDS
} from '../constants/ActionTypes';

const kweets = (state = { kweets: [], kweetsFound: [], trends: [] }, action) => {
    switch (action.type) {
        case SET_KWEETS:
            return { ...state, kweets: action.kweets };

        case POST_KWEET:
            action.kweet.user = action.user;
            return { ...state, kweets: [action.kweet, ...state.kweets] };

        case LIKE_KWEET:
            return {
                ...state,
                kweets: state.kweets.map(kweet => {
                        if (kweet.id === action.kweetId) {
                            return { ...kweet, likes: kweet.likes + 1, liked: true };
                        }
                        return kweet;
                    }
                )
            };

        case SET_LIKED_KWEETS:
            return {
                ...state,
                kweets: state.kweets.map(kweet => {
                        if (action.likes.includes(kweet.id)) {
                            return { ...kweet, liked: true };
                        }
                        return kweet;
                    }
                )
            };

        case SET_KWEETS_FOUND:
            return { ...state, kweetsFound: action.kweetsFound };

        case SET_TRENDS:
            return { ...state, trends: action.trends };

        default:
            return state;
    }
};

export default kweets;