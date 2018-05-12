import React from 'react';
import './Navigation.css';
import logo from '../../images/kwetter-logo.png';
import SearchBar from '../SearchBar/SearchBar';
import { Link } from 'react-router-dom';

const Navigation = ({ onSearch, kweetSuggestions, onSearchEnter, onSearchCancel, isAuthenticated, userLoggedIn, onLogout }) => (
    <nav className="light">
        <div className="nav__brand h1">
            <a href={isAuthenticated ? `/profile/${userLoggedIn.username}` : '/login'}>
                <img src={logo} alt="Kwetter Logo"/>
            </a>
            Kwetter
        </div>
        <div className="nav__search">
            <SearchBar onSearch={onSearch}
                       suggestions={kweetSuggestions}
                       onEnter={onSearchEnter}
                       onCancel={onSearchCancel}/>
        </div>
        {isAuthenticated ?
            <div>
                <span className="nav__logged-in">
                    Logged in as <Link to={`/profile/${userLoggedIn.username}`}>{userLoggedIn.username}</Link>
                </span>
                <Link to="/login" className="nav__login" onClick={onLogout}>Log out</Link>
            </div> :
            <div>
                <Link to="/register">Registreer</Link>
                <Link to="/login" className="nav__login">Log in</Link>
            </div>}
    </nav>
);

export default Navigation;