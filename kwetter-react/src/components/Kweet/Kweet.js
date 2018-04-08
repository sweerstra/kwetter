import React from 'react';
import './Kweet.css';
import icons from '../../icons';

const Kweet = ({ user: { username, profilePicture }, text, date, likes = 0, liked, onLike }) => (
    <div className="kweet">
        <div className="kweet__profile-picture">
            <img src={profilePicture}/>
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

const transformText = (text) => {
    return text.split(' ').map((word, index) => {
        if (word.startsWith('@')) {
            return <a href={`/profile/${word.slice(1)}/kweets`} key={index}>{word}</a>;
        } else if (word.startsWith('#')) {
            return <a href={`/search/${word.slice(1)}`} key={index}>{word}</a>;
        } else {
            return ` ${word} `;
        }
    });
};

export default Kweet;