import React, { Component } from 'react';
import './Kweets.css';
import { NavLink } from 'react-router-dom';
import PostKweet from '../PostKweet/PostKweet';
import Kweet from '../Kweet/Kweet';

class Kweets extends Component {
    constructor(props) {
        super(props);

        this.state = { postKweetOpen: false };
    }

    render() {
        const { postKweetOpen } = this.state;
        const { className, kweets, onKweetLike, authenticated } = this.props;

        return (
            <div className={`${className} kweets`}>
                {authenticated && <div className="kweets__header">
                    <NavLink to="kweets" className="h2">Kweets</NavLink>
                    <NavLink to="timeline" className="h2">Timeline</NavLink>
                    <NavLink to="mentions" className="h2">Mentions</NavLink>
                    {!postKweetOpen && <button className="kweets__header__post-kweet btn"
                                               onClick={() => this.setPostKweet(true)}>Post Kweet</button>}
                </div>}
                {postKweetOpen && <PostKweet onKweetPost={this.onPostKweet} onCancel={() => this.setPostKweet(false)}/>}
                {kweets.map((kweet, index) =>
                    <Kweet {...kweet} onLike={() => onKweetLike(kweet)} key={index}/>
                )}
            </div>
        );
    }

    onPostKweet = (value) => {
        this.setPostKweet(false);
        this.props.onKweetPost(value);
    };

    setPostKweet = (postKweetOpen) => this.setState({ postKweetOpen });
}

export default Kweets;