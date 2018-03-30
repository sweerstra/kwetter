import React from 'react';
import './ProfileDetails.css';
import icons from '../../icons';
import EditDetail from '../../components/EditDetail/EditDetail';

const ProfileDetailsEditable = ({ className, profile: { username, profilePicture, bio, location, website }, onEdit }) => (
    <div className={`${className} profile-details`}>
        <EditDetail className="profile-details__picture"
                    name="profilePicture"
                    placeholder="Profile Picture"
                    onEdit={onEdit}>
            <img src={profilePicture} alt="Profile"/>
        </EditDetail>
        <div className="profile__details__info">
            <EditDetail className="profile__details__info__username"
                        name="username"
                        placeholder="Username"
                        onEdit={onEdit}>
                <a href="#" className="h2">
                    @{username}
                </a>
            </EditDetail>
            <EditDetail className="profile__details__info__bio"
                        name="bio"
                        placeholder="Bio"
                        onEdit={onEdit}>
                {bio}
            </EditDetail>
            <EditDetail className="profile__details__info__location"
                        name="location"
                        placeholder="Location"
                        onEdit={onEdit}>
                <icons.map/>
                {location}
            </EditDetail>
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

export default ProfileDetailsEditable;