import React, { Component } from 'react';
import './profile.css';
import Navigation from '../../components/Navigation/Navigation';
import ProfileDetails from '../../components/ProfileDetails/ProfileDetails';
import ProfileDetailsEditable from '../../components/ProfileDetails/ProfileDetailsEditable';
import Kweets from '../../components/Kweets/Kweets';
import ProfileActivity from '../../components/ProfileActivity/ProfileActivity';
import { Redirect } from 'react-router-dom';
import { connect } from 'react-redux';
import { editSelectedUser, logout, postKweet, setKweetsOfUser, setSelectedUser } from '../../actions/index';

class Profile extends Component {
    constructor(props) {
        super(props);

        this.state = { suggestions: [] };
    }

    async componentDidMount() {
        const { username, kweetsType } = this.props.match.params;

        this.props.onSetSelectedUser(username)
            .then(() => this.props.onSetKweetsOfUser(username, kweetsType));
    }

    async componentWillReceiveProps(nextProps) {
        if (this.props.match.params.kweetsType === nextProps.match.params.kweetsType) return;

        const { username, kweetsType } = nextProps.match.params;
        this.props.onSetKweetsOfUser(username, kweetsType);
    }

    render() {
        const users = [
            {
                profilePicture: 'https://pbs.twimg.com/profile_images/446356636710363136/OYIaJ1KK_bigger.png',
                username: 'react.js'
            },
            {
                profilePicture: 'https://pbs.twimg.com/profile_images/702185727262482432/n1JRsFeB_bigger.png',
                username: 'node.js'
            },
            {
                profilePicture: 'https://pbs.twimg.com/profile_images/875996174305472512/upM71pVR_bigger.jpg',
                username: 'vue.js'
            }
        ];

        const trends = [
            '#react',
            '#node',
            '#vue'
        ];

        const { suggestions } = this.state;
        const { selectedUser, isOwnUser, isAuthenticated, userLoggedIn, userNotFound } = this.props;

        if (userNotFound) {
            return <Redirect to="/404"/>
        }

        const profileDetails = isAuthenticated && isOwnUser
            ? <ProfileDetailsEditable className="profile__profile-details"
                                      profile={selectedUser}
                                      onEdit={user => this.props.onEditSelectedUser(userLoggedIn.username, user)}/>
            : <ProfileDetails className="profile__profile-details"
                              profile={selectedUser}
                              followed={false}
                              onFollowChange={this.onFollowChange}/>;

        return (
            <div className="profile">
                <Navigation className="profile__nav"
                            onSearch={this.onSearchKweets}
                            kweetSuggestions={suggestions}
                            onSearchCancel={() => this.setState({ suggestions: [] })}
                            userLoggedIn={userLoggedIn}
                            onLogout={this.props.onLogout}/>
                {profileDetails}
                <Kweets className="profile__kweets"
                        kweets={this.props.kweets}
                        onKweetPost={text => this.props.onPostKweet(text, userLoggedIn)}
                        onKweetLike={this.onKweetLike}
                        authenticated={isAuthenticated}/>
                <ProfileActivity className="profile__profile-activity"
                                 following={users.concat(users.concat(users))}
                                 followers={users.reverse()}
                                 trends={trends}/>
            </div>
        );
    }

    onSearchKweets = (value) => {
        if (!value) return;

        const query = value.toLowerCase();
        // TODO: make api call to /search
        const suggestions = this.state.kweets.filter(kweet => kweet.text.toLowerCase().includes(query));
        this.setState(state => ({ suggestions }));
    };

    onKweetLike = (kweet) => {
        console.log('like', kweet);
    };

    // TODO: change username to id
    onFollowChange = (type, username) => {
        console.log(type, username);
    };
}

const mapStateToProps = ({ auth, user, kweets }) => {
    const isOwnUser = auth.userLoggedIn
        && user.selectedUser
        && auth.userLoggedIn.username === user.selectedUser.username;

    return ({
        ...auth,
        ...user,
        ...kweets,
        isOwnUser
    });
};

const mapDispatchToProps = (dispatch) => ({
    onSetSelectedUser: (username) => dispatch(setSelectedUser(username)),
    onEditSelectedUser: (userame, user) => dispatch(editSelectedUser(userame, user)),
    onSetKweetsOfUser: (username, kweetsType) => dispatch(setKweetsOfUser(username, kweetsType)),
    onPostKweet: (text, user) => dispatch(postKweet(text, user)),
    onLogout: () => dispatch(logout())
});

export default connect(mapStateToProps, mapDispatchToProps)(Profile);