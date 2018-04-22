import React, { Component } from 'react';
import Navigation from '../components/Navigation/Navigation';
import { connect } from 'react-redux';
import { logout, searchKweets } from '../actions/index';

class NavigationContainer extends Component {
    constructor(props) {
        super(props);

        this.state = { kweetSuggestions: [] };
    }

    render() {
        const { kweetSuggestions } = this.state;
        const { userLoggedIn, onSearchEnter, onLogout } = this.props;

        return (
            <Navigation onSearch={this.onSearchKweetSuggestions}
                        kweetSuggestions={kweetSuggestions}
                        onSearchEnter={onSearchEnter}
                        onSearchCancel={this.onEmptyKweetSuggestions}
                        userLoggedIn={userLoggedIn}
                        onLogout={onLogout}
            />
        );
    }

    onSearchKweetSuggestions = (text) => {
        if (text) {
            console.log('search', text);
            searchKweets(text)
                .then(kweetSuggestions => this.setState({ kweetSuggestions }));
        }
    };

    onEmptyKweetSuggestions = () => {
        this.setState({ kweetSuggestions: [] });
    };
}

const mapStateToProps = ({ auth, kweets }) => ({
    ...auth,
    ...kweets
});

const mapDispatchToProps = (dispatch) => ({
    onLogout: () => dispatch(logout())
});

export default connect(mapStateToProps, mapDispatchToProps)(NavigationContainer);