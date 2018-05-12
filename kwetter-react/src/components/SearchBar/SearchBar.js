import React, { Component } from 'react';
import './SearchBar.css';
import icons from '../../icons/index';
import enhanceWithClickOutside from 'react-click-outside';
import { debounce, transformText } from '../../utils';

class SearchBar extends Component {
    componentDidMount() {
        this.searchCallback = debounce(value => {
            if (value.length === 0) {
                this.props.onCancel();
            } else {
                this.props.onSearch(value);
            }
        }, 600);
    }

    render() {
        const { onSearch, suggestions } = this.props;

        return (
            <div className="search-bar">
                <div className="nav__search">
                    <input type="text"
                           onChange={this.onSearchChange}
                           onKeyDown={this.onSearchKeyDown}
                           onFocus={e => onSearch(e.target.value)}
                           placeholder="Search kweets..."/>
                    <icons.search/>
                </div>
                {suggestions.length > 0 && <ul className="search-bar__list">
                    {suggestions.map((kweet, index) =>
                        <li className="search-bar__list__item" key={index}>
                            {transformText(kweet.text)}
                        </li>
                    )}
                </ul>}
            </div>
        );
    }

    onSearchChange = (e) => {
        const { value } = e.target;
        e.persist();
        this.setState({ value });
        this.searchCallback(value);
    };

    onSearchKeyDown = ({ keyCode }) => {
        if (keyCode === 13) {
            this.props.onEnter(this.state.value);
        }
    };

    handleClickOutside() {
        this.props.onCancel();
    }
}

export default enhanceWithClickOutside(SearchBar);