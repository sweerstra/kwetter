import React from 'react';
import './ProfileDetails.css';
import icons from '../../icons';

const ProfileDetails = ({ className, profile: { username, profilePicture, bio, location, website }, followed, onFollowChange }) => (
    <div className={`${className} profile-details`}>
        <div className="profile-details__picture">
            <img src={profilePicture} alt="Profile"/>
        </div>
        <div className="profile__details__info">
            <div className="profile__details__info__username">
                <a href="#" className="h2">
                    @{username}
                </a>
                <button className={`profile__details__info__follow btn ${followed ? 'red' : ''}`}
                        onClick={() => onFollowChange(followed ? 'unfollow' : 'follow', username)}>
                    {followed ? 'Unfollow' : 'Follow'}
                </button>
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
                <a href="#">{website}</a>
            </div>
        </div>
    </div>
);

export default ProfileDetails;