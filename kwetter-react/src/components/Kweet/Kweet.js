import React from 'react';
import './Kweet.css';
import icons from '../../icons';
import { transformText } from '../../utils';

const Kweet = ({ user: { username, profilePicture }, text, date, likes, liked, onLike }) => (
    <div className="kweet">
        <div className="kweet__profile-picture">
            <img src={profilePicture} alt="Profile"/>
        </div>
        <div className="kweet__content">
            <div className="kweet__content__details">
                <a href={`/profile/${username}/kweets`}>@{username}</a>
                <span className="date">{new Date(date).toLocaleDateString()}</span>
            </div>
            <div className="kweet__content__text">
                {transformText(text)}
            </div>
            <div className="kweet__content__like">
                <icons.heart onClick={onLike} liked={liked}/>
                <span className="kweet__content__like-count">{likes.toLocaleString()}</span>
            </div>
        </div>
    </div>
);

export default Kweet;