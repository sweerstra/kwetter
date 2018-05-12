import React from 'react';
import './ProfileDetails.css';
import icons from '../../icons';

const ProfileDetails = ({ className, profile: { id, username, profilePicture, bio, location, website }, isAuthenticated, isFollowing, onFollowChange }) => (
    <div className={`${className} profile-details`}>
        <div className="profile-details__picture">
            <img src={profilePicture} alt="Profile"/>
        </div>
        <div className="profile__details__info">
            <div className="profile__details__info__username">
                <a href={`/profile/${username}/kweets`} className="h2">
                    @{username}
                </a>
                {isAuthenticated && <button className={`profile__details__info__follow btn ${isFollowing ? 'red' : ''}`}
                                            onClick={() => onFollowChange(isFollowing ? 'unfollow' : 'follow', id)}>
                    {isFollowing ? 'Unfollow' : 'Follow'}
                </button>}
            </div>
            <div className="profile__details__info__bio">
                <icons.user/>
                {bio}
            </div>
            <div className="profile__details__info__location">
                <icons.map/>
                {location}
            </div>
            <div className="profile__details__info__website">
                <icons.link/>
                <a href={website}>{website}</a>
            </div>
        </div>
    </div>
);

export default ProfileDetails;