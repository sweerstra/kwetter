import React from 'react';
import './Kweets.css';
import PostKweet from '../PostKweet/PostKweet';
import Kweet from '../Kweet/Kweet';

const Kweets = ({ className, kweets, showPostKweet, onShowPostKweet, onKweetPost, onKweetCancel }) => (
    <div className={`${className} kweets`}>
        <div className="kweets__heading">
            <a href="#" className="h2">Kweets</a>
            <a href="#" className="h2">Timeline</a>
            {!showPostKweet && <button className="kweets__heading__post-kweet btn"
                                       onClick={onShowPostKweet}>Post Kweet</button>}
        </div>
        {showPostKweet && <PostKweet onKweetPost={onKweetPost}
                                     onKweetCancel={onKweetCancel}/>
        }
        {kweets.map((kweet, index) =>
            <Kweet {...kweet} key={index}/>
        )}
    </div>
);

export default Kweets;