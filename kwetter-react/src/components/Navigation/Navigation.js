import React from 'react';
import './Navigation.css';
import logo from '../../images/kwetter-logo.png';
import SearchBar from '../SearchBar/SearchBar';
import { Link } from 'react-router-dom';

const Navigation = ({ className, onSearch, kweetSuggestions, onSearchCancel, userLoggedIn, onLogout }) => (
    <nav className={`${className} light`}>
        <div className="nav__brand h1">
            <img src={logo}/>
            Kwetter
        </div>
        <div className="nav__search">
            <SearchBar onSearch={onSearch} suggestions={kweetSuggestions} onCancel={onSearchCancel}/>
        </div>
        {userLoggedIn ?
            <div className="nav__logged-in">
                <span>Logged in as <b>{userLoggedIn.username}</b></span>
                <Link to="/login" className="nav__login" onClick={onLogout}>Log out</Link>
            </div> :
            <div className="nav__context-menu">
                <a href="#">Registreer</a>
                <Link to="/login" className="nav__login">Log in</Link>
            </div>}
    </nav>
);

export default Navigation;