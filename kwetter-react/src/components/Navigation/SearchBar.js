import React, { Component } from 'react';
import './SearchBar.css';
import icons from '../../icons';
import enhanceWithClickOutside from 'react-click-outside';
import { debounce } from '../../utils/debounce';

class SearchBar extends Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
        this.searchCallback = debounce(e => {
            this.props.onSearch(e.target.value);
        }, 600);
    }

    render() {
        const { onSearch, suggestions } = this.props;

        return (
            <div className="search-bar">
                <div className="nav__search">
                    <input type="text"
                           onChange={this.onSearchChange}
                           onFocus={e => onSearch(e.target.value)}
                           placeholder="Search kweets..."/>
                    <icons.search/>
                </div>
                {suggestions.length > 0 && <ul className="search-bar__list">
                    {suggestions.map((kweet, index) =>
                        <li className="search-bar__list__item" key={index}>
                            {kweet.text}
                        </li>
                    )}
                </ul>}
            </div>
        );
    }

    onSearchChange = (e) => {
        e.persist();
        this.searchCallback(e);
    };

    handleClickOutside() {
        this.props.onCancel();
    }
}

export default enhanceWithClickOutside(SearchBar);