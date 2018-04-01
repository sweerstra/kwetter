import React, { Component } from 'react';
import './profile.css';
import Navigation from '../../components/Navigation/Navigation';
import ProfileDetails from '../../components/ProfileDetails/ProfileDetails';
import ProfileDetailsEditable from '../../components/ProfileDetails/ProfileDetailsEditable';
import Kweets from '../../components/Kweets/Kweets';
import ProfileActivity from '../../components/ProfileActivity/ProfileActivity';
import Api from '../../api';
import { connect } from 'react-redux';
import { logout } from '../../actions/index';

class Profile extends Component {
    constructor(props) {
        super(props);

        this.state = {
            profile: {},
            kweets: [],
            suggestions: [],
            isOwnProfile: false
        };
    }

    async componentDidMount() {
        const { username, kweetsType } = this.props.match.params;

        const profile = await Api.user.getUser(username);
        const kweets = await this.fetchKweetsType(username, kweetsType);

        const isOwnProfile = this.props.userLoggedIn.username === username;

        this.setState({ profile, kweets, isOwnProfile });
    }

    async componentWillReceiveProps(nextProps) {
        if (this.props.match.params.kweetsType === nextProps.match.params.kweetsType) return;

        const { username, kweetsType } = nextProps.match.params;
        this.setState({ kweets: (await this.fetchKweetsType(username, kweetsType)) });
    }

    async fetchKweetsType(username, kweetsType) {
        if (kweetsType === 'kweets') {
            return await Api.kweet.getKweets(username);
        } else if (kweetsType === 'timeline') {
            return await Api.kweet.getTimeline(username);
        }
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

        const { profile, kweets, suggestions, isOwnProfile } = this.state;
        const { isAuthenticated, userLoggedIn, onLogout } = this.props;

        const profileDetails = isAuthenticated && isOwnProfile
            ? <ProfileDetailsEditable className="profile__profile-details"
                                      profile={profile}
                                      onEdit={this.onEdit}/>
            : <ProfileDetails className="profile__profile-details"
                              profile={profile}
                              followed={false}
                              onFollowChange={this.onFollowChange}/>;

        return (
            <div className="profile">
                <Navigation className="profile__nav"
                            onSearch={this.onSearchKweets}
                            kweetSuggestions={suggestions}
                            onSearchCancel={this.onSearchCancel}
                            userLoggedIn={userLoggedIn}
                            onLogout={onLogout}/>
                {profileDetails}
                <Kweets className="profile__kweets"
                        kweets={kweets}
                        onKweetPost={this.onKweetPost}
                        onKweetLike={this.onKweetLike}
                        authenticated={isAuthenticated}/>
                <ProfileActivity className="profile__profile-activity"
                                 following={users.concat(users.concat(users))}
                                 followers={users.reverse()}
                                 trends={trends}/>
            </div>
        );
    }

    onEdit = (obj) => {
        this.setState(state => ({ profile: { ...state.profile, ...obj } }));
    };

    onSearchKweets = (value) => {
        if (!value) return;

        const query = value.toLowerCase();
        // TODO: make api call to /search
        const suggestions = this.state.kweets.filter(kweet => kweet.text.toLowerCase().includes(query));
        this.setState(state => ({ suggestions }));
    };

    onSearchCancel = () => {
        this.setState({ suggestions: [] });
    };

    onKweetPost = (text) => {
        const kweet = {
            username: 'reactjs',
            profilePicture: 'https://pbs.twimg.com/profile_images/446356636710363136/OYIaJ1KK_bigger.png',
            text,
            date: new Date(),
            likes: 0
        };
        this.setState(state => ({ kweets: [kweet, ...state.kweets] }));
    };

    onKweetLike = (kweet) => {
        console.log('like', kweet);
    };

    // TODO: change username to id
    onFollowChange = (type, username) => {
        console.log(type, username);
    };
}

const mapStateToProps = ({ auth }) => ({ ...auth });

const mapDispatchToProps = (dispatch) => ({
    onLogout: () => dispatch(logout())
});

export default connect(mapStateToProps, mapDispatchToProps)(Profile);