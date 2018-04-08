import React, { Component } from 'react';
import './Kweets.css';
import { Link } from 'react-router-dom';
import PostKweet from '../PostKweet/PostKweet';
import Kweet from '../Kweet/Kweet';

class Kweets extends Component {
    constructor(props) {
        super(props);

        this.state = { postKweetOpen: false };
    }

    render() {
        const { postKweetOpen } = this.state;
        const { className, kweets, kweetsType, onKweetLike, authenticated } = this.props;
        const isActive = (type) => kweetsType === type ? 'active' : '';

        return (
            <div className={`${className} kweets`}>
                {authenticated && <div className="kweets__header">
                    <Link to="kweets" className={`h2 kweets__heading ${isActive('kweets')}`}>Kweets</Link>
                    <Link to="timeline" className={`h2 kweets__heading ${isActive('timeline')}`}>Timeline</Link>
                    <Link to="mentions" className={`h2 kweets__heading ${isActive('mentions')}`}>Mentions</Link>
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