import React, { Component } from 'react';
import './App.css';
import { Redirect, Route, Switch } from 'react-router-dom';
import Login from '../../pages/Login/Login';
import Register from '../../pages/Register/Register';
import Profile from '../../pages/Profile/Profile';
import Search from '../../pages/Search/Search';
import NotFound from '../../pages/NotFound/NotFound';

class App extends Component {
    render() {
        return (
            <div className="App">
                <Switch>
                    <Route path="/login" component={Login}/>
                    <Route path="/register" component={Register}/>
                    <Route path="/profile/:username/:kweetsType" component={Profile}/>
                    <Route path="/search/:searchType/:query" component={Search}/>
                    <Route path="/not-found" component={NotFound}/>
                    <Redirect to="/login"/>
                </Switch>
            </div>
        );
    }
}

export default App;
