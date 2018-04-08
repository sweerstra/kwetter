import React, { Component } from 'react';
import './App.css';
import { Redirect, Route, Switch } from 'react-router-dom';
import Login from '../../pages/Login/Login';
import Profile from '../../pages/Profile/Profile';
import NotFound from '../../pages/NotFound/NotFound';

class App extends Component {
    render() {
        return (
            <div className="App">
                <Switch>
                    <Route path="/login" component={Login}/>
                    <Route path="/profile/:username/:kweetsType" component={Profile}/>
                    <Route path="/not-found" component={NotFound}/>
                    <Redirect to="/login"/>
                </Switch>
            </div>
        );
    }
}

export default App;
