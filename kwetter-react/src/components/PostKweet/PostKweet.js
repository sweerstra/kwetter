import React, { Component } from 'react';
import './PostKweet.css';

class PostKweet extends Component {
    onKweetConfirm = () => {
        const { value } = this.state;
        this.props.onKweetPost(value.slice(0, 140));
    };
    onTextChange = (e) => {
        const { value } = e.target;
        const charactersLeft = 140 - parseFloat(value.length);

        if (charactersLeft < 0) {
            this.setState({ charactersLeft: 0 });
            return;
        }

        this.setState({ value, charactersLeft });
    };

    constructor(props) {
        super(props);

        this.state = {
            value: '',
            charactersLeft: 140
        }
    }

    render() {
        const { text, charactersLeft } = this.state;
        const { onKweetCancel } = this.props;

        return (
            <div className="post-kweet">
                <textarea className="post-kweet__value"
                          placeholder="What's happening?"
                          value={text}
                          onChange={this.onTextChange}
                          spellCheck="false">
                </textarea>
                <div className="post-kweet__controls">
                    <button className="post-kweet__controls--confirm btn"
                            onClick={this.onKweetConfirm}>Confirm
                    </button>
                    <button className="post-kweet__controls--cancel"
                            onClick={onKweetCancel}>Cancel
                    </button>
                    {charactersLeft === 0 && <span className="post-kweet__controls__warning">Maximum reaced, only 140 characters will be posted.</span>}
                    <span className="post-kweet__controls__characters-left">{charactersLeft}</span>
                </div>
            </div>
        );
    }
}

export default PostKweet;