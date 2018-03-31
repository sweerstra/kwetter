import React, { Component } from 'react';
import './Kweets.css';
import PostKweet from '../PostKweet/PostKweet';
import Kweet from '../Kweet/Kweet';

class Kweets extends Component {
    constructor(props) {
        super(props);

        this.state = { showPostKweet: false };
    }

    render() {
        const { showPostKweet } = this.state;
        const { className, kweets, onKweetLike, authenticated } = this.props;

        return (
            <div className={`${className} kweets`}>
                {authenticated && <div className="kweets__header">
                    <a href="#" className="h2">My Kweets</a>
                    <a href="#" className="h2">Timeline</a>
                    <a href="#" className="h2">Mentions</a>
                    {!showPostKweet && <button className="kweets__header__post-kweet btn"
                                               onClick={this.showPostKweet}>Post Kweet</button>}
                </div>}
                {showPostKweet && <PostKweet onKweetPost={this.onPostKweet}
                                             onKweetPostCancel={this.hidePostKweet}/>
                }
                {kweets.map((kweet, index) =>
                    <Kweet {...kweet} onLike={() => onKweetLike(kweet)} key={index}/>
                )}
            </div>
        );
    }

    onPostKweet = (value) => {
        this.hidePostKweet();
        this.props.onKweetPost(value);
    };

    showPostKweet = () => this.setState({ showPostKweet: true });

    hidePostKweet = () => this.setState({ showPostKweet: false });
}

export default Kweets;