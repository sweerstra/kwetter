import React from 'react';
import './ProfileDetails.css';
import icons from '../../icons';

const ProfileDetails = ({ className }) => (
    <div className={`${className} profile-details`}>
        <div className="profile-details__picture">
            <img src="https://pbs.twimg.com/profile_images/446356636710363136/OYIaJ1KK_bigger.png" alt="Profile"/>
        </div>
        <div className="profile__details__info">
            <div className="profile__details__info__username">
                <a href="#" className="h2">
                    @reactjs
                </a>
            </div>
            <div className="profile__details__info__bio">
                React is a declarative, efficient, and flexible JavaScript library for building user interfaces.
            </div>
            <div className="profile__details__info__location">
                <icons.map/>
                Toronto
            </div>
            <div className="profile__details__info__website">
                <icons.link/>
                <a href="#">acebook.github.io/react/</a>
            </div>
        </div>
    </div>
);

export default ProfileDetails;