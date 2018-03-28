import React, { Component } from 'react';
import './PostKweet.css';

class PostKweet extends Component {
    state = {
        hideKweetBox: true
    };

    render() {
        return (
            <div className="post-kweet">
                <textarea className="post-kweet__value"
                          placeholder="What's happening?">

                </textarea>
                <div className="post-kweet__controls">
                    <button className="post-kweet__controls__confirm btn">Confirm</button>
                    <span className="post-kweet__controls__characters-left">140</span>
                </div>
            </div>
        );
    }
}

export default PostKweet;