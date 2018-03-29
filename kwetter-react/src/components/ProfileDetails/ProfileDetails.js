import React from 'react';
import './ProfileDetails.css';
import icons from '../../icons';
import EditDetail from '../../components/EditDetail/EditDetail';

const ProfileDetails = ({ className, profile: { username, profilePicture, bio, location, website }, onEdit }) => (
    <div className={`${className} profile-details`}>
        <div className="profile-details__picture">
            <img src={profilePicture} alt="Profile"/>
        </div>
        <div className="profile__details__info">
            <div className="profile__details__info__username">
                <a href="#" className="h2">
                    @{username}
                </a>
            </div>
            <div className="profile__details__info__bio">
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
            <EditDetail className="profile__details__info__website"
                        name="website"
                        placeholder="Website"
                        onEdit={onEdit}>
                <icons.link/>
                <a href="#">{website}</a>
            </EditDetail>
        </div>
    </div>
);

export default ProfileDetails;