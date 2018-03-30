import React, { Component } from 'react';
import './PostKweet.css';

class PostKweet extends Component {
    constructor(props) {
        super(props);

        this.state = {
            value: '',
            charactersLeft: 140
        }
    }

    render() {
        const { text, charactersLeft } = this.state;
        const { onKweetPostCancel } = this.props;

        return (
            <div className="post-kweet">
                <textarea className="post-kweet__value"
                          placeholder="What's happening?"
                          value={text}
                          onChange={this.onTextChange}
                          autoFocus="true"
                          spellCheck="false">
                </textarea>
                <div className="post-kweet__controls">
                    <button className="post-kweet__controls--confirm btn"
                            onClick={this.onKweetConfirm}
                            disabled={charactersLeft === 140}>Confirm
                    </button>
                    <button className="post-kweet__controls--cancel"
                            onClick={onKweetPostCancel}>Cancel
                    </button>
                    {charactersLeft === 0 && <span className="post-kweet__controls__warning">
                        Maximum reached, only 140 characters will be posted.
                    </span>}
                    <span className="post-kweet__controls__characters-left">{charactersLeft}</span>
                </div>
            </div>
        );
    }

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
}

export default PostKweet;