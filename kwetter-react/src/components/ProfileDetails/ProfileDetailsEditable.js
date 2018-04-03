import React from 'react';
import './ProfileDetails.css';
import icons from '../../icons';
import EditProfileDetail from '../EditProfileDetail/EditProfileDetail';

const ProfileDetailsEditable = ({ className, profile: { username, profilePicture, bio, location, website }, onEdit }) => (
    <div className={`${className} profile-details`}>
        <EditProfileDetail className="profile-details__picture"
                           value={profilePicture}
                           name="profilePicture"
                           placeholder="Profile Picture"
                           onEdit={onEdit}>
            <img src={profilePicture} alt="Profile"/>
        </EditProfileDetail>
        <div className="profile__details__info">
            <EditProfileDetail className="profile__details__info__username"
                               value={username}
                               name="username"
                               placeholder="Username"
                               onEdit={onEdit}>
                <a href="#" className="h2">
                    @{username}
                </a>
            </EditProfileDetail>
            <EditProfileDetail className="profile__details__info__bio"
                               value={bio}
                               name="bio"
                               placeholder="Bio"
                               onEdit={onEdit}>
                <icons.user/>
                {bio}
            </EditProfileDetail>
            <EditProfileDetail className="profile__details__info__location"
                               value={location}
                               name="location"
                               placeholder="Location"
                               onEdit={onEdit}>
                <icons.map/>
                {location}
            </EditProfileDetail>
            <EditProfileDetail className="profile__details__info__website"
                               value={website}
                               name="website"
                               placeholder="Website"
                               onEdit={onEdit}>
                <icons.link/>
                <a href={website}>{website}</a>
            </EditProfileDetail>
        </div>
    </div>
);

export default ProfileDetailsEditable;