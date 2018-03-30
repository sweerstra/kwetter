import React from 'react';
import './Kweet.css';
import icons from '../../icons';

const Kweet = ({ profilePicture, username, text, date, likes, onLike }) => (
    <div className="kweet">
        <div className="kweet__profile-picture">
            <img src={profilePicture}/>
        </div>
        <div className="kweet__content">
            <div className="kweet__content__details">
                <a href="#">@{username}</a>
                <span className="date">{date.toLocaleDateString()}</span>
            </div>
            <div className="kweet__content__text">
                {text}
            </div>
            <div className="kweet__content__like">
                <icons.heart onClick={onLike}/>
                <span className="kweet__content__like-count">{likes.toLocaleString()}</span>
            </div>
        </div>
    </div>
);

export default Kweet;